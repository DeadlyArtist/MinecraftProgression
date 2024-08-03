package com.prog.itemOrBlock.data;

import com.prog.itemOrBlock.PBlocks;
import com.prog.recipe.PRecipeTypes;
import com.prog.screen.PScreenHandlerTypes;
import com.prog.text.PTexts;
import net.minecraft.block.Block;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.text.Text;

import java.util.List;
import java.util.function.Supplier;

public enum FlexibleCraftingData {
    ASSEMBLY(() -> PBlocks.ASSEMBLY, List.of(RecipeType.CRAFTING, PRecipeTypes.ASSEMBLY), 5, 3, PTexts.ASSEMBLY_UI_TITLE.get(), () -> PScreenHandlerTypes.ASSEMBLY),
    COSMIC_CONSTRUCTOR(() -> PBlocks.COSMIC_CONSTRUCTOR, List.of(RecipeType.CRAFTING, PRecipeTypes.ASSEMBLY, PRecipeTypes.COSMIC_CONSTRUCTOR), 5, 5, PTexts.COSMIC_CONSTRUCTOR_UI_TITLE.get(), () -> PScreenHandlerTypes.COSMIC_CONSTRUCTOR);

    public final Supplier<Block> block;
    public final List<RecipeType<? extends Recipe<CraftingInventory>>> recipeTypes;
    public final int width;
    public final int height;
    public final Text title;
    public final Supplier<ScreenHandlerType<?>> screenHandlerType;

    FlexibleCraftingData(Supplier<Block> block, List<RecipeType<? extends Recipe<CraftingInventory>>> recipeTypes, int width, int height, Text title, Supplier<ScreenHandlerType<?>> screenHandlerType) {
        this.block = block;
        this.recipeTypes = recipeTypes;
        this.width = width;
        this.height = height;
        this.title = title;
        this.screenHandlerType = screenHandlerType;
    }
}
