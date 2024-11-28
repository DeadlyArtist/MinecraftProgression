package com.prog.entity;

import com.prog.Prog;
import com.prog.itemOrBlock.PItems;
import com.prog.utils.LOGGER;
import com.prog.utils.StringUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;

import java.util.HashMap;
import java.util.Map;

public class PEntityTypes {

    public static class EntityTypeData {
        public String name;

        public EntityTypeData(String name) {
            this.name = name;
        }
    }

    public static final Map<EntityType<?>, EntityTypeData> data = new HashMap<>();

    public static Map<Item, EntityType<FlexibleTridentEntity>> tridentEntityTypesByItem = new HashMap<>();
    public static Map<EntityType<FlexibleTridentEntity>, Item> itemsByTridentEntityType = new HashMap<>();

    public static final EntityType<FlexibleTridentEntity> AMETHYST_TRIDENT = registerTrident("AMETHYST_TRIDENT", PItems.AMETHYST_TRIDENT);
    public static final EntityType<FlexibleTridentEntity> APOCALYPTIC_TRIDENT = registerTrident("APOCALYPTIC_TRIDENT", PItems.APOCALYPTIC_TRIDENT);
    public static final EntityType<FlexibleTridentEntity> HELL_TRIDENT = registerTrident("HELL_TRIDENT", PItems.HELL_TRIDENT);
    public static final EntityType<FlexibleTridentEntity> PRIMAL_TRIDENT = registerTrident("PRIMAL_TRIDENT", PItems.PRIMAL_TRIDENT);
    public static final EntityType<FlexibleTridentEntity> SANGUINE_TRIDENT = registerTrident("SANGUINE_TRIDENT", PItems.SANGUINE_TRIDENT);
    public static final EntityType<FlexibleTridentEntity> STELLAR_TRIDENT = registerTrident("STELLAR_TRIDENT", PItems.STELLAR_TRIDENT);


    public static EntityType<FlexibleTridentEntity> getFlexibleTridentEntityType(Item trident) {
        return tridentEntityTypesByItem.get(trident);
    }

    public static Item getFlexibleTridentItem(EntityType<FlexibleTridentEntity> trident) {
        return itemsByTridentEntityType.get(trident);
    }

    public static EntityType<FlexibleTridentEntity> registerTrident(String id, Item trident) {
        var type = register(id, EntityType.Builder.<FlexibleTridentEntity>create(FlexibleTridentEntity::new, SpawnGroup.MISC).setDimensions(0.5F, 0.5F).maxTrackingRange(4).trackingTickInterval(20));
        tridentEntityTypesByItem.put(trident, type);
        itemsByTridentEntityType.put(type, trident);
        return type;
    }

    public static <T extends Entity> EntityType<T> register(String id, EntityType.Builder<T> typeBuilder) {
        id = id.toLowerCase();
        var type = Registry.register(Registry.ENTITY_TYPE, id, typeBuilder.build(id));
        data.put(type, new EntityTypeData(StringUtils.toNormalCase(id)));
        return type;
    }

    public static void init() {
        LOGGER.info("Registering Entity Types for: " + Prog.MOD_ID);
    }
}
