package com.lazrproductions.cuffed.init;

import com.lazrproductions.cuffed.CuffedMod;
import com.lazrproductions.cuffed.items.*;
import com.lazrproductions.cuffed.items.base.AbstractRestraintKeyItem;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class ModItems {
    // Normal Items
    public static final Item KEY = register("key",
        new KeyItem(new Item.Properties().stacksTo(1)));
    public static final Item KEY_RING = register("key_ring",
        new KeyRingItem(new Item.Properties().stacksTo(1)));
    public static final Item KEY_MOLD = register("key_mold",
        new KeyMoldItem(new Item.Properties().stacksTo(1)));
    public static final Item BAKED_KEY_MOLD = register("baked_key_mold",
        new BakedKeyMoldItem(new Item.Properties().stacksTo(1)));


    public static final Item HANDCUFFS_KEY = register("handcuffs_key",
        new AbstractRestraintKeyItem(new Item.Properties().stacksTo(1)));
    public static final Item SHACKLES_KEY = register("shackles_key",
        new AbstractRestraintKeyItem(new Item.Properties().stacksTo(1)));


    public static final Item HANDCUFFS = register("handcuffs",
        new HandcuffsItem(new Item.Properties().stacksTo(1)
            .durability(999)));
    public static final Item FUZZY_HANDCUFFS = register("fuzzy_handcuffs",
        new FuzzyHandcuffsItem(new Item.Properties().stacksTo(1)
            .durability(999)));
    public static final Item SHACKLES = register("shackles",
        new ShacklesItem(new Item.Properties().stacksTo(1)
            .durability(999)));


    public static final Item WEIGHTED_ANCHOR_ITEM = register("weighted_anchor",
        new WeightedAnchorItem(new Item.Properties().stacksTo(1)));

    // public static final Item INFORMATION_BOOKLET = register("information_booklet",
    //                 new InformationBookletItem(new Item.Properties().stacksTo(1)));

    public static final Item POSSESSIONSBOX = register("possessions_box",
        new PossessionsBox(new Item.Properties().stacksTo(1)));

    public static final Item PADLOCK = register("padlock",
        new Padlock(new Item.Properties().stacksTo(16)));
    public static final Item LOCKPICK = register("lockpick",
        new Item(new Item.Properties().stacksTo(1).durability(3)));

    public static final Item PRISONER_TAG = register("prisoner_tag",
        new PrisonerTagItem(new Item.Properties().stacksTo(1)));


    public static final Item FORK = register("fork",
        new Item(new Item.Properties().stacksTo(1).durability(5)));
    public static final Item SPOON = register("spoon",
        new Item(new Item.Properties().stacksTo(1).durability(5)));
    public static final Item KNIFE = register("knife",
        new KnifeItem(new Item.Properties().stacksTo(1).durability(5)));


    public static final Item DUCK_TAPE = register("duck_tape", new DuckTapeItem(new Item.Properties()));
    public static final Item BANDAGE = register("bandage", new BandageItem(new Item.Properties()));

    // Block Items
    public static final Item CELL_DOOR_ITEM = register("cell_door",
        new BlockItem(ModBlocks.CELL_DOOR, new Item.Properties()));

    public static final Item REINFORCED_STONE_ITEM = register("reinforced_stone",
        new BlockItem(ModBlocks.REINFORCED_STONE, new Item.Properties()));
    public static final Item REINFORCED_SMOOTH_STONE_ITEM = register("reinforced_smooth_stone",
        new BlockItem(ModBlocks.REINFORCED_SMOOTH_STONE, new Item.Properties()));
    public static final Item REINFORCED_LAMP_ITEM = register("reinforced_lamp",
        new BlockItem(ModBlocks.REINFORCED_LAMP, new Item.Properties()));
    public static final Item REINFORCED_STONE_CHISELED_ITEM = register("chiseled_reinforced_stone",
        new BlockItem(ModBlocks.REINFORCED_STONE_CHISELED, new Item.Properties()));
    public static final Item REINFORCED_STONE_SLAB_ITEM = register("reinforced_stone_slab",
        new BlockItem(ModBlocks.REINFORCED_STONE_SLAB, new Item.Properties()));
    public static final Item REINFORCED_STONE_STAIRS_ITEM = register("reinforced_stone_stairs",
        new BlockItem(ModBlocks.REINFORCED_STONE_STAIRS, new Item.Properties()));
    public static final Item REINFORCED_BARS_ITEM = register("reinforced_bars",
        new BlockItem(ModBlocks.REINFORCED_BARS, new Item.Properties()));
    public static final Item REINFORCED_BARS_GAPPED_ITEM = register("reinforced_bars_gap",
        new BlockItem(ModBlocks.REINFORCED_BARS_GAPPED, new Item.Properties()));

    public static final Item PILLORY_ITEM = register("pillory",
        new BlockItem(ModBlocks.PILLORY, new Item.Properties()));
    public static final Item GUILLOTINE_ITEM = register("guillotine",
        new BlockItem(ModBlocks.GUILLOTINE, new Item.Properties()));

    public static final Item SAFE_ITEM = register("safe",
        new BlockItem(ModBlocks.SAFE, new Item.Properties()));

    public static final Item BUNK_ITEM = register("bunk",
        new BlockItem(ModBlocks.BUNK, new Item.Properties().stacksTo(1)));

    public static final Item POSTER_ITEM = register("poster",
        new PosterBlockItem(ModBlocks.POSTER, new Item.Properties().stacksTo(1)));

    public static final Item TRAY = register("tray",
        new TrayItem(ModBlocks.TRAY, new Item.Properties().stacksTo(1)));

    //public static final Item TOILET_ITEM = register("toilet",
    //        new BlockItem(ModBlocks.TOILET, new Item.Properties()));


    // Creative Items
    public static final Item CREATIVE_RESTRAINT_CUTTER = register("creative_restraint_cutter", new CreativeRestraintCutter(new Item.Properties()));
    public static final Item CREATIVE_KEY = register("creative_key", new CreativeKey(new Item.Properties().rarity(Rarity.EPIC)));
    public static final Item CREATIVE_BIND_BREAKER = register("creative_bind_breaker", new CreativeKey(new Item.Properties().rarity(Rarity.EPIC)));

    public static Item register(String name, Item item) {
        return Registry.register(BuiltInRegistries.ITEM, CuffedMod.id(name), item);
    }

    public static void register() {
    }
}
