package com.prog.client.utils;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.item.Item;
import net.minecraft.resource.ReloadableResourceManagerImpl;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.SynchronousResourceReloader;

import java.util.*;

// FROM: https://github.com/fzzyhmstrs/fc/tree/1.19.2-New

/**
 * Registry for custom item models. Use this if you want to implement items that appear differently in different [ModelTransformation.Mode].
 * <p>
 * The easy example of an item that does this is the Minecraft Trident. In inventory, it appears as a pixel art icon, but in hand and in third person, it appears as a rendered entity.
 * <p>
 * This registry provides methods for handling both of those situations (rendering differently per Mode, and rendering as an entity)
 */
@SuppressWarnings("unused")
@Environment(EnvType.CLIENT)
public class ItemModelRegistry implements SynchronousResourceReloader {
    private static final ModelIdentifier FALLBACK_ID = new ModelIdentifier("minecraft", "trident_in_hand", "inventory");
    private static final Map<Item, ModelIdentifierPerModes> MODEL_ID_MAP = new HashMap<>();
    private static final Map<Item, CustomItemEntityModelLoader> ENTITY_MODEL_MAP = new HashMap<>();

    @Override
    public void reload(ResourceManager manager) {
        ENTITY_MODEL_MAP.forEach((item, loader) -> loader.reload());
    }

    public static void registerAll() {
        registerReloader();
    }

    /**
     * Base registration method for assigning different models to different view modes. Uses a [ModelIdentifierPerModes] instance to define what models you want to appear when.
     * If you are registering an item entity, the model JSON corresponding to the entity must use builtin/entity as its model parent.
     */
    public static void registerItemModelId(Item item, ModelIdentifierPerModes models) {
        if (MODEL_ID_MAP.containsKey(item)) {
            throw new IllegalStateException("Item " + item.getName() + " already present in ItemModelRegistry");
        }
        MODEL_ID_MAP.put(item, models);
    }

    /**
     * If you want your item to appear as a rendered entity in one or more render modes, register that information here. This method wraps the needed underlying registries into one method call.
     *
     * @param item      The registered item to define an entity model for.
     * @param renderer  A [BuiltinItemRendererRegistry.DynamicItemRenderer] used by Fabric for rendering the entity.
     * @param layer     The model layer for the entity.
     * @param classType The Entity model Java class.
     */
    public static void registerItemEntityModel(Item item, BuiltinItemRendererRegistry.DynamicItemRenderer renderer,
                                               EntityModelLayer layer, Class<? extends Model> classType) {
        ENTITY_MODEL_MAP.put(item, new CustomItemEntityModelLoader(layer, classType));
        BuiltinItemRendererRegistry.INSTANCE.register(item, renderer);
    }

    private static void registerReloader() {
        ClientLifecycleEvents.CLIENT_STARTED.register(client -> {
            ReloadableResourceManagerImpl resourceManager = (ReloadableResourceManagerImpl) client.getResourceManager();
            resourceManager.registerReloader(new ItemModelRegistry());
        });
    }

    public static boolean itemHasCustomModel(Item item) {
        return MODEL_ID_MAP.containsKey(item);
    }

    public static ModelIdentifier getModel(Item item, ModelTransformation.Mode mode) {
        return MODEL_ID_MAP.containsKey(item)
                ? MODEL_ID_MAP.get(item).getIdFromMode(mode)
                : FALLBACK_ID;
    }

    /**
     * Returns a [CustomItemEntityModelLoader] for the specified item that can be used to fetch baked models.
     */
    public static CustomItemEntityModelLoader getEntityModelLoader(Item item) {
        if (!ENTITY_MODEL_MAP.containsKey(item)) {
            throw new NoSuchElementException("Item " + item.getName() + " not present in model loader registry.");
        }
        return ENTITY_MODEL_MAP.get(item);
    }

    /**
     * Used to tell the registry which models to use for which transformation mode.
     */
    public static class ModelIdentifierPerModes {
        private final ModelIdentifier defaultId;
        private final EnumMap<ModelTransformation.Mode, ModelIdentifier> modeMap = new EnumMap<>(ModelTransformation.Mode.class);

