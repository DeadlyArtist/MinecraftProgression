package com.prog.itemOrBlock;

import com.prog.Prog;
import com.prog.entity.attribute.XEntityAttributes;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.Item;
import net.minecraft.item.Items;

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

    static {
        // Vanilla gourmet food overrides
        register(Items.GOLDEN_APPLE, UEffect.add(EntityAttributes.GENERIC_MAX_HEALTH, 2));
        register(Items.ENCHANTED_GOLDEN_APPLE, UEffect.add(EntityAttributes.GENERIC_MAX_HEALTH, 5));
        register(Items.CHORUS_FRUIT, UEffect.add(XEntityAttributes.JUMP_HEIGHT, 0.25F));
        register(Items.POISONOUS_POTATO, UEffect.add(EntityAttributes.GENERIC_MAX_HEALTH, 2));
        register(Items.GOLDEN_CARROT, UEffect.add(EntityAttributes.GENERIC_MAX_HEALTH, 1));
        register(Items.GLISTERING_MELON_SLICE, UEffect.add(EntityAttributes.GENERIC_MAX_HEALTH, 1));
        register(Items.ROTTEN_FLESH, UEffect.add(EntityAttributes.GENERIC_MAX_HEALTH, 1));
        register(Items.SPIDER_EYE, UEffect.add(EntityAttributes.GENERIC_MAX_HEALTH, 1));
    }

    public static void register(Item item, List<UEffect> effects) {
        data.put(item, GourmetFoodData.of(effects));
    }

    public static void register(Item item, UEffect effect) {
        data.put(item, GourmetFoodData.of(List.of(effect)));
    }

    public static void init() {
        Prog.LOGGER.info("Registering Gourmet Food for: " + Prog.MOD_ID);
    }
}
