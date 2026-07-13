package com.lazrproductions.cuffed.entity.base;

import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public interface IDetainableEntity {
    void detainToBlock(@NotNull Level level, Vector3f detainPos, @NotNull BlockPos pos, int detaintType, float facingRotation);
    void undetain();

    int getDetained();
    void setDetained(int value);

    float getDetainedRotation();
    void setDetainedRotation(float value);
    
    BlockState getBlockDetainedTo(@NotNull Level level);
    void setBlockDetainedTo(@NotNull BlockPos pos);

    Vector3f getDetainedPosition();
    void setDetainedPosition(Vector3f value);
}
