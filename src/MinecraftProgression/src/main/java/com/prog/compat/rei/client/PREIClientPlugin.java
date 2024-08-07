package com.prog.compat.rei.client;

import com.prog.client.gui.screen.ingame.FlexibleCookingScreen;
import com.prog.client.gui.screen.ingame.FlexibleCraftingScreen;
import com.prog.compat.rei.FlexibleCookingDisplay;
import com.prog.compat.rei.FlexibleCraftingDisplay;
import com.prog.compat.rei.PREICategories;
import com.prog.itemOrBlock.data.FlexibleCookingData;
import com.prog.itemOrBlock.data.FlexibleCraftingData;
import com.prog.recipe.FlexibleCookingRecipe;
import com.prog.recipe.FlexibleCraftingRecipe;
import com.prog.recipe.PRecipeTypes;
import com.prog.screen.FlexibleCookingScreenHandler;
import com.prog.screen.FlexibleCraftingScreenHandler;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.client.registry.screen.ScreenRegistry;
import me.shedaniel.rei.api.client.registry.transfer.TransferHandlerRegistry;
import me.shedaniel.rei.api.client.registry.transfer.simple.SimpleTransferHandler;
import me.shedaniel.rei.api.common.entry.EntryStack;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.ingame.BrewingStandScreen;
import net.minecraft.client.gui.screen.ingame.CraftingScreen;
import net.minecraft.client.gui.screen.ingame.FurnaceScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.recipe.*;
import net.minecraft.screen.*;

@Environment(EnvType.CLIENT)
public class PREIClientPlugin implements REIClientPlugin {
    @Override
    public void registerCategories(CategoryRegistry registry) {
        registerCategory(registry, FlexibleCraftingData.ASSEMBLY);
        registerCategory(registry, FlexibleCraftingData.COSMIC_CONSTRUCTOR);
        registerCategory(registry, FlexibleCookingData.INCINERATOR);
    }

    @Override
    public void registerDisplays(DisplayRegistry registry) {
        registry.registerRecipeFiller(CraftingRecipe.class, RecipeType.CRAFTING, recipe -> new FlexibleCraftingDisplay(FlexibleCraftingData.ASSEMBLY, recipe));
        registry.registerRecipeFiller(FlexibleCraftingRecipe.class, PRecipeTypes.ASSEMBLY, recipe -> new FlexibleCraftingDisplay(FlexibleCraftingData.ASSEMBLY, recipe));

        registry.registerRecipeFiller(CraftingRecipe.class, RecipeType.CRAFTING, recipe -> new FlexibleCraftingDisplay(FlexibleCraftingData.COSMIC_CONSTRUCTOR, recipe));
        registry.registerRecipeFiller(FlexibleCraftingRecipe.class, PRecipeTypes.ASSEMBLY, recipe -> new FlexibleCraftingDisplay(FlexibleCraftingData.COSMIC_CONSTRUCTOR, recipe));
        registry.registerRecipeFiller(FlexibleCraftingRecipe.class, PRecipeTypes.COSMIC_CONSTRUCTOR, recipe -> new FlexibleCraftingDisplay(FlexibleCraftingData.COSMIC_CONSTRUCTOR, recipe));

        registry.registerRecipeFiller(BlastingRecipe.class, RecipeType.BLASTING, recipe -> new FlexibleCookingDisplay(FlexibleCookingData.INCINERATOR, recipe));
        registry.registerRecipeFiller(FlexibleCookingRecipe.class, PRecipeTypes.INCINERATOR, recipe -> new FlexibleCookingDisplay(FlexibleCookingData.INCINERATOR, recipe));
    }

    @Override
    public void registerScreens(ScreenRegistry registry) {
        registry.registerContainerClickArea(new Rectangle(88, 32, 28, 23), FlexibleCraftingScreen.class, PREICategories.ASSEMBLY);
        registry.registerContainerClickArea(new Rectangle(88, 32, 28, 23), FlexibleCraftingScreen.class, PREICategories.COSMIC_CONSTRUCTOR);
        registry.registerContainerClickArea(new Rectangle(78, 32, 28, 23), FlexibleCookingScreen.class, PREICategories.INCINERATOR);
    }

    @Override
    public void registerTransferHandlers(TransferHandlerRegistry registry) {
        registry.register(SimpleTransferHandler.create(FlexibleCraftingScreenHandler.class, PREICategories.ASSEMBLY, new SimpleTransferHandler.IntRange(1, FlexibleCraftingData.ASSEMBLY.height * FlexibleCraftingData.ASSEMBLY.width)));
        registry.register(SimpleTransferHandler.create(FlexibleCraftingScreenHandler.class, PREICategories.ASSEMBLY, new SimpleTransferHandler.IntRange(1, FlexibleCraftingData.COSMIC_CONSTRUCTOR.height * FlexibleCraftingData.COSMIC_CONSTRUCTOR.width)));

        registry.register(SimpleTransferHandler.create(FlexibleCookingScreenHandler.class, PREICategories.INCINERATOR, new SimpleTransferHandler.IntRange(0, 1)));
    }

    public static void registerCategory(CategoryRegistry registry, FlexibleCraftingData data) {
        registry.add(new FlexibleCraftingCategory(data));
        registry.addWorkstations(data.categoryIdentifier.get(), EntryStacks.of(data.block.get()));
    }

    public static void registerCategory(CategoryRegistry registry, FlexibleCookingData data) {
        registry.add(new FlexibleCookingCategory(data));
        registry.addWorkstations(data.categoryIdentifier.get(), EntryStacks.of(data.block.get()));
    }
}
