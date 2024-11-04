package com.prog.mixin.compat.ironjetpacks;

import com.blakebr0.ironjetpacks.handler.ColorHandler;
import net.minecraft.item.ItemConvertible;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(value = ColorHandler.class, remap = false)
public interface ColorHandlerAccessor {

    @Accessor("COLORED_ITEMS")
    static List<ItemConvertible> getColoredItems() {
        throw new AssertionError();
    }
}
