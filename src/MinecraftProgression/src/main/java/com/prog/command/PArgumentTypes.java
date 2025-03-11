package com.prog.command;

import com.prog.command.argumentType.MobEntitySummonArgumentType;
import net.minecraft.command.argument.ArgumentTypes;
import net.minecraft.command.argument.EntitySummonArgumentType;
import net.minecraft.command.argument.serialize.ArgumentSerializer;
import net.minecraft.command.argument.serialize.ConstantArgumentSerializer;
import net.minecraft.util.registry.Registry;

public class PArgumentTypes {
    public static void registerAll(Registry<ArgumentSerializer<?, ?>> registry) {
        ArgumentTypes.register(registry, "mob_entity_summon", MobEntitySummonArgumentType.class, ConstantArgumentSerializer.of(MobEntitySummonArgumentType::entitySummon));
    }
}
