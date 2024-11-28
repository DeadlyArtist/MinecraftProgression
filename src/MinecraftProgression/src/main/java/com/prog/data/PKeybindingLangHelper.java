package com.prog.data;

import com.prog.Prog;
import com.prog.utils.LOGGER;
import com.prog.utils.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class PKeybindingLangHelper {
    public static Map<String, String> data = new HashMap<>();;

    static {
        register("TOGGLE_LUMINANCE");
        register("TOGGLE_STEP_ASSIST");
        register("TOGGLE_BAD_OMEN_IMMUNITY");
        register("TOGGLE_MAGNET");
        register("TOGGLE_SILENT");
        register("TOGGLE_ELYTRA");
    }

    public static void register(String id) {
        data.put("key." + Prog.MOD_ID + "." + id.toLowerCase(), StringUtils.toNormalCase(id));
    }

    public static void init() {
        LOGGER.info("Registering KeybindingLangHelper for: " + Prog.MOD_ID);
    }
}
