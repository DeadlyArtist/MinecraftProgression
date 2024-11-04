package com.prog.recipe;

import com.prog.Prog;
import com.prog.utils.LOGGER;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class PRecipeTypes {
    public static RecipeType<FlexibleCraftingRecipe> ASSEMBLY = register("ASSEMBLY");
    public static RecipeType<FlexibleCraftingRecipe> COSMIC_CONSTRUCTOR = register("COSMIC_CONSTRUCTOR");
    public static RecipeType<FlexibleCookingRecipe> INCINERATOR = register("INCINERATOR");
    public static RecipeType<FlexibleCookingRecipe> COSMIC_INCUBATOR = register("COSMIC_INCUBATOR");

    public static final RecipeType<NbtSmithingRecipe> NBT_SMITHING = register("NBT_SMITHING");

    static <T extends Recipe<?>> RecipeType<T> register(String id) {
        id = id.toLowerCase();
        String finalId = id;
        return Registry.register(Registry.RECIPE_TYPE, new Identifier(Prog.MOD_ID, finalId), new RecipeType<T>() {
            public String toString() {
                return finalId;
            }
        });
    }

    public static void init() {
        LOGGER.info("Registering Recipe Types for: " + Prog.MOD_ID);
    }
}
