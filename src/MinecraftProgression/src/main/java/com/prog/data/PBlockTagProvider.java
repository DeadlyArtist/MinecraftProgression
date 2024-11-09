package com.prog.data;

import com.prog.itemOrBlock.PBlockTags;
import com.prog.itemOrBlock.PBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.TagKey;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PBlockTagProvider extends FabricTagProvider.BlockTagProvider {
    public PBlockTagProvider(FabricDataGenerator dataGenerator) {
        super(dataGenerator);
    }

    public static final Map<TagKey<Block>, ArrayList<Block>> tags = new HashMap<>();

    public static void addToTag(TagKey<Block> tag, Block block) {
        tags.computeIfAbsent(tag, k -> new ArrayList<>()).add(block);
    }

    @Override
    protected void generateTags() {
        // Vanilla Overrides
        addToTag(PBlockTags.NEEDS_TITAN_TOOL, Blocks.ANCIENT_DEBRIS);

        // Preregistered
        PBlocks.data.forEach((block, data) -> data.tags.forEach(tag -> addToTag(tag, block)));

        // Add all tags
        tags.forEach((tag, blocks) -> {
            FabricTagProvider<Block>.FabricTagBuilder<Block> tagBuilder = getOrCreateTagBuilder(tag);
            blocks.forEach(block -> tagBuilder.add(block));
        });
    }

    public FabricTagBuilder<Block> getOrCreateTagBuilder(TagKey<Block> tag) {
        return super.getOrCreateTagBuilder(tag);
    }
}
