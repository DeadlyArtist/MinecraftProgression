package com.prog.data;

import com.prog.Prog;
import com.prog.itemOrBlock.PBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Block;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.TagKey;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PBlockTagProvider extends FabricTagProvider.BlockTagProvider {
    public PBlockTagProvider(FabricDataGenerator dataGenerator) {
        super(dataGenerator);
    }

    public static final Map<TagKey<Block>, ArrayList<Block>> tags = new HashMap<>();

    @Override
    protected void generateTags() {
        PBlocks.data.forEach((block, data) -> data.tags.forEach(tag -> tags.computeIfAbsent(tag, k -> new ArrayList<>()).add(block)));

        getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE)
                .add(PBlocks.STEEL_BLOCK)
                .add(PBlocks.STEEL_FRAME)
        ;

        getOrCreateTagBuilder(BlockTags.NEEDS_IRON_TOOL)
                .add(PBlocks.STEEL_BLOCK)
                .add(PBlocks.STEEL_FRAME)
        ;
    }

    public FabricTagBuilder<Block> getOrCreateTagBuilder(TagKey<Block> tag) {
        return super.getOrCreateTagBuilder(tag);
    }
}