        public ModelIdentifierPerModes(ModelIdentifier defaultId) {
            this.defaultId = defaultId;
        }

        public ModelIdentifierPerModes with(ModelTransformation.Mode mode, ModelIdentifier modelId, boolean needsRegistration) {
            if (needsRegistration) {
                registerIdWithModelLoading(modelId);
            }
            modeMap.put(mode, modelId);
            return this;
        }

        public ModelIdentifierPerModes withGuiGroundFixed(ModelIdentifier modelId, boolean needsRegistration) {
            if (needsRegistration) {
                registerIdWithModelLoading(modelId);
            }
            modeMap.put(ModelTransformation.Mode.GUI, modelId);
            modeMap.put(ModelTransformation.Mode.FIXED, modelId);
            modeMap.put(ModelTransformation.Mode.GROUND, modelId);
            return this;
        }

        public ModelIdentifierPerModes withFirstHeld(ModelIdentifier modelId, boolean needsRegistration) {
            if (needsRegistration) {
                registerIdWithModelLoading(modelId);
            }
            modeMap.put(ModelTransformation.Mode.FIRST_PERSON_RIGHT_HAND, modelId);
            modeMap.put(ModelTransformation.Mode.FIRST_PERSON_LEFT_HAND, modelId);
            return this;
        }

        public ModelIdentifierPerModes withThirdHeld(ModelIdentifier modelId, boolean needsRegistration) {
            if (needsRegistration) {
                registerIdWithModelLoading(modelId);
            }
            modeMap.put(ModelTransformation.Mode.THIRD_PERSON_RIGHT_HAND, modelId);
            modeMap.put(ModelTransformation.Mode.THIRD_PERSON_LEFT_HAND, modelId);
            return this;
        }

        public ModelIdentifierPerModes withHeld(ModelIdentifier modelId, boolean needsRegistration) {
            if (needsRegistration) {
                registerIdWithModelLoading(modelId);
            }
            modeMap.put(ModelTransformation.Mode.FIRST_PERSON_RIGHT_HAND, modelId);
            modeMap.put(ModelTransformation.Mode.FIRST_PERSON_LEFT_HAND, modelId);
            modeMap.put(ModelTransformation.Mode.THIRD_PERSON_RIGHT_HAND, modelId);
            modeMap.put(ModelTransformation.Mode.THIRD_PERSON_LEFT_HAND, modelId);
            return this;
        }

        public ModelIdentifierPerModes withAll(ModelIdentifier modelId, boolean needsRegistration) {
            if (needsRegistration) {
                registerIdWithModelLoading(modelId);
            }
            for (ModelTransformation.Mode mode : ModelTransformation.Mode.values()) {
                modeMap.put(mode, modelId);
            }
            return this;
        }

        public ModelIdentifier getIdFromMode(ModelTransformation.Mode mode) {
            return modeMap.getOrDefault(mode, defaultId);
        }

        private static void registerIdWithModelLoading(ModelIdentifier id) {
            ModelLoadingRegistry.INSTANCE.registerModelProvider((manager, out) -> out.accept(id));
        }
    }

    /**
     * Container storing a custom item entity model that automatically reloads with the client.
     */
    public static class CustomItemEntityModelLoader {
        private final EntityModelLayer layer;
        private final Class<? extends Model> classType;
        private Model model;

        public CustomItemEntityModelLoader(EntityModelLayer layer, Class<? extends Model> classType) {
            this.layer = layer;
            this.classType = classType;
        }

        public void reload() {
            this.model = internalReload();
        }

        public Model getModel() {
            if (this.model == null) {
                this.model = internalReload();
            }
            return this.model;
        }

        private Model internalReload() {
            try {
                ModelPart modelPart = MinecraftClient.getInstance().getEntityModelLoader().getModelPart(layer);
                return classType.getConstructor(ModelPart.class).newInstance(modelPart);
            } catch (Exception e) {
                throw new RuntimeException("Failed to load custom entity model.", e);
            }
        }
    }
}
