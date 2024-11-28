package com.prog.mixin;

import com.prog.event.RendererEvents;
import com.prog.itemOrBlock.PBlocks;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.RenderLayers;
import org.spongepowered.asm.mixin.Mixin;

import com.google.common.collect.Maps;
import net.minecraft.block.Block;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.Util;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Environment(EnvType.CLIENT)
@Mixin(RenderLayers.class)
public class RenderLayersMixin {
    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void injectRenderLayers(CallbackInfo ci) {
        RendererEvents.RENDER_LAYERS.invoker().onRenderLayers(RenderLayers.BLOCKS);
    }
}
