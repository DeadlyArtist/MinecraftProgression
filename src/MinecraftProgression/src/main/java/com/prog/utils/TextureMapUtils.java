package com.prog.utils;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.client.TextureKey;
import net.minecraft.data.client.TextureMap;

import static net.minecraft.data.client.TextureMap.getSubId;

public class TextureMapUtils {
    public static TextureMap topBottomSide(Block block) {
        return new TextureMap()
                .put(TextureKey.PARTICLE, getSubId(block, "_bottom"))
                .put(TextureKey.TOP, getSubId(block, "_top"))
                .put(TextureKey.BOTTOM, getSubId(block, "_bottom"))
                .put(TextureKey.SIDE, getSubId(block, "_side"));
    }
}
