package com.prog.data;

import com.prog.itemOrBlock.PBlocks;
import com.prog.itemOrBlock.PItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;

public class PModelProvider extends FabricModelProvider {
    public PModelProvider(FabricDataGenerator generator) {
        super(generator);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        PBlocks.data.forEach((item, data) -> data.modelSupplier.accept(blockStateModelGenerator));
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        PItems.data.forEach((item, data) -> data.modelSupplier.accept(itemModelGenerator));
    }
}
