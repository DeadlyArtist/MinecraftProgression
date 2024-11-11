package com.prog.client.utils;

import com.prog.entity.PComponents;
import com.prog.entity.attribute.PEntityAttributes;
import com.prog.text.PTexts;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.TextContent;
import net.minecraft.text.TranslatableTextContent;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Unique;

import java.util.ArrayList;
import java.util.List;

@Environment(EnvType.CLIENT)
public class TooltipUtils {

    public static Text tryAppendDisabled(Text text, EntityAttribute attribute) {
        var player = MinecraftClient.getInstance().player;
        if (player == null) return text;
        var pComponent = PComponents.PLAYER.get(player);

        var append = false;
        if (attribute == PEntityAttributes.LUMINANCE) {
            if (pComponent.headlightDisabled) append = true;
        } else if (attribute == PEntityAttributes.STEP_HEIGHT) {
            if (pComponent.stepAssistDisabled) append = true;
        } else if (attribute == PEntityAttributes.BAD_OMEN_IMMUNITY) {
            if (pComponent.badOmenImmunityDisabled) append = true;
        } else if (attribute == PEntityAttributes.MAGNET) {
            if (pComponent.magnetDisabled) append = true;
        }

        if (append) return appendDisabled(text);
        return text;
    }

    public static MutableText appendDisabled(Text text) {
        return Text.literal(text.getString() + " (" + PTexts.DISABLED_TOOLTIP.get().getString() + ")");
    }

    public static void mergeMultiHandTooltips(List<Text> tooltip) {
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
    }
}
