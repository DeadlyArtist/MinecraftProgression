package com.prog.screen;

import com.prog.Prog;
import com.prog.itemOrBlock.data.FlexibleCraftingData;
import com.prog.recipe.PRecipeTypes;
import net.minecraft.recipe.RecipeType;
import net.minecraft.screen.CraftingScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.List;

public class PScreenHandlerTypes {
    public static final ScreenHandlerType<FlexibleCraftingScreenHandler> ASSEMBLY = register("ASSEMBLY", FlexibleCraftingScreenHandler.factory(FlexibleCraftingData.ASSEMBLY));
    public static final ScreenHandlerType<FlexibleCraftingScreenHandler> COSMIC_CONSTRUCTOR = register("COSMIC_CONSTRUCTOR", FlexibleCraftingScreenHandler.factory(FlexibleCraftingData.COSMIC_CONSTRUCTOR));

    private static <T extends ScreenHandler> ScreenHandlerType<T> register(String id, ScreenHandlerType.Factory<T> factory) {
        return Registry.register(Registry.SCREEN_HANDLER, new Identifier(Prog.MOD_ID, id.toLowerCase()), new ScreenHandlerType<>(factory));
    }

    public static void init() {
        Prog.LOGGER.info("Registering Screen Handler Types for: " + Prog.MOD_ID);
    }
}
