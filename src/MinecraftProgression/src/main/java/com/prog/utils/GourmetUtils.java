package com.prog.utils;

import net.minecraft.item.Item;

public class GourmetUtils {
    public static final String GOURMET_FOOD_MODIFIER_IDENTIFIER = "gourmet_food___";

    public static String getGourmetModifierNamePrefix(String foodId) {
        return GOURMET_FOOD_MODIFIER_IDENTIFIER + foodId + "___";
    }

    public static String getGourmetModifierNamePrefix(Item food) {
        return GOURMET_FOOD_MODIFIER_IDENTIFIER + ItemUtils.getId(food) + "___";
    }

    public static String getGourmetModifierName(String foodId, int effectIndex) {
        return getGourmetModifierNamePrefix(foodId) + effectIndex;
    }

    public static String getGourmetModifierName(Item food, int effectIndex) {
        return getGourmetModifierName(ItemUtils.getId(food).toString(), effectIndex);
    }
}
