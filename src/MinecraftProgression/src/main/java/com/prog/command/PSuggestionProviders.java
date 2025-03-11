package com.prog.command;

import com.mojang.brigadier.suggestion.SuggestionProvider;
import net.minecraft.command.CommandSource;
import net.minecraft.command.suggestion.SuggestionProviders;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.registry.Registry;

public class PSuggestionProviders {
    public static final SuggestionProvider<ServerCommandSource> SUMMONABLE_MOB_ENTITIES = register(
            new Identifier("summonable_mob_entities"),
            (context, builder) -> CommandSource.suggestFromIdentifier(
                    Registry.ENTITY_TYPE.stream().filter(e -> e.isSummonable()),
                    builder,
                    EntityType::getId,
                    entityType -> Text.translatable(Util.createTranslationKey("entity", EntityType.getId(entityType)))
            )
    );

    public static <S extends CommandSource> SuggestionProvider<S> register(Identifier id, SuggestionProvider<CommandSource> provider) {
        return SuggestionProviders.register(id, provider);
    }
}
