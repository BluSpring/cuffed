package com.lazrproductions.cuffed.client.network;

import com.lazrproductions.cuffed.api.CuffedAPI;
import com.lazrproductions.cuffed.network.packet.clientbound.ClientLockpickLockPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

public class CuffedClientNetworking {
    static {
        ClientPlayNetworking.registerGlobalReceiver(ClientLockpickLockPacket.TYPE, (packet, ctx) -> {
            CuffedAPI.Lockpicking.beginLockpicking(ctx.client(), packet.speedIncreasePerPick(), packet.progressPerPick(), packet.lockpickable());
        });
    }

    public static void init() {}
}
