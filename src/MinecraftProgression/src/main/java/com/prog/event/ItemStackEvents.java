package com.prog.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;

public class ItemStackEvents {
    public static final Event<ItemStackCtor> ITEM_STACK_CTOR = EventFactory.createArrayBacked(ItemStackCtor.class, callbacks -> (stack) -> {
        for (ItemStackCtor callback : callbacks) {
            callback.ctor(stack);
        }
    });

    @FunctionalInterface
    public interface ItemStackCtor {
        void ctor(ItemStack stack);
    }
}
