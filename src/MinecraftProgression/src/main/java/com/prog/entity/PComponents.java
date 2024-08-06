package com.prog.entity;

import com.prog.Prog;
import com.prog.itemOrBlock.PItemGroups;
import dev.onyxstudios.cca.api.v3.component.Component;
import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import dev.onyxstudios.cca.api.v3.entity.RespawnCopyStrategy;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

public class PComponents implements EntityComponentInitializer {
    // retrieving a type for my component or for a required dependency's
    public static final ComponentKey<PComponent> COMPONENT = register("COMPONENT", PComponent.class);


    public static <T extends Component> ComponentKey<T> register(String id, Class<T> componentClass) {
        var key = ComponentRegistry.getOrCreate(Identifier.of(Prog.MOD_ID, id.toLowerCase()), componentClass);
        return key;
    }

    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.registerFor(LivingEntity.class, COMPONENT, PComponent::new);
        registry.registerForPlayers(COMPONENT, PComponent::new, RespawnCopyStrategy.ALWAYS_COPY);
    }
}
