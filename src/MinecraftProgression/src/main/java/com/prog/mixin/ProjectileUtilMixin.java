package com.prog.mixin;

import com.prog.mixinInterfaces.IPersistentProjectileEntityMixin;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ProjectileUtil.class)
public class ProjectileUtilMixin {
    @Redirect(method = "createArrowProjectile", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/projectile/PersistentProjectileEntity;applyEnchantmentEffects(Lnet/minecraft/entity/LivingEntity;F)V"))
    private static void createArrowProjectile(PersistentProjectileEntity persistentProjectileEntity, LivingEntity entity, float damageModifier) {
        var projectileMixin = (IPersistentProjectileEntityMixin) (Object) persistentProjectileEntity;
        projectileMixin.setChargeModifier(damageModifier);
    }
}
