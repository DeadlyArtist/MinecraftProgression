package com.prog.utils;

import com.blakebr0.ironjetpacks.handler.InputHandler;
import com.blakebr0.ironjetpacks.item.JetpackItem;
import com.blakebr0.ironjetpacks.item.storage.ItemSlotStorage;
import com.blakebr0.ironjetpacks.item.storage.StackBaseStorage;
import com.blakebr0.ironjetpacks.mixins.ServerPlayNetworkHandlerAccessor;
import com.blakebr0.ironjetpacks.registry.Jetpack;
import com.prog.entity.attribute.PEntityAttributes;
import com.prog.itemOrBlock.PJetpacks;
import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.Vec3d;
import team.reborn.energy.api.EnergyStorage;

public class JetpackUtils {
    public static Jetpack getJetpack(PlayerEntity player) {
        if (hasDefaultJetpack(player)) return PJetpacks.getDefault();

        ItemStack chest = player.getEquippedStack(EquipmentSlot.CHEST);
        if (!chest.isEmpty() && chest.getItem() instanceof JetpackItem jetpackItem) return jetpackItem.getJetpack();

        return null;
    }

    public static Jetpack getJetpack(ItemStack stack) {
        if (!stack.isEmpty() && stack.getItem() instanceof JetpackItem jetpackItem) return jetpackItem.getJetpack();
        return PJetpacks.getDefault();
    }

    public static Item getJetpackItem(PlayerEntity player) {
        var stack = getJetpackItemStack(player);
        if (stack == null) return null;
        return stack.getItem();
    }

    public static JetpackItem tryGetJetpackItem(PlayerEntity player) {
        var item = getJetpackItem(player);
        if (item instanceof JetpackItem jetpackItem) return jetpackItem;
        return null;
    }

    public static ItemStack getJetpackItemStack(PlayerEntity player) {
        ItemStack chest = player.getEquippedStack(EquipmentSlot.CHEST);
        if (chest.isEmpty()) return null;

        if (hasDefaultJetpack(player)) return chest;
        if (chest.getItem() instanceof JetpackItem jetpackItem) return chest;

        return null;
    }

    public static boolean hasDefaultJetpack(PlayerEntity player) {
        return player.getAttributeValue(PEntityAttributes.JETPACK) == 1;
    }

    public static boolean hasEnergy(Item item) {
        return item instanceof JetpackItem jetpackItem && jetpackItem.getJetpack().capacity > 0;
    }

    public static boolean isEngineOn(ItemStack stack) {
        NbtCompound tag = stack.getNbt();
        return tag != null && tag.contains("Engine") && tag.getBoolean("Engine");
    }

    public static boolean toggleEngine(ItemStack stack) {
        NbtCompound tag = stack.getOrCreateNbt();
        boolean current = tag.contains("Engine") && tag.getBoolean("Engine");
        tag.putBoolean("Engine", !current);
        return !current;
    }

    public static boolean isHovering(ItemStack stack) {
        NbtCompound tag = stack.getNbt();
        return tag != null && tag.contains("Hover") && tag.getBoolean("Hover");
    }

    public static boolean toggleHover(ItemStack stack) {
        NbtCompound tag = stack.getOrCreateNbt();
        boolean current = tag.contains("Hover") && tag.getBoolean("Hover");
        tag.putBoolean("Hover", !current);
        return !current;
    }

    public static void fly(PlayerEntity player, double y) {
        Vec3d motion = player.getVelocity();
        player.setVelocity(motion.getX(), y, motion.getZ());
    }

    public static boolean isFlying(PlayerEntity player) {
        var stack = getJetpackItemStack(player);
        if (stack == null) return false;

        Item item = stack.getItem();
        if (item instanceof JetpackItem jetpack) {
            ItemSlotStorage storage = new ItemSlotStorage(player, EquipmentSlot.CHEST);
            if (jetpack.isEngineOn(stack) && (((EnergyStorage) EnergyStorage.ITEM.find(stack, ContainerItemContext.ofSingleSlot(storage))).getAmount() > 0L || player.isCreative() || jetpack.getJetpack().creative)) {
                if (jetpack.isHovering(stack)) {
                    return !player.isOnGround();
                } else {
                    return InputHandler.isHoldingUp(player);
                }
            }
        } else {
            if (isEngineOn(stack)) {
                if (isHovering(stack)) {
                    return !player.isOnGround();
                } else {
                    return InputHandler.isHoldingUp(player);
                }
            }
        }

        return false;
    }

