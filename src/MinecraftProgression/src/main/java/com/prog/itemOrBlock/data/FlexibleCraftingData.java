package com.prog.itemOrBlock.data;

import com.prog.compat.rei.FlexibleCookingDisplay;
import com.prog.compat.rei.FlexibleCraftingDisplay;
import com.prog.compat.rei.PREICategories;
import com.prog.itemOrBlock.PBlocks;
import com.prog.recipe.PRecipeSerializers;
import com.prog.recipe.PRecipeTypes;
import com.prog.screen.PScreenHandlerTypes;
import com.prog.text.PTexts;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import net.minecraft.block.Block;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public enum FlexibleCraftingData {
    ASSEMBLY(() -> PBlocks.ASSEMBLY, PRecipeTypes.ASSEMBLY, List.of(RecipeType.CRAFTING), () -> PRecipeSerializers.ASSEMBLY_SHAPED, () -> PRecipeSerializers.ASSEMBLY_SHAPELESS, 5, 3, PTexts.ASSEMBLY_UI_TITLE.get(), () -> PScreenHandlerTypes.ASSEMBLY, () -> PREICategories.ASSEMBLY),
    COSMIC_CONSTRUCTOR(() -> PBlocks.COSMIC_CONSTRUCTOR, PRecipeTypes.COSMIC_CONSTRUCTOR, List.of(RecipeType.CRAFTING, PRecipeTypes.ASSEMBLY), () -> PRecipeSerializers.COSMIC_CONSTRUCTOR_SHAPED, () -> PRecipeSerializers.COSMIC_CONSTRUCTOR_SHAPELESS, 5, 5, PTexts.COSMIC_CONSTRUCTOR_UI_TITLE.get(), () -> PScreenHandlerTypes.COSMIC_CONSTRUCTOR, () -> PREICategories.COSMIC_CONSTRUCTOR);

    public final Supplier<Block> block;
    public final RecipeType<? extends Recipe<CraftingInventory>> recipeType;
    public final List<RecipeType<? extends Recipe<CraftingInventory>>> supportedRecipeTypes;
    public final Supplier<RecipeSerializer<?>> shapedSerializer;
    public final Supplier<RecipeSerializer<?>> shapelessSerializer;
    public final int width;
    public final int height;
    public final Text title;
    public final Supplier<ScreenHandlerType<?>> screenHandlerType;
    public final Supplier<CategoryIdentifier<? extends FlexibleCraftingDisplay>> categoryIdentifier;

    FlexibleCraftingData(Supplier<Block> block, RecipeType<? extends Recipe<CraftingInventory>> recipeType, List<RecipeType<? extends Recipe<CraftingInventory>>> additionalSupportedRecipeTypes, Supplier<RecipeSerializer<?>> shapedSerializer, Supplier<RecipeSerializer<?>> shapelessSerializer, int width, int height, Text title, Supplier<ScreenHandlerType<?>> screenHandlerType, Supplier<CategoryIdentifier<? extends FlexibleCraftingDisplay>> categoryIdentifier) {
        this.block = block;
        this.recipeType = recipeType;
        this.shapedSerializer = shapedSerializer;
        this.shapelessSerializer = shapelessSerializer;
        this.width = width;
        this.height = height;
        this.title = title;
        this.screenHandlerType = screenHandlerType;
        this.categoryIdentifier = categoryIdentifier;

        additionalSupportedRecipeTypes = new ArrayList<>(additionalSupportedRecipeTypes);
        additionalSupportedRecipeTypes.add(recipeType);
        this.supportedRecipeTypes = additionalSupportedRecipeTypes;
    }

    FlexibleCraftingData(Supplier<Block> block, RecipeType<? extends Recipe<CraftingInventory>> recipeType, Supplier<RecipeSerializer<?>> shapedSerializer, Supplier<RecipeSerializer<?>> shapelessSerializer, int width, int height, Text title, Supplier<ScreenHandlerType<?>> screenHandlerType, Supplier<CategoryIdentifier<? extends FlexibleCraftingDisplay>> categoryIdentifier) {
        this(block, recipeType, List.of(), shapelessSerializer, shapedSerializer, width, height, title, screenHandlerType, categoryIdentifier);
    }
}
