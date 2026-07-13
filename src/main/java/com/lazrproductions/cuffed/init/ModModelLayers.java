package com.lazrproductions.cuffed.init;

import com.lazrproductions.cuffed.CuffedMod;
import com.lazrproductions.cuffed.blocks.entity.model.GuillotineBlockEntityModel;
import com.lazrproductions.cuffed.entity.model.ChainKnotEntityModel;
import com.lazrproductions.cuffed.entity.model.CrumblingBlockModel;
import com.lazrproductions.cuffed.entity.model.PadlockEntityModel;
import com.lazrproductions.cuffed.entity.model.WeightedAnchorModel;
import com.lazrproductions.cuffed.restraints.client.model.*;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;

public class ModModelLayers {
    // Restraints
    public static final ModelLayerLocation HANDCUFFS_LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(CuffedMod.MODID, "handcuffs_layer"), "main");
    public static final ModelLayerLocation SHACKLES_LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(CuffedMod.MODID, "shackles_layer"), "main");
    public static final ModelLayerLocation FUZZY_HANDCUFFS_LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(CuffedMod.MODID, "fuzzy_handcuffs_layer"), "main");
    public static final ModelLayerLocation LEGCUFFS_LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(CuffedMod.MODID, "legcuffs_layer"), "main");
    public static final ModelLayerLocation LEG_SHACKELS_LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(CuffedMod.MODID, "leg_shackles_layer"), "main");
    public static final ModelLayerLocation DUCK_TAPE_HEAD_LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(CuffedMod.MODID, "duck_tape_head_layer"), "main");
    public static final ModelLayerLocation DUCK_TAPE_ARM_LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(CuffedMod.MODID, "duck_tape_arm_layer"), "main");
    public static final ModelLayerLocation DUCK_TAPE_LEG_LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(CuffedMod.MODID, "duck_tape_leg_layer"), "main");
    public static final ModelLayerLocation BUNDLE_LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(CuffedMod.MODID, "bundle_layer"), "main");

    // Entities
	public static final ModelLayerLocation CHAIN_KNOT_LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(CuffedMod.MODID, "chain_knot"), "main");
    public static final ModelLayerLocation PADLOCK_LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(CuffedMod.MODID, "padlock"), "main");
    public static final ModelLayerLocation WEIGHTED_ANCHOR_LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(CuffedMod.MODID, "weighted_anchor"), "main");
    public static final ModelLayerLocation CRUMBLING_BLOCK_LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(CuffedMod.MODID, "crumbling_block"), "main");

    // Block Entities
	public static final ModelLayerLocation GUILLOTINE_LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(CuffedMod.MODID, "guillotine_block_entity"), "main");

    @Environment(EnvType.CLIENT)
    public static void registerLayers() {
        EntityModelLayerRegistry.registerModelLayer(CHAIN_KNOT_LAYER, ChainKnotEntityModel::getModelData);
        EntityModelLayerRegistry.registerModelLayer(PADLOCK_LAYER, PadlockEntityModel::getModelData);
        EntityModelLayerRegistry.registerModelLayer(WEIGHTED_ANCHOR_LAYER, WeightedAnchorModel::getModelData);
        EntityModelLayerRegistry.registerModelLayer(CRUMBLING_BLOCK_LAYER, CrumblingBlockModel::getModelData);
        EntityModelLayerRegistry.registerModelLayer(HANDCUFFS_LAYER, HandcuffsModel::createArmorLayer);
        EntityModelLayerRegistry.registerModelLayer(SHACKLES_LAYER, ShacklesModel::createArmorLayer);
        EntityModelLayerRegistry.registerModelLayer(FUZZY_HANDCUFFS_LAYER, FuzzyHandcuffsModel::createArmorLayer);
        EntityModelLayerRegistry.registerModelLayer(LEGCUFFS_LAYER, LegcuffsModel::createArmorLayer);
        EntityModelLayerRegistry.registerModelLayer(LEG_SHACKELS_LAYER, LegShacklesModel::createArmorLayer);
        EntityModelLayerRegistry.registerModelLayer(DUCK_TAPE_HEAD_LAYER, DuckTapeHeadModel::createArmorLayer);
        EntityModelLayerRegistry.registerModelLayer(DUCK_TAPE_ARM_LAYER, DuckTapeArmsModel::createArmorLayer);
        EntityModelLayerRegistry.registerModelLayer(DUCK_TAPE_LEG_LAYER, DuckTapeLegsModel::createArmorLayer);
        EntityModelLayerRegistry.registerModelLayer(BUNDLE_LAYER, BundleModel::createArmorLayer);
        EntityModelLayerRegistry.registerModelLayer(GUILLOTINE_LAYER, GuillotineBlockEntityModel::createBodyLayer);
    }
}
