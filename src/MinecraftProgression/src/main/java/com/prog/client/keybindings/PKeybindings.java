package com.prog.client.keybindings;

import com.prog.Prog;
import com.prog.network.PNetwork;
import com.prog.utils.LOGGER;
import com.prog.utils.StringUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Formatting;

import java.util.HashMap;
import java.util.Map;

@Environment(EnvType.CLIENT)
public class PKeybindings {
    public static class KeyBindingData {
        public String name;

        public KeyBindingData(String name) {
            this.name = name;
        }
    }

    public static final Map<KeyBinding, KeyBindingData> data = new HashMap<>();

    public static KeyBinding TOGGLE_HEADLIGHT = register("TOGGLE_HEADLIGHT", InputUtil.UNKNOWN_KEY.getCode());
    public static KeyBinding TOGGLE_STEP_ASSIST = register("TOGGLE_STEP_ASSIST", InputUtil.UNKNOWN_KEY.getCode());
    public static KeyBinding TOGGLE_BAD_OMEN_IMMUNITY = register("TOGGLE_BAD_OMEN_IMMUNITY", InputUtil.UNKNOWN_KEY.getCode());

    public static KeyBinding register(String id, int key) {
        var binding = KeyBindingHelper.registerKeyBinding(new KeyBinding("key." + Prog.MOD_ID + "." + id.toLowerCase(), InputUtil.Type.KEYSYM, key, Prog.NAME));
        data.put(binding, new KeyBindingData(StringUtils.toNormalCase(id)));
        return binding;
    }

    public static void onClientTick(MinecraftClient client) {
        var player = client.player;
        if (player == null) return;

        while (TOGGLE_HEADLIGHT.wasPressed()) PNetwork.sendToServer(PNetwork.TOGGLE_HEADLIGHT);
        while (TOGGLE_STEP_ASSIST.wasPressed()) PNetwork.sendToServer(PNetwork.TOGGLE_STEP_ASSIST);
        while (TOGGLE_BAD_OMEN_IMMUNITY.wasPressed()) PNetwork.sendToServer(PNetwork.TOGGLE_BAD_OMEN_IMMUNITY);
    }

    public static void init() {
        LOGGER.info("Registering Keybindings for: " + Prog.MOD_ID);
    }
}
