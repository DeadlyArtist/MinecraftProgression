package com.prog.utils;

import com.prog.entity.PComponents;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.EnchantedBookItem;

import java.util.HashSet;
import java.util.List;

public class SquadUtils {

    public static void DropBonusLoot(MobEntity entity) {
        var squad = PComponents.SQUAD.get(entity);
        if (squad.normal()) return;

        var maxEnchantmentLevel = EnchantmentUtils.MAX_ENCHANTMENT_LEVEL;
        var amount = Math.max(1, squad.rank + 1 - maxEnchantmentLevel);
        var enchantmentLevel = Math.min(maxEnchantmentLevel, squad.rank);
        var excludedEnchantments = new HashSet<>(List.of(Enchantments.MENDING, Enchantments.UNBREAKING));
        for (var i = 0; i < amount; i++) {
            var stack = EnchantedBookItem.forEnchantment(EnchantmentUtils.getRandomEnchantmentLevelEntry(entity.random, enchantmentLevel, false, excludedEnchantments));
            entity.dropStack(stack);
        }
    }
}
