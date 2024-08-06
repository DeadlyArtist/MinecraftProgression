package com.prog.itemOrBlock;

import com.prog.Prog;
import net.minecraft.item.Item;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class PItemTags {
    public static final TagKey<Item> UPGRADE = createTag("UPGRADE");
    public static final TagKey<Item> GOURMET_FOOD = createTag("GOURMET_FOOD");
    public static final TagKey<Item> UPGRADABLE = createTag("UPGRADABLE");

    private static TagKey<Item> createTag(String id) {
        return TagKey.of(Registry.ITEM_KEY, new Identifier(Prog.MOD_ID, id.toLowerCase()));
    }

    private static TagKey<Item> createExternalTag(String namespace, String id) {
        return TagKey.of(Registry.ITEM_KEY, new Identifier(namespace, id.toLowerCase()));
    }

    public static void init() {
        Prog.LOGGER.info("Registering Item Tags for: " + Prog.MOD_ID);
    }
}
