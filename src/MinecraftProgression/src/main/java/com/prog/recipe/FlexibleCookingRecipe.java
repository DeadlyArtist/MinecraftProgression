package com.prog.recipe;

import com.prog.itemOrBlock.data.FlexibleCookingData;
import com.prog.screen.FlexibleCraftingScreenHandler;
import net.minecraft.block.Block;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.*;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.function.Supplier;

public class FlexibleCookingRecipe extends AbstractCookingRecipe {
    public Supplier<Block> blockSupplier;
    public final Supplier<RecipeSerializer<?>> recipeSerializerSupplier;
    public FlexibleCookingRecipe(Supplier<Block> blockSupplier, Supplier<RecipeSerializer<?>> recipeSerializerSupplier, RecipeType<?> recipeType, Identifier id, String group, Ingredient input, ItemStack output, float experience, int cookTime) {
        super(recipeType, id, group, input, output, experience, cookTime);
        this.blockSupplier = blockSupplier;
        this.recipeSerializerSupplier = recipeSerializerSupplier;
    }

    public FlexibleCookingRecipe(FlexibleCookingData data, Identifier id, String group, Ingredient input, ItemStack output, float experience, int cookTime) {
        this(data.block, data.recipeSerializerSupplier, data.recipeType, id, group, input, output, experience, cookTime);
    }

    public static CookingRecipeSerializer.RecipeFactory<FlexibleCookingRecipe> factory(FlexibleCookingData data) {
        return (net.minecraft.util.Identifier id,
                String group,
                net.minecraft.recipe.Ingredient input,
                net.minecraft.item.ItemStack output,
                float experience,
                int cookTime) -> new FlexibleCookingRecipe(data, id, group, input, output, experience, cookTime);
    }

    public static CookingRecipeSerializer createSerializer(FlexibleCookingData data) {
        return new CookingRecipeSerializer(factory(data), data.defaultCookingTime);
    }

    @Override
    public ItemStack createIcon() {
        return new ItemStack(blockSupplier.get());
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return recipeSerializerSupplier.get();
    }
}