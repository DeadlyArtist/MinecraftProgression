package com.prog;

import com.prog.data.*;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class ProgDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator generator) {
		generator.addProvider(PLangProvider::new);
		generator.addProvider(PModelProvider::new);
		generator.addProvider(PRecipeProvider::new);
		generator.addProvider(PLootTableProvider::new);

		PBlockTagProvider pBlockTagProvider = new PBlockTagProvider(generator);
		generator.addProvider(pBlockTagProvider);
		generator.addProvider(gen -> new PItemTagProvider(gen, pBlockTagProvider));
	}
}
