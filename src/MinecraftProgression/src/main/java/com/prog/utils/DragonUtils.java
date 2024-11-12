package com.prog.utils;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.boss.dragon.phase.PhaseType;
import net.minecraft.entity.projectile.DragonFireballEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.WorldEvents;

public class DragonUtils {

    public static int DRAGON_MAX_HEALTH = 1000;
    public static int DRAGON_ATTACK_DAMAGE = 40;
    public static int HIGHEST_PHASE = 3;
    public static int MAXIMUM_FIREBALL_ANGLE = 70;

    public static int getPhase(EnderDragonEntity dragon) {
        if (dragon.getHealth() / dragon.getMaxHealth() < 0.2) return 3;
        if (dragon.getHealth() / dragon.getMaxHealth() < 0.5) return 2;
        return 1;
    }

    public static int getPhase(DragonFireballEntity entity) {
        var owner = entity.getOwner();
        if (!(owner instanceof EnderDragonEntity dragon)) return 1;
        return getPhase(dragon);
    }

    public static int getMultiplier(EnderDragonEntity dragon) {
        return (int) Math.pow(2, getPhase(dragon) - 1);
    }

    public static int getMultiplier(DragonFireballEntity entity) {
        return (int) Math.pow(2, getPhase(entity) - 1);
    }

    public static DragonFireballEntity tryShootFireball(EnderDragonEntity dragon, LivingEntity target) {
        if (target == null || !dragon.canSee(target)) return null;

        Vec3d vec3d = new Vec3d(target.getX() - dragon.getX(), 0.0, target.getZ() - dragon.getZ()).normalize();
        Vec3d vec3d2 = new Vec3d(
                (double) MathHelper.sin(dragon.getYaw() * (float) (Math.PI / 180.0)),
                0.0,
                (double) (-MathHelper.cos(dragon.getYaw() * (float) (Math.PI / 180.0)))
        )
                .normalize();
        float j = (float) vec3d2.dotProduct(vec3d);
        float k = (float) (Math.acos((double) j) * 180.0F / (float) Math.PI);
        k += 0.5F;
        if (k < 0.0F || k >= MAXIMUM_FIREBALL_ANGLE) return null;

        double h = 1.0;
        Vec3d vec3d3 = dragon.getRotationVec(1.0F);
        double l = dragon.head.getX() - vec3d3.x * 1.0;
        double m = dragon.head.getBodyY(0.5) + 0.5;
        double n = dragon.head.getZ() - vec3d3.z * 1.0;
        double o = target.getX() - l;
        double p = target.getBodyY(0.5) - m;
        double q = target.getZ() - n;
        if (!dragon.isSilent()) {
            dragon.world.syncWorldEvent(null, WorldEvents.ENDER_DRAGON_SHOOTS, dragon.getBlockPos(), 0);
        }

        DragonFireballEntity dragonFireballEntity = new DragonFireballEntity(dragon.world, dragon, o, p, q);
        dragonFireballEntity.refreshPositionAndAngles(l, m, n, 0.0F, 0.0F);
        dragon.world.spawnEntity(dragonFireballEntity);
        return dragonFireballEntity;
    }
}
