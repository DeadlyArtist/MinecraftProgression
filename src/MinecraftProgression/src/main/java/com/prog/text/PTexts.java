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


    public static TextWrapper registerText(String id, String text) {
        TextWrapper wrapper = new TextWrapper("text." + Prog.MOD_ID + "." + id.toLowerCase());
        data.put(wrapper, new TextData(text));
        return wrapper;
    }
}
