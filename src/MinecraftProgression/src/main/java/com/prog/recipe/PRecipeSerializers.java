package com.prog.recipe;

import com.prog.Prog;
import com.prog.itemOrBlock.data.FlexibleCraftingData;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class PRecipeSerializers {
    public static RecipeSerializer<?> ASSEMBLY_SHAPED = register("ASSEMBLY_SHAPED", new FlexibleShapedRecipe.Serializer(PRecipeTypes.ASSEMBLY, FlexibleCraftingData.ASSEMBLY));
    public static RecipeSerializer<?> COSMIC_CONSTRUCTOR_SHAPED = register("COSMIC_CONSTRUCTOR_SHAPED", new FlexibleShapedRecipe.Serializer(PRecipeTypes.COSMIC_CONSTRUCTOR, FlexibleCraftingData.COSMIC_CONSTRUCTOR));

    public static <S extends RecipeSerializer<T>, T extends Recipe<?>> S register(String id, S serializer) {
        return Registry.register(Registry.RECIPE_SERIALIZER, new Identifier(Prog.MOD_ID, id.toLowerCase()), serializer);
    }

    public static void init() {
        Prog.LOGGER.info("Registering Recipe Serializers for: " + Prog.MOD_ID);
    }
}
