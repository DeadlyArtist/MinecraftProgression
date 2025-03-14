package com.prog.utils;

import com.prog.entity.attribute.PEntityAttributes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.util.math.Vec3d;

public class ShieldUtils {

    public static float applyShieldToDamage(LivingEntity entity, DamageSource source, float damage) {
        var oldAmount = damage;
        var newAmount = oldAmount;
        var shield = entity.getAttributeValue(PEntityAttributes.SHIELD);
        var damageReduction = Math.floor(shield / 4);
        if (reducibleByShield(entity, source)) newAmount = (float) Math.max(0.5, oldAmount - damageReduction);
        return newAmount;
    }

    public static boolean reducibleByShield(LivingEntity entity, DamageSource source) {
        Entity sourceEntity = source.getSource();
        boolean bl = false;
        if (sourceEntity instanceof PersistentProjectileEntity persistentProjectileEntity && persistentProjectileEntity.getPierceLevel() > 0) {
            bl = true;
        } else if (!source.bypassesArmor()) bl = true;

        return false;
    }
}
