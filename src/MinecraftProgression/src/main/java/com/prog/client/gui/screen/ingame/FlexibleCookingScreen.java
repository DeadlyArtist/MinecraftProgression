package com.prog.client.gui.screen.ingame;

import com.prog.screen.FlexibleAbstractFurnaceScreenHandler;
import com.prog.screen.FlexibleCookingScreenHandler;
import com.prog.utils.ScreenUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.ingame.AbstractFurnaceScreen;
import net.minecraft.client.gui.screen.recipebook.FurnaceRecipeBookScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.FurnaceScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class FlexibleCookingScreen extends FlexibleAbstractFurnaceScreen<FlexibleCookingScreenHandler> {
    public FlexibleCookingScreen(FlexibleCookingScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title, ScreenUtils.getScreenBackgroundTexture(handler));
    }
}