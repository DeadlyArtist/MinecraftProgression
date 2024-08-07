package com.prog.utils;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.function.Consumer;

public class ScreenUtils {
    public static void addPlayerInventory(Consumer<Slot> slotConsumer, PlayerInventory playerInventory, int x, int y){
        for (int i = 0; i < InventoryUtils.inventoryRows; i++) {
            for (int j = 0; j < InventoryUtils.inventoryColumns; j++) {
                slotConsumer.accept(new Slot(playerInventory, j + i * SlotUtils.HALF_SIZE + SlotUtils.HALF_SIZE, x + j * SlotUtils.SIZE, y + i * SlotUtils.SIZE));
            }
        }

        for (int i = 0; i < InventoryUtils.hotbarColumns; i++) {
            slotConsumer.accept(new Slot(playerInventory, i, x + i * SlotUtils.SIZE, y + InventoryUtils.inventoryRows * SlotUtils.SIZE + 4));
        }
    }

    public static Identifier getId(ScreenHandler screenHandler) {
        return Registry.SCREEN_HANDLER.getId(screenHandler.getType());
    }

    public static Identifier getId(ScreenHandlerType screenHandler) {
        return Registry.SCREEN_HANDLER.getId(screenHandler);
    }

    public static Identifier getScreenBackgroundTexture(ScreenHandler screenHandler){
        var id = getId(screenHandler);
        return TextureUtils.getGuiContainerTexture(id.getNamespace(), id.getPath());
    }

    public static Identifier getScreenBackgroundTexture(ScreenHandlerType screenHandler) {
        var id = getId(screenHandler);
        return TextureUtils.getGuiContainerTexture(id.getNamespace(), id.getPath());
    }
}
