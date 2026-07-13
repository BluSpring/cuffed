package com.lazrproductions.cuffed.init;

import com.lazrproductions.cuffed.CuffedMod;
import com.lazrproductions.cuffed.enchantment.*;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModEnchantments {
        public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister
                        .create(ForgeRegistries.ENCHANTMENTS, CuffedMod.MODID);

        public static final Enchantment IMBUE = ENCHANTMENTS.register("imbue",
                        () -> new ImbueEnchantment(Enchantment.Rarity.VERY_RARE, EnchantmentCategory.BREAKABLE, EquipmentSlot.MAINHAND));
        public static final Enchantment FAMINE = ENCHANTMENTS.register("famine",
                        () -> new FamineEnchantment(Enchantment.Rarity.RARE, EnchantmentCategory.BREAKABLE, EquipmentSlot.MAINHAND));
        public static final Enchantment SHROUD = ENCHANTMENTS.register("shroud",
                        () -> new ShroudEnchantment(Enchantment.Rarity.RARE, EnchantmentCategory.BREAKABLE, EquipmentSlot.MAINHAND));
        public static final Enchantment EXHAUST = ENCHANTMENTS.register("exhaust",
                        () -> new ExhaustEnchantment(Enchantment.Rarity.RARE, EnchantmentCategory.BREAKABLE, EquipmentSlot.MAINHAND));
        public static final Enchantment SILENCE = ENCHANTMENTS.register("silence",
                        () -> new DrainEnchantment(Enchantment.Rarity.RARE, EnchantmentCategory.BREAKABLE, EquipmentSlot.MAINHAND));
        public static final Enchantment BUOYANT = ENCHANTMENTS.register("buoyant",
                        () -> new BuoyantEnchantment(Enchantment.Rarity.RARE, EnchantmentCategory.BREAKABLE, EquipmentSlot.MAINHAND));

        public static void register(IEventBus bus) {
                ENCHANTMENTS.register(bus);
        }
}
