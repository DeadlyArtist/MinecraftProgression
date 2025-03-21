package com.prog.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.prog.entity.FlexibleTridentEntity;
import com.prog.entity.PEntityTypes;
import com.prog.itemOrBlock.custom.TieredTridentItem;
import com.prog.utils.RangedUtils;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.TridentItem;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.nbt.NbtInt;
import net.minecraft.nbt.NbtString;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TridentItem.class)
public class TridentItemMixin {
    @Redirect(
            method = "onStoppedUsing",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/projectile/TridentEntity;setVelocity(Lnet/minecraft/entity/Entity;FFFFF)V"
            )
    )
    private void redirectSetVelocity(TridentEntity instance, Entity entity, float pitch, float yaw, float roll, float speed, float divergence, @Local ItemStack stack) {
        var player = (PlayerEntity) entity;
        var loyalty = EnchantmentHelper.getLoyalty(stack);
        if (loyalty > 0) {
            stack.setSubNbt("thrown", NbtHelper.fromUuid(instance.getUuid()));
            stack.setSubNbt("thrown_ticks", NbtInt.of(0));
        }
        instance.setVelocity(player, pitch, yaw, roll, (float) (speed * RangedUtils.getProjectileSpeedMultiplier(player)), divergence);
    }

    @Redirect(
            method = "onStoppedUsing",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/PlayerInventory;removeOne(Lnet/minecraft/item/ItemStack;)V"
            )
    )
    private void redirectRemoveTrident(PlayerInventory instance, ItemStack stack) {
        var loyalty = EnchantmentHelper.getLoyalty(stack);
        if (loyalty <= 0) {
            instance.removeOne(stack);
        }
    }

    @Redirect(
            method = "onStoppedUsing",
            at = @At(
                    value = "NEW",
                    target = "net/minecraft/entity/projectile/TridentEntity",
                    ordinal = 0
            )
    )
    private TridentEntity redirectTridentEntity(World world, LivingEntity owner, ItemStack stack) {
        if (stack.getItem() instanceof TieredTridentItem trident) return new FlexibleTridentEntity(PEntityTypes.getFlexibleTridentEntityType(trident), world, owner, stack);

        return new TridentEntity(world, owner, stack);
    }

    @Inject(method = "use", at = @At("HEAD"), cancellable = true)
    public void use(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> cir) {
        ItemStack itemStack = user.getStackInHand(hand);
        var result = TypedActionResult.fail(itemStack);
        if (itemStack.getDamage() >= itemStack.getMaxDamage() - 1 && itemStack.isDamageable()) {
            // fail
        } else if (EnchantmentHelper.getRiptide(itemStack) > 0 && !user.isTouchingWaterOrRain()) {
            // fail
        } else if (itemStack.hasNbt() && itemStack.getNbt().contains("thrown")) {
            // fail
        } else {
            user.setCurrentHand(hand);
            result = TypedActionResult.consume(itemStack);
        }

        cir.setReturnValue(result);
    }
}
