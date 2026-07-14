package com.lazrproductions.cuffed.network;

import com.lazrproductions.cuffed.network.packet.serverbound.ServerLockpickLockPacket;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

public class CuffedServerNetworking {
    static {
        ServerPlayNetworking.registerGlobalReceiver(ServerLockpickLockPacket.TYPE, (packet, ctx) -> {
            packet.lockpickable().finishLockpicking(ctx.player(), packet.wasFailed());
        });
    }

    public static void init() {}
}
