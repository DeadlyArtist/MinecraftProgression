package com.prog.data;

import com.prog.itemOrBlock.PBlocks;
import com.prog.itemOrBlock.PItemTags;
import com.prog.itemOrBlock.PItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.tag.ItemTags;
import net.minecraft.tag.TagKey;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PItemTagProvider extends FabricTagProvider.ItemTagProvider {

    public static final Map<TagKey<Item>, ArrayList<Item>> tags = new HashMap<>();

    public PItemTagProvider(FabricDataGenerator dataGenerator, @Nullable FabricTagProvider.BlockTagProvider blockTagProvider) {
        super(dataGenerator, blockTagProvider);
    }

    public static void addToTag(TagKey<Item> tag, Item item) {
        tags.computeIfAbsent(tag, k -> new ArrayList<>()).add(item);
    }

    @Override
    protected void generateTags() {
        // Vanilla overrides
        addToTag(PItemTags.UPGRADE, Items.PRISMARINE_SHARD);
        addToTag(PItemTags.UPGRADE, Items.BLAZE_POWDER);
        addToTag(PItemTags.UPGRADE, Items.CHORUS_FRUIT);
        addToTag(PItemTags.UPGRADE, Items.HONEY_BLOCK);
        addToTag(PItemTags.UPGRADE, Items.PHANTOM_MEMBRANE);
        addToTag(PItemTags.UPGRADE, Items.WITHER_ROSE);
        addToTag(PItemTags.UPGRADE, Items.HEART_OF_THE_SEA);
        addToTag(PItemTags.UPGRADE, Items.EMERALD);
        addToTag(PItemTags.UPGRADE, Items.MUSIC_DISC_PIGSTEP);

        // Preregistered
        PItems.data.forEach((item, data) -> data.tags.forEach(tag -> addToTag(tag, item)));

        // Add all tags
        tags.forEach((tag, items) -> {
            FabricTagProvider<Item>.FabricTagBuilder<Item> tagBuilder = getOrCreateTagBuilder(tag);
            items.forEach(item -> tagBuilder.add(item));
        });
    }
}
