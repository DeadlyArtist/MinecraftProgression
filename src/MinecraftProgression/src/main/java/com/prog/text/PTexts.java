package com.prog.text;

import com.prog.Prog;
import com.prog.utils.LOGGER;
import net.minecraft.text.Text;

import java.util.HashMap;
import java.util.Map;

public class PTexts {
    public static class TextWrapper {
        public final String id;
        public TextWrapper(String id){
            this.id = id;
        }

        public Text get(){
            return Text.translatable(id);
        }
    }

    public static class TextData {
        public String text;

        public TextData(String text) {
            this.text = text;
        }
    }

    public static final Map<TextWrapper, TextData> data = new HashMap<>();


    public static final TextWrapper FIREPROOF_TOOLTIP = registerText("FIREPROOF_TOOLTIP", "Fireproof");
    public static final TextWrapper SOULBOUND_TOOLTIP = registerText("SOULBOUND_TOOLTIP", "Soulbound");

    public static final TextWrapper UPGRADEABLE_UPGRADE_TOOLTIP = registerText("UPGRADEABLE_UPGRADE_TOOLTIP", "Upgrades");
    public static final TextWrapper UPGRADE_TOOLTIP = registerText("UPGRADE_TOOLTIP", "Upgrade");
    public static final TextWrapper TIER_CORE_TOOLTIP = registerText("TIER_CORE_TOOLTIP", "Tier Core");
    public static final TextWrapper UPGRADABLE_TOOLTIP = registerText("UPGRADABLE_TOOLTIP", "Upgradable");
    public static final TextWrapper GOURMET_NOT_EATEN_TOOLTIP = registerText("GOURMET_NOT_EATEN_TOOLTIP", "Must... eat...");
    public static final TextWrapper GOURMET_HAS_EATEN_TOOLTIP = registerText("GOURMET_HAS_EATEN_TOOLTIP", "Already tasted");
    public static final TextWrapper WHEN_HELD_TOOLTIP = registerText("WHEN_HELD_TOOLTIP", "When Held");
    public static final TextWrapper ASSEMBLY_UI_TITLE = registerText("ASSEMBLY_UI_TITLE", "Assembly");
    public static final TextWrapper COSMIC_CONSTRUCTOR_UI_TITLE = registerText("COSMIC_CONSTRUCTOR_UI_TITLE", "Cosmic Constructor");
    public static final TextWrapper INCINERATOR_UI_TITLE = registerText("INCINERATOR_UI_TITLE", "Incinerator");
    public static final TextWrapper COSMIC_INCUBATOR_UI_TITLE = registerText("COSMIC_INCUBATOR_UI_TITLE", "Cosmic Incubator");

    // Ranks
    public static final TextWrapper GENERAL_RANK = registerText("GENERAL_RANK", "General");
    public static final TextWrapper COMMANDER_RANK = registerText("COMMANDER_RANK", "Commander");
    public static final TextWrapper WARLORD_RANK = registerText("WARLORD_RANK", "Warlord");
    public static final TextWrapper OVERLORD_RANK = registerText("OVERLORD_RANK", "Overlord");
    public static final TextWrapper GOD_RANK = registerText("GOD_RANK", "God");

    public static Map<Integer, TextWrapper> nameByRank = new HashMap<>();
    static {
        nameByRank.put(1, GENERAL_RANK);
        nameByRank.put(2, COMMANDER_RANK);
        nameByRank.put(3, WARLORD_RANK);
        nameByRank.put(4, OVERLORD_RANK);
        nameByRank.put(5, GOD_RANK);
    }


    public static TextWrapper registerText(String id, String text) {
        TextWrapper wrapper = new TextWrapper("text." + Prog.MOD_ID + "." + id.toLowerCase());
        data.put(wrapper, new TextData(text));
        return wrapper;
    }

    public static void init() {
        LOGGER.info("Registering Translatable Texts for: " + Prog.MOD_ID);
    }
}
