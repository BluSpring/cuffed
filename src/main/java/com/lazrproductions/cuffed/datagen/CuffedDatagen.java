package com.lazrproductions.cuffed.datagen;

import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class CuffedDatagen implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator generator) {
        var pack = generator.createPack();
        pack.addProvider(CuffedItemTagGen::new);
        pack.addProvider(CuffedRegistryGen::new);
    }

    @Override
    public void buildRegistry(RegistrySetBuilder registryBuilder) {
        DataGeneratorEntrypoint.super.buildRegistry(registryBuilder);
        registryBuilder.add(Registries.ENCHANTMENT, CuffedEnchantmentsGen::bootstrap);
    }
}
