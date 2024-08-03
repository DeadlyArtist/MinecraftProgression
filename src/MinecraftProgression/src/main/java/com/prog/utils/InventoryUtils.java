package com.prog.utils;

public class InventoryUtils {
    public static final int inventoryRows = 3;
    public static final int inventoryColumns = 9;
    public static final int hotbarColumns = 9;

    public static int getInventorySlotsCount(){
        return inventoryRows * inventoryColumns;
    }

    public static int getAllSlotsCount() {
        return getInventorySlotsCount() + hotbarColumns;
    }
}
