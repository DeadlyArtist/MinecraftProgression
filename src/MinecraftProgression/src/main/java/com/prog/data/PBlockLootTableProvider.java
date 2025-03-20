package com.prog.data;

import com.prog.itemOrBlock.PBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.block.Blocks;

public class PBlockLootTableProvider extends FabricBlockLootTableProvider {

    public PBlockLootTableProvider(FabricDataGenerator dataGenerator) {
        super(dataGenerator);
    }

    @Override
    protected void generateBlockLootTables() {
        // Vanilla overrides
        addDrop(Blocks.REINFORCED_DEEPSLATE);

        // Preregistered
        PBlocks.data.forEach((item, data) -> data.lootSupplier.accept(this));
    }
}
