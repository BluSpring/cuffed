package com.lazrproductions.cuffed.entity.base;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;

import javax.annotation.Nullable;

public interface INicknamable {
    Component getNickname();
    void setNickname(@Nullable Component value);
    String serializeNickname();
    void deserializeNickname(CompoundTag tag);
    void deserializeNickname(String nickTag);
}
