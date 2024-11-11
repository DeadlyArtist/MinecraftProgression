package com.prog.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.prog.entity.attribute.PEntityAttributes;
import com.prog.utils.RangedUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.TridentItem;
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
    private void redirectSetVelocity(TridentEntity instance, Entity entity, float pitch, float yaw, float roll, float speed, float divergence) {
        var player = (PlayerEntity) entity;
        instance.setVelocity(player, pitch, yaw, roll, (float) (speed * RangedUtils.getProjectileSpeedMultiplier(player)), divergence);
    }
}
