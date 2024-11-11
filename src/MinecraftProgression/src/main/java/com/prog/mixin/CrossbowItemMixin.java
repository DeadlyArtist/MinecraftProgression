package com.prog.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.prog.entity.attribute.PEntityAttributes;
import com.prog.mixinInterfaces.IPersistentProjectileEntityMixin;
import com.prog.utils.RangedUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CrossbowItem.class)
public class CrossbowItemMixin {
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
}
