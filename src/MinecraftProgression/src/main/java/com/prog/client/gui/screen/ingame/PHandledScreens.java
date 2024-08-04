package com.prog.client.gui.screen.ingame;

import com.google.common.collect.Maps;
import com.mojang.logging.LogUtils;

import java.util.Map;

import com.prog.Prog;
import com.prog.screen.PScreenHandlerTypes;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.gui.screen.ingame.ScreenHandlerProvider;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.text.Text;
import net.minecraft.util.registry.Registry;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

@Environment(EnvType.CLIENT)
public class PHandledScreens {
    static {
        register(PScreenHandlerTypes.ASSEMBLY, FlexibleCraftingScreen::new);
        register(PScreenHandlerTypes.COSMIC_CONSTRUCTOR, FlexibleCraftingScreen::new);
        register(PScreenHandlerTypes.INCINERATOR, FlexibleCookingScreen::new);
    }

    public static <M extends ScreenHandler, U extends Screen & ScreenHandlerProvider<M>> void register(
            ScreenHandlerType<? extends M> type, HandledScreens.Provider<M, U> provider
    ) {
        HandledScreens.register(type, provider);
    }

    public static void init() {
        Prog.LOGGER.info("Registering Handled Screens for: " + Prog.MOD_ID);
    }
}
