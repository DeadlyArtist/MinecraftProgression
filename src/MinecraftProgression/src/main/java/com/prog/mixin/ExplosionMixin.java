package com.prog.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalFloatRef;
import com.prog.entity.PComponents;
import com.prog.utils.DragonUtils;
import com.prog.utils.WitherUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.projectile.WitherSkullEntity;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.explosion.ExplosionBehavior;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Explosion.class)
public class ExplosionMixin {

    @Inject(
            method = "<init>(Lnet/minecraft/world/World;Lnet/minecraft/entity/Entity;Lnet/minecraft/entity/damage/DamageSource;Lnet/minecraft/world/explosion/ExplosionBehavior;DDDFZLnet/minecraft/world/explosion/Explosion$DestructionType;)V",
            at = @At("TAIL")
    )
    private void modifyExplosionRadius(World world, Entity entity, DamageSource damageSource, ExplosionBehavior behavior, double x, double y, double z, float power, boolean createFire, Explosion.DestructionType destructionType, CallbackInfo ci) {
        Explosion explosion = (Explosion) (Object) this;

        if (entity instanceof LivingEntity living) {
            var squad = PComponents.SQUAD.get(living);
            if (!squad.normal()) {
                explosion.power = (float) (power * 5 / (5 + squad.rank));
            }
        }
    }

    @Inject(
            method = "collectBlocksAndDamageEntities",
            at = @At(
                    value = "INVOKE_ASSIGN",
                    target = "Lnet/minecraft/util/math/MathHelper;floor(D)I",
                    shift = At.Shift.BEFORE,
                    ordinal = 0
            )
    )
    private void modifyDamage(CallbackInfo ci, @Local LocalFloatRef q) {
        Explosion explosion = (Explosion) (Object) this;

        if (explosion.entity instanceof LivingEntity living) {
            var squad = PComponents.SQUAD.get(living);
            if (!squad.normal()) {
                q.set((float) (q.get() * squad.getDamageMultiplier()));
            }
        }

        if (explosion.entity instanceof EnderDragonEntity) {
            q.set((float) (q.get() * DragonUtils.FIREBALL_EXPLOSION_DAMAGE_MULTIPLIER));
        } else if (explosion.entity instanceof WitherEntity) {
            q.set((float) (q.get() * WitherUtils.EXPLOSION_DAMAGE_MULTIPLIER));
        } else if (explosion.entity instanceof WitherSkullEntity) {
            q.set((float) (q.get() * WitherUtils.EXPLOSION_DAMAGE_MULTIPLIER));
        }
    }
}