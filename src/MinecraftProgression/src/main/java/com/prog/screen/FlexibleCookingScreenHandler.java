package com.prog.screen;

import com.prog.itemOrBlock.data.FlexibleCookingData;
import com.prog.itemOrBlock.data.FlexibleCraftingData;
import com.prog.itemOrBlock.entity.FlexibleAbstractFurnaceBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.recipe.AbstractCookingRecipe;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.book.RecipeBookCategory;
import net.minecraft.screen.AbstractFurnaceScreenHandler;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandlerType;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class FlexibleCookingScreenHandler extends FlexibleAbstractFurnaceScreenHandler {
    public FlexibleCookingScreenHandler(ScreenHandlerType<?> screenHandlerType, Supplier<Map<Item, Integer>> fuelMap, List<RecipeType<? extends AbstractCookingRecipe>> recipeTypes, int syncId, PlayerInventory playerInventory) {
        super(screenHandlerType, fuelMap, recipeTypes, null, syncId, playerInventory);
    }

    public FlexibleCookingScreenHandler(ScreenHandlerType<?> screenHandlerType, Supplier<Map<Item, Integer>> fuelMap, List<RecipeType<? extends AbstractCookingRecipe>> recipeTypes, int syncId, PlayerInventory playerInventory, Inventory inventory, PropertyDelegate propertyDelegate) {
        super(screenHandlerType, fuelMap, recipeTypes, null, syncId, playerInventory, inventory, propertyDelegate);
    }

    public FlexibleCookingScreenHandler(FlexibleCookingData data, int syncId, PlayerInventory playerInventory, Inventory inventory, PropertyDelegate propertyDelegate) {
        this(data.screenHandlerType.get(), data.fuelMap, data.supportedRecipeTypes, syncId, playerInventory, inventory, propertyDelegate);
    }

    public static ScreenHandlerType.Factory<FlexibleCookingScreenHandler> factory(Supplier<ScreenHandlerType<?>> screenHandlerTypeSupplier, Supplier<Map<Item, Integer>> fuelMap, List<RecipeType<? extends AbstractCookingRecipe>> recipeTypes) {
        return (syncId, playerInventory) -> new FlexibleCookingScreenHandler(screenHandlerTypeSupplier.get(), fuelMap, recipeTypes, syncId, playerInventory);
    }

    public static ScreenHandlerType.Factory<FlexibleCookingScreenHandler> factory(FlexibleCookingData data) {
        return factory(data.screenHandlerType, data.fuelMap, data.supportedRecipeTypes);
    }
}