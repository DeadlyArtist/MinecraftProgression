package com.prog.mixin.compat.ironjetpacks;

import com.blakebr0.ironjetpacks.IronJetpacks;
import com.blakebr0.ironjetpacks.item.JetpackItem;
import com.blakebr0.ironjetpacks.registry.Jetpack;
import com.google.common.base.Suppliers;
import com.prog.itemOrBlock.PItemGroups;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Jetpack.class)
public class JetpackMixin {
    @Inject(method = "<init>", at = @At("TAIL"))
    private void redirectCreateGroup(String name, int tier, int color, int armorPoints, int enchantability, String craftingMaterialString, CallbackInfo ci) {
        var self = (Jetpack) (Object) this;
        self.item = Suppliers.memoize(() -> {
            return new JetpackItem(self, (new Item.Settings()).group(PItemGroups.MORE_PROGRESSION));
        });
    }
}
