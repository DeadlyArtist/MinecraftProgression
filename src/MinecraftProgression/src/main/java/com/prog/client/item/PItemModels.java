package com.prog.client.item;

import com.prog.Prog;
import com.prog.client.entity.PEntityModelLayers;
import com.prog.client.utils.BuiltinEntityItemEntityRenderer;
import com.prog.client.utils.ItemModelRegistry;
import com.prog.utils.ItemUtils;
import com.prog.utils.LOGGER;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.TridentEntityModel;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class PItemModels {

    // FROM: https://github.com/fzzyhmstrs/tns/tree/1.20.1
    public static void registerTrident(String name, Item item) {
        name = name.toLowerCase();
        // Create a ModelIdentifierPerModes instance and set the models for held modes
        ItemModelRegistry.ModelIdentifierPerModes modes = new ItemModelRegistry.ModelIdentifierPerModes(
                new ModelIdentifier(Prog.MOD_ID, name, "inventory")
        ).withHeld(
                new ModelIdentifier(Prog.MOD_ID, name + "_in_hand", "inventory"),
                true
        );

        // Register the item with the ItemModelRegistry
        ItemModelRegistry.registerItemModelId(item, modes);

        // Register the item entity model with a custom renderer
        ItemModelRegistry.registerItemEntityModel(
                item,
                new BuiltinEntityItemEntityRenderer(item, Identifier.of(Prog.MOD_ID, "textures/entity/" + name + ".png")),
                PEntityModelLayers.getLayer(item),
                TridentEntityModel.class
        );
    }


    public static void init() {
        LOGGER.info("Registering Item Models for: " + Prog.MOD_ID);
    }
}
