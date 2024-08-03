package com.prog.itemOrBlock.custom;

import com.prog.Prog;
import com.prog.itemOrBlock.data.FlexibleCraftingData;
import com.prog.recipe.FlexibleShapedRecipe;
import com.prog.recipe.PRecipeTypes;
import com.prog.screen.FlexibleCraftingScreenHandler;
import com.prog.text.PTexts;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CraftingTableBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class FlexibleCraftingBlock extends Block {
    public final ScreenHandlerType<?> screenHandlerType;
    public final List<RecipeType<? extends Recipe<CraftingInventory>>> recipeTypes;
    public final Text title;
    public final int width;
    public final int height;

    public FlexibleCraftingBlock(ScreenHandlerType<?> screenHandlerType, List<RecipeType<? extends Recipe<CraftingInventory>>> recipeTypes, int width, int height, Text title, AbstractBlock.Settings settings) {
        super(settings);
        this.screenHandlerType = screenHandlerType;
        this.recipeTypes = recipeTypes;
        this.width = width;
        this.height = height;
        this.title = title;
    }

    public FlexibleCraftingBlock(FlexibleCraftingData data, AbstractBlock.Settings settings) {
        this(data.screenHandlerType.get(), data.recipeTypes, data.width, data.height, data.title, settings);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient) {
            return ActionResult.SUCCESS;
        } else {
            player.openHandledScreen(state.createScreenHandlerFactory(world, pos));
            player.incrementStat(Stats.INTERACT_WITH_CRAFTING_TABLE);
            return ActionResult.CONSUME;
        }
    }

    @Override
    public NamedScreenHandlerFactory createScreenHandlerFactory(BlockState state, World world, BlockPos pos) {
        return new SimpleNamedScreenHandlerFactory(
                (syncId, inventory, player) -> new FlexibleCraftingScreenHandler(this, screenHandlerType, recipeTypes, width, height, syncId, inventory, ScreenHandlerContext.create(world, pos)), title
        );
    }
}
