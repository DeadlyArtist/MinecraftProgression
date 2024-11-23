package com.prog.client.entity;

import com.prog.Prog;
import com.prog.entity.PEntityTypes;
import com.prog.utils.LOGGER;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.TridentEntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;

@Environment(EnvType.CLIENT)
public class PEntityRenderers {

    static {
        register(PEntityTypes.AMETHYST_TRIDENT, TridentEntityRenderer::new);
        register(PEntityTypes.APOCALYPTIC_TRIDENT, TridentEntityRenderer::new);
        register(PEntityTypes.HELL_TRIDENT, TridentEntityRenderer::new);
        register(PEntityTypes.PRIMAL_TRIDENT, TridentEntityRenderer::new);
        register(PEntityTypes.STELLAR_TRIDENT, TridentEntityRenderer::new);
    }

    private static <T extends Entity> void register(EntityType<? extends T> type, EntityRendererFactory<T> factory) {
        EntityRendererRegistry.register(type, factory);
    }

    public static void init() {
        LOGGER.info("Registering Entity Renderers for: " + Prog.MOD_ID);
    }
}
