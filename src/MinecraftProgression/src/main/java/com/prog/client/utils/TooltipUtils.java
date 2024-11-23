package com.prog.client.utils;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.prog.entity.PComponents;
import com.prog.entity.attribute.PEntityAttributes;
import com.prog.text.PTexts;
import com.prog.utils.MeleeUtils;
import com.prog.utils.RangedUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.TextContent;
import net.minecraft.text.TranslatableTextContent;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Unique;

import java.util.*;

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

    /**
     * Combines modifiers with Operation.ADD and excludes those with specific IDs (i.e., ATTACK_DAMAGE and ATTACK_SPEED).
     *
     * @param originalMultimap the original multimap which will be modified
     * @return a new multimap containing the combined modifiers
     */
    public static Multimap<EntityAttribute, EntityAttributeModifier> processAndCombineModifiers(Multimap<EntityAttribute, EntityAttributeModifier> originalMultimap) {
        Multimap<EntityAttribute, EntityAttributeModifier> finalMultimap = LinkedHashMultimap.create();

        // Separate the special modifiers and the eligible modifiers
        List<Map.Entry<EntityAttribute, EntityAttributeModifier>> specialModifiers = new ArrayList<>();
        Multimap<EntityAttribute, EntityAttributeModifier> combiningEligible = LinkedHashMultimap.create();

        // Iterate over the original entries to separate special vs. combinable modifiers
        for (Map.Entry<EntityAttribute, EntityAttributeModifier> entry : originalMultimap.entries()) {
            EntityAttribute attribute = entry.getKey();
            EntityAttributeModifier modifier = entry.getValue();

            // Identify special modifiers that should not be combined
            UUID modifierId = modifier.getId();
            if (modifierId.equals(Item.ATTACK_DAMAGE_MODIFIER_ID) ||
                    modifierId.equals(Item.ATTACK_SPEED_MODIFIER_ID) ||
                    modifierId.equals(RangedUtils.PROJECTILE_DAMAGE_BASE_MODIFIER_ID) ||
                    modifierId.equals(MeleeUtils.SHIELD_BASE_MODIFIER_ID) ||
                    modifierId.equals(RangedUtils.TREASURE_QUALITY_BASE_MODIFIER_ID) ||
                    Arrays.asList(ArmorItem.MODIFIERS).contains(modifierId)) {

                specialModifiers.add(entry);
            } else {
                // Eligible for combination
                combiningEligible.put(attribute, modifier);
            }
        }

        // First, add the special modifiers in the expected order
        specialModifiers.forEach(entry -> finalMultimap.put(entry.getKey(), entry.getValue()));

        // Then, combine the eligible modifiers and add them to the map
        combineModifiers(combiningEligible, finalMultimap);

        return finalMultimap;
    }

    /**
     * Combines all eligible modifiers for ADD operation and ensures that they are added properly
     * without mixing with the special ones.
     *
     * @param eligibleMultimap The multimap containing modifiers that are eligible for combination.
     * @param finalMultimap    The final map where accumulated/resultant modifiers should be placed.
     */
    private static void combineModifiers(Multimap<EntityAttribute, EntityAttributeModifier> eligibleMultimap, Multimap<EntityAttribute, EntityAttributeModifier> finalMultimap) {

        for (EntityAttribute attribute : eligibleMultimap.keySet()) {
            Collection<EntityAttributeModifier> modifiers = eligibleMultimap.get(attribute);

            // Combine only if there's more than one modifier for this attribute
            if (!modifiers.isEmpty()) {
                double combinedValue = 0.0;

                // Combine the modifiers
                for (EntityAttributeModifier modifier : modifiers) {
                    combinedValue += modifier.getValue();
                }

                // Add the combined value as a new modifier
                // Picking the first one's ID to use for consistency in naming
                EntityAttributeModifier firstModifier = modifiers.iterator().next();
                EntityAttributeModifier combinedModifier = new EntityAttributeModifier(
                        firstModifier.getId(),
                        firstModifier.getName() + "_combined",
                        combinedValue,
                        EntityAttributeModifier.Operation.ADDITION
                );

                // Add to the final multimap
                finalMultimap.put(attribute, combinedModifier);
            }
        }
    }
}
