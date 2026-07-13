package com.lazrproductions.cuffed.blocks.base;

import org.jetbrains.annotations.NotNull;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

public interface ILockableBlock {
    BooleanProperty LOCKED = BooleanProperty.create("locked");
    BooleanProperty BOUND = BooleanProperty.create("bound");
    
    static boolean isLocked(@NotNull BlockState state) {
        return state.getValue(LOCKED);
    }
    
    static void setIsLocked(@NotNull Player player, @NotNull BlockState state, @NotNull BlockPos pos, boolean locked) {
        Level level = player.level();
        if(level != null) {
            state = state.setValue(LOCKED, locked);
            level.setBlock(pos, state, Block.UPDATE_NEIGHBORS);
        
            level.playSound(null, pos, SoundEvents.CHAIN_PLACE, SoundSource.BLOCKS, 1.0F,
                                level.getRandom().nextFloat() * 0.1F + 0.9F);
            player.displayClientMessage(
                    Component.translatable("info.lock.toggle_" + (locked ? "on" : "off")), true);
        }
    }

    static boolean isBound(@NotNull BlockState state) {
        return state.getValue(BOUND);
    }
    
        
    static boolean tryToBindToKey(@NotNull Player player, @NotNull BlockState state, @NotNull BlockPos pos, @NotNull ItemStack stack) {
        if(!isBound(state)) {
            Level level = player.level();
            if(level != null) {        
                // if(KeyItem.tryToSetBoundBlock(player, stack, pos)) {
                //     state = state.setValue(BOUND, true);
                //     level.setBlock(pos, state, Block.UPDATE_NEIGHBORS);
                //     return true;
                // }
            }
        }
        return false;
    }

    static void bindToKey(@NotNull Player player, @NotNull BlockState state, @NotNull BlockPos pos, @NotNull ItemStack stack) {
        Level level = player.level();
        if(level != null) {        
            // if(KeyItem.tryToSetBoundBlock(player, stack, pos)) {
            //     state = state.setValue(BOUND, true);
            //     level.setBlock(pos, state, Block.UPDATE_NEIGHBORS);
            // }
        }
    }
}
