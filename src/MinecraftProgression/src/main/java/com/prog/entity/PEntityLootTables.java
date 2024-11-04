package com.prog.entity;

import com.prog.Prog;
import com.prog.utils.LOGGER;
import net.minecraft.util.Identifier;

public class PEntityLootTables {

    public static final Identifier RANK_1 = register("RANK_1");

    public static Identifier register(String id) {
        return new Identifier("prog:entities/" + id.toLowerCase());
    }

    public static void init() {
        LOGGER.info("Registering Entity Loot Tables for: " + Prog.MOD_ID);
    }
}
