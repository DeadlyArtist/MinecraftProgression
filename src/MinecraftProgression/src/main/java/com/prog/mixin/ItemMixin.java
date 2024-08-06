package com.prog.mixin;

import com.prog.event.EntityEvents;
import com.prog.event.ItemEvents;
import com.prog.itemOrBlock.PFoodComponents;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(Item.class)
public class ItemMixin {
//    // Doesn't work since the item has not yet been assigned.
//    @ModifyVariable(
//            method = "<init>(Lnet/minecraft/item/Item$Settings;)V",
//            at = @At("HEAD"),
//            ordinal = 0
//    )
//    private static Item.Settings modifyItemSettings(Item.Settings settings) {
//        return settings.group(ItemGroup.FOOD).food(PFoodComponents.GLISTERING_MELON_SLICE);
//    }

    @Inject(method = "appendTooltip", at = @At("HEAD"))
    private void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context, CallbackInfo info) {
        ItemEvents.APPEND_TOOLTIP.invoker().append(stack, context, tooltip);
    }
}
