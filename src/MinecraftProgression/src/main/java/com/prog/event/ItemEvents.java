package com.prog.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;

import java.util.List;

public class ItemEvents {
    public static final Event<ItemEvents.AppendTooltip> APPEND_TOOLTIP = EventFactory.createArrayBacked(ItemEvents.AppendTooltip.class, callbacks -> (stack, context, lines) -> {
        for (ItemEvents.AppendTooltip callback : callbacks) {
            callback.append(stack, context, lines);
        }
    });

    @FunctionalInterface
    public interface AppendTooltip {
        void append(ItemStack stack, TooltipContext context, List<Text> lines);
    }
}
