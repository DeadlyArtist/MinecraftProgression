package com.prog.data;

import com.prog.item.PItemGroups;
import com.prog.item.PItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;

public class PLangProvider extends FabricLanguageProvider {
    public PLangProvider(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generateTranslations(TranslationBuilder translationBuilder) {
        PItems.names.forEach(translationBuilder::add);
        PItemGroups.names.forEach(translationBuilder::add);
    }
}
