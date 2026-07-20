package com.lazrproductions.cuffed.datagen;

import com.lazrproductions.cuffed.init.ModItems;
import com.lazrproductions.cuffed.init.ModTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;

import java.util.concurrent.CompletableFuture;

public class CuffedItemTagGen extends FabricTagProvider.ItemTagProvider {
    public CuffedItemTagGen(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(ModTags.Items.ARM_RESTRAINTS)
            .add(ModItems.DUCK_TAPE.builtInRegistryHolder().key())
            .add(ModItems.HANDCUFFS.builtInRegistryHolder().key())
            .add(ModItems.FUZZY_HANDCUFFS.builtInRegistryHolder().key())
            .add(ModItems.SHACKLES.builtInRegistryHolder().key());

        this.tag(ModTags.Items.HEAD_RESTRAINTS)
            .add(ModItems.DUCK_TAPE.builtInRegistryHolder().key())
            .add(ModItems.PILLORY_ITEM.builtInRegistryHolder().key());

        this.tag(ModTags.Items.LEG_RESTRAINTS)
            .add(ModItems.DUCK_TAPE.builtInRegistryHolder().key())
            .add(ModItems.HANDCUFFS.builtInRegistryHolder().key())
            .add(ModItems.SHACKLES.builtInRegistryHolder().key());

        this.tag(ModTags.Items.RESTRAINTS)
            .addTag(ModTags.Items.HEAD_RESTRAINTS)
            .addTag(ModTags.Items.ARM_RESTRAINTS)
            .addTag(ModTags.Items.LEG_RESTRAINTS);

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
