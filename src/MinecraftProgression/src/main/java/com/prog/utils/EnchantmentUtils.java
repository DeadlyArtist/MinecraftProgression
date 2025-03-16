package com.prog.utils;

import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalIntRef;
import net.minecraft.enchantment.*;
import net.minecraft.entity.EntityGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.registry.Registry;
import net.minecraft.village.TradeOffers;
import org.apache.commons.lang3.mutable.MutableFloat;
import org.apache.commons.lang3.mutable.MutableInt;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class EnchantmentUtils {
    public static final float specificConstraintMultiplier = 2;
    public static int MAX_ENCHANTMENT_LEVEL = 30;
    public static Set<Enchantment> BAD_ENCHANTMENTS = new HashSet<>(List.of(Enchantments.MENDING, Enchantments.UNBREAKING));
    public static int FALL_PROTECTION_MULTIPLIER = 2;

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

        MutableInt baseLevel = new MutableInt(0);
        MutableInt specificLevel = new MutableInt(0);
        EnchantmentHelper.forEachEnchantment((enchantment, level) -> {
            if (enchantment instanceof DamageEnchantment damageEnchantment) {
                switch (damageEnchantment.typeIndex) {
                    case 0 -> baseLevel.setValue(level);
                    case 1 -> {
                        if (group == EntityGroup.UNDEAD) {
                            specificLevel.setValue(level);
                        }
                    }
                    case 2 -> {
                        if (group == EntityGroup.ARTHROPOD) {
                            specificLevel.setValue(level);
                        }
                    }
                    // No damage multiplier for other cases
                }
            } else {
                damage.add(enchantment.getAttackDamage(level, group));
            }
        }, stack);

        var multiplier = getCommonDamageMultiplier(baseLevel.getValue());
        if (specificLevel.getValue() > 0)
            multiplier += getCommonDamageMultiplier(specificLevel.getValue()) * specificConstraintMultiplier;
        damage.setValue(damage.getValue() * multiplier);

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

    public static int getAnvilEnchantmentCost(int oldEnchantmentLevel, int newEnchantmentLevel, ItemStack stack, Enchantment enchantment) {
        var levelDifference = newEnchantmentLevel - oldEnchantmentLevel;
        var scaling = 2; // Scales higher than monster xp drops, as looting enchantment increases xp gained (maybe???)
        var newCost = (int) Math.pow(scaling, newEnchantmentLevel) - (int) Math.pow(scaling, oldEnchantmentLevel);
        if (oldEnchantmentLevel == 0) newCost += getAnvilEnchantmentBonusCost(enchantment);

        if (stack.isOf(Items.ENCHANTED_BOOK)) newCost = newEnchantmentLevel - oldEnchantmentLevel;
        return newCost;
    }

    public static int getAnvilEnchantmentBonusCost(Enchantment enchantment) {
        var rarity = enchantment.getRarity();
        var cost = 1;
        if (rarity == Enchantment.Rarity.UNCOMMON) cost = 2;
        if (rarity == Enchantment.Rarity.RARE) cost = 5;
        if (rarity == Enchantment.Rarity.VERY_RARE) cost = 10;

        return cost;
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