    public static void tickJetpack(PlayerEntity player) {
        ItemStack stack = getJetpackItemStack(player);
        if (stack == null || !isEngineOn(stack)) return;

        boolean hover = isHovering(stack);
        if (InputHandler.isHoldingUp(player) || hover && !player.isOnGround()) {
            Jetpack info = getJetpack(stack);
            double hoverSpeed = InputHandler.isHoldingDown(player) ? info.speedHover : info.speedHoverSlow;
            double currentAccel = info.accelVert * (player.getVelocity().getY() < 0.3 ? 2.5 : 1.0);
            double currentSpeedVertical = info.speedVert * (player.isSubmergedInWater() ? 0.4 : 1.0);
            double usage = player.isSprinting() ? info.usage * info.sprintFuel : info.usage;
            boolean creative = info.creative;
            boolean hasEnergy = hasEnergy(stack.getItem());
            StackBaseStorage storage = !hasEnergy ? null : new StackBaseStorage(stack.copy());
            EnergyStorage energy = !hasEnergy ? null : (EnergyStorage) EnergyStorage.ITEM.find(stack.copy(), ContainerItemContext.ofPlayerSlot(player, storage));
            Transaction transaction = !hasEnergy ? null : Transaction.openOuter();

            try {
                if (creative || !hasEnergy || (double) energy.extract((long) usage, transaction) >= usage) {
                    if (!creative && hasEnergy) {
                        transaction.commit();
                        ItemStack newStack = storage.getResource().toStack();
                        stack.setNbt(newStack.getNbt());
                        stack.setCount(newStack.getCount());
                    }

                    double motionY = player.getVelocity().getY();
                    if (InputHandler.isHoldingUp(player)) {
                        if (!hover) {
                            fly(player, Math.min(motionY + currentAccel, currentSpeedVertical));
                        } else if (InputHandler.isHoldingDown(player)) {
                            fly(player, Math.min(motionY + currentAccel, -info.speedHoverSlow));
                        } else {
                            fly(player, Math.min(motionY + currentAccel, info.speedHover));
                        }
                    } else {
                        fly(player, Math.min(motionY + currentAccel, -hoverSpeed));
                    }

                    float speedSideways = (float) (player.isSneaking() ? info.speedSide * 0.5 : info.speedSide);
                    float speedForward = (float) (player.isSprinting() ? (double) speedSideways * info.sprintSpeed : (double) speedSideways);
                    if (InputHandler.isHoldingForwards(player)) {
                        player.updateVelocity(speedForward, new Vec3d(0.0, 0.0, 1.0));
                    }

                    if (InputHandler.isHoldingBackwards(player)) {
                        player.updateVelocity(-speedSideways * 0.8F, new Vec3d(0.0, 0.0, 1.0));
                    }

                    if (InputHandler.isHoldingLeft(player)) {
                        player.updateVelocity(speedSideways, new Vec3d(1.0, 0.0, 0.0));
                    }

                    if (InputHandler.isHoldingRight(player)) {
                        player.updateVelocity(-speedSideways, new Vec3d(1.0, 0.0, 0.0));
                    }

                    if (!player.world.isClient()) {
                        player.fallDistance = 0.0F;
                        if (player instanceof ServerPlayerEntity) {
                            ((ServerPlayNetworkHandlerAccessor) ((ServerPlayerEntity) player).networkHandler).setFloatingTicks(0);
                        }
                    }
                }
            } catch (Throwable var25) {
                if (transaction != null) {
                    try {
                        transaction.close();
                    } catch (Throwable var24) {
                        var25.addSuppressed(var24);
                    }
                }

                throw var25;
            }

            if (transaction != null) {
                transaction.close();
            }
        }

    }
}
