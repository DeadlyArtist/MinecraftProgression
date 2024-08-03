package com.prog;

import com.prog.client.gui.screen.ingame.PHandledScreens;
import com.prog.itemOrBlock.*;
import com.prog.recipe.PRecipeSerializers;
import com.prog.recipe.PRecipeTypes;
import com.prog.text.PTexts;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Prog implements ModInitializer {
    public static final String MOD_ID = "prog";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Loading Up Mod: " + MOD_ID);

        // Order matters maybe
        PTexts.init();
        PRecipeTypes.init();
        PRecipeSerializers.init();
        PItemTags.init();
        PBlockTags.init();
        PItemGroups.init();
        PItems.init();
        PBlocks.init();
    }
}