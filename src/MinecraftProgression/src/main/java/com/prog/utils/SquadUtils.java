package com.prog.utils;

import com.prog.entity.PComponents;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.util.math.random.Random;

import java.util.HashSet;
import java.util.List;

public class SquadUtils {

    public static void DropBonusLoot(MobEntity entity) {
        var squad = PComponents.SQUAD.get(entity);
        if (squad.normal()) return;

        var maxEnchantmentLevel = EnchantmentUtils.MAX_ENCHANTMENT_LEVEL;
        var minLevel = (int) Math.floor(squad.rank * 1.2);
        var maxLevel = (int) Math.ceil(squad.rank * 1.5);
        var desiredLevel = entity.random.nextBetween(minLevel, maxLevel);
        var amount = Math.max(1, desiredLevel + 1 - maxEnchantmentLevel);
        var enchantmentLevel = Math.min(maxEnchantmentLevel, desiredLevel);
        for (var i = 0; i < amount; i++) {
            var stack = EnchantedBookItem.forEnchantment(EnchantmentUtils.getRandomEnchantmentLevelEntry(entity.random, enchantmentLevel, false, EnchantmentUtils.BAD_ENCHANTMENTS));
            entity.dropStack(stack);
        }
    }
}
