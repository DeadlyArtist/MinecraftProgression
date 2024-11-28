package com.prog.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.block.Block;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.model.EntityModelLoader;

import java.util.Map;

public class RendererEvents {
    public static final Event<IReloadModels> RELOAD_MODELS = EventFactory.createArrayBacked(IReloadModels.class, callbacks -> (loader) -> {
        for (var callback : callbacks) {
            callback.onReloadModels(loader);
        }
    });

    public static final Event<IRenderLayers> RENDER_LAYERS = EventFactory.createArrayBacked(IRenderLayers.class, callbacks -> (loader) -> {
        for (var callback : callbacks) {
            callback.onRenderLayers(loader);
        }
    });

    @FunctionalInterface
    public interface IReloadModels {
        void onReloadModels(EntityModelLoader loader);
    }

    @FunctionalInterface
    public interface IRenderLayers {
        void onRenderLayers(Map<Block, RenderLayer> blocks);
    }
}
