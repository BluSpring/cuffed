package com.lazrproductions.cuffed.init;

import com.lazrproductions.cuffed.CuffedMod;
import com.lazrproductions.cuffed.entity.ChainKnotEntity;
import com.lazrproductions.cuffed.entity.CrumblingBlockEntity;
import com.lazrproductions.cuffed.entity.PadlockEntity;
import com.lazrproductions.cuffed.entity.WeightedAnchorEntity;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class ModEntityTypes {
    public static EntityType<ChainKnotEntity> CHAIN_KNOT = register("chain_knot",
        EntityType.Builder.<ChainKnotEntity>of(ChainKnotEntity::new, MobCategory.MISC)
            .clientTrackingRange(10)
            .updateInterval(Integer.MAX_VALUE)
            .alwaysUpdateVelocity(false)
            .sized(6 / 16f, 0.5f).canSpawnFarFromPlayer().fireImmune()
            .build(ResourceLocation.fromNamespaceAndPath(CuffedMod.MODID, "chain_knot").toString()));

    public static EntityType<PadlockEntity> PADLOCK = register("padlock",
        EntityType.Builder.<PadlockEntity>of(PadlockEntity::new, MobCategory.MISC)
            .clientTrackingRange(10)
            .updateInterval(Integer.MAX_VALUE)
            .alwaysUpdateVelocity(false)
            .sized(6 / 16f, 0.1f).canSpawnFarFromPlayer().fireImmune()
            .build(ResourceLocation.fromNamespaceAndPath(CuffedMod.MODID, "padlock").toString()));

    public static EntityType<WeightedAnchorEntity> WEIGHTED_ANCHOR = register(
        "weighted_anchor",
        EntityType.Builder.<WeightedAnchorEntity>of(WeightedAnchorEntity::new, MobCategory.MISC)
            .sized(8 / 16f, 0.5f).canSpawnFarFromPlayer().fireImmune()
            .clientTrackingRange(10)
            .build(ResourceLocation.fromNamespaceAndPath(CuffedMod.MODID, "weighted_anchor").toString()));

    public static EntityType<CrumblingBlockEntity> CRUMBLING_BLOCK = register(
        "crumbling_block",
        EntityType.Builder.<CrumblingBlockEntity>of(CrumblingBlockEntity::new, MobCategory.MISC)
            .sized(0.3F, 0.3F).build("crumbling_block"));

    public static void register() {
    }

    private static <T extends Entity> EntityType<T> register(String name, EntityType<T> type) {
        return Registry.register(BuiltInRegistries.ENTITY_TYPE, CuffedMod.id(name), type);
    }

    public static void registerAttributes() {
        FabricDefaultAttributeRegistry.register(ModEntityTypes.WEIGHTED_ANCHOR, WeightedAnchorEntity.createAttributes().build());
    }
}
