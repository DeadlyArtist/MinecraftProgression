package com.deadlyartist.minecraftprogression.datagen;

import java.util.function.Consumer;

import javax.annotation.Nullable;

import com.deadlyartist.minecraftprogression.init.BlockInit;
import com.deadlyartist.minecraftprogression.init.ItemInit;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {

	public ModRecipeProvider(DataGenerator pGenerator) {
		super(pGenerator);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void buildCraftingRecipes(Consumer<FinishedRecipe> pFinishedRecipeConsumer) {
		nineToBlockRecipes(pFinishedRecipeConsumer, ItemInit.STEEL_INGOT.get(), BlockInit.STEEL_BLOCK.get());
	}
	
	   protected static void nineToBlockRecipes(Consumer<FinishedRecipe> pFinishedRecipeConsumer, ItemLike pUnpacked, ItemLike pPacked) {
		   nineToBlockRecipes(pFinishedRecipeConsumer, pUnpacked, pPacked, getSimpleRecipeName(pPacked), (String)null, getSimpleRecipeName(pUnpacked), (String)null);
		   }

		   protected static void nineToBlockRecipes(Consumer<FinishedRecipe> pFinishedRecipeConsumer, ItemLike pUnpacked, ItemLike pPacked, String pPackingRecipeName, String pPackingRecipeGroup) {
			   nineToBlockRecipes(pFinishedRecipeConsumer, pUnpacked, pPacked, pPackingRecipeName, pPackingRecipeGroup, getSimpleRecipeName(pUnpacked), (String)null);
		   }

		   protected static void nineToBlockRecipesWithUnpacking(Consumer<FinishedRecipe> pFinishedRecipeConsumer, ItemLike pUnpacked, ItemLike pPacked, String pUnpackingRecipeName, String pUnpackingRecipeGroup) {
			   nineToBlockRecipes(pFinishedRecipeConsumer, pUnpacked, pPacked, getSimpleRecipeName(pPacked), (String)null, pUnpackingRecipeName, pUnpackingRecipeGroup);
		   }

    protected static void nineToBlockRecipes(Consumer<FinishedRecipe> pFinishedRecipeConsumer, ItemLike pUnpacked, ItemLike pPacked, String pPackingRecipeName, @Nullable String pPackingRecipeGroup, String pUnpackingRecipeName, @Nullable String pUnpackingRecipeGroup) {
      ShapelessRecipeBuilder.shapeless(pUnpacked, 9).requires(pPacked).group(pUnpackingRecipeGroup).unlockedBy(getHasName(pPacked), has(pPacked)).save(pFinishedRecipeConsumer, getLocation(pUnpackingRecipeName + "s"));
      ShapedRecipeBuilder.shaped(pPacked).define('#', pUnpacked).pattern("###").pattern("###").pattern("###").group(pPackingRecipeGroup).unlockedBy(getHasName(pUnpacked), has(pUnpacked)).save(pFinishedRecipeConsumer, getLocation(pPackingRecipeName));
   }
    
    protected static ResourceLocation getLocation(String name) {
    	return new ResourceLocation("minecraftprogression", name);
    }
}
