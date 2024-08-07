package com.prog.utils;

import com.mojang.datafixers.util.Pair;
import com.prog.itemOrBlock.data.FlexibleCookingData;
import com.prog.recipe.FlexibleCookingRecipe;
import com.prog.recipe.FlexibleShapedRecipe;
import net.minecraft.inventory.Inventory;
import net.minecraft.recipe.*;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class RecipeUtils {
    public static <C extends Inventory, T extends Recipe<C>> RecipeManager.MatchGetter<C, T> createCachedMatchGetter(List<RecipeType<? extends T>> types) {
        return new RecipeManager.MatchGetter<C, T>() {
            @Nullable
            private Identifier id;

            @Override
            public Optional<T> getFirstMatch(C inventory, World world) {
                RecipeManager recipeManager = world.getRecipeManager();
                Optional<? extends Pair<Identifier, ? extends T>> optional = null;
                for (RecipeType<? extends T> type : types) {
                    optional = recipeManager.getFirstMatch(type, inventory, world, this.id);
                    if (optional.isPresent()) {
                        Pair<Identifier, T> pair = (Pair<Identifier, T>) optional.get();
                        this.id = pair.getFirst();
                        return Optional.of(pair.getSecond());
                    }
                }
                return Optional.empty();
            }
        };
    }
}
