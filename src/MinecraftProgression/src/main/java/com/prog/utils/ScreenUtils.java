package com.prog.utils;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

import java.util.function.Consumer;

public class ScreenUtils {
    public static void addPlayerInventory(Consumer<Slot> slotConsumer, PlayerInventory playerInventory, int x, int y){
        for (int i = 0; i < InventoryUtils.inventoryRows; i++) {
            for (int j = 0; j < InventoryUtils.inventoryColumns; j++) {
                slotConsumer.accept(new Slot(playerInventory, j + i * 9 + 9, x + j * 18, y + i * 18));
            }
        }

        for (int i = 0; i < InventoryUtils.hotbarColumns; i++) {
            slotConsumer.accept(new Slot(playerInventory, i, x + i * 18, y + 58));
        }
    }
}
