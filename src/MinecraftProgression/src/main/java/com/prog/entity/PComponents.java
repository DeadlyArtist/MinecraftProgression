package com.prog.entity;

import com.prog.Prog;
import dev.onyxstudios.cca.api.v3.component.Component;
import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import dev.onyxstudios.cca.api.v3.entity.RespawnCopyStrategy;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.Identifier;

public class PComponents implements EntityComponentInitializer {
    // retrieving a type for my component or for a required dependency's
    // DONT FORGET TO ADD TO fabric.mod.json
    public static final ComponentKey<SquadComponent> SQUAD = register("SQUAD", SquadComponent.class);
    public static final ComponentKey<LivingEntityComponent> LIVING_ENTITY = register("LIVING_ENTITY", LivingEntityComponent.class);
    public static final ComponentKey<PlayerComponent> PLAYER = register("PLAYER", PlayerComponent.class);


    public static <T extends Component> ComponentKey<T> register(String id, Class<T> componentClass) {
        var key = ComponentRegistry.getOrCreate(Identifier.of(Prog.MOD_ID, id.toLowerCase()), componentClass);
        return key;
    }

    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.registerFor(MobEntity.class, SQUAD, SquadComponent::new);
        registry.registerFor(LivingEntity.class, LIVING_ENTITY, LivingEntityComponent::new);
        registry.registerForPlayers(LIVING_ENTITY, LivingEntityComponent::new, RespawnCopyStrategy.ALWAYS_COPY);
        registry.registerForPlayers(PLAYER, PlayerComponent::new, RespawnCopyStrategy.ALWAYS_COPY);
    }
}
