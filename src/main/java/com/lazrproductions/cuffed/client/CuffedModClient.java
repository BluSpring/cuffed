package com.lazrproductions.cuffed.client;

import com.lazrproductions.cuffed.CuffedMod;
import com.lazrproductions.cuffed.client.network.CuffedClientNetworking;
import net.fabricmc.api.ClientModInitializer;

public class CuffedModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        CuffedMod.ClientModEvents.onClientSetup();
        CuffedClientNetworking.init();
    }
}
