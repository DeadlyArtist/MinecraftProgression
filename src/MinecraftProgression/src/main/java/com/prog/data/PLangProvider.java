package com.prog.data;

import com.prog.itemOrBlock.PBlocks;
import com.prog.itemOrBlock.PItemGroups;
import com.prog.itemOrBlock.PItems;
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
