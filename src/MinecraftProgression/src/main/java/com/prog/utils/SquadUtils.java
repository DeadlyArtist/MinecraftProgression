package com.prog.utils;

import com.prog.entity.PComponents;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.EnchantedBookItem;

public class SquadUtils {

    public static double SCALING_BASE = 1.6;

    public static void DropBonusLoot(MobEntity entity) {
        var squad = PComponents.SQUAD.get(entity);
        if (squad.normal()) return;

        // https://deadlyartist.github.io/aidevsuite/#extern?url=data/Live%20Calculator.json&mode=run
        // var rank = 8;
        // var scaling = 1.15;
        // var min = Math.pow(rank - 1, scaling) + 1;
        // var max = Math.pow(rank, scaling);
        // [min, max, Math.round(min), Math.round(max)].join("    ")
        var maxEnchantmentLevel = EnchantmentUtils.MAX_ENCHANTMENT_LEVEL;
        var desiredLevel = squad.rank;
        var amount = Math.max(1, desiredLevel + 1 - maxEnchantmentLevel);
        var enchantmentLevel = Math.min(maxEnchantmentLevel, desiredLevel);
        for (var i = 0; i < amount; i++) {
            var stack = EnchantedBookItem.forEnchantment(EnchantmentUtils.getRandomEnchantmentLevelEntry(entity.random, enchantmentLevel, false, EnchantmentUtils.BAD_ENCHANTMENTS));
            entity.dropStack(stack);
        }
    }

    public static int adjustXPDrop(MobEntity mob, int experience) {
        var squad = PComponents.SQUAD.get(mob);
        if (!squad.normal()) experience *= (int) Math.pow(1.7, squad.rank) * 2;
        return experience;
    }
}
