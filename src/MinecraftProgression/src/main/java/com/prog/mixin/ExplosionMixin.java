package com.prog.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalFloatRef;
import com.prog.entity.PComponents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.explosion.ExplosionBehavior;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

/**
 * Mixin that modifies explosions created by Creeper entities.
 */
@Mixin(Explosion.class)
public class ExplosionMixin {

    /**
     * Modify "power" (radius) for explosions if the entity is a Creeper.
     * This doubles the explosion radius for a Creeper explosion.
     */
    @Inject(
            method = "<init>(Lnet/minecraft/world/World;Lnet/minecraft/entity/Entity;Lnet/minecraft/entity/damage/DamageSource;Lnet/minecraft/world/explosion/ExplosionBehavior;DDDFZLnet/minecraft/world/explosion/Explosion$DestructionType;)V",
            at = @At("TAIL")
    )
    private void modifyExplosionRadiusForCreeper(World world, Entity entity, DamageSource damageSource, ExplosionBehavior behavior, double x, double y, double z, float power, boolean createFire, Explosion.DestructionType destructionType, CallbackInfo ci) {
        Explosion explosion = (Explosion) (Object) this;

        if (entity instanceof CreeperEntity creeper) {
            var squad = PComponents.SQUAD.get(creeper);
            if (!squad.normal()) {
                explosion.power = (float) (power * 5 / (5 + squad.rank));
            }
        }
    }

    /**
     * Modify damage calculation for entities affected by the explosion if the explosion was caused by a Creeper.
     * This triples the damage specifically for a Creeper explosion after the radius (q) has been determined.
     */
    @Inject(
            method = "collectBlocksAndDamageEntities",
            at = @At(
                    value = "INVOKE_ASSIGN",
                    target = "Lnet/minecraft/util/math/MathHelper;floor(D)I",
                    shift = At.Shift.BEFORE,
                    ordinal = 0
            )
    )
    private void modifyDamageForCreeper(CallbackInfo ci, @Local LocalFloatRef q) {
        Explosion explosion = (Explosion) (Object) this;

        if (explosion.entity instanceof CreeperEntity creeper) {
            var squad = PComponents.SQUAD.get(creeper);
            if (!squad.normal()) {
                q.set((float) (q.get() * squad.getDamageMultiplier()));
            }
        }
    }
}