package com.prog.utils;

import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.Item;

import java.util.HashSet;
import java.util.List;

public class GourmetUtils {
    public static final String GOURMET_FOOD_MODIFIER_IDENTIFIER = "gourmet_food___";

    public static String getGourmetModifierNamePrefix(String foodId) {
        return GOURMET_FOOD_MODIFIER_IDENTIFIER + foodId + "___";
    }

    public static String getGourmetModifierNamePrefix(Item food) {
        return getGourmetModifierNamePrefix(ItemUtils.getId(food).toString());
    }

    public static String getGourmetModifierName(String foodId, int effectIndex) {
        return getGourmetModifierNamePrefix(foodId) + effectIndex;
    }

    public static String getGourmetModifierName(Item food, int effectIndex) {
        return getGourmetModifierName(ItemUtils.getId(food).toString(), effectIndex);
    }
}
