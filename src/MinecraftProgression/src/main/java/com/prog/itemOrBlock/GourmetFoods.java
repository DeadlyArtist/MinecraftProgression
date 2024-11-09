package com.prog.itemOrBlock;

import com.github.teamfusion.rottencreatures.common.registries.RCItems;
import com.prog.Prog;
import com.prog.XIDs;
import com.prog.utils.LOGGER;
import com.prog.utils.XCompat;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.purejosh.froglegs.init.FroglegsModItems;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        // Vanilla overrides
        register(Items.GOLDEN_APPLE, UEffect.add(EntityAttributes.GENERIC_MAX_HEALTH, 4));
        register(Items.ENCHANTED_GOLDEN_APPLE, UEffect.add(EntityAttributes.GENERIC_MAX_HEALTH, 10));
        register(Items.CHORUS_FRUIT, UEffect.add(EntityAttributes.GENERIC_MAX_HEALTH, 3));
        register(Items.POISONOUS_POTATO, UEffect.add(EntityAttributes.GENERIC_MAX_HEALTH, 2));
        register(Items.GOLDEN_CARROT, UEffect.add(EntityAttributes.GENERIC_MAX_HEALTH, 1));
        register(Items.GLISTERING_MELON_SLICE, UEffect.add(EntityAttributes.GENERIC_MAX_HEALTH, 1));
        register(Items.ROTTEN_FLESH, UEffect.add(EntityAttributes.GENERIC_MAX_HEALTH, 1));
        register(Items.SPIDER_EYE, UEffect.add(EntityAttributes.GENERIC_MAX_HEALTH, 1));
        register(Items.PUFFERFISH, UEffect.add(EntityAttributes.GENERIC_MAX_HEALTH, 1));
        register(Items.COOKED_CHICKEN, UEffect.add(EntityAttributes.GENERIC_MAX_HEALTH, 1));
        register(Items.COOKED_BEEF, UEffect.add(EntityAttributes.GENERIC_MAX_HEALTH, 1));
        register(Items.COOKED_COD, UEffect.add(EntityAttributes.GENERIC_MAX_HEALTH, 1));
        register(Items.COOKED_MUTTON, UEffect.add(EntityAttributes.GENERIC_MAX_HEALTH, 1));
        register(Items.COOKED_RABBIT, UEffect.add(EntityAttributes.GENERIC_MAX_HEALTH, 1));
        register(Items.COOKED_PORKCHOP, UEffect.add(EntityAttributes.GENERIC_MAX_HEALTH, 1));
        register(Items.COOKED_SALMON, UEffect.add(EntityAttributes.GENERIC_MAX_HEALTH, 1));
        register(Items.BEETROOT_SOUP, UEffect.add(EntityAttributes.GENERIC_MAX_HEALTH, 1));
        register(Items.MUSHROOM_STEW, UEffect.add(EntityAttributes.GENERIC_MAX_HEALTH, 1));
        register(Items.SUSPICIOUS_STEW, UEffect.add(EntityAttributes.GENERIC_MAX_HEALTH, 1));
        register(Items.RABBIT_STEW, UEffect.add(EntityAttributes.GENERIC_MAX_HEALTH, 1));
        register(Items.HONEY_BOTTLE, UEffect.add(EntityAttributes.GENERIC_MAX_HEALTH, 1));
        register(Items.POTION, UEffect.add(EntityAttributes.GENERIC_MAX_HEALTH, 1));
        register(Items.COOKIE, UEffect.add(EntityAttributes.GENERIC_MAX_HEALTH, 1));
        register(Items.BAKED_POTATO, UEffect.add(EntityAttributes.GENERIC_MAX_HEALTH, 1));
        register(Items.BREAD, UEffect.add(EntityAttributes.GENERIC_MAX_HEALTH, 1));
        register(Items.APPLE, UEffect.add(EntityAttributes.GENERIC_MAX_HEALTH, 1));
        register(Items.PUMPKIN_PIE, UEffect.add(EntityAttributes.GENERIC_MAX_HEALTH, 1));
        register(Items.CAKE, UEffect.add(EntityAttributes.GENERIC_MAX_HEALTH, 1));
        register(Items.MILK_BUCKET, UEffect.add(EntityAttributes.GENERIC_MAX_HEALTH, 1));
        register(Items.NETHER_WART, UEffect.add(EntityAttributes.GENERIC_MAX_HEALTH, 3));
        register(Items.TURTLE_EGG, UEffect.add(EntityAttributes.GENERIC_MAX_HEALTH, 1));
        register(Items.GLOW_BERRIES, UEffect.add(EntityAttributes.GENERIC_MAX_HEALTH, 1));
        register(Items.DRIED_KELP, UEffect.add(EntityAttributes.GENERIC_MAX_HEALTH, 1));
        register(Items.SLIME_BALL, UEffect.add(EntityAttributes.GENERIC_MAX_HEALTH, 1));
        //register(Items.SEA_PICKLE, UEffect.add(EntityAttributes.GENERIC_MAX_HEALTH, 1)); // doesn't work because it glitches, as it can also be placed
        register(Items.MAGMA_CREAM, UEffect.add(EntityAttributes.GENERIC_MAX_HEALTH, 1));

        // Compat
        if (XCompat.isModLoaded(XIDs.FROG_LEGS)) {
            register(FroglegsModItems.COOKED_FROG_LEG, UEffect.add(EntityAttributes.GENERIC_MAX_HEALTH, 1));
        }
        if (XCompat.isModLoaded(XIDs.ROTTEN_CREATURES)) {
            register(RCItems.MAGMA_ROTTEN_FLESH.get(), UEffect.add(EntityAttributes.GENERIC_MAX_HEALTH, 1));
            register(RCItems.CORRUPTED_WART.get(), UEffect.add(EntityAttributes.GENERIC_MAX_HEALTH, 2));
        }

        // Custom
        register(PItems.STAR_APPLE, UEffect.add(EntityAttributes.GENERIC_MAX_HEALTH, 10));
        register(PItems.ENCHANTED_STAR_APPLE, UEffect.add(EntityAttributes.GENERIC_MAX_HEALTH, 20));
        register(PItems.SILENT_HEART, UEffect.add(EntityAttributes.GENERIC_MAX_HEALTH, 5));
        register(PItems.COSMIC_SOUP, UEffect.add(EntityAttributes.GENERIC_MAX_HEALTH, 5));
    }

    public static void register(Item item, List<UEffect> effects) {
        data.put(item, GourmetFoodData.of(effects));
    }

    public static void register(Item item, UEffect effect) {
        data.put(item, GourmetFoodData.of(List.of(effect)));
    }

    public static void init() {
        LOGGER.info("Registering Gourmet Food for: " + Prog.MOD_ID);
    }
}
