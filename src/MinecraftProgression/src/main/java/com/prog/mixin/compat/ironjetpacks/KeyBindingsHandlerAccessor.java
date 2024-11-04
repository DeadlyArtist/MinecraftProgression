package com.prog.mixin.compat.ironjetpacks;

import com.blakebr0.ironjetpacks.handler.KeyBindingsHandler;
import net.minecraft.client.option.KeyBinding;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = KeyBindingsHandler.class, remap = false)
public interface KeyBindingsHandlerAccessor {
    @Accessor("keyEngine")
    static KeyBinding getKeyEngine() {
        throw new AssertionError();
    }

    @Accessor("keyHover")
    static KeyBinding getKeyHover() {
        throw new AssertionError();
    }
}
