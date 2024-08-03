package com.prog.client;

import com.prog.client.gui.screen.ingame.PHandledScreens;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class PClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        PHandledScreens.init();
    }
}
