package com.prog.command;

import com.mojang.brigadier.CommandDispatcher;
import com.prog.command.custom.SummonRankedCommand;
import net.minecraft.server.command.ServerCommandSource;

public class PCommands {

    public static void registerAll(CommandDispatcher<ServerCommandSource> dispatcher) {
        SummonRankedCommand.register(dispatcher);
    }
}
