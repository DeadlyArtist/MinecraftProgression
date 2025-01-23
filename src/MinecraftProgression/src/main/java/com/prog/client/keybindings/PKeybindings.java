package com.prog.client.keybindings;

import com.prog.Prog;
import com.prog.entity.PComponents;
import com.prog.entity.component.PlayerComponent;
import com.prog.network.PNetwork;
import com.prog.text.PTexts;
import com.prog.utils.LOGGER;
import com.prog.utils.StringUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Environment(EnvType.CLIENT)
public class PKeybindings {
    public static class KeyBindingData {
        public String name;

        public KeyBindingData(String name) {
            this.name = name;
        }
    }

    public static final Map<KeyBinding, KeyBindingData> data = new HashMap<>();
    public static final Map<KeyBinding, Integer> toggleNetworkMap = new HashMap<>();
    public static final Map<KeyBinding, Function<PlayerComponent, Boolean>> toggleStateMap = new HashMap<>();

    public static KeyBinding TOGGLE_LUMINANCE = registerNetworkedToggle("TOGGLE_LUMINANCE", InputUtil.UNKNOWN_KEY.getCode(), PNetwork.TOGGLE_LUMINANCE, player -> !player.luminanceDisabled);
    public static KeyBinding TOGGLE_STEP_ASSIST = registerNetworkedToggle("TOGGLE_STEP_ASSIST", InputUtil.UNKNOWN_KEY.getCode(), PNetwork.TOGGLE_STEP_ASSIST, player -> !player.stepAssistDisabled);
    public static KeyBinding TOGGLE_INSOMNIA_IMMUNITY = registerNetworkedToggle("TOGGLE_INSOMNIA_IMMUNITY", InputUtil.UNKNOWN_KEY.getCode(), PNetwork.TOGGLE_INSOMNIA_IMMUNITY, player -> !player.insomniaImmunityDisabled);
    public static KeyBinding TOGGLE_BAD_OMEN_IMMUNITY = registerNetworkedToggle("TOGGLE_BAD_OMEN_IMMUNITY", InputUtil.UNKNOWN_KEY.getCode(), PNetwork.TOGGLE_BAD_OMEN_IMMUNITY, player -> !player.badOmenImmunityDisabled);
    public static KeyBinding TOGGLE_MAGNET = registerNetworkedToggle("TOGGLE_MAGNET", InputUtil.UNKNOWN_KEY.getCode(), PNetwork.TOGGLE_MAGNET, player -> !player.magnetDisabled);
    public static KeyBinding TOGGLE_SILENT = registerNetworkedToggle("TOGGLE_SILENT", InputUtil.UNKNOWN_KEY.getCode(), PNetwork.TOGGLE_SILENT, player -> !player.silentDisabled);
    public static KeyBinding TOGGLE_ELYTRA = registerNetworkedToggle("TOGGLE_ELYTRA", InputUtil.GLFW_KEY_Y, PNetwork.TOGGLE_ELYTRA, player -> !player.elytraDisabled);

    public static KeyBinding registerNetworkedToggle(String id, int key, int networkId, Function<PlayerComponent, Boolean> toggleStateProvider) {
        var binding = register(id, key);
        toggleNetworkMap.put(binding, networkId);
        toggleStateMap.put(binding, toggleStateProvider);
        return binding;
    }

    public static KeyBinding register(String id, int key) {
        var binding = KeyBindingHelper.registerKeyBinding(new KeyBinding("key." + Prog.MOD_ID + "." + id.toLowerCase(), InputUtil.Type.KEYSYM, key, Prog.NAME));
        data.put(binding, new KeyBindingData(StringUtils.toNormalCase(id)));
        return binding;
    }

    public static void onClientTick(MinecraftClient client) {
        var player = client.player;
        if (player == null) return;

        toggleNetworkMap.forEach((key, id) -> {
            while (key.wasPressed()) toggle(key, id);
        });
    }

    public static void toggle(KeyBinding key, int networkId) {
        var player = MinecraftClient.getInstance().player;
        var newState = !toggleStateMap.get(key).apply(PComponents.PLAYER.get(player));
        Text message = Text.translatable((newState ? PTexts.KEYBIND_TOGGLED_ON_TOOLTIP : PTexts.KEYBIND_TOGGLED_OFF_TOOLTIP).id, data.get(key).name.replaceFirst("Toggle ", ""));
        MinecraftClient.getInstance().player.sendMessage(message, true);

        PNetwork.sendToServer(networkId);
    }

    public static void init() {
        LOGGER.info("Registering Keybindings for: " + Prog.MOD_ID);
    }
}
