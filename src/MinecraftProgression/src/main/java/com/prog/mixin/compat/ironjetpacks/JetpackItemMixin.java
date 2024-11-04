package com.prog.mixin.compat.ironjetpacks;

import com.blakebr0.ironjetpacks.config.ModConfigs;
import com.blakebr0.ironjetpacks.item.JetpackItem;
import com.blakebr0.ironjetpacks.handler.InputHandler;
import com.blakebr0.ironjetpacks.lib.ModTooltips;
import com.blakebr0.ironjetpacks.util.UnitUtils;
import com.prog.utils.JetpackUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import team.reborn.energy.api.EnergyStorage;

import java.util.List;

@Mixin(JetpackItem.class)
public class JetpackItemMixin {
    @Inject(method = "isItemBarVisible", at = @At("HEAD"), cancellable = true)
    private void isItemBarVisible(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        var self = (JetpackItem) (Object) this;
        if (self.getJetpack().capacity <= 0) {
            cir.setReturnValue(false);
            cir.cancel();
        }
    }

    @Inject(method = "appendStacks", at = @At("HEAD"), cancellable = true)
    private void injectAppendStacks(ItemGroup group, DefaultedList<ItemStack> stacks, CallbackInfo ci) {
        var self = (JetpackItem) (Object) this;
        if (self.isIn(group)) {
            stacks.add(new ItemStack(self));
            if (!self.getJetpack().creative && self.getJetpack().capacity > 0) {
                ItemStack stack = new ItemStack(self);
                stack.getOrCreateNbt().putDouble("energy", self.getJetpack().capacity);
                stacks.add(stack);
            }
        }

        ci.cancel();
    }

    @Environment(EnvType.CLIENT)
    @Inject(method = "appendTooltip", at = @At("HEAD"), cancellable = true)
    private void injectAppendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context, CallbackInfo ci) {
        var self = (JetpackItem) (Object) this;
        var jetpack = self.getJetpack();

        if (jetpack.capacity > 0) {
            if (!jetpack.creative) {
                EnergyStorage energy = (EnergyStorage) EnergyStorage.ITEM.find(stack, ContainerItemContext.withInitial(stack));
                tooltip.add(Text.literal(UnitUtils.formatEnergy((double) energy.getAmount(), (Formatting) null)).formatted(Formatting.GRAY).append(" / ").append(Text.literal(UnitUtils.formatEnergy(jetpack.capacity, (Formatting) null))));
            } else {
                tooltip.add(Text.literal("-1 E / ").formatted(Formatting.GRAY).append(ModTooltips.INFINITE.color(Formatting.GRAY)).append(" E"));
            }
        }

        Text tier = ModTooltips.TIER.args(new Object[]{jetpack.creative ? "Creative" : jetpack.tier}).formatted(jetpack.rarity.formatting);
        Text engine = ModTooltips.ENGINE.color(JetpackUtils.isEngineOn(stack) ? Formatting.GREEN : Formatting.RED);
        Text hover = ModTooltips.HOVER.color(JetpackUtils.isHovering(stack) ? Formatting.GREEN : Formatting.RED);
        tooltip.add(ModTooltips.STATE_TOOLTIP_LAYOUT.args(new Object[]{tier, engine, hover}));
        if (ModConfigs.getClient().general.enableAdvancedInfoTooltips) {
            tooltip.add(Text.literal(""));
            if (!Screen.hasShiftDown()) {
                tooltip.add(Text.translatable("tooltip.iron-jetpacks.hold_shift_for_info"));
            } else {
                if (jetpack.capacity > 0) tooltip.add(ModTooltips.FUEL_USAGE.args(new Object[]{jetpack.usage + " E/t"}));
                tooltip.add(ModTooltips.VERTICAL_SPEED.args(new Object[]{jetpack.speedVert}));
                tooltip.add(ModTooltips.VERTICAL_ACCELERATION.args(new Object[]{jetpack.accelVert}));
                tooltip.add(ModTooltips.HORIZONTAL_SPEED.args(new Object[]{jetpack.speedSide}));
                tooltip.add(ModTooltips.HOVER_SPEED.args(new Object[]{jetpack.speedHoverSlow}));
                tooltip.add(ModTooltips.DESCEND_SPEED.args(new Object[]{jetpack.speedHover}));
                tooltip.add(ModTooltips.SPRINT_MODIFIER.args(new Object[]{jetpack.sprintSpeed}));
                if (jetpack.capacity > 0) tooltip.add(ModTooltips.SPRINT_FUEL_MODIFIER.args(new Object[]{jetpack.sprintFuel}));
            }
        }

        ci.cancel();
    }

    @Inject(method = "tickArmor", at = @At("HEAD"), cancellable = true)
    private void interceptTickArmor(ItemStack stack, PlayerEntity player, CallbackInfo ci) {
        ci.cancel();
    }
}
