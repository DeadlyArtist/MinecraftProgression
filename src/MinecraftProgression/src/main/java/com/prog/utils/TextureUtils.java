package com.prog.utils;

import net.minecraft.util.Identifier;

public class TextureUtils {
    public static final String TEXTURE_BASE_PATH = "textures";
    public static final String GUI_SUB_PATH = "gui";
    public static final String CONTAINER_SUB_PATH = "container";

    public static Identifier getGuiTexture(String namespace, String name) {
        return Identifier.of(namespace, PathUtils.create(TEXTURE_BASE_PATH, GUI_SUB_PATH, name + ".png"));
    }

    public static Identifier getGuiContainerTexture(String namespace, String name) {
        return getGuiTexture(namespace, PathUtils.create(CONTAINER_SUB_PATH, name));
    }
}
