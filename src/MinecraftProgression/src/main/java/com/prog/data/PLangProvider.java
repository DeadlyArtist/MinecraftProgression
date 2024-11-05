package com.prog.data;

import com.prog.enchantment.PEnchantments;
import com.prog.entity.PStatusEffects;
import com.prog.entity.attribute.PEntityAttributes;
import com.prog.itemOrBlock.PBlocks;
import com.prog.itemOrBlock.PItemGroups;
import com.prog.itemOrBlock.PItems;
import com.prog.itemOrBlock.PJetpacks;
import com.prog.text.PTexts;
import com.prog.utils.JetpackUtils;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;

public class PLangProvider extends FabricLanguageProvider {
    public PLangProvider(FabricDataGenerator generator) {
        super(generator);
    }

    @Override
    public void generateTranslations(TranslationBuilder translationBuilder) {
        // Preregistered
        PTexts.data.forEach((item, data) -> translationBuilder.add(item.id, data.text));
        PEnchantments.data.forEach((item, data) -> translationBuilder.add(item, data.name));
        PItems.data.forEach((item, data) -> translationBuilder.add(item, data.name));
        PBlocks.data.forEach((item, data) -> translationBuilder.add(item, data.name));
        PItemGroups.data.forEach((item, data) -> translationBuilder.add(item, data.name));
        PEntityAttributes.data.forEach((item, data) -> translationBuilder.add(item, data.name));
        PStatusEffects.data.forEach((item, data) -> translationBuilder.add(item, data.name));

        // Custom
        translationBuilder.add(PJetpacks.MECHANICAL.item.get(), JetpackUtils.getDefaultName(PJetpacks.MECHANICAL));
    }
}
