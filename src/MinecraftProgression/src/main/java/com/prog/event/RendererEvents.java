package com.prog.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.render.entity.model.EntityModelLoader;

public class RendererEvents {
    public static final Event<IReloadModels> RELOAD_MODELS = EventFactory.createArrayBacked(IReloadModels.class, callbacks -> (loader) -> {
        for (var callback : callbacks) {
            callback.onReloadModels(loader);
        }
    });

    @FunctionalInterface
    public interface IReloadModels {
        void onReloadModels(EntityModelLoader loader);
    }
}
