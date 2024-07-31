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

    public static void addToUpgrades(Item item) {
        addToTag(PItemTags.UPGRADE, item);
    }

    @Override
    protected void generateTags() {
        // Vanilla overrides
        addToUpgrades(Items.PRISMARINE_SHARD);
        addToUpgrades(Items.MAGMA_CREAM);
        addToUpgrades(Items.CHORUS_FRUIT);
        addToUpgrades(Items.HONEY_BLOCK);
        addToUpgrades(Items.PHANTOM_MEMBRANE);
        addToUpgrades(Items.WITHER_ROSE);
        addToUpgrades(Items.HEART_OF_THE_SEA);
        addToUpgrades(Items.EMERALD);
        addToUpgrades(Items.MUSIC_DISC_PIGSTEP);
        addToUpgrades(Items.SCUTE);
        addToUpgrades(Items.GOLDEN_APPLE);
        addToUpgrades(Items.ENCHANTED_GOLDEN_APPLE);
        addToUpgrades(Items.SLIME_BALL);
        addToUpgrades(Items.ENDER_EYE);
        addToUpgrades(Items.POISONOUS_POTATO);
        addToUpgrades(Items.TOTEM_OF_UNDYING);
        addToUpgrades(Items.GOAT_HORN);
        addToUpgrades(Items.CREEPER_HEAD);
        addToUpgrades(Items.ZOMBIE_HEAD);
        addToUpgrades(Items.SKELETON_SKULL);
        addToUpgrades(Items.WITHER_SKELETON_SKULL);

        // Preregistered
        PItems.data.forEach((item, data) -> data.tags.forEach(tag -> addToTag(tag, item)));

        // Add all tags
        tags.forEach((tag, items) -> {
            FabricTagProvider<Item>.FabricTagBuilder<Item> tagBuilder = getOrCreateTagBuilder(tag);
            items.forEach(item -> tagBuilder.add(item));
        });
    }
}
