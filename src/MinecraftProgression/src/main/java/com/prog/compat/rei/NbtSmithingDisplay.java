package com.prog.compat.rei;

import com.prog.recipe.NbtSmithingRecipe;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.minecraft.util.Identifier;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class NbtSmithingDisplay extends BasicDisplay {
    public NbtSmithingDisplay(NbtSmithingRecipe recipe) {
        this(
                Arrays.asList(
                        EntryIngredients.ofIngredient(recipe.getBase()),
                        EntryIngredients.ofIngredient(recipe.getIngredient())
                ),
                Collections.singletonList(EntryIngredients.of(recipe.getOutput())),
                Optional.ofNullable(recipe.getId())
        );
    }

    public NbtSmithingDisplay(List<EntryIngredient> inputs, List<EntryIngredient> outputs, Optional<Identifier> location) {
        super(inputs, outputs, location);
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return PREICategories.NBT_SMITHING;
    }
}