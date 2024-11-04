package com.prog.utils;

import net.fabricmc.loader.api.FabricLoader;

public class XCompat {
    public static boolean isModLoaded(String id) {
        return FabricLoader.getInstance().isModLoaded(id);
    }
}
