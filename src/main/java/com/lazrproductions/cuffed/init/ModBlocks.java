package com.lazrproductions.cuffed.init;

import com.lazrproductions.cuffed.CuffedMod;
import com.lazrproductions.cuffed.blocks.*;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

public class ModBlocks {
    public static final Block CELL_DOOR = register("cell_door",
        new CellDoor(
            BlockBehaviour.Properties.of().mapColor(MapColor.METAL).noOcclusion()
                .strength(5.0F, 6.0F).requiresCorrectToolForDrops()
                .sound(SoundType.METAL).pushReaction(PushReaction.IGNORE),
            BlockSetType.IRON));

    public static final Block REINFORCED_STONE = register("reinforced_stone",
        new Block(BlockBehaviour.Properties.of().sound(SoundType.STONE).mapColor(MapColor.METAL)
            .requiresCorrectToolForDrops().strength(6.0F, 12.0F)
            .pushReaction(PushReaction.IGNORE)));

    public static final Block REINFORCED_LAMP = register("reinforced_lamp",
        new Block(BlockBehaviour.Properties.of().sound(SoundType.GLASS).mapColor(MapColor.ICE)
            .requiresCorrectToolForDrops().strength(3.0F, 8.0F)
            .noOcclusion()
            .emissiveRendering((state, getter, pos) -> {
                return true;
            }).lightLevel((state) -> {
                return 15;
            })
            .pushReaction(PushReaction.IGNORE)));

    public static final Block REINFORCED_STONE_CHISELED = register(
        "chiseled_reinforced_stone",
        new Block(BlockBehaviour.Properties.of().sound(SoundType.STONE).mapColor(MapColor.METAL)
            .requiresCorrectToolForDrops().strength(6.0F, 12.0F)
            .pushReaction(PushReaction.IGNORE)));

    public static final Block REINFORCED_STONE_SLAB = register("reinforced_stone_slab",
        new SlabBlock(BlockBehaviour.Properties.ofFullCopy(REINFORCED_STONE)));

    public static final Block REINFORCED_STONE_STAIRS = register("reinforced_stone_stairs",
        new StairBlock(REINFORCED_STONE.defaultBlockState(),
            BlockBehaviour.Properties.ofFullCopy(REINFORCED_STONE)));

    public static final Block REINFORCED_SMOOTH_STONE = register("reinforced_smooth_stone",
        new Block(BlockBehaviour.Properties.ofFullCopy(REINFORCED_STONE)));

    public static final Block REINFORCED_BARS = register("reinforced_bars",
        new ReinforcedBarsBlock(
            BlockBehaviour.Properties.of().sound(SoundType.METAL).mapColor(MapColor.METAL)
                .noOcclusion().strength(6.0F, 18.0F)
                .pushReaction(PushReaction.IGNORE)));

    public static final Block REINFORCED_BARS_GAPPED = register("reinforced_bars_gap",
        new ReinforcedBarsGappedBlock(
            BlockBehaviour.Properties.of().sound(SoundType.METAL).mapColor(MapColor.METAL)
                .noOcclusion().strength(6.0F, 18.0F)
                .pushReaction(PushReaction.IGNORE)));

    public static final Block PILLORY = register("pillory",
        new PilloryBlock(BlockBehaviour.Properties.of().sound(SoundType.WOOD)
            .mapColor(MapColor.WOOD).noCollission().strength(1.25F)));
    public static final Block GUILLOTINE = register("guillotine",
        new GuillotineBlock(BlockBehaviour.Properties.of().sound(SoundType.WOOD)
            .mapColor(MapColor.WOOD).noCollission().strength(1.25F)));

    public static final Block SAFE = register("safe",
        new SafeBlock(BlockBehaviour.Properties.of().sound(SoundType.NETHERITE_BLOCK)
            .mapColor(MapColor.COLOR_GRAY).noOcclusion().strength(6.0F, 18.0F)));

    public static final Block BUNK = register("bunk",
        new BunkBlock(BlockBehaviour.Properties.of().sound(SoundType.NETHERITE_BLOCK)
            .mapColor(MapColor.COLOR_GRAY).noOcclusion().strength(6.0F, 18.0F)));

    public static final Block POSTER = register("poster",
        new PosterBlock(BlockBehaviour.Properties.of().sound(SoundType.SCAFFOLDING)
            .mapColor(MapColor.COLOR_RED).noOcclusion().instabreak()));

    public static final Block TRAY = register("tray",
        new TrayBlock(BlockBehaviour.Properties.of().sound(SoundType.LANTERN)
            .mapColor(MapColor.COLOR_LIGHT_GRAY).noOcclusion()));

    // public static final Block TOILET = register("toilet",
    // new
    // ToiletBlock(BlockBehaviour.Properties.of().sound(SoundType.BONE_BLOCK)
    // .mapColor(MapColor.COLOR_LIGHT_GRAY).noOcclusion().strength(0.75F)));

    public static Block register(String name, Block block) {
        return Registry.register(BuiltInRegistries.BLOCK, CuffedMod.id(name), block);
    }

    public static void register() {
    }
}
