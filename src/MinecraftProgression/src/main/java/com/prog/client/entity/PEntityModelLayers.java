package com.prog.client.entity;

import com.prog.Prog;
import com.prog.client.entity.custom.EdgedTridentEntityModel;
import com.prog.client.item.PItemModels;
import com.prog.client.utils.RenderUtils;
import com.prog.itemOrBlock.PItems;
import com.prog.utils.LOGGER;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.entity.model.*;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

@Environment(EnvType.CLIENT)
public class PEntityModelLayers {
    public static String MAIN = "main";

    public static Map<Item, Map<String, EntityModelLayer>> layersByItem = new HashMap<>();
    public static Map<EntityModelLayer, Function<ModelPart, Model>> modelProvidersByLayer = new HashMap<>();
    public static Map<EntityModelLayer, Model> modelsByLayer = new HashMap<>();

    // Shields
    public static final EntityModelLayer STEEL_SHIELD = registerShield("STEEL_SHIELD", PItems.STEEL_SHIELD, part -> new ShieldEntityModel(part), ShieldEntityModel::getTexturedModelData);
    public static final EntityModelLayer ULTIMATE_DIAMOND_SHIELD = registerShield("ULTIMATE_DIAMOND_SHIELD", PItems.ULTIMATE_DIAMOND_SHIELD, part -> new ShieldEntityModel(part), ShieldEntityModel::getTexturedModelData);
    public static final EntityModelLayer REFINED_OBSIDIAN_SHIELD = registerShield("REFINED_OBSIDIAN_SHIELD", PItems.REFINED_OBSIDIAN_SHIELD, part -> new ShieldEntityModel(part), ShieldEntityModel::getTexturedModelData);
    public static final EntityModelLayer TITAN_SHIELD = registerShield("TITAN_SHIELD", PItems.TITAN_SHIELD, part -> new ShieldEntityModel(part), ShieldEntityModel::getTexturedModelData);
    public static final EntityModelLayer PRIMAL_NETHERITE_SHIELD = registerShield("PRIMAL_NETHERITE_SHIELD", PItems.PRIMAL_NETHERITE_SHIELD, part -> new ShieldEntityModel(part), ShieldEntityModel::getTexturedModelData);
    public static final EntityModelLayer VERDITE_SHIELD = registerShield("VERDITE_SHIELD", PItems.VERDITE_SHIELD, part -> new ShieldEntityModel(part), ShieldEntityModel::getTexturedModelData);
    public static final EntityModelLayer END_SHIELD = registerShield("END_SHIELD", PItems.END_SHIELD, part -> new ShieldEntityModel(part), ShieldEntityModel::getTexturedModelData);

    // Tridents
    public static final EntityModelLayer AMETHYST_TRIDENT = registerTrident("AMETHYST_TRIDENT", PItems.AMETHYST_TRIDENT, part -> new TridentEntityModel(part), TridentEntityModel::getTexturedModelData);
    public static final EntityModelLayer APOCALYPTIC_TRIDENT = registerTrident("APOCALYPTIC_TRIDENT", PItems.APOCALYPTIC_TRIDENT, part -> new TridentEntityModel(part), EdgedTridentEntityModel::getTexturedModelData);
    public static final EntityModelLayer HELL_TRIDENT = registerTrident("HELL_TRIDENT", PItems.HELL_TRIDENT, part -> new TridentEntityModel(part), TridentEntityModel::getTexturedModelData);
    public static final EntityModelLayer PRIMAL_TRIDENT = registerTrident("PRIMAL_TRIDENT", PItems.PRIMAL_TRIDENT, part -> new TridentEntityModel(part), TridentEntityModel::getTexturedModelData);
    public static final EntityModelLayer STELLAR_TRIDENT = registerTrident("STELLAR_TRIDENT", PItems.STELLAR_TRIDENT, part -> new TridentEntityModel(part), EdgedTridentEntityModel::getTexturedModelData);



    public static EntityModelLayer registerShield(String name, Item item, Function<ModelPart, Model> modelProvider, Supplier<TexturedModelData> dataProvider) {
        var model = registerMain(name, item, modelProvider, dataProvider);
        BuiltinItemRendererRegistry.INSTANCE.register(item, (stack, mode, matrices, vertexConsumers, light, overlay) -> {
            RenderUtils.renderBanner(stack, matrices, vertexConsumers, light, overlay, (ShieldEntityModel) getModel(item), RenderUtils.getShieldBaseSpriteIdentifier(item), RenderUtils.getShieldBaseNoPatternSpriteIdentifier(item));
        });
        return model;
    }

    public static EntityModelLayer registerTrident(String name, Item item, Function<ModelPart, Model> modelProvider, Supplier<TexturedModelData> dataProvider) {
        var model = registerMain(name, item, modelProvider, dataProvider);
        PItemModels.registerTrident(name, item);
        return model;
    }

    public static EntityModelLayer registerMain(String name, Item item, Function<ModelPart, Model> modelProvider, Supplier<TexturedModelData> dataProvider) {
        return register(name, MAIN, item, modelProvider, dataProvider);
    }

    public static EntityModelLayer register(String name, String layer, Item item, Function<ModelPart, Model> modelProvider, Supplier<TexturedModelData> dataProvider) {
        var model = new EntityModelLayer(new Identifier(Prog.MOD_ID, name.toLowerCase()), layer);
        layersByItem.computeIfAbsent(item, key -> new HashMap<String, EntityModelLayer>()).put(layer, model);
        modelProvidersByLayer.put(model, modelProvider);

        EntityModelLayerRegistry.registerModelLayer(model, () -> dataProvider.get());
        return model;
    }

    public static void onReload(EntityModelLoader loader) {
        layersByItem.forEach((item, map) -> {
            map.forEach((layer, model) -> {
                modelsByLayer.put(model, modelProvidersByLayer.get(model).apply(loader.getModelPart(model)));
            });
        });
    }

    public static EntityModelLayer getLayer(Item item, String layer) {
        return layersByItem.get(item).get(layer);
    }

    public static EntityModelLayer getLayer(Item item) {
        return getLayer(item, MAIN);
    }

    public static Model getModel(Item item) {
        return modelsByLayer.get(getLayer(item, MAIN));
    }

    public static void init() {
        LOGGER.info("Registering Entity Model Layers for: " + Prog.MOD_ID);
    }
}
