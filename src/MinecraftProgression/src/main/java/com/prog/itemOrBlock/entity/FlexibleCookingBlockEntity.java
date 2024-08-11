package com.prog.itemOrBlock.entity;

import com.prog.itemOrBlock.data.FlexibleCookingData;
import com.prog.screen.FlexibleCookingScreenHandler;
import com.prog.screen.FlexibleCraftingScreenHandler;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.recipe.RecipeType;
import net.minecraft.screen.FurnaceScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;

public class FlexibleCookingBlockEntity extends FlexibleAbstractFurnaceBlockEntity {
    private final FlexibleCookingData data;
    public FlexibleCookingBlockEntity(FlexibleCookingData data, BlockPos pos, BlockState state) {
        super(data.blockEntityType.get(), pos, state, data.cookingTimeDivisor, data.fuelMap, data.supportedRecipeTypes);
        this.data = data;
    }

    public static FabricBlockEntityTypeBuilder.Factory<FlexibleCookingBlockEntity> factory(FlexibleCookingData data){
        return (BlockPos pos, BlockState state) -> new FlexibleCookingBlockEntity(data, pos, state);
    }

    public static FabricBlockEntityTypeBuilder<FlexibleCookingBlockEntity> builder(FlexibleCookingData data){
        return FabricBlockEntityTypeBuilder.create(FlexibleCookingBlockEntity.factory(data), data.block.get());
    }

    @Override
    protected Text getContainerName() {
        return data.title;
    }

    @Override
    protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
        return new FlexibleCookingScreenHandler(data, syncId, playerInventory, this, this.propertyDelegate);
    }
}