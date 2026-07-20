package com.lazrproductions.cuffed.datagen;

import java.nio.file.Path;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import com.google.gson.JsonElement;
import com.lazrproductions.cuffed.CuffedMod;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.Encoder;
import com.mojang.serialization.JsonOps;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.RegistryDataLoader;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.Unit;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;

public class CuffedRegistryGen implements DataProvider {
    private final FabricDataOutput output;
    private final CompletableFuture<HolderLookup.Provider> registries;

    public CuffedRegistryGen(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        this.registries = registries;
        this.output = output;
    }

    public CompletableFuture<?> run(CachedOutput output) {
        return this.registries.thenCompose((provider) -> {
            DynamicOps<JsonElement> dynamicOps = provider.createSerializationContext(JsonOps.INSTANCE);
            return CompletableFuture.allOf(RegistryDataLoader.WORLDGEN_REGISTRIES.stream().flatMap((registryData) -> this.dumpRegistryCap(output, provider, dynamicOps, registryData).stream()).toArray((i) -> new CompletableFuture[i]));
        });
    }

    private <T> Optional<CompletableFuture<?>> dumpRegistryCap(CachedOutput output, HolderLookup.Provider registries, DynamicOps<JsonElement> ops, RegistryDataLoader.RegistryData<T> registryData) {
        ResourceKey<? extends Registry<T>> resourceKey = registryData.key();
        return registries.lookup(resourceKey).map((registryLookup) -> {
            PackOutput.PathProvider pathProvider = this.output.createRegistryElementsPathProvider(resourceKey);
            return CompletableFuture.allOf(registryLookup.listElements().map((reference) -> {
                if (!reference.key().location().getNamespace().equals(CuffedMod.MODID)) // safeguard, must be your mod ID, to filter out Vanilla's datagen.
                    return CompletableFuture.completedFuture(Unit.INSTANCE);

                return dumpValue(pathProvider.json(reference.key().location()), output, ops, registryData.elementCodec(), reference.value());
            }).toArray(CompletableFuture[]::new));
        });
    }

    public static <E> CompletableFuture<?> dumpValue(Path valuePath, CachedOutput output, DynamicOps<JsonElement> ops, Encoder<E> encoder, E value) {
        return encoder.encodeStart(ops, value).mapOrElse((jsonElement) -> DataProvider.saveStable(output, jsonElement, valuePath), (error) -> {
            String var10002 = String.valueOf(valuePath);
            return CompletableFuture.failedFuture(new IllegalStateException("Couldn't generate file '" + var10002 + "': " + error.message()));
        });
    }

    public String getName() {
        return "Registries";
    }
}
