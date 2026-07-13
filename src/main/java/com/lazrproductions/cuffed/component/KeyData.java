package com.lazrproductions.cuffed.component;

import java.util.Optional;
import java.util.UUID;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.core.UUIDUtil;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public record KeyData(UUID id, Optional<Component> name) {
    public KeyData(UUID id) {
        this(id, Optional.empty());
    }

    public static final Codec<KeyData> CODEC = RecordCodecBuilder.create(instance ->
        instance.group(
            UUIDUtil.CODEC.fieldOf("id")
                .forGetter(KeyData::id),
            ComponentSerialization.CODEC.optionalFieldOf("name")
                .forGetter(KeyData::name)
        )
            .apply(instance, KeyData::new)
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, KeyData> STREAM_CODEC = StreamCodec.composite(
        UUIDUtil.STREAM_CODEC, KeyData::id,
        ByteBufCodecs.optional(ComponentSerialization.STREAM_CODEC), KeyData::name,
        KeyData::new
    );
}
