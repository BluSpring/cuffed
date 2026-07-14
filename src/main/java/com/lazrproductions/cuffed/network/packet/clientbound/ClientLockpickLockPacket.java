package com.lazrproductions.cuffed.network.packet.clientbound;

import com.lazrproductions.cuffed.CuffedMod;
import com.lazrproductions.cuffed.api.lock.Lockpickable;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public record ClientLockpickLockPacket(int speedIncreasePerPick, int progressPerPick, Lockpickable lockpickable) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<ClientLockpickLockPacket> TYPE = new CustomPacketPayload.Type<>(CuffedMod.id("lockpick_lock/client"));
    public static final StreamCodec<RegistryFriendlyByteBuf, ClientLockpickLockPacket> CODEC = StreamCodec.composite(
        ByteBufCodecs.VAR_INT, ClientLockpickLockPacket::speedIncreasePerPick,
        ByteBufCodecs.VAR_INT, ClientLockpickLockPacket::progressPerPick,
        Lockpickable.STREAM_CODEC, ClientLockpickLockPacket::lockpickable,
        ClientLockpickLockPacket::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
