package com.prog.itemOrBlock;

import com.prog.Prog;
import com.prog.utils.LOGGER;
import net.minecraft.item.Item;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class PItemTags {
    public static final TagKey<Item> UPGRADABLE = createTag("UPGRADABLE");
    public static final TagKey<Item> TIER_CORE = createTag("TIER_CORE");
    public static final TagKey<Item> UPGRADE = createTag("UPGRADE");
    public static final TagKey<Item> GOURMET_FOOD = createTag("GOURMET_FOOD");

    // Tiers
    public static final TagKey<Item> STEEL = createTag("STEEL");
    public static final TagKey<Item> ULTIMATE_DIAMOND = createTag("ULTIMATE_DIAMOND");
    public static final TagKey<Item> REFINED_OBSIDIAN = createTag("REFINED_OBSIDIAN");
    public static final TagKey<Item> TITAN = createTag("TITAN");
    public static final TagKey<Item> PRIMAL_NETHERITE = createTag("PRIMAL_NETHERITE");
    public static final TagKey<Item> VERUM = createTag("VERUM");

    public static final TagKey<Item> STEEL_OR_HIGHER = createTag("STEEL_OR_HIGHER");
    public static final TagKey<Item> ULTIMATE_DIAMOND_OR_HIGHER = createTag("ULTIMATE_DIAMOND_OR_HIGHER");
    public static final TagKey<Item> REFINED_OBSIDIAN_OR_HIGHER = createTag("REFINED_OBSIDIAN_OR_HIGHER");
    public static final TagKey<Item> TITAN_OR_HIGHER = createTag("TITAN_OR_HIGHER");
    public static final TagKey<Item> PRIMAL_NETHERITE_OR_HIGHER = createTag("PRIMAL_NETHERITE_OR_HIGHER");
    public static final TagKey<Item> VERUM_OR_HIGHER = createTag("VERUM_OR_HIGHER");

    private static TagKey<Item> createTag(String id) {
        return TagKey.of(Registry.ITEM_KEY, new Identifier(Prog.MOD_ID, id.toLowerCase()));
    }

    private static TagKey<Item> createExternalTag(String namespace, String id) {
        return TagKey.of(Registry.ITEM_KEY, new Identifier(namespace, id.toLowerCase()));
    }

    public static void init() {
        LOGGER.info("Registering Item Tags for: " + Prog.MOD_ID);
    }
}
