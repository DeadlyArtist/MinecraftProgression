package com.prog.mixin.compat.ringofattraction;

import com.prog.client.utils.TooltipUtils;
import com.prog.entity.PComponents;
import com.prog.event.ItemEvents;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import com.kwpugh.ring_of_attraction.items.ItemRingAttraction;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(ItemRingAttraction.class)
public class ItemRingAttractionMixin {

    @Environment(EnvType.CLIENT)
    @Inject(method = "appendTooltip", at = @At("HEAD"))
    private void injectAppendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext, CallbackInfo ci) {
        ItemEvents.APPEND_TOOLTIP.invoker().append(itemStack, tooltipContext, tooltip);
    }

    @Environment(EnvType.CLIENT)
    @Redirect(
            method = "appendTooltip",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/text/MutableText;formatted(Lnet/minecraft/util/Formatting;)Lnet/minecraft/text/MutableText;",
                    ordinal = 0
            )
    )
    private MutableText redirectAppendTooltip(MutableText instance, Formatting formatting) {
        var player = MinecraftClient.getInstance().player;
        var disabled = PComponents.PLAYER.get(player).magnetDisabled;
        if (disabled) return TooltipUtils.appendDisabled(instance);

        return instance.formatted(Formatting.GREEN);
    }
}
