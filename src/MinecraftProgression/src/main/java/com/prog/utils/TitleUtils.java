package com.prog.utils;

import com.prog.Prog;
import net.minecraft.GameVersion;

public class TitleUtils {
    public static String getVersionAppendum(GameVersion instance) {
        //return "(" + instance.getName() + "): Progression (" + Prog.VERSION + ")";
        return instance.getName() + ": Progression " + Prog.VERSION;
    }
}
