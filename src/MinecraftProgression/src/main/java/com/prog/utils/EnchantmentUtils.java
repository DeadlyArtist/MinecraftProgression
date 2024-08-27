package com.prog.utils;

import net.minecraft.enchantment.DamageEnchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityGroup;
import net.minecraft.item.ItemStack;
import org.apache.commons.lang3.mutable.MutableFloat;

public class EnchantmentUtils {
    public static final float specificConstraintMultiplier = 2;

    public static float getCommonDamageMultiplier(int level) {
        return 1 + level * 0.1F + 0.1F;
    }

    public static double getAttackDamage(EntityGroup group, ItemStack stack, double baseDamage) {
        MutableFloat damage = new MutableFloat(baseDamage);

        EnchantmentHelper.forEachEnchantment((enchantment, level) -> {
            if (enchantment instanceof DamageEnchantment damageEnchantment) {
                float multiplier = 0.0F;

                switch (damageEnchantment.typeIndex) {
                    case 0 -> multiplier = getCommonDamageMultiplier(level);
                    case 1 -> {
                        if (group == EntityGroup.UNDEAD) {
                            multiplier = getCommonDamageMultiplier(level) * specificConstraintMultiplier;
                        }
                    }
                    case 2 -> {
                        if (group == EntityGroup.ARTHROPOD) {
                            multiplier = getCommonDamageMultiplier(level) * specificConstraintMultiplier;
                        }
                    }
                    default -> multiplier = 0.0F; // No damage multiplier for other cases
                }

                damage.setValue(damage.getValue() * multiplier);
            } else {
                damage.add(enchantment.getAttackDamage(level, group));
            }
        }, stack);

        return damage.getValue();
    }
}
