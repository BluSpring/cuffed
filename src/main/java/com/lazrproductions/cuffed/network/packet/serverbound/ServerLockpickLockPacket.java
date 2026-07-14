package com.lazrproductions.cuffed.network.packet.serverbound;

import com.lazrproductions.cuffed.CuffedMod;
import com.lazrproductions.cuffed.api.lock.Lockpickable;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public record ServerLockpickLockPacket(boolean wasFailed, Lockpickable lockpickable) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<ServerLockpickLockPacket> TYPE = new Type<>(CuffedMod.id("lockpick_lock/server"));
    public static final StreamCodec<RegistryFriendlyByteBuf, ServerLockpickLockPacket> CODEC = StreamCodec.composite(
        ByteBufCodecs.BOOL, ServerLockpickLockPacket::wasFailed,
        Lockpickable.STREAM_CODEC, ServerLockpickLockPacket::lockpickable,
        ServerLockpickLockPacket::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
