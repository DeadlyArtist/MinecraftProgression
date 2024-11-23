package com.prog.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.prog.client.entity.PEntityModelLayers;
import com.prog.entity.FlexibleTridentEntity;
import com.prog.entity.PEntityTypes;
import com.prog.utils.ItemUtils;
import com.prog.utils.LOGGER;
import net.minecraft.client.model.Model;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.TridentEntityRenderer;
import net.minecraft.client.render.entity.model.TridentEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TridentEntityRenderer.class)
public class TridentEntityRendererMixin {

    @Inject(method = "getTexture(Lnet/minecraft/entity/projectile/TridentEntity;)Lnet/minecraft/util/Identifier;", at = @At("HEAD"), cancellable = true)
    public void injectGetTexture(TridentEntity tridentEntity, CallbackInfoReturnable<Identifier> cir) {
        var item = Items.TRIDENT;
        if (tridentEntity instanceof FlexibleTridentEntity flexibleTridentEntity) item = PEntityTypes.getFlexibleTridentItem(flexibleTridentEntity.getEntityType());
        var id = ItemUtils.getId(item);
        cir.setReturnValue(new Identifier(id.getNamespace(), "textures/entity/" + id.getPath() + ".png"));
    }

    @Redirect(method = "render(Lnet/minecraft/entity/projectile/TridentEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/model/TridentEntityModel;getLayer(Lnet/minecraft/util/Identifier;)Lnet/minecraft/client/render/RenderLayer;"))
    public RenderLayer injectRender(TridentEntityModel instance, Identifier identifier, @Local TridentEntity trident) {
        var item = Items.TRIDENT;
        if (trident instanceof FlexibleTridentEntity flexibleTridentEntity)
            item = PEntityTypes.getFlexibleTridentItem(flexibleTridentEntity.getEntityType());
        Model model = instance;
        if (PEntityModelLayers.layersByItem.containsKey(item)) model = PEntityModelLayers.getModel(item);
        return model.getLayer(identifier);
    }

    @Redirect(method = "render(Lnet/minecraft/entity/projectile/TridentEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/model/TridentEntityModel;render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumer;IIFFFF)V"))
    public void injectRender2(TridentEntityModel instance, MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha, @Local TridentEntity trident) {
        var item = Items.TRIDENT;
        if (trident instanceof FlexibleTridentEntity flexibleTridentEntity)
            item = PEntityTypes.getFlexibleTridentItem(flexibleTridentEntity.getEntityType());
        Model model = instance;
        if (PEntityModelLayers.layersByItem.containsKey(item)) model = PEntityModelLayers.getModel(item);
        model.render(matrices, vertices, light, overlay, red, green, blue, alpha);
    }
}
