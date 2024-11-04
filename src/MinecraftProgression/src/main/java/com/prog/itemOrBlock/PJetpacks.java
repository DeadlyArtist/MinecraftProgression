package com.prog.itemOrBlock;

import com.blakebr0.ironjetpacks.item.JetpackItem;
import com.blakebr0.ironjetpacks.registry.Jetpack;
import com.blakebr0.ironjetpacks.registry.JetpackRegistry;

public class PJetpacks {
    public static final Jetpack MECHANICAL = JetpackRegistry.createJetpack("mechanical",0,5658198,3,15,"null").setStats(0.0, 0.0, 0.5, 0.1, 0.01, 0.16, 0.14, 1.2, 1.0);

    public static Jetpack getDefault() {
        return MECHANICAL;
    }

    public static JetpackItem getDefaultItem() {
        return getDefault().item.get();
    }

    public static void registerJetpacks() {
        JetpackRegistry registry = JetpackRegistry.getInstance();
        var jetpack = PJetpacks.MECHANICAL;
        registry.register(jetpack);
    }
}
