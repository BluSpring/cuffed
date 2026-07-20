package com.lazrproductions.cuffed.datagen;

import java.util.concurrent.CompletableFuture;

import com.lazrproductions.cuffed.init.ModItems;
import com.lazrproductions.cuffed.init.ModTags;

import net.minecraft.core.HolderLookup;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;

public class CuffedItemTagGen extends FabricTagProvider.ItemTagProvider {
    public CuffedItemTagGen(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(ModTags.Items.SUPPORTS_BUOYANT)
            .add(ModItems.WEIGHTED_ANCHOR_ITEM.builtInRegistryHolder().key());

        this.tag(ModTags.Items.SUPPORTS_SILENCE)
            .addTag(ModTags.Items.RESTRAINTS);

        this.tag(ModTags.Items.SUPPORTS_EXHAUST)
            .addTag(ModTags.Items.RESTRAINTS);

        this.tag(ModTags.Items.SUPPORTS_FAMINE)
            .addTag(ModTags.Items.RESTRAINTS);

        this.tag(ModTags.Items.SUPPORTS_IMBUE)
            .addTag(ModTags.Items.RESTRAINTS);

        this.tag(ModTags.Items.SUPPORTS_EXHAUST)
            .addTag(ModTags.Items.RESTRAINTS);
    }
}
