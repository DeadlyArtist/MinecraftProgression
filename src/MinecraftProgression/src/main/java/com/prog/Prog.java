package com.prog;

import com.prog.block.PBlocks;
import com.prog.item.PItemGroups;
import com.prog.item.PItems;
import net.fabricmc.api.ModInitializer;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Prog implements ModInitializer {
    public static final String MOD_ID = "prog";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Loading Up Mod: " + MOD_ID);

        PItemGroups.init();
        PItems.init();
        PBlocks.init();
    }
}