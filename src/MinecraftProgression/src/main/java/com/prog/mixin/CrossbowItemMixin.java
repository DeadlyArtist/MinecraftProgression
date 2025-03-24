package com.prog.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.prog.utils.EnchantmentUtils;
import com.prog.utils.LOGGER;
import com.prog.utils.RangedUtils;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CrossbowItem.class)
public class CrossbowItemMixin {

    @Unique
    private final CrossbowItem self = (CrossbowItem) (Object) this;

    @Redirect(
            method = "shoot",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/projectile/ProjectileEntity;setVelocity(DDDFF)V"
            )
    )
    private static void redirectSetVelocity(ProjectileEntity instance, double x, double y, double z, float speed, float divergence, @Local LivingEntity shooter) {
        instance.setVelocity(x, y, z, (float) (speed * RangedUtils.getProjectileSpeedMultiplier(shooter)), divergence);
    }

    @ModifyConstant(method = "loadProjectiles", constant = @Constant(intValue = 3, ordinal = 0))
    private static int changeMaxProjectiles(int input, @Local(ordinal = 0) int i) {
        return 1 + i*2;
    }

    @ModifyConstant(method = "getPullTime", constant = @Constant(intValue = 5, ordinal = 0))
    private static int changePullTime(int input) {
        return 0;
    }

    @Redirect(method = "shootAll", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isEmpty()Z"))
    private static boolean redirectShooting(ItemStack itemStack, @Local World world, @Local LivingEntity entity, @Local Hand hand, @Local(ordinal = 0) ItemStack stack, @Local(ordinal = 0) float speed, @Local(ordinal = 1) float divergence, @Local boolean bl, @Local int i) {
        if (!itemStack.isEmpty()) {
            var soundPitch = 1F;
            var random = entity.getRandom();
            if (i > 0) soundPitch = CrossbowItem.getSoundPitch(random.nextBoolean(), random);
            CrossbowItem.shoot(world, entity, hand, stack, itemStack, soundPitch, bl, speed, divergence, RangedUtils.getMultishotOffset(i));
        }
        return true;
    }
}
