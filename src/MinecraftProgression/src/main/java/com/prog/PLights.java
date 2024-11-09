package com.prog;

import com.prog.entity.PComponents;
import com.prog.entity.attribute.PEntityAttributes;
import dev.lambdaurora.lambdynlights.api.DynamicLightsInitializer;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;

import static dev.lambdaurora.lambdynlights.api.DynamicLightHandlers.registerDynamicLightHandler;

public class PLights implements DynamicLightsInitializer {
    @Override
    public void onInitializeDynamicLights() {
        registerDynamicLightHandler(EntityType.PLAYER, entity -> (entity instanceof PlayerEntity && PComponents.PLAYER.get(entity).headlightDisabled) ? 0 : (int) entity.getAttributeValue(PEntityAttributes.LUMINANCE));
    }
}
