package com.prog.utils;

import com.prog.entity.attribute.PEntityAttributes;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.TextContent;
import net.minecraft.text.TranslatableTextContent;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RangedUtils {
    public static final UUID PROJECTILE_DAMAGE_BASE_MODIFIER_ID = UUIDUtils.of("PROJECTILE_DAMAGE_BASE_MODIFIER");
    public static final UUID TREASURE_QUALITY_BASE_MODIFIER_ID = UUIDUtils.of("TREASURE_QUALITY_BASE_MODIFIER");

    public static final double BASE_BOW_RANGED_DAMAGE = 6;
    public static final double BASE_CROSSBOW_RANGED_DAMAGE = 10;
    public static final double BASE_TRIDENT_RANGED_DAMAGE = 8;
    public static final double BASE_TREASURE_QUALITY = 5;

    public static float getPullProgress(LivingEntity entity, int useTicks) {
        float f = (float) useTicks / 20.0F;
        f = (f * f + f * 2.0F) / 3.0F;
        //f *= (float) entity.getAttributeValue(PEntityAttributes.CHARGING_SPEED); // Would need to be crit speed instead, but doesn't make too much sense.
        if (f > 1.0F) {
            f = 1.0F;
        }

        return f;
    }

    public static double getBaseProjectileDamage(LivingEntity shooter, ItemStack source) {
        return shooter.getAttributeValue(PEntityAttributes.PROJECTILE_DAMAGE);
    }

    public static double getChargeDamageIncrease(Random random, double damage, double chargeModifier) {
        var chargeDamageModifier = chargeModifier / 1.5;
        if (chargeModifier == 1) chargeDamageModifier = chargeModifier * random.nextTriangular(1, 0.2);
        return damage * chargeDamageModifier;
    }

    public static double getProjectileSpeedMultiplier(LivingEntity entity) {
         return entity.getAttributeValue(PEntityAttributes.PROJECTILE_SPEED) / 4;
    }
}
