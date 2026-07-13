package com.lazrproductions.cuffed.init;

import com.lazrproductions.cuffed.CuffedMod;
import com.lazrproductions.cuffed.blocks.entity.*;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

@SuppressWarnings("null")
public class ModBlockEntities {
    public static final BlockEntityType<GuillotineBlockEntity> GUILLOTINE = register(
        "guillotine_block_entity", BlockEntityType.Builder.of(GuillotineBlockEntity::new,
            ModBlocks.GUILLOTINE).build());

    public static final BlockEntityType<SafeBlockEntity> SAFE_BLOCK_ENTITY = register("safe_block_entity", BlockEntityType.Builder.of(SafeBlockEntity::new,
        ModBlocks.SAFE).build());

    public static final BlockEntityType<BunkBlockEntity> BUNK_BLOCK_ENTITY = register("bunk_block_entity", BlockEntityType.Builder.of(BunkBlockEntity::new,
        ModBlocks.BUNK).build());

    public static final BlockEntityType<TrayBlockEntity> TRAY = register("tray_block_entity", BlockEntityType.Builder.of(TrayBlockEntity::new,
        ModBlocks.TRAY).build());

    public static final BlockEntityType<LockableBlockEntity> CELL_DOOR_BLOCK_ENTITY = register("cell_door_block_entity", BlockEntityType.Builder.of(LockableBlockEntity::new,
        ModBlocks.CELL_DOOR).build());

    //public static final BlockEntityType<ToiletBlockEntity> TOILET = BLOCK_ENTITIES
    //                .register("toilet_block_entity", BlockEntityType.Builder.of(ToiletBlockEntity::new,
    //                                ModBlocks.TOILET).build(null));

    private static <T extends BlockEntity> BlockEntityType<T> register(String name, BlockEntityType<T> type) {
        return Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, CuffedMod.id(name), type);
    }

    public static void register() {
    }
}
