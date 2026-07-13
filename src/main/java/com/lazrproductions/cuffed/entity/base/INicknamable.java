package com.lazrproductions.cuffed.entity.base;

import org.jetbrains.annotations.Nullable;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;

public interface INicknamable {
    Component getNickname();
    void setNickname(@Nullable Component value);
    String serializeNickname();
    void deserializeNickname(CompoundTag tag);
    void deserializeNickname(String nickTag);
}
