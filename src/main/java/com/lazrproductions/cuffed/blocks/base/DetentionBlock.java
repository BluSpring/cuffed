package com.lazrproductions.cuffed.blocks.base;

import org.jetbrains.annotations.NotNull;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public abstract class DetentionBlock extends Block {
    public DetentionBlock(Properties properties) {
        super(properties);
    }

    public abstract boolean canDetainPlayer(@NotNull Level level, @NotNull BlockState state, @NotNull BlockPos pos, @NotNull Player player, boolean ignoreState);
}
