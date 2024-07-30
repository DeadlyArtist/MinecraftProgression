package com.prog.data;

import com.prog.Prog;
import com.prog.itemOrBlock.PBlocks;
import com.prog.itemOrBlock.PItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.CookingRecipeJsonBuilder;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.CookingRecipeSerializer;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.List;
import java.util.function.Consumer;

public class PRecipeProvider extends FabricRecipeProvider {
    public PRecipeProvider(FabricDataGenerator generator) {
        super(generator);
    }

    public static class Input {
        public final char identifier;
        public final ItemConvertible itemProvider;
        public Input(char identifier, ItemConvertible itemProvider) {
            this.identifier = identifier;
            this.itemProvider = itemProvider;
        }
    }

    public static char findFirstNonSpaceChar(List<String> strings) {
        for (String string : strings) {
            for (char ch : string.toCharArray()) {
                if (ch != ' ') {
                    return ch;
                }
            }
        }
        throw new RuntimeException("No non-space character found in the list");
    }

    public static void offerShapedRecipe(Consumer<RecipeJsonProvider> exporter, List<String> pattern, List<Input> inputs, ItemConvertible output) {
        ShapedRecipeJsonBuilder builder = ShapedRecipeJsonBuilder.create(output).criterion(hasItem(inputs.get(0).itemProvider), conditionsFromItem(inputs.get(0).itemProvider));
        pattern.forEach(line -> builder.pattern(line));
        inputs.forEach(input -> builder.input(input.identifier, input.itemProvider));
        builder.offerTo(exporter);
    }

    public static void offerShapedRecipe(Consumer<RecipeJsonProvider> exporter, List<String> pattern, ItemConvertible input, ItemConvertible output) {
        offerShapedRecipe(exporter, pattern, List.of(new Input(findFirstNonSpaceChar(pattern), input)), output);
    }

    public static void offerCookingRecipe(
            Consumer<RecipeJsonProvider> exporter,
            CookingRecipeSerializer<?> serializer,
            ItemConvertible input,
            ItemConvertible output,
            int cookingTime,
            float experience
    ) {
        CookingRecipeJsonBuilder.create(Ingredient.ofItems(input), output, experience, cookingTime, serializer)
                .criterion(hasItem(input), conditionsFromItem(input))
                .offerTo(exporter, new Identifier(Prog.MOD_ID, getItemPath(output) + "_from_" + Registry.RECIPE_SERIALIZER.getId(serializer).getPath()));
    }

    @Override
    public void generateRecipes(Consumer<RecipeJsonProvider> exporter) {
        offerShapedRecipe(exporter, List.of("# #", " # ", "# #"), PItems.STEEL_INGOT, PItems.STEEL_BINDING);
        offerCookingRecipe(exporter, RecipeSerializer.SMELTING, Items.IRON_INGOT, PItems.STEEL_INGOT, 300,0.45F);
        offerReversibleCompactingRecipes(exporter, PItems.STEEL_INGOT, PBlocks.STEEL_BLOCK);
    }
}
