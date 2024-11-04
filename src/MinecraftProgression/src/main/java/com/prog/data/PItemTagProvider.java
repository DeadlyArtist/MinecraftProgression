package com.prog.data;

import com.prog.itemOrBlock.*;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.item.Item;
import net.minecraft.tag.TagKey;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

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
        // Custom
        addToTag(PItemTags.UPGRADE, PJetpacks.MECHANICAL.item.get());

        // Preregistered
        PItems.data.forEach((item, data) -> data.tags.forEach(tag -> addToTag(tag, item)));
        Upgrades.data.forEach((item, upgrade) -> addToTag(PItemTags.UPGRADE, item));
        GourmetFoods.data.forEach((item, data) -> addToTag(PItemTags.GOURMET_FOOD, item));

        // Add all tags
        tags.forEach((tag, items) -> {
            FabricTagProvider<Item>.FabricTagBuilder<Item> tagBuilder = getOrCreateTagBuilder(tag);
            items.forEach(item -> tagBuilder.add(item));
        });
    }
}
