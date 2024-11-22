package com.prog.mixin;

import com.prog.utils.ItemUtils;
import net.minecraft.client.render.entity.TridentEntityRenderer;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TridentEntityRenderer.class)
public class TridentEntityRendererMixin {

    @Inject(method = "getTexture(Lnet/minecraft/entity/projectile/TridentEntity;)Lnet/minecraft/util/Identifier;", at = @At("HEAD"), cancellable = true)
    public void injectGetTexture(TridentEntity tridentEntity, CallbackInfoReturnable<Identifier> cir) {
        var item = tridentEntity.tridentStack.getItem();
        var id = ItemUtils.getId(item);
        cir.setReturnValue(new Identifier(id.getNamespace(), "textures/entity/" + id.getPath() + ".png"));
    }
}
