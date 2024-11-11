package com.prog.mixin.compat.ringofattraction;

import com.prog.client.utils.TooltipUtils;
import com.prog.entity.PComponents;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.MutableText;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import com.kwpugh.ring_of_attraction.items.ItemRingAttraction;

@Mixin(value = ItemRingAttraction.class, remap = false)
public class ItemRingAttractionMixin {

    @Environment(EnvType.CLIENT)
    @Redirect(
            method = "appendTooltip",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/text/MutableText;formatted(Lnet/minecraft/util/Formatting;)Lnet/minecraft/text/MutableText;",
                    ordinal = 0
            )
    )
    private MutableText redirectFirstFormatted(MutableText instance, Formatting formatting) {
        var player = MinecraftClient.getInstance().player;
        var disabled = PComponents.PLAYER.get(player).magnetDisabled;
        if (disabled) return TooltipUtils.appendDisabled(instance);

        return instance.formatted(Formatting.GREEN);
    }
}
