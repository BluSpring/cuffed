package com.lazrproductions.cuffed.entity.base;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.joml.Vector3f;

import javax.annotation.Nonnull;

public interface IDetainableEntity {
    void detainToBlock(@Nonnull Level level, Vector3f detainPos, @Nonnull BlockPos pos, int detaintType, float facingRotation);
    void undetain();

    int getDetained();
    void setDetained(int value);

    float getDetainedRotation();
    void setDetainedRotation(float value);
    
    BlockState getBlockDetainedTo(@Nonnull Level level);
    void setBlockDetainedTo(@Nonnull BlockPos pos);

    Vector3f getDetainedPosition();
    void setDetainedPosition(Vector3f value);
}
