package com.prog.compat.rei;

import com.prog.itemOrBlock.data.FlexibleCraftingData;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.SimpleGridMenuDisplay;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.registry.RecipeManagerContext;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.AbstractCookingRecipe;
import net.minecraft.recipe.Recipe;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class FlexibleCraftingDisplay extends BasicDisplay implements SimpleGridMenuDisplay {
    public final FlexibleCraftingData data;
    public final Recipe<?> recipe;

    public FlexibleCraftingDisplay(FlexibleCraftingData data, Recipe<?> recipe) {
        this(data, EntryIngredients.ofIngredients(recipe.getIngredients()), Collections.singletonList(EntryIngredients.of(recipe.getOutput())),
                recipe);
    }

    public FlexibleCraftingDisplay(FlexibleCraftingData data, List<EntryIngredient> input, List<EntryIngredient> output, NbtCompound tag) {
        this(data, input, output, RecipeManagerContext.getInstance().byId(tag, "location"));
    }

    public FlexibleCraftingDisplay(FlexibleCraftingData data, List<EntryIngredient> input, List<EntryIngredient> output, Recipe<?> recipe) {
        super(input, output, Optional.ofNullable(recipe).map(Recipe::getId));
        this.data = data;
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
}
