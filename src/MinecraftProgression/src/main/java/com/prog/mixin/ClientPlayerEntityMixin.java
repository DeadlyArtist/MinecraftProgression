package com.prog.mixin;

import com.prog.entity.attribute.PEntityAttributes;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin {
    @Inject(method = "shouldAutoJump", at = @At("HEAD"))
    private void onShouldAutoJump(CallbackInfoReturnable<Boolean> info) {
        var self = (ClientPlayerEntity) (Object) this;
        if (self.getAttributeValue(PEntityAttributes.STEP_HEIGHT) >= 1) {
            // Uncomment for auto jump fix. Disabled cause it doesn't consider jump height (attribute and effect), and there's no jitter work anyways.
//            info.setReturnValue(false);
//            info.cancel();
        }
    }
}
