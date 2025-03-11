package com.prog.event;

import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.server.command.ServerCommandSource;

public class CommandEvents {
    public static final Event<RegisterCommands> REGISTER_COMMANDS = EventFactory.createArrayBacked(RegisterCommands.class, callbacks -> (stack) -> {
        for (RegisterCommands callback : callbacks) {
            callback.register(stack);
        }
    });

    @FunctionalInterface
    public interface RegisterCommands {
        void register(CommandDispatcher<ServerCommandSource> dispatcher);
    }
}
