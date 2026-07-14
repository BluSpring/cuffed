package com.lazrproductions.cuffed.api.lock;

import com.mojang.serialization.Codec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.level.ServerPlayer;

public interface Lockpickable {
    Codec<Lockpickable> CODEC = LockpickableType.REGISTRY.byNameCodec().dispatch("type", Lockpickable::type, LockpickableType::codec);
    StreamCodec<RegistryFriendlyByteBuf, Lockpickable> STREAM_CODEC = ByteBufCodecs.registry(LockpickableType.REGISTRY.key())
        .dispatch(Lockpickable::type, LockpickableType::streamCodec);

    LockpickableType<?> type();

    void finishLockpicking(ServerPlayer lockpicker, boolean wasFailed);
}
