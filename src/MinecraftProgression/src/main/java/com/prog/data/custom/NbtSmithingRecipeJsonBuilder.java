package com.prog.data.custom;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementRewards;
import net.minecraft.advancement.CriterionMerger;
import net.minecraft.advancement.criterion.RecipeUnlockedCriterion;
import net.minecraft.data.server.recipe.CraftingRecipeJsonBuilder;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.SmithingRecipeJsonBuilder;
import net.minecraft.item.Item;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.function.Consumer;

public class NbtSmithingRecipeJsonBuilder extends SmithingRecipeJsonBuilder {
    public JsonObject baseNbt;
    public JsonObject additionNbt;
    public JsonObject resultNbt;

    public NbtSmithingRecipeJsonBuilder(RecipeSerializer<?> serializer, Ingredient base, Ingredient addition, Item result) {
        super(serializer, base, addition, result);
    }

    public static NbtSmithingRecipeJsonBuilder create(Ingredient base, Ingredient addition, Item result) {
        return new NbtSmithingRecipeJsonBuilder(RecipeSerializer.SMITHING, base, addition, result);
    }

    public NbtSmithingRecipeJsonBuilder addBaseNbt(String property, JsonElement value, boolean require) {
        if (baseNbt == null) {
            baseNbt = new JsonObject();
        }
        String target = require ? "require" : "deny";
        if (!baseNbt.has(target)) baseNbt.add(target, new JsonObject());
        baseNbt.get(target).getAsJsonObject().add(property, value);
        return this;
    }

    public NbtSmithingRecipeJsonBuilder addBaseNbt(String property, JsonElement value) {
        return addBaseNbt(property, value, true);
    }

    public NbtSmithingRecipeJsonBuilder addAdditionNbt(String property, JsonElement value, boolean require) {
        if (additionNbt == null) {
            additionNbt = new JsonObject();
        }
        String target = require ? "require" : "deny";
        if (!additionNbt.has(target)) additionNbt.add(target, new JsonObject());
        additionNbt.get(target).getAsJsonObject().add(property, value);
        return this;
    }

    public NbtSmithingRecipeJsonBuilder addAdditionNbt(String property, JsonElement value) {
        return addAdditionNbt(property, value, true);
    }

    public NbtSmithingRecipeJsonBuilder addResultNbt(String property, JsonElement value, boolean mergeNbt) {
        if (resultNbt == null) {
            resultNbt = new JsonObject();
            if (mergeNbt) resultNbt.addProperty("$", "$base");
        }
        resultNbt.add(property, value);
        return this;
    }

    public NbtSmithingRecipeJsonBuilder addResultNbt(String property, JsonElement value) {
        return addResultNbt(property, value, true);
    }

    @Override
    public void offerTo(Consumer<RecipeJsonProvider> exporter, Identifier recipeId) {
        if (baseNbt == null && additionNbt == null && resultNbt == null) {
            super.offerTo(exporter, recipeId);
            return;
        }

        this.validate(recipeId);
        this.advancementBuilder
                .parent(CraftingRecipeJsonBuilder.ROOT)
                .criterion("has_the_recipe", RecipeUnlockedCriterion.create(recipeId))
                .rewards(AdvancementRewards.Builder.recipe(recipeId))
                .criteriaMerger(CriterionMerger.OR);
        exporter.accept(
                new NbtSmithingRecipeJsonProvider(
                        recipeId,
                        this.serializer,
                        this.base,
                        this.addition,
                        this.result,
                        this.advancementBuilder,
                        new Identifier(recipeId.getNamespace(), "recipes/" + this.result.getGroup().getName() + "/" + recipeId.getPath()),
                        baseNbt,
                        additionNbt,
                        resultNbt
                )
        );
    }

    public static class NbtSmithingRecipeJsonProvider extends SmithingRecipeJsonProvider {
        protected final JsonObject baseNbt;
        protected final JsonObject ingredientNbt;
        protected final JsonObject resultNbt;

        public NbtSmithingRecipeJsonProvider(Identifier recipeId, RecipeSerializer<?> serializer, Ingredient base, Ingredient addition, Item result, Advancement.Builder advancementBuilder, Identifier advancementId, JsonObject baseNbt, JsonObject ingredientNbt, JsonObject resultNbt) {
            super(recipeId, serializer, base, addition, result, advancementBuilder, advancementId);
            this.baseNbt = baseNbt;
            this.ingredientNbt = ingredientNbt;
            this.resultNbt = resultNbt;
        }

        @Override
        public JsonObject toJson() {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("type", "nbtcrafting:smithing");
            this.serialize(jsonObject);
            return jsonObject;
        }

        @Override
        public void serialize(JsonObject json) {
            JsonObject baseJson = base.toJson().getAsJsonObject();
            if (baseNbt != null) baseJson.add("data", baseNbt);
            json.add("base", baseJson);

            JsonObject ingredientJson = addition.toJson().getAsJsonObject();
            if (ingredientNbt != null) ingredientJson.add("data", ingredientNbt);
            json.add("ingredient", ingredientJson);

            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("item", Registry.ITEM.getId(this.result).toString());
            if (resultNbt != null) jsonObject.add("data", resultNbt);
            json.add("result", jsonObject);
        }
    }
}
