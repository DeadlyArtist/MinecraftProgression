package com.prog.itemOrBlock.data;

import com.prog.itemOrBlock.PBlockEntityTypes;
import com.prog.itemOrBlock.PBlocks;
import com.prog.itemOrBlock.entity.FlexibleCookingBlockEntity;
import com.prog.recipe.PRecipeSerializers;
import com.prog.recipe.PRecipeTypes;
import com.prog.screen.PScreenHandlerTypes;
import com.prog.text.PTexts;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.recipe.AbstractCookingRecipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public enum FlexibleCookingData {
    INCINERATOR(() -> PBlocks.INCINERATOR, () -> PBlockEntityTypes.INCINERATOR, () -> PRecipeSerializers.INCINERATOR, PRecipeTypes.INCINERATOR, List.of(RecipeType.SMELTING), 1000, 5, PTexts.INCINERATOR_UI_TITLE.get(), () -> PScreenHandlerTypes.INCINERATOR);

    public final Supplier<Block> block;
    public final Supplier<BlockEntityType<FlexibleCookingBlockEntity>> blockEntityType;
    public final Supplier<RecipeSerializer<?>> recipeSerializerSupplier;
    public final RecipeType<? extends AbstractCookingRecipe> recipeType;
    public final List<RecipeType<? extends AbstractCookingRecipe>> supportedRecipeTypes;
    public final int defaultCookingTime;
    public final float cookingTimeDivisor;
    public final Text title;
    public final Supplier<ScreenHandlerType<?>> screenHandlerType;

    FlexibleCookingData(Supplier<Block> block, Supplier<BlockEntityType<FlexibleCookingBlockEntity>> blockEntityType, Supplier<RecipeSerializer<?>> recipeSerializerSupplier, RecipeType<? extends AbstractCookingRecipe> recipeType, List<RecipeType<? extends AbstractCookingRecipe>> additionalSupportedRecipeTypes, int defaultCookingTime, float cookingTimeDivisor, Text title, Supplier<ScreenHandlerType<?>> screenHandlerType) {
        this.block = block;
        this.blockEntityType = blockEntityType;
        this.recipeSerializerSupplier = recipeSerializerSupplier;
        this.recipeType = recipeType;
        this.defaultCookingTime = defaultCookingTime;
        this.cookingTimeDivisor = cookingTimeDivisor;
        this.title = title;
        this.screenHandlerType = screenHandlerType;

        List<RecipeType<? extends AbstractCookingRecipe>> tempList = new ArrayList<>(additionalSupportedRecipeTypes);
        tempList.add(recipeType);
        this.supportedRecipeTypes = tempList;
    }

    FlexibleCookingData(Supplier<Block> block, Supplier<BlockEntityType<FlexibleCookingBlockEntity>> blockEntityType, Supplier<RecipeSerializer<?>> recipeSerializerSupplier, RecipeType<? extends AbstractCookingRecipe> recipeType, int defaultCookingTime, float cookingTimeDivisor, Text title, Supplier<ScreenHandlerType<?>> screenHandlerType) {
        this(block, blockEntityType, recipeSerializerSupplier, recipeType, List.of(), defaultCookingTime, cookingTimeDivisor, title, screenHandlerType);
    }
}
