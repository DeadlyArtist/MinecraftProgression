package com.prog.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.task.SonicBoomTask;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.WardenEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Optional;
import java.util.function.Consumer;

@Mixin(SonicBoomTask.class)
public class SonicBoomTaskMixin {
    @Redirect(method = "keepRunning(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/entity/mob/WardenEntity;J)V", at = @At(value = "INVOKE",
            target = "Ljava/util/Optional;ifPresent(Ljava/util/function/Consumer;)V", ordinal = 1))
    private void changeDamage(Optional instance, Consumer<LivingEntity> action, @Local ServerWorld serverWorld, @Local WardenEntity wardenEntity) {
        instance.ifPresent((s) -> {
            var target = (LivingEntity) s;
            Vec3d vec3d = wardenEntity.getPos().add(0.0, 1.6F, 0.0);
            Vec3d vec3d2 = target.getEyePos().subtract(vec3d);
            Vec3d vec3d3 = vec3d2.normalize();

            for (int i = 1; i < MathHelper.floor(vec3d2.length()) + 7; i++) {
                Vec3d vec3d4 = vec3d.add(vec3d3.multiply((double) i));
                serverWorld.spawnParticles(ParticleTypes.SONIC_BOOM, vec3d4.x, vec3d4.y, vec3d4.z, 1, 0.0, 0.0, 0.0, 0.0);
            }

            wardenEntity.playSound(SoundEvents.ENTITY_WARDEN_SONIC_BOOM, 3.0F, 1.0F);
            target.damage(DamageSource.sonicBoom(wardenEntity), (float) wardenEntity.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE) / 6);
            double d = 0.5 * (1.0 - target.getAttributeValue(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE));
            double e = 2.5 * (1.0 - target.getAttributeValue(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE));
            target.addVelocity(vec3d3.getX() * e, vec3d3.getY() * d, vec3d3.getZ() * e);
        });
    }
}
