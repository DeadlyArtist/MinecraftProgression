package com.prog;

import com.prog.data.PLangProvider;
import com.prog.data.PModelProvider;
import com.prog.data.PRecipeProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class ProgDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator generator) {
		FabricDataGenerator.Pack pack = generator.createPack();

		pack.addProvider(PLangProvider::new);
		pack.addProvider(PModelProvider::new);
		pack.addProvider(PRecipeProvider::new);
	}
}
