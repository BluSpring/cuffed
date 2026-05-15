package com.lazrproductions.cuffed.compat;
import javax.annotation.Nonnull;
import com.mna.api.ManaAndArtificeMod;
import net.minecraft.server.level.ServerPlayer;

public class ManaAndArtificeCompat {
    public static void load() {
    }

    public static void DrainMana(@Nonnull ServerPlayer player, int amount) {
        player.getCapability(ManaAndArtificeMod.getMagicCapability()).ifPresent((magic) -> {
            magic.getCastingResource().consume(player, amount);
        });;
    }

    public static void DrainMana(@Nonnull ServerPlayer player, double amountPercentage) {
         player.getCapability(ManaAndArtificeMod.getMagicCapability()).ifPresent((magic) -> {
            float max = magic.getCastingResource().getMaxAmount();
            magic.getCastingResource().consume(player, max * (float)amountPercentage);
        });;
    }
}
