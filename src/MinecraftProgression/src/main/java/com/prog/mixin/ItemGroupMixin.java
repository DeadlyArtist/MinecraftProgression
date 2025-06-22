package com.prog.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.prog.event.ItemEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemGroup.class)
public class ItemGroupMixin {

    @Inject(method = "appendStacks", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/Item;appendStacks(Lnet/minecraft/item/ItemGroup;Lnet/minecraft/util/collection/DefaultedList;)V", shift = At.Shift.AFTER))
    public void injectAppendStacks(DefaultedList<ItemStack> stacks, CallbackInfo ci, @Local Item item) {
        var self = (ItemGroup) (Object) this;
        ItemEvents.APPEND_STACKS_TO_GROUP.invoker().append(self, stacks, item);
    }
}
