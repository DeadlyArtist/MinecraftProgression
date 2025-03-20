package com.prog.lootTable;

import com.prog.Prog;
import net.minecraft.util.Identifier;

public class PLootTables {
    public static final Identifier LAVA_FISHING_GAMEPLAY = register("gameplay/lava_fishing");
    public static final Identifier LAVA_FISHING_JUNK_GAMEPLAY = register("gameplay/lava_fishing/junk");
    public static final Identifier LAVA_FISHING_TREASURE_GAMEPLAY = register("gameplay/lava_fishing/treasure");
    public static final Identifier LAVA_FISHING_FISH_GAMEPLAY = register("gameplay/lava_fishing/fish");
    public static final Identifier VOID_FISHING_GAMEPLAY = register("gameplay/void_fishing");
    public static final Identifier VOID_FISHING_JUNK_GAMEPLAY = register("gameplay/void_fishing/junk");
    public static final Identifier VOID_FISHING_TREASURE_GAMEPLAY = register("gameplay/void_fishing/treasure");
    public static final Identifier VOID_FISHING_FISH_GAMEPLAY = register("gameplay/void_fishing/fish");

    public static Identifier register(String id) {
        return Identifier.of(Prog.MOD_ID, id.toLowerCase());
    }
}
