package com.prog.itemOrBlock;

import net.minecraft.item.Item;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class GourmetFoods {
    public static class GourmetFoodData {
        public final List<UEffect> effects;

        public GourmetFoodData(List<UEffect> effects) {
            this.effects = effects;
        }

        public static GourmetFoodData of(List<UEffect> effects) {
            return new GourmetFoodData(effects);
        }
    }

    public static final Map<Item, GourmetFoodData> data = new HashMap<>();

    public static void register(Item item, List<UEffect> effects) {
        data.put(item, GourmetFoodData.of(effects));
    }
}
