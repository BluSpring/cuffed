package com.lazrproductions.cuffed.init;

import com.lazrproductions.cuffed.CuffedMod;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.enchantment.Enchantment;

public class ModEnchantments {
    public static final ResourceKey<Enchantment> IMBUE = key("imbue");
    public static final ResourceKey<Enchantment> FAMINE = key("famine");
    public static final ResourceKey<Enchantment> SHROUD = key("shroud");
    public static final ResourceKey<Enchantment> EXHAUST = key("exhaust");
    public static final ResourceKey<Enchantment> SILENCE = key("silence");
    public static final ResourceKey<Enchantment> BUOYANT = key("buoyant");

    private static ResourceKey<Enchantment> key(String name) {
        return ResourceKey.create(Registries.ENCHANTMENT, CuffedMod.id(name));
    }

    public static void register() {
    }
}
