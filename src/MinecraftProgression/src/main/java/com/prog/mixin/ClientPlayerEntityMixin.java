package com.prog.mixin;

import com.prog.entity.attribute.PEntityAttributes;
import com.prog.utils.ElytraUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.TridentItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Environment(EnvType.CLIENT)
@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin {
    @Unique
    public ClientPlayerEntity self = (ClientPlayerEntity)(Object)this;

    @Inject(method = "shouldAutoJump", at = @At("HEAD"))
    private void onShouldAutoJump(CallbackInfoReturnable<Boolean> info) {
        if (self.getAttributeValue(PEntityAttributes.STEP_HEIGHT) >= 1) {
            // Uncomment for auto jump fix. Disabled cause it doesn't consider jump height (attribute and effect), and there's no jitter work anyways.
//            info.setReturnValue(false);
//            info.cancel();
        }
    }

    @Redirect(method = "tickMovement", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z"))
    public boolean redirectIsOf(ItemStack instance, Item item) {
        return ElytraUtils.canUse(self, instance);
    }
}
