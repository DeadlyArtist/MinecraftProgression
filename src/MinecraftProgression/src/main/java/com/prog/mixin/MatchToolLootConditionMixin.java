package com.prog.mixin;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ShearsItem;
import net.minecraft.loot.condition.MatchToolLootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.predicate.item.ItemPredicate;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MatchToolLootCondition.class)
public class MatchToolLootConditionMixin {
    @Final
    @Shadow
    ItemPredicate predicate;

    @Inject(method = "test(Lnet/minecraft/loot/context/LootContext;)Z", at = @At("HEAD"), cancellable = true)
    public void injectTest(LootContext lootContext, CallbackInfoReturnable<Boolean> cir) {
        ItemStack itemStack = lootContext.get(LootContextParameters.TOOL);
        if (itemStack == null) return;

        var shearAllowed = this.predicate.test(new ItemStack(Items.SHEARS));
        if (shearAllowed && itemStack.getItem() instanceof ShearsItem) cir.setReturnValue(true);
    }
}
