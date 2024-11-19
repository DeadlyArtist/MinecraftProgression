package com.prog.itemOrBlock;

import com.github.teamfusion.rottencreatures.common.registries.RCItems;
import com.prog.Prog;
import com.prog.XIDs;
import com.prog.utils.ItemUtils;
import com.prog.utils.LOGGER;
import com.prog.utils.XCompat;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.purejosh.froglegs.init.FroglegsModItems;

import java.util.ArrayList;
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
    public static final List<Item> compatData = new ArrayList<>();

    static {
        // Vanilla overrides
        register(Items.GOLDEN_APPLE, UEffect.increment(EntityAttributes.GENERIC_MAX_HEALTH, 4));
        register(Items.ENCHANTED_GOLDEN_APPLE, UEffect.increment(EntityAttributes.GENERIC_MAX_HEALTH, 10));
        register(Items.CHORUS_FRUIT, UEffect.increment(EntityAttributes.GENERIC_MAX_HEALTH, 3));
        register(Items.POISONOUS_POTATO, UEffect.increment(EntityAttributes.GENERIC_MAX_HEALTH, 2));
        register(Items.GOLDEN_CARROT, UEffect.increment(EntityAttributes.GENERIC_MAX_HEALTH, 1));
        register(Items.GLISTERING_MELON_SLICE, UEffect.increment(EntityAttributes.GENERIC_MAX_HEALTH, 1));
        register(Items.ROTTEN_FLESH, UEffect.increment(EntityAttributes.GENERIC_MAX_HEALTH, 1));
        register(Items.SPIDER_EYE, UEffect.increment(EntityAttributes.GENERIC_MAX_HEALTH, 1));
        register(Items.PUFFERFISH, UEffect.increment(EntityAttributes.GENERIC_MAX_HEALTH, 1));
        register(Items.COOKED_CHICKEN, UEffect.increment(EntityAttributes.GENERIC_MAX_HEALTH, 1));
        register(Items.COOKED_BEEF, UEffect.increment(EntityAttributes.GENERIC_MAX_HEALTH, 1));
        register(Items.COOKED_COD, UEffect.increment(EntityAttributes.GENERIC_MAX_HEALTH, 1));
        register(Items.COOKED_MUTTON, UEffect.increment(EntityAttributes.GENERIC_MAX_HEALTH, 1));
        register(Items.COOKED_RABBIT, UEffect.increment(EntityAttributes.GENERIC_MAX_HEALTH, 1));
        register(Items.COOKED_PORKCHOP, UEffect.increment(EntityAttributes.GENERIC_MAX_HEALTH, 1));
        register(Items.COOKED_SALMON, UEffect.increment(EntityAttributes.GENERIC_MAX_HEALTH, 1));
        register(Items.BEETROOT_SOUP, UEffect.increment(EntityAttributes.GENERIC_MAX_HEALTH, 1));
        register(Items.MUSHROOM_STEW, UEffect.increment(EntityAttributes.GENERIC_MAX_HEALTH, 1));
        register(Items.SUSPICIOUS_STEW, UEffect.increment(EntityAttributes.GENERIC_MAX_HEALTH, 1));
        register(Items.RABBIT_STEW, UEffect.increment(EntityAttributes.GENERIC_MAX_HEALTH, 1));
        register(Items.HONEY_BOTTLE, UEffect.increment(EntityAttributes.GENERIC_MAX_HEALTH, 1));
        register(Items.COOKIE, UEffect.increment(EntityAttributes.GENERIC_MAX_HEALTH, 1));
        register(Items.BAKED_POTATO, UEffect.increment(EntityAttributes.GENERIC_MAX_HEALTH, 1));
        register(Items.BREAD, UEffect.increment(EntityAttributes.GENERIC_MAX_HEALTH, 1));
        register(Items.APPLE, UEffect.increment(EntityAttributes.GENERIC_MAX_HEALTH, 1));
        register(Items.PUMPKIN_PIE, UEffect.increment(EntityAttributes.GENERIC_MAX_HEALTH, 1));
        register(Items.CAKE, UEffect.increment(EntityAttributes.GENERIC_MAX_HEALTH, 1));
        register(Items.MILK_BUCKET, UEffect.increment(EntityAttributes.GENERIC_MAX_HEALTH, 1));
        register(Items.NETHER_WART, UEffect.increment(EntityAttributes.GENERIC_MAX_HEALTH, 3));
        register(Items.TURTLE_EGG, UEffect.increment(EntityAttributes.GENERIC_MAX_HEALTH, 1));
        register(Items.GLOW_BERRIES, UEffect.increment(EntityAttributes.GENERIC_MAX_HEALTH, 1));
        register(Items.DRIED_KELP, UEffect.increment(EntityAttributes.GENERIC_MAX_HEALTH, 1));
        register(Items.SLIME_BALL, UEffect.increment(EntityAttributes.GENERIC_MAX_HEALTH, 1));
        //register(Items.SEA_PICKLE, UEffect.add(EntityAttributes.GENERIC_MAX_HEALTH, 1)); // doesn't work because it glitches, as it can also be placed
        register(Items.MAGMA_CREAM, UEffect.increment(EntityAttributes.GENERIC_MAX_HEALTH, 1));

        // Custom
        register(PItems.STAR_APPLE, UEffect.increment(EntityAttributes.GENERIC_MAX_HEALTH, 10));
        register(PItems.ENCHANTED_STAR_APPLE, UEffect.increment(EntityAttributes.GENERIC_MAX_HEALTH, 20));
        register(PItems.SILENT_HEART, UEffect.increment(EntityAttributes.GENERIC_MAX_HEALTH, 5));
        register(PItems.COSMIC_SOUP, UEffect.increment(EntityAttributes.GENERIC_MAX_HEALTH, 5));
    }

    // Combat. Separated so we can wait for other mods to finish registering
    public static void registerAllCompat() {
        if (!compatData.isEmpty()) return;

        if (XCompat.isModLoaded(XIDs.FROG_LEGS)) {
            registerCompat(FroglegsModItems.COOKED_FROG_LEG, UEffect.increment(EntityAttributes.GENERIC_MAX_HEALTH, 1));
        }
        if (XCompat.isModLoaded(XIDs.ROTTEN_CREATURES)) {
            registerCompat(RCItems.MAGMA_ROTTEN_FLESH.get(), UEffect.increment(EntityAttributes.GENERIC_MAX_HEALTH, 1));
            registerCompat(RCItems.CORRUPTED_WART.get(), UEffect.increment(EntityAttributes.GENERIC_MAX_HEALTH, 2));
        }
        if (XCompat.isModLoaded(XIDs.ECOLOGICS)) {
            registerCompat(ItemUtils.byId(XIDs.ECOLOGICS, "prickly_pear"), UEffect.increment(EntityAttributes.GENERIC_MAX_HEALTH, 1));
            registerCompat(ItemUtils.byId(XIDs.ECOLOGICS, "tropical_stew"), UEffect.increment(EntityAttributes.GENERIC_MAX_HEALTH, 1));
            registerCompat(ItemUtils.byId(XIDs.ECOLOGICS, "walnut"), UEffect.increment(EntityAttributes.GENERIC_MAX_HEALTH, 1));
            registerCompat(ItemUtils.byId(XIDs.ECOLOGICS, "crab_meat"), UEffect.increment(EntityAttributes.GENERIC_MAX_HEALTH, 1));
        }
        if (XCompat.isModLoaded(XIDs.SUPPLEMENTARIES)) {
            //registerCompat(ItemUtils.byId(XIDs.SUPPLEMENTARIES, "pancake"), UEffect.increment(EntityAttributes.GENERIC_MAX_HEALTH, 1));
            registerCompat(ItemUtils.byId(XIDs.SUPPLEMENTARIES, "candy"), UEffect.increment(EntityAttributes.GENERIC_MAX_HEALTH, 1));
        }
    }

    public static void registerCompat(Item item, List<UEffect> effects) {
        register(item, effects);
        compatData.add(item);
    }

    public static void registerCompat(Item item, UEffect effect) {
        registerCompat(item, List.of(effect));
    }

    public static void register(Item item, List<UEffect> effects) {
        data.put(item, GourmetFoodData.of(effects));
    }

    public static void register(Item item, UEffect effect) {
        register(item, List.of(effect));
    }

    public static void init() {
        LOGGER.info("Registering Gourmet Food for: " + Prog.MOD_ID);
    }
}
