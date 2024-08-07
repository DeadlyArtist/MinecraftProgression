package com.prog.compat.rei;

import com.prog.itemOrBlock.data.FlexibleCookingData;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.SimpleGridMenuDisplay;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.registry.RecipeManagerContext;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.AbstractCookingRecipe;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.SmeltingRecipe;
import org.jetbrains.annotations.ApiStatus;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class FlexibleCookingDisplay extends BasicDisplay implements SimpleGridMenuDisplay {
    public final FlexibleCookingData data;
    private Recipe<?> recipe;
    private float xp;
    private double cookTime;


    public FlexibleCookingDisplay(FlexibleCookingData data, AbstractCookingRecipe recipe) {
        this(data, EntryIngredients.ofIngredients(recipe.getIngredients()), Collections.singletonList(EntryIngredients.of(recipe.getOutput())),
                recipe, recipe.getExperience(), recipe.getCookTime());
    }

    public FlexibleCookingDisplay(FlexibleCookingData data, List<EntryIngredient> input, List<EntryIngredient> output, NbtCompound tag) {
        this(data, input, output, RecipeManagerContext.getInstance().byId(tag, "location"),
                tag.getFloat("xp"), tag.getDouble("cookTime"));
    }

    public FlexibleCookingDisplay(FlexibleCookingData data, List<EntryIngredient> input, List<EntryIngredient> output, Recipe<?> recipe, float xp, double cookTime) {
        super(input, output, Optional.ofNullable(recipe).map(Recipe::getId));
        this.data = data;
        this.recipe = recipe;
        this.xp = xp;
        this.cookTime = cookTime;
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return data.categoryIdentifier.get();
    }


    public float getXp() {
        return xp;
    }

    public double getCookingTime() {
        return cookTime;
    }

    @Override
    public int getWidth() {
        return 1;
    }

    @Override
    public int getHeight() {
        return 1;
    }
}
