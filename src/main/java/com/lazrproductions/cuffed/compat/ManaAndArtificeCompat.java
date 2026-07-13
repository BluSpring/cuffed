package com.lazrproductions.cuffed.compat;

import com.mna.api.ManaAndArtificeMod;
import org.jetbrains.annotations.NotNull;

import net.minecraft.server.level.ServerPlayer;

public class ManaAndArtificeCompat {
    public static void load() {
    }

    public static void DrainMana(@NotNull ServerPlayer player, int amount) {
        player.getCapability(ManaAndArtificeMod.getMagicCapability()).ifPresent((magic) -> {
            magic.getCastingResource().consume(player, amount);
        });
    }

    public static void DrainMana(@NotNull ServerPlayer player, double amountPercentage) {
         player.getCapability(ManaAndArtificeMod.getMagicCapability()).ifPresent((magic) -> {
            float max = magic.getCastingResource().getMaxAmount();
            magic.getCastingResource().consume(player, max * (float)amountPercentage);
        });
    }
}
