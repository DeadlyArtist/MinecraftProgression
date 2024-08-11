package com.prog.mixin.compat.projectile_damage;

import com.prog.Prog;
import net.minecraft.item.ItemStack;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.TextContent;
import net.minecraft.text.TranslatableTextContent;
import net.minecraft.util.Formatting;
import net.projectile_damage.client.TooltipHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Mixin(TooltipHelper.class)
public class TooltipHelperMixin {
    @Inject(method = "replaceAttributeLines_BlueWithGreen", at = @At("HEAD"), cancellable = true, remap = false)
    private static void replaceAttributeLines_BlueWithGreen(List<Text> tooltip, CallbackInfo ci) {
        ci.cancel();
    }

    @Inject(method = "mergeAttributeLines_MainHandOffHand", at = @At("HEAD"), cancellable = true, remap = false)
    private static void mergeAttributeLines_MainHandOffHand(List<Text> tooltip, CallbackInfo ci) {
        List<Text> heldInHandLines = new ArrayList();
        List<Text> mainHandAttributes = new ArrayList();
        List<Text> offHandAttributes = new ArrayList();

        int mainHandLine;
        int lastModifierIndex = -1;
        for (mainHandLine = 0; mainHandLine < tooltip.size(); ++mainHandLine) {
            Text line = (Text) tooltip.get(mainHandLine);
            if (line instanceof MutableText) {
                for (var sibling : line.getSiblings()) {
                    TextContent content = sibling.getContent();
                    if (content instanceof TranslatableTextContent translatableText) {
                        if (translatableText.getKey().startsWith("attribute.modifier")) {
                            lastModifierIndex = mainHandLine;
                            if (heldInHandLines.size() == 1) mainHandAttributes.add(line);
                            else if (heldInHandLines.size() == 2) offHandAttributes.add(line);
                            break;
                        }
                    }
                }
            }

            TextContent content = line.getContent();
            if (content instanceof TranslatableTextContent translatableText) {
                if (translatableText.getKey().startsWith("item.modifiers")) {
                    heldInHandLines.add(line);
                }

                if (translatableText.getKey().startsWith("attribute.modifier")) {
                    lastModifierIndex = mainHandLine;
                    if (heldInHandLines.size() == 1) mainHandAttributes.add(line);
                    else if (heldInHandLines.size() == 2) offHandAttributes.add(line);
                }
            }
        }

        if (heldInHandLines.size() == 2) {
            mainHandLine = tooltip.indexOf(heldInHandLines.get(0));
            int offHandLine = tooltip.indexOf(heldInHandLines.get(1));
            tooltip.remove(mainHandLine);
            tooltip.add(mainHandLine, Text.translatable("item.modifiers.both_hands").formatted(Formatting.GRAY));
            tooltip.remove(offHandLine);
            int potentialEmptyIndex = offHandLine - 1;
            if (tooltip.get(potentialEmptyIndex).getString().trim().isEmpty()) tooltip.remove(potentialEmptyIndex);

            for (Text offhandAttribute : offHandAttributes) {
                if (mainHandAttributes.contains(offhandAttribute)) {
                    tooltip.remove(tooltip.lastIndexOf(offhandAttribute));
                }
            }
        }
        ci.cancel();
    }
}
