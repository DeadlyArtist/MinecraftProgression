package com.prog.text;

import com.prog.Prog;
import com.prog.itemOrBlock.PItemGroups;
import net.minecraft.item.ItemGroup;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableTextContent;

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


    public static final TextWrapper UPGRADEABLE_UPGRADE_TOOLTIP = registerText("UPGRADEABLE_UPGRADE_TOOLTIP", "Upgrades");
    public static final TextWrapper UPGRADE_TOOLTIP = registerText("UPGRADE_TOOLTIP", "Upgrade");
    public static final TextWrapper UPGRADABLE_TOOLTIP = registerText("UPGRADABLE_TOOLTIP", "Upgradable");
    public static final TextWrapper GOURMET_NOT_EATEN_TOOLTIP = registerText("GOURMET_NOT_EATEN_TOOLTIP", "Must... eat...");
    public static final TextWrapper GOURMET_HAS_EATEN_TOOLTIP = registerText("GOURMET_HAS_EATEN_TOOLTIP", "Already tasted");
    public static final TextWrapper WHEN_HELD_TOOLTIP = registerText("WHEN_HELD_TOOLTIP", "When Held");
    public static final TextWrapper ASSEMBLY_UI_TITLE = registerText("ASSEMBLY_UI_TITLE", "Assembly");
    public static final TextWrapper COSMIC_CONSTRUCTOR_UI_TITLE = registerText("COSMIC_CONSTRUCTOR_UI_TITLE", "Cosmic Constructor");
    public static final TextWrapper INCINERATOR_UI_TITLE = registerText("INCINERATOR_UI_TITLE", "Incinerator");
    public static final TextWrapper COSMIC_INCUBATOR_UI_TITLE = registerText("COSMIC_INCUBATOR_UI_TITLE", "Cosmic Incubator");


    public static TextWrapper registerText(String id, String text) {
        TextWrapper wrapper = new TextWrapper("text." + Prog.MOD_ID + "." + id.toLowerCase());
        data.put(wrapper, new TextData(text));
        return wrapper;
    }

    public static void init() {
        Prog.LOGGER.info("Registering Translatable Texts for: " + Prog.MOD_ID);
    }
}
