package com.deadlyartist.minecraftprogression.init;

import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;

public class ModTiers {
	public static final ForgeTier STEELBOUND_IRON = new ForgeTier(2, 0, 6.0F, 2.0F, 14, BlockTags.NEEDS_IRON_TOOL, () -> {
      return Ingredient.of(Items.IRON_INGOT);
   });
	
	public static final ForgeTier DIAMOND = new ForgeTier(3, 0, 8.0F, 3.0F, 10, BlockTags.NEEDS_DIAMOND_TOOL, () -> {
      return Ingredient.of(Items.DIAMOND);
   });
	
	public static final ForgeTier NETHERITE = new ForgeTier(3, 0, 8.0F, 3.0F, 10, BlockTags.NEEDS_DIAMOND_TOOL, () -> {
      return Ingredient.of(Items.DIAMOND);
   });
	
	public static final ForgeTier GOLD = new ForgeTier(5, 0, 12.0F, 5.0F, 22, BlockTags.NEEDS_DIAMOND_TOOL, () -> {
      return Ingredient.of(Items.GOLD_INGOT);
   });

}
