package com.lazrproductions.cuffed.entity.base;

import net.minecraft.world.entity.Entity;

import javax.annotation.Nullable;

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
