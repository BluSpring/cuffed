package com.lazrproductions.cuffed.restraints.base;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.StringRepresentable;

public enum RestraintType implements StringRepresentable {
    ARM("arm"),
    LEG("leg"),
    HEAD("head");

    public static final Codec<RestraintType> CODEC = StringRepresentable.fromEnum(RestraintType::values);
    public static final StreamCodec<ByteBuf, RestraintType> STREAM_CODEC = ByteBufCodecs.idMapper(ByIdMap.continuous(RestraintType::ordinal, RestraintType.values(), ByIdMap.OutOfBoundsStrategy.CLAMP), RestraintType::ordinal);

    private final String serializedName;

    RestraintType(String name) {
        this.serializedName = name;
    }

    @Override
    public String getSerializedName() {
        return this.serializedName;
    }
}
