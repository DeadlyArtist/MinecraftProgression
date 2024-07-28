package com.prog.data;

import com.prog.block.PBlocks;
import com.prog.item.PItemGroups;
import com.prog.item.PItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;

public class PLangProvider extends FabricLanguageProvider {
    public PLangProvider(FabricDataGenerator generator) {
        super(generator);
    }

    @Override
    public void generateTranslations(TranslationBuilder translationBuilder) {
        PItems.data.forEach((item, data) -> translationBuilder.add(item, data.name));
        PBlocks.data.forEach((item, data) -> translationBuilder.add(item, data.name));
        PItemGroups.data.forEach((item, data) -> translationBuilder.add(item, data.name));
    }
}
