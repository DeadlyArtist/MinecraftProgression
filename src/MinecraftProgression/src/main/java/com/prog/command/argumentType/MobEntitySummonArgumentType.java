package com.prog.command.argumentType;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;

import java.util.Arrays;
import java.util.Collection;

import net.minecraft.entity.mob.MobEntity;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class MobEntitySummonArgumentType implements ArgumentType<Identifier> {
    private static final Collection<String> EXAMPLES = Arrays.asList("minecraft:skeleton", "zombie");
    public static final DynamicCommandExceptionType NOT_FOUND_EXCEPTION = new DynamicCommandExceptionType(id -> Text.translatable("entity.notFound", id));

    public static MobEntitySummonArgumentType entitySummon() {
        return new MobEntitySummonArgumentType();
    }

    public static Identifier getEntitySummon(CommandContext<ServerCommandSource> context, String name) throws CommandSyntaxException {
        return validate(context.getArgument(name, Identifier.class));
    }

    private static Identifier validate(Identifier id) throws CommandSyntaxException {
        // sadly, cannot check for mob entities
        Registry.ENTITY_TYPE.getOrEmpty(id).filter(e -> e.isSummonable()).orElseThrow(() -> NOT_FOUND_EXCEPTION.create(id));
        return id;
    }

    public Identifier parse(StringReader stringReader) throws CommandSyntaxException {
        return validate(Identifier.fromCommandInput(stringReader));
    }

    @Override
    public Collection<String> getExamples() {
        return EXAMPLES;
    }
}
