package com.prog.client;

import com.prog.Prog;
import com.prog.client.gui.screen.ingame.PHandledScreens;
import com.prog.entity.PComponents;
import com.prog.entity.attribute.XEntityAttributes;
import com.prog.event.ItemEvents;
import com.prog.itemOrBlock.GourmetFoods;
import com.prog.itemOrBlock.PItemTags;
import com.prog.itemOrBlock.custom.TieredBowItem;
import com.prog.text.PTexts;
import com.prog.utils.ItemStackUtils;
import com.prog.utils.UpgradeUtils;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.projectile_damage.ProjectileDamageMod;
import net.projectile_damage.api.EntityAttributes_ProjectileDamage;
import net.projectile_damage.api.IProjectileWeapon;
import net.projectile_damage.internal.Constants;

@Environment(EnvType.CLIENT)
public class PClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        PHandledScreens.init();

        // Events
        ItemEvents.APPEND_TOOLTIP.register((stack, context, lines) -> {
            var player = MinecraftClient.getInstance().player;
            var item = stack.getItem();

            // Upgradable
            if (stack.isIn(PItemTags.UPGRADABLE)) {
                lines.add(PTexts.UPGRADABLE_TOOLTIP.get().copy().formatted(Formatting.GRAY).formatted(Formatting.ITALIC));
            }

            // Tier Core
            if (stack.isIn(PItemTags.TIER_CORE)) {
                lines.add(PTexts.TIER_CORE_TOOLTIP.get().copy().formatted(Formatting.GRAY).formatted(Formatting.ITALIC));
            }

            // Upgrade
            if (stack.isIn(PItemTags.UPGRADE)) {
                lines.add(PTexts.UPGRADE_TOOLTIP.get().copy().formatted(Formatting.GRAY).formatted(Formatting.ITALIC));
            }

            // Gourmet food
            if (GourmetFoods.data.containsKey(item)) {
                var component = PComponents.LIVING_ENTITY.get(player);
                if (component.hasEaten(stack.getItem())) {
                    lines.add(PTexts.GOURMET_HAS_EATEN_TOOLTIP.get().copy().formatted(Formatting.GRAY).formatted(Formatting.ITALIC));
                } else {
                    lines.add(PTexts.GOURMET_NOT_EATEN_TOOLTIP.get().copy().formatted(Formatting.RED).formatted(Formatting.ITALIC));
                }
            }

            // Contains upgrades
            UpgradeUtils.addUpgradeTooltip(lines, stack);
        });
    }
}
