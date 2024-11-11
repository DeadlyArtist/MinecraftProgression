package com.prog.event;

import com.llamalad7.mixinextras.sugar.Local;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

public class ItemEvents {
    public static final Event<ItemEvents.AppendTooltip> APPEND_TOOLTIP = EventFactory.createArrayBacked(ItemEvents.AppendTooltip.class, callbacks -> (stack, context, lines) -> {
        for (ItemEvents.AppendTooltip callback : callbacks) {
            callback.append(stack, context, lines);
        }
    });

    public static final Event<ItemEvents.AppendStacks> APPEND_STACKS = EventFactory.createArrayBacked(ItemEvents.AppendStacks.class, callbacks -> (group, stacks, item) -> {
        for (ItemEvents.AppendStacks callback : callbacks) {
            callback.append(group, stacks, item);
        }
    });

    @FunctionalInterface
    public interface AppendTooltip {
        void append(ItemStack stack, TooltipContext context, List<Text> lines);
    }

    @FunctionalInterface
    public interface AppendStacks {
        void append(ItemGroup group, DefaultedList<ItemStack> stacks, Item item);
    }
}
