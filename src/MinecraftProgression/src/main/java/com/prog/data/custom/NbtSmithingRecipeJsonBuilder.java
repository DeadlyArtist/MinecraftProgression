package com.prog.data.custom;

import com.google.gson.JsonObject;
import net.minecraft.advancement.Advancement;
import net.minecraft.data.server.recipe.SmithingRecipeJsonBuilder;
import net.minecraft.item.Item;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.util.Identifier;

public class NbtSmithingRecipeJsonBuilder extends SmithingRecipeJsonBuilder {
    public NbtSmithingRecipeJsonBuilder(RecipeSerializer<?> serializer, Ingredient base, Ingredient addition, Item result) {
        super(serializer, base, addition, result);
    }

    public static class NbtSmithingRecipeJsonProvider extends SmithingRecipeJsonProvider {

        public NbtSmithingRecipeJsonProvider(Identifier recipeId, RecipeSerializer<?> serializer, Ingredient base, Ingredient addition, Item result, Advancement.Builder advancementBuilder, Identifier advancementId) {
            super(recipeId, serializer, base, addition, result, advancementBuilder, advancementId);
        }

        @Override
        public void serialize(JsonObject json) {
            super.serialize(json);
        }
    }
}
