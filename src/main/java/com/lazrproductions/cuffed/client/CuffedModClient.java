package com.lazrproductions.cuffed.client;

import com.lazrproductions.cuffed.CuffedMod;
import net.fabricmc.api.ClientModInitializer;

public class CuffedModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        CuffedMod.ClientModEvents.onClientSetup();

    }
}
