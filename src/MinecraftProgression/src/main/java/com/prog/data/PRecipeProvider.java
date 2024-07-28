package com.prog.data;

import com.prog.item.PItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.CookingRecipeJsonBuilder;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.RecipeProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.book.RecipeCategory;

import java.util.List;
import java.util.function.Consumer;

public class PRecipeProvider extends FabricRecipeProvider {
    public PRecipeProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generate(Consumer<RecipeJsonProvider> exporter) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, PItems.STEEL_BINDING).pattern("# #").pattern(" # ").pattern("# #").input('#', PItems.STEEL).offerTo(exporter);
        CookingRecipeJsonBuilder.create(Ingredient.ofItems(Items.IRON_INGOT), RecipeCategory.MISC, PItems.STEEL, 0.45F, 300, RecipeSerializer.SMELTING).offerTo(exporter);
    }
}
