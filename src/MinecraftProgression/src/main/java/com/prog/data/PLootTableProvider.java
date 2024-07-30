package com.prog.data;

import com.prog.itemOrBlock.PBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLootTableProvider;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContextType;
import net.minecraft.util.Identifier;

import java.util.function.BiConsumer;

public class PLootTableProvider extends FabricBlockLootTableProvider {

    public PLootTableProvider(FabricDataGenerator dataGenerator) {
        super(dataGenerator);
    }

    @Override
    protected void generateBlockLootTables() {
        addDrop(PBlocks.STEEL_BLOCK);
        addDrop(PBlocks.STEEL_FRAME);
    }
}
