package com.lazrproductions.cuffed.init;

import com.lazrproductions.cuffed.CuffedMod;
import com.lazrproductions.cuffed.enchantment.BuoyantEnchantment;
import com.lazrproductions.cuffed.enchantment.DrainEnchantment;
import com.lazrproductions.cuffed.enchantment.ExhaustEnchantment;
import com.lazrproductions.cuffed.enchantment.FamineEnchantment;
import com.lazrproductions.cuffed.enchantment.ShroudEnchantment;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;

public class ModEnchantments {
    public static final ResourceKey<Enchantment> IMBUE = key("imbue");
    public static final ResourceKey<Enchantment> FAMINE = ENCHANTMENTS.register("famine",
        () -> new FamineEnchantment(Enchantment.Rarity.RARE, EnchantmentCategory.BREAKABLE, EquipmentSlot.MAINHAND));
    public static final ResourceKey<Enchantment> SHROUD = ENCHANTMENTS.register("shroud",
        () -> new ShroudEnchantment(Enchantment.Rarity.RARE, EnchantmentCategory.BREAKABLE, EquipmentSlot.MAINHAND));
    public static final ResourceKey<Enchantment> EXHAUST = ENCHANTMENTS.register("exhaust",
        () -> new ExhaustEnchantment(Enchantment.Rarity.RARE, EnchantmentCategory.BREAKABLE, EquipmentSlot.MAINHAND));
    public static final ResourceKey<Enchantment> SILENCE = ENCHANTMENTS.register("silence",
        () -> new DrainEnchantment(Enchantment.Rarity.RARE, EnchantmentCategory.BREAKABLE, EquipmentSlot.MAINHAND));
    public static final ResourceKey<Enchantment> BUOYANT = ENCHANTMENTS.register("buoyant",
        () -> new BuoyantEnchantment(Enchantment.Rarity.RARE, EnchantmentCategory.BREAKABLE, EquipmentSlot.MAINHAND));

    private static ResourceKey<Enchantment> key(String name) {
        return ResourceKey.create(Registries.ENCHANTMENT, CuffedMod.id(name));
    }

    public static void register() {
    }
}
