package com.prog.client;

import com.prog.client.gui.screen.ingame.PHandledScreens;
import com.prog.entity.PComponents;
import com.prog.event.ItemEvents;
import com.prog.itemOrBlock.GourmetFoods;
import com.prog.text.PTexts;
import com.prog.utils.UpgradeUtils;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Formatting;

@Environment(EnvType.CLIENT)
public class PClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        PHandledScreens.init();

        // Events
        ItemEvents.APPEND_TOOLTIP.register((stack, context, lines) -> {
            var player = MinecraftClient.getInstance().player;
            var item = stack.getItem();
            if (GourmetFoods.data.containsKey(item)) {
                var component = PComponents.LIVING_ENTITY.get(player);
                if (component.hasEaten(stack.getItem())) {
                    lines.add(PTexts.GOURMET_HAS_EATEN_TOOLTIP.get().copy().formatted(Formatting.GRAY).formatted(Formatting.ITALIC));
                } else {
                    lines.add(PTexts.GOURMET_NOT_EATEN_TOOLTIP.get().copy().formatted(Formatting.RED).formatted(Formatting.ITALIC));
                }
            }

            UpgradeUtils.addUpgradeTooltip(lines, stack);
        });
    }
}
