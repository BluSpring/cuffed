package com.lazrproductions.cuffed.compat;

import com.hollingsworth.arsnouveau.api.mana.IManaCap;
import com.hollingsworth.arsnouveau.setup.registry.CapabilityRegistry;
import org.jetbrains.annotations.NotNull;

import net.minecraft.server.level.ServerPlayer;

public class ArsNouveauCompat {
    public static void load() {
    }

    @SuppressWarnings("null")
    public static void DrainMana(@NotNull ServerPlayer player, int amount) {
        IManaCap manaCap = CapabilityRegistry.getMana(player).orElse(null);
        if(manaCap!=null)
            manaCap.removeMana(amount);
    }

    @SuppressWarnings("null")
    public static void DrainMana(@NotNull ServerPlayer player, double amountPercentage) {
        IManaCap manaCap = CapabilityRegistry.getMana(player).orElse(null);
        int max = manaCap.getMaxMana();
        double amount = max * amountPercentage;
        if(manaCap!=null)
            manaCap.removeMana(amount);
    }
}
