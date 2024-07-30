package com.prog.data;

import com.prog.itemOrBlock.PBlocks;
import com.prog.itemOrBlock.PItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
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

    @Override
    protected void generateTags() {
        PItems.data.forEach((item, data) -> data.tags.forEach(tag -> tags.computeIfAbsent(tag, k -> new ArrayList<>()).add(item)));

    }
}
