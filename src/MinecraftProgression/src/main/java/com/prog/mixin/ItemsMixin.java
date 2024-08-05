package com.prog.mixin;

import com.prog.itemOrBlock.PFoodComponents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Items.class)
public class ItemsMixin {
    @Inject(at = @At("HEAD"), method = "register(Lnet/minecraft/util/Identifier;Lnet/minecraft/item/Item;)Lnet/minecraft/item/Item;")
    private static void register(Identifier id, Item item, CallbackInfoReturnable<Item> cir) {
        if ("glistering_melon_slice".equals(id.getPath())) {
            item.group = ItemGroup.FOOD;
            item.foodComponent = PFoodComponents.GLISTERING_MELON_SLICE;
        }
    }
}
