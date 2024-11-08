package com.prog.screen;

import com.prog.Prog;
import com.prog.itemOrBlock.data.FlexibleCookingData;
import com.prog.itemOrBlock.data.FlexibleCraftingData;
import com.prog.utils.LOGGER;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class PScreenHandlerTypes {
    public static final ScreenHandlerType<FlexibleCraftingScreenHandler> ASSEMBLY = register("ASSEMBLY", FlexibleCraftingScreenHandler.factory(FlexibleCraftingData.ASSEMBLY));
    public static final ScreenHandlerType<FlexibleCraftingScreenHandler> COSMIC_CONSTRUCTOR = register("COSMIC_CONSTRUCTOR", FlexibleCraftingScreenHandler.factory(FlexibleCraftingData.COSMIC_CONSTRUCTOR));
    public static final ScreenHandlerType<FlexibleCookingScreenHandler> INCINERATOR = register("INCINERATOR", FlexibleCookingScreenHandler.factory(FlexibleCookingData.INCINERATOR));
    public static final ScreenHandlerType<FlexibleCookingScreenHandler> COSMIC_INCUBATOR = register("COSMIC_INCUBATOR", FlexibleCookingScreenHandler.factory(FlexibleCookingData.COSMIC_INCUBATOR));

    private static <T extends ScreenHandler> ScreenHandlerType<T> register(String id, ScreenHandlerType.Factory<T> factory) {
        return Registry.register(Registry.SCREEN_HANDLER, new Identifier(Prog.MOD_ID, id.toLowerCase()), new ScreenHandlerType<>(factory));
    }

    public static void init() {
        LOGGER.info("Registering Screen Handler Types for: " + Prog.MOD_ID);
    }
}
