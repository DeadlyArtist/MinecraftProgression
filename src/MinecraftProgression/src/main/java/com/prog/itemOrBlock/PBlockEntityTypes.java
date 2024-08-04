package com.prog.itemOrBlock;

import com.mojang.datafixers.types.Type;
import com.prog.Prog;
import com.prog.itemOrBlock.data.FlexibleCookingData;
import com.prog.itemOrBlock.entity.FlexibleCookingBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.FurnaceBlockEntity;
import net.minecraft.datafixer.TypeReferences;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.registry.Registry;

public class PBlockEntityTypes {
    public static final BlockEntityType<FlexibleCookingBlockEntity> INCINERATOR = create("INCINERATOR", FlexibleCookingBlockEntity.builder(FlexibleCookingData.INCINERATOR));

    private static <T extends BlockEntity> BlockEntityType<T> create(String id, FabricBlockEntityTypeBuilder<T> builder) {
        Identifier finalId = new Identifier(Prog.MOD_ID, id.toLowerCase());
        return Registry.register(Registry.BLOCK_ENTITY_TYPE, finalId, builder.build());
    }

    public static void init() {
        Prog.LOGGER.info("Registering Block Entity Types for: " + Prog.MOD_ID);
    }
}
