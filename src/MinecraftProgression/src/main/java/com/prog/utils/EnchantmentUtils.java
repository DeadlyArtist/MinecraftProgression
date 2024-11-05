package com.prog.utils;

import net.minecraft.enchantment.DamageEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentLevelEntry;
import net.minecraft.entity.EntityGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.registry.Registry;
import net.minecraft.village.TradeOffers;
import org.apache.commons.lang3.mutable.MutableFloat;

import java.util.List;
import java.util.stream.Collectors;

public class EnchantmentUtils {
    public static final float specificConstraintMultiplier = 2;

    public static float getCommonDamageMultiplier(int level) {
        if (level < 1) return 1;
        return 1 + level * 0.1F + 0.1F;
    }

    public static double getAttackDamageIncrease(EntityGroup group, ItemStack stack, double baseDamage) {
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

        return damage.getValue() - baseDamage;
    }

    public static EnchantmentLevelEntry getRandomEnchantmentLevelEntry(Random random, int minLevel, int maxLevel) {
        int effectiveLevel = MathHelper.nextInt(random, minLevel, maxLevel);

        // Fetch all Enchantments and filter by whether they meet the min and max level criteria
        List<Enchantment> availableEnchantments = Registry.ENCHANTMENT.stream()
                .filter(enchantment -> {
                    // For treasure enchantments, treat the level requirement as doubled.
                    var multiplier = enchantment.isTreasure() ? 2 : 1;
                    int effectiveMinLevel = enchantment.getMinLevel() * multiplier;
                    int effectiveMaxLevel = enchantment.getMaxLevel() * multiplier;

                    // Only include enchantments that have levels within the desired range
                    return effectiveMaxLevel >= effectiveLevel && effectiveMinLevel <= effectiveLevel;
                })
                .toList();

        // Get a random enchantment from the filtered list
        Enchantment selectedEnchantment = availableEnchantments.get(random.nextInt(availableEnchantments.size()));
        var selectedMultiplier = selectedEnchantment.isTreasure() ? 2 : 1;

        int actualLevel = effectiveLevel / selectedMultiplier;

        return new EnchantmentLevelEntry(selectedEnchantment, actualLevel);
    }

    public static EnchantmentLevelEntry getRandomEnchantmentLevelEntry(Random random, int level) {
        return getRandomEnchantmentLevelEntry(random, level, level);
    }
}
