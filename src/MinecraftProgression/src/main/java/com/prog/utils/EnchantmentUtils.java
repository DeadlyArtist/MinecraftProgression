package com.prog.utils;

import net.minecraft.enchantment.*;
import net.minecraft.entity.EntityGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.registry.Registry;
import net.minecraft.village.TradeOffers;
import org.apache.commons.lang3.mutable.MutableFloat;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class EnchantmentUtils {
    public static final float specificConstraintMultiplier = 2;
    public static int MAX_ENCHANTMENT_LEVEL = 30;
    public static Set<Enchantment> BAD_ENCHANTMENTS = new HashSet<>(List.of(Enchantments.MENDING, Enchantments.UNBREAKING));

    public static float getCommonDamageMultiplier(int level) {
        if (level < 1) return 1;
        return 1 + level * 0.1F + 0.1F;
    }

    public static double getAttackDamageIncrease(EntityGroup group, ItemStack stack, double baseDamage, boolean ranged) {
        MutableFloat damage = new MutableFloat(baseDamage);

        if (ranged) {
            int powerLevel = EnchantmentHelper.getLevel(Enchantments.POWER, stack);
            damage.setValue(baseDamage * EnchantmentUtils.getCommonDamageMultiplier(powerLevel));
        }

        EnchantmentHelper.forEachEnchantment((enchantment, level) -> {
            if (enchantment instanceof DamageEnchantment damageEnchantment) {
                float multiplier = 1.0F;

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
                    // No damage multiplier for other cases
                }

                damage.setValue(damage.getValue() * multiplier);
            } else {
                damage.add(enchantment.getAttackDamage(level, group));
            }
        }, stack);

        return damage.getValue() - baseDamage;
    }

    public static double getAttackDamageIncrease(EntityGroup group, ItemStack stack, double baseDamage) {
        return getAttackDamageIncrease(group, stack, baseDamage, false);
    }

    public static EnchantmentLevelEntry getRandomEnchantmentLevelEntry(Random random, int minLevel, int maxLevel, boolean allowCursed, Set<Enchantment> excludedEnchantments) {
        int effectiveLevel = MathHelper.nextInt(random, minLevel, maxLevel);

        // Fetch all Enchantments and filter by whether they meet the min and max level criteria
        List<Enchantment> availableEnchantments = Registry.ENCHANTMENT.stream()
                .filter(enchantment -> {
                    if (!allowCursed && enchantment.isCursed()) return false;
                    if (excludedEnchantments.contains(enchantment)) return false;

                    // For treasure enchantments, treat the level requirement as doubled.
                    var multiplier = enchantment.isTreasure() ? 2 : 1;
                    int effectiveMinLevel = enchantment.getMinLevel() * multiplier;
                    int effectiveMaxLevel = EnchantmentUtils.getMaxEnchantmentLevelForAnvil(enchantment) * multiplier;

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

    public static EnchantmentLevelEntry getRandomEnchantmentLevelEntry(Random random, int level, boolean allowCursed, Set<Enchantment> excludedEnchantments) {
        return getRandomEnchantmentLevelEntry(random, level, level, allowCursed, excludedEnchantments);
    }

    public static int getMaxEnchantmentLevelForAnvil(Enchantment enchantment) {
        if (enchantment instanceof DamageEnchantment ||
                enchantment instanceof EfficiencyEnchantment ||
                enchantment instanceof ImpalingEnchantment ||
                enchantment instanceof KnockbackEnchantment ||
                enchantment instanceof LoyaltyEnchantment ||
                enchantment instanceof LuckEnchantment ||
                enchantment instanceof LureEnchantment ||
                enchantment instanceof MendingEnchantment ||
                enchantment instanceof PiercingEnchantment ||
                enchantment instanceof PowerEnchantment ||
                enchantment instanceof PunchEnchantment ||
                enchantment instanceof ProtectionEnchantment ||
                enchantment instanceof RespirationEnchantment ||
                enchantment instanceof RiptideEnchantment ||
                enchantment instanceof SoulSpeedEnchantment ||
                enchantment instanceof SweepingEnchantment ||
                enchantment instanceof SwiftSneakEnchantment ||
                enchantment instanceof ThornsEnchantment ||
                enchantment instanceof UnbreakingEnchantment
        ) return MAX_ENCHANTMENT_LEVEL;

        return enchantment.getMaxLevel();
    }
}
