package com.prog.mixin;

import com.prog.event.ItemEvents;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockItem.class)
public class BlockItemMixin {
    @Unique
    private final BlockItem self = (BlockItem) (Object) this;

    @Inject(method = "appendStacks", at = @At("TAIL"))
    private void injectIntoAppendStacks(ItemGroup group, DefaultedList<ItemStack> stacks, CallbackInfo ci) {
        ItemEvents.APPEND_STACKS.invoker().append(group, stacks, self);
    }
}
