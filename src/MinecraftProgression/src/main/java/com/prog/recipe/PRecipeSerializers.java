package com.prog.recipe;

import com.prog.Prog;
import com.prog.itemOrBlock.data.FlexibleCookingData;
import com.prog.itemOrBlock.data.FlexibleCraftingData;
import com.prog.utils.LOGGER;
import net.minecraft.recipe.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class PRecipeSerializers {
    // Crafting
    public static RecipeSerializer<?> ASSEMBLY_SHAPED = register("ASSEMBLY_SHAPED", new FlexibleShapedRecipe.Serializer(FlexibleCraftingData.ASSEMBLY));
    public static RecipeSerializer<?> ASSEMBLY_SHAPELESS = register("ASSEMBLY_SHAPELESS", new FlexibleShapelessRecipe.Serializer(FlexibleCraftingData.ASSEMBLY));
    public static RecipeSerializer<?> COSMIC_CONSTRUCTOR_SHAPED = register("COSMIC_CONSTRUCTOR_SHAPED", new FlexibleShapedRecipe.Serializer(FlexibleCraftingData.COSMIC_CONSTRUCTOR));
    public static RecipeSerializer<?> COSMIC_CONSTRUCTOR_SHAPELESS = register("COSMIC_CONSTRUCTOR_SHAPELESS", new FlexibleShapelessRecipe.Serializer(FlexibleCraftingData.COSMIC_CONSTRUCTOR));

    public static final RecipeSerializer<NbtSmithingRecipe> NBT_SMITHING = registerNbtRecipeSerializer("NBT_SMITHING", new IngredientRecipe.Serializer<>((id, base, ingredient, result, serializer) -> new NbtSmithingRecipe(id, base, ingredient, result, PRecipeTypes.NBT_SMITHING, serializer)));

    // Smelting
    public static CookingRecipeSerializer<?> INCINERATOR = register("INCINERATOR", FlexibleCookingRecipe.createSerializer(FlexibleCookingData.INCINERATOR));
    public static CookingRecipeSerializer<?> COSMIC_INCUBATOR = register("COSMIC_INCUBATOR", FlexibleCookingRecipe.createSerializer(FlexibleCookingData.COSMIC_INCUBATOR));

    public static <S extends RecipeSerializer<T>, T extends Recipe<?>> S register(String id, S serializer) {
        return Registry.register(Registry.RECIPE_SERIALIZER, new Identifier(Prog.MOD_ID, id.toLowerCase()), serializer);
    }

    public static <S extends RecipeSerializer<T>, T extends Recipe<?>> S registerNbtRecipeSerializer(String id, S recipeSerializer) {
        Identifier serializerId = new Identifier(Prog.MOD_ID, id.toLowerCase());
        //RecipeTypeHelper.addToSyncBlacklist(serializerId);
        return Registry.register(Registry.RECIPE_SERIALIZER, serializerId, recipeSerializer);
    }

    public static void init() {
        LOGGER.info("Registering Recipe Serializers for: " + Prog.MOD_ID);
    }
}
