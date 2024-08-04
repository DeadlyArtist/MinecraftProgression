package com.prog.recipe;

import com.prog.Prog;
import com.prog.itemOrBlock.data.FlexibleCookingData;
import com.prog.itemOrBlock.data.FlexibleCraftingData;
import net.minecraft.recipe.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class PRecipeSerializers {
    // Crafting
    public static RecipeSerializer<?> ASSEMBLY_SHAPED = register("ASSEMBLY_SHAPED", new FlexibleShapedRecipe.Serializer(FlexibleCraftingData.ASSEMBLY));
    public static RecipeSerializer<?> ASSEMBLY_SHAPELESS = register("ASSEMBLY_SHAPELESS", new FlexibleShapelessRecipe.Serializer(FlexibleCraftingData.ASSEMBLY));
    public static RecipeSerializer<?> COSMIC_CONSTRUCTOR_SHAPED = register("COSMIC_CONSTRUCTOR_SHAPED", new FlexibleShapedRecipe.Serializer(FlexibleCraftingData.COSMIC_CONSTRUCTOR));
    public static RecipeSerializer<?> COSMIC_CONSTRUCTOR_SHAPELESS = register("COSMIC_CONSTRUCTOR_SHAPELESS", new FlexibleShapelessRecipe.Serializer(FlexibleCraftingData.COSMIC_CONSTRUCTOR));

    // Smelting
    public static CookingRecipeSerializer<?> INCINERATOR = register("INCINERATOR", FlexibleCookingRecipe.createSerializer(FlexibleCookingData.INCINERATOR));

    public static <S extends RecipeSerializer<T>, T extends Recipe<?>> S register(String id, S serializer) {
        return Registry.register(Registry.RECIPE_SERIALIZER, new Identifier(Prog.MOD_ID, id.toLowerCase()), serializer);
    }

    public static void init() {
        Prog.LOGGER.info("Registering Recipe Serializers for: " + Prog.MOD_ID);
    }
}
