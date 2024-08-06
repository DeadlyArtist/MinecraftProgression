package com.prog;

import com.prog.entity.attribute.PEntityAttributes;
import dev.lambdaurora.lambdynlights.api.DynamicLightsInitializer;
import net.minecraft.entity.EntityType;

import static dev.lambdaurora.lambdynlights.api.DynamicLightHandlers.registerDynamicLightHandler;

public class PLights implements DynamicLightsInitializer {
    @Override
    public void onInitializeDynamicLights() {
        registerDynamicLightHandler(EntityType.PLAYER, entity -> (int) entity.getAttributeValue(PEntityAttributes.LUMINANCE));
    }
}
