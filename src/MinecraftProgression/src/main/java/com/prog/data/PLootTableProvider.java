package com.prog.data;

import com.prog.itemOrBlock.PBlocks;
import com.prog.itemOrBlock.PItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;

public class PLootTableProvider extends FabricBlockLootTableProvider {

    public PLootTableProvider(FabricDataGenerator dataGenerator) {
        super(dataGenerator);
    }

    @Override
    protected void generateBlockLootTables() {
        PBlocks.data.forEach((item, data) -> data.lootSupplier.accept(this));
    }
}
