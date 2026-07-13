package com.lazrproductions.cuffed.entity.base;

import org.jetbrains.annotations.Nullable;

import net.minecraft.world.entity.Entity;

public interface IAnchorableEntity {
    boolean isAnchored();
    Entity getAnchor();
    Entity getAnchorClientSide();
    /**
     * Set the anchor of this entity, if null than unanchor the player and drop a chain
     */
    void setAnchoredTo(@Nullable Entity e);
    void setAnchor(@Nullable Entity e);
}
