package com.prog.compat.rei;

import com.prog.itemOrBlock.data.FlexibleCraftingData;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.SimpleGridMenuDisplay;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.entry.EntryStack;
import me.shedaniel.rei.api.common.entry.InputIngredient;
import me.shedaniel.rei.api.common.registry.RecipeManagerContext;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.AbstractCookingRecipe;
import net.minecraft.recipe.Recipe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class FlexibleCraftingDisplay extends BasicDisplay implements SimpleGridMenuDisplay {
    public final FlexibleCraftingData data;
    public final int recipeWidth;
    public final Recipe<?> recipe;

    public FlexibleCraftingDisplay(FlexibleCraftingData data, int recipeWidth, Recipe<?> recipe) {
        this(data, recipeWidth, EntryIngredients.ofIngredients(recipe.getIngredients()), Collections.singletonList(EntryIngredients.of(recipe.getOutput())),
                recipe);
    }

    public FlexibleCraftingDisplay(FlexibleCraftingData data, int recipeWidth, List<EntryIngredient> input, List<EntryIngredient> output, NbtCompound tag) {
        this(data, recipeWidth, input, output, RecipeManagerContext.getInstance().byId(tag, "location"));
    }

    public FlexibleCraftingDisplay(FlexibleCraftingData data, int recipeWidth, List<EntryIngredient> input, List<EntryIngredient> output, Recipe<?> recipe) {
        super(input, output, Optional.ofNullable(recipe).map(Recipe::getId));
        this.data = data;
        this.recipeWidth = recipeWidth;
        this.recipe = recipe;
    }

    @Override
    public int getWidth() {
        return data.width;
    }

    @Override
    public int getHeight() {
        return data.height;
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return data.categoryIdentifier.get();
    }

    @Override
    public List<EntryIngredient> getInputEntries() {
        var width = data.width;
        var height = data.height;
        List<EntryIngredient> adjustedList = new ArrayList<>(width * height);
        for (int index = 0; index < inputs.size(); index++) {
            var input = inputs.get(index);
            adjustedList.add(input);
            if (index % recipeWidth == recipeWidth - 1) {
                for (int j = 0; j < width - recipeWidth; j++) {
                    adjustedList.add(EntryIngredient.empty());
                }
            }
        }
        return adjustedList;
    }
}
