package com.prog.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.prog.utils.ItemUtils;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.model.TridentEntityModel;
import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(BuiltinModelItemRenderer.class)
public class BuiltinModelItemRendererMixin {

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/model/TridentEntityModel;getLayer(Lnet/minecraft/util/Identifier;)Lnet/minecraft/client/render/RenderLayer;"))
    public RenderLayer redirectRenderTrident(TridentEntityModel instance, Identifier identifier, @Local ItemStack stack) {
        var id = ItemUtils.getId(stack.getItem());
        return instance.getLayer(new Identifier(id.getNamespace(), "textures/entity/" + id.getPath() + ".png"));
    }
}
