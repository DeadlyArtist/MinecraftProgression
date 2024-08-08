package com.prog.recipe;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;

public class NbtSmithingRecipe extends IngredientRecipe<Inventory> {
    public NbtSmithingRecipe(Identifier identifier, Ingredient base, Ingredient ingredient, ItemStack result, RecipeType<? extends IngredientRecipe<Inventory>> recipeType, RecipeSerializer<? extends IngredientRecipe<Inventory>> serializer) {
        super(identifier, base, ingredient, result, recipeType, serializer);
    }
}
