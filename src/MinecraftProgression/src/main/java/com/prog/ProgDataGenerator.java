package com.prog;

import com.prog.data.PLangProvider;
import com.prog.data.PModelProvider;
import com.prog.data.PRecipeProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class ProgDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator generator) {
		generator.addProvider(PLangProvider::new);
		generator.addProvider(PModelProvider::new);
		generator.addProvider(PRecipeProvider::new);
	}
}
