package com.lazrproductions.cuffed.init;

import com.lazrproductions.cuffed.CuffedMod;
import com.lazrproductions.cuffed.blocks.base.PosterType;
import com.lazrproductions.cuffed.items.PosterBlockItem;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Items;

public class ModCreativeTabs {
    public static final CreativeModeTab CUFFED_TAB = register("cuffed_tab",
        FabricItemGroup.builder()
            .title(Component.translatable("itemGroup.cuffed"))
            .icon(ModItems.HANDCUFFS::getDefaultInstance)
            .displayItems((parameters, output) -> {
                output.accept(ModItems.HANDCUFFS);
                output.accept(ModItems.SHACKLES);
                output.accept(ModItems.FUZZY_HANDCUFFS);

                output.accept(ModItems.HANDCUFFS_KEY);
                output.accept(ModItems.SHACKLES_KEY);

                output.accept(Items.BUNDLE);

                output.accept(ModItems.POSSESSIONSBOX);
                output.accept(ModItems.PRISONER_TAG);

                output.accept(ModItems.DUCK_TAPE);
                output.accept(ModItems.BANDAGE);

                output.accept(ModItems.TRAY);
                output.accept(ModItems.FORK);
                output.accept(ModItems.SPOON);
                output.accept(ModItems.KNIFE);

                output.accept(ModItems.PADLOCK);
                output.accept(ModItems.KEY);
                output.accept(ModItems.KEY_RING);
                output.accept(ModItems.KEY_MOLD);
                output.accept(ModItems.BAKED_KEY_MOLD);
                output.accept(ModItems.LOCKPICK);


                output.accept(ModItems.WEIGHTED_ANCHOR_ITEM);


                output.accept(ModItems.CELL_DOOR_ITEM);
                output.accept(ModItems.REINFORCED_BARS_ITEM);
                output.accept(ModItems.REINFORCED_BARS_GAPPED_ITEM);
                output.accept(ModItems.REINFORCED_SMOOTH_STONE_ITEM);
                output.accept(ModItems.REINFORCED_STONE_ITEM);
                output.accept(ModItems.REINFORCED_STONE_SLAB_ITEM);
                output.accept(ModItems.REINFORCED_STONE_STAIRS_ITEM);
                output.accept(ModItems.REINFORCED_STONE_CHISELED_ITEM);
                output.accept(ModItems.REINFORCED_LAMP_ITEM);


                output.accept(ModItems.PILLORY_ITEM);
                output.accept(ModItems.GUILLOTINE_ITEM);


                output.accept(ModItems.BUNK_ITEM);


                output.accept(ModItems.SAFE_ITEM);


                output.accept(PosterBlockItem.newItemFromType(PosterType.NONE));
                output.accept(PosterBlockItem.newItemFromType(PosterType.SERENITY));
                output.accept(PosterBlockItem.newItemFromType(PosterType.SKELETON));
                output.accept(PosterBlockItem.newItemFromType(PosterType.IMPUNITY));
                output.accept(PosterBlockItem.newItemFromType(PosterType.ZOOOM));
                output.accept(PosterBlockItem.newItemFromType(PosterType.A_SHADOW_LOCKED_AWAY));
                output.accept(PosterBlockItem.newItemFromType(PosterType.PRISONER));
                output.accept(PosterBlockItem.newItemFromType(PosterType.LANTERN));


                output.accept(ModItems.CREATIVE_RESTRAINT_CUTTER);
                output.accept(ModItems.CREATIVE_KEY);
                output.accept(ModItems.CREATIVE_BIND_BREAKER);

                //output.accept(ModItems.INFORMATION_BOOKLET);
            }).build());

    public static void register() {
    }

    private static CreativeModeTab register(String name, CreativeModeTab tab) {
        return Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, CuffedMod.id(name), tab);
    }
}
