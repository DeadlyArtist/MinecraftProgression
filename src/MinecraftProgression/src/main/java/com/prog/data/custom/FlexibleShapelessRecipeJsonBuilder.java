package com.prog.data.custom;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.prog.recipe.PRecipeSerializers;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementRewards;
import net.minecraft.advancement.CriterionMerger;
import net.minecraft.advancement.criterion.CriterionConditions;
import net.minecraft.advancement.criterion.RecipeUnlockedCriterion;
import net.minecraft.data.server.recipe.CraftingRecipeJsonBuilder;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;

public class FlexibleShapelessRecipeJsonBuilder implements CraftingRecipeJsonBuilder {
    private final Item output;
    private final int outputCount;
    private final List<Ingredient> inputs = Lists.<Ingredient>newArrayList();
    private final Advancement.Builder advancementBuilder = Advancement.Builder.create();
    @Nullable
    private String group;
    public RecipeSerializer<?> recipeSerializer = RecipeSerializer.SHAPELESS;

    public FlexibleShapelessRecipeJsonBuilder(ItemConvertible output, int outputCount) {
        this.output = output.asItem();
        this.outputCount = outputCount;
    }


    public FlexibleShapelessRecipeJsonBuilder setSerializer(RecipeSerializer<?> recipeSerializer) {
        this.recipeSerializer = recipeSerializer;
        return this;
    }

    public FlexibleShapelessRecipeJsonBuilder requireAssembly() {
        return setSerializer(PRecipeSerializers.ASSEMBLY_SHAPELESS);
    }

    public FlexibleShapelessRecipeJsonBuilder requireCosmicConstructor() {
        return setSerializer(PRecipeSerializers.COSMIC_CONSTRUCTOR_SHAPELESS);
    }

    public static FlexibleShapelessRecipeJsonBuilder create(ItemConvertible output) {
        return new FlexibleShapelessRecipeJsonBuilder(output, 1);
    }

    public static FlexibleShapelessRecipeJsonBuilder create(ItemConvertible output, int outputCount) {
        return new FlexibleShapelessRecipeJsonBuilder(output, outputCount);
    }

    public FlexibleShapelessRecipeJsonBuilder input(TagKey<Item> tag) {
        return this.input(Ingredient.fromTag(tag));
    }

    public FlexibleShapelessRecipeJsonBuilder input(ItemConvertible itemProvider) {
        return this.input(itemProvider, 1);
    }

    public FlexibleShapelessRecipeJsonBuilder input(ItemConvertible itemProvider, int size) {
        for (int i = 0; i < size; i++) {
            this.input(Ingredient.ofItems(itemProvider));
        }

        return this;
    }

    public FlexibleShapelessRecipeJsonBuilder input(Ingredient ingredient) {
        return this.input(ingredient, 1);
    }

    public FlexibleShapelessRecipeJsonBuilder input(Ingredient ingredient, int size) {
        for (int i = 0; i < size; i++) {
            this.inputs.add(ingredient);
        }

        return this;
    }

    public FlexibleShapelessRecipeJsonBuilder criterion(String string, CriterionConditions criterionConditions) {
        this.advancementBuilder.criterion(string, criterionConditions);
        return this;
    }

    public FlexibleShapelessRecipeJsonBuilder group(@Nullable String string) {
        this.group = string;
        return this;
    }

    @Override
    public Item getOutputItem() {
        return this.output;
    }

    @Override
    public void offerTo(Consumer<RecipeJsonProvider> exporter, Identifier recipeId) {
        this.validate(recipeId);
        this.advancementBuilder
                .parent(ROOT)
                .criterion("has_the_recipe", RecipeUnlockedCriterion.create(recipeId))
                .rewards(AdvancementRewards.Builder.recipe(recipeId))
                .criteriaMerger(CriterionMerger.OR);
        exporter.accept(
                new FlexibleShapelessRecipeJsonBuilder.ShapelessRecipeJsonProvider(
                        recipeSerializer,
                        recipeId,
                        this.output,
                        this.outputCount,
                        this.group == null ? "" : this.group,
                        this.inputs,
                        this.advancementBuilder,
                        new Identifier(recipeId.getNamespace(), "recipes/" + this.output.getGroup().getName() + "/" + recipeId.getPath())
                )
        );
    }

    private void validate(Identifier recipeId) {
        if (this.advancementBuilder.getCriteria().isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + recipeId);
        }
    }

    public static class ShapelessRecipeJsonProvider implements RecipeJsonProvider {
        private final Identifier recipeId;
        private final Item output;
        private final int count;
        private final String group;
        private final List<Ingredient> inputs;
        private final Advancement.Builder advancementBuilder;
        private final Identifier advancementId;
        private final RecipeSerializer<?> recipeSerializer;

        public ShapelessRecipeJsonProvider(
                RecipeSerializer<?> recipeSerializer, Identifier recipeId, Item output, int outputCount, String group, List<Ingredient> inputs, Advancement.Builder advancementBuilder, Identifier advancementId
        ) {
            this.recipeSerializer = recipeSerializer;
            this.recipeId = recipeId;
            this.output = output;
            this.count = outputCount;
            this.group = group;
            this.inputs = inputs;
            this.advancementBuilder = advancementBuilder;
            this.advancementId = advancementId;
        }

        @Override
        public void serialize(JsonObject json) {
            if (!this.group.isEmpty()) {
                json.addProperty("group", this.group);
            }

            JsonArray jsonArray = new JsonArray();

            for (Ingredient ingredient : this.inputs) {
                jsonArray.add(ingredient.toJson());
            }

            json.add("ingredients", jsonArray);
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("item", Registry.ITEM.getId(this.output).toString());
            if (this.count > 1) {
                jsonObject.addProperty("count", this.count);
            }

            json.add("result", jsonObject);
        }

        @Override
        public RecipeSerializer<?> getSerializer() {
            return recipeSerializer;
        }

        @Override
        public Identifier getRecipeId() {
            return this.recipeId;
        }

        @Nullable
        @Override
        public JsonObject toAdvancementJson() {
            return this.advancementBuilder.toJson();
        }

        @Nullable
        @Override
        public Identifier getAdvancementId() {
            return this.advancementId;
        }
    }
}
