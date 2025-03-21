package com.prog.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.prog.utils.EnchantmentUtils;
import com.prog.utils.RangedUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Objects;

@Mixin(TridentEntity.class)
public abstract class TridentEntityMixin {

    @Unique
    private final TridentEntity self = (TridentEntity) (Object) this;
    @Unique
    private final TridentEntityAccessor accessor = (TridentEntityAccessor) (Object) this;

    @Inject(
            method = "<init>(Lnet/minecraft/world/World;Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/item/ItemStack;)V",
            at = @At("TAIL")
    )
    private void redirectGetAttackDamage(World world, LivingEntity owner, ItemStack stack, CallbackInfo ci) {
        var self = (TridentEntity) (Object) this;
        self.setDamage(RangedUtils.getBaseProjectileDamage(owner, stack));
    }


    @Redirect(
            method = "onEntityHit(Lnet/minecraft/util/hit/EntityHitResult;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/enchantment/EnchantmentHelper;getAttackDamage(Lnet/minecraft/item/ItemStack;Lnet/minecraft/entity/EntityGroup;)F"
            )
    )
    private float redirectGetAttackDamage(ItemStack stack, EntityGroup group, @Local float f, @Local Entity entity) {
        var self = (TridentEntity) (Object) this;
        var damage = self.getDamage();
        var base = (float) EnchantmentUtils.getAttackDamageIncrease(group, stack, damage, true);
        base *= (float) entity.random.nextTriangular(1, 0.1);
        return (float) (base + damage - f);
    }

    @Inject(method = "tick()V", at = @At("HEAD"))
    private void returnFromVoid(CallbackInfo ci) {
        if (self.getDataTracker().get(accessor.getLoyalty()) == 0 || accessor.getDealtDamage()) return;

        if (self.getY() <= self.getWorld().getBottomY()) {
            accessor.setDealtDamage(true);
            self.setVelocity(0, 0, 0);
        }
    }

    @Redirect(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/projectile/TridentEntity;dropStack(Lnet/minecraft/item/ItemStack;F)Lnet/minecraft/entity/ItemEntity;"))
    private ItemEntity redirectDropStack(TridentEntity instance, ItemStack stack, float v) {
        return null;
    }

    @Inject(method = "age", at = @At("HEAD"), cancellable = true)
    private void injectAge(CallbackInfo ci) {
        self.life++;
        if (self.life >= 20 * 20) { // 20 seconds
            self.discard();
        }
        ci.cancel();
    }

    @Inject(method = "onPlayerCollision", at = @At("HEAD"), cancellable = true)
    private void injectOnPlayerCollision(CallbackInfo ci, @Local PlayerEntity player) {
        var loyalty = self.getDataTracker().get(accessor.getLoyalty());
        if (!self.world.isClient && (self.inGround || self.isNoClip()) && self.shake <= 0) {
            if (loyalty <= 0 && self.getOwner() != null) {
                if (self.tryPickup(player)) {
                    player.sendPickup(self, 1);
                    self.discard();
                }
            } else if (loyalty > 0 && player == self.getOwner()) {
                var inventory = player.getInventory();
                for (DefaultedList<ItemStack> defaultedList : List.of(inventory.main, inventory.offHand)) {
                    for (ItemStack stack : defaultedList) {
                        if (!stack.isEmpty() && stack.hasNbt()) {
                            var nbt = stack.getNbt();
                            if (Objects.equals(nbt.getString("thrown"), self.getUuidAsString())) {
                                nbt.remove("thrown");
                            }
                        }
                    }
                }
                self.discard();
            }
        }
        ci.cancel();
    }
}