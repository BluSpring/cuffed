package com.lazrproductions.cuffed.datagen;

import java.util.List;
import java.util.function.Function;

import com.lazrproductions.cuffed.init.ModEnchantments;
import com.lazrproductions.cuffed.init.ModTags;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.enchantment.Enchantment;

public class CuffedEnchantmentsGen {
    public static void bootstrap(BootstrapContext<Enchantment> context) {
        var itemLookup = context.lookup(Registries.ITEM);
        var restraintsSet = itemLookup.get(ModTags.Items.RESTRAINTS);

        /*
         COMMON(10),
        UNCOMMON(5),
        RARE(2),
        VERY_RARE(1);
         */
        context.register(ModEnchantments.IMBUE,
            Enchantment.enchantment(
                new Enchantment.EnchantmentDefinition(
                    itemLookup.getOrThrow(ModTags.Items.SUPPORTS_IMBUE),
                    restraintsSet.map(Function.identity()),
                    1, // very rare
                    3,
                    new Enchantment.Cost(2, 10),
                    new Enchantment.Cost(50, 0),
                    3,
                    List.of(EquipmentSlotGroup.MAINHAND)
                )
            )
                .build(ModEnchantments.IMBUE.location())
        );

        context.register(ModEnchantments.FAMINE, Enchantment.enchantment(
            new Enchantment.EnchantmentDefinition(
                itemLookup.getOrThrow(ModTags.Items.SUPPORTS_FAMINE),
                restraintsSet.map(Function.identity()),
                2, // rare
                1, // max level
                new Enchantment.Cost(1, 0),
                new Enchantment.Cost(51, 0),
                3, // anvil cost
                List.of(EquipmentSlotGroup.MAINHAND)
            )
        ).build(ModEnchantments.FAMINE.location()));

        context.register(ModEnchantments.SHROUD, Enchantment.enchantment(
            new Enchantment.EnchantmentDefinition(
                itemLookup.getOrThrow(ModTags.Items.SUPPORTS_SHROUD),
                restraintsSet.map(Function.identity()),
                2, // rare
                1, // max level
                new Enchantment.Cost(2, 0),
                new Enchantment.Cost(52, 0),
                3, // anvil cost
                List.of(EquipmentSlotGroup.MAINHAND)
            )
        ).build(ModEnchantments.SHROUD.location()));

        context.register(ModEnchantments.EXHAUST, Enchantment.enchantment(
            new Enchantment.EnchantmentDefinition(
                itemLookup.getOrThrow(ModTags.Items.SUPPORTS_EXHAUST),
                restraintsSet.map(Function.identity()),
                2, // rare
                1, // max level
                new Enchantment.Cost(1, 0),
                new Enchantment.Cost(51, 0),
                3, // anvil cost
                List.of(EquipmentSlotGroup.MAINHAND)
            )
        ).build(ModEnchantments.EXHAUST.location()));

        context.register(ModEnchantments.SILENCE, Enchantment.enchantment(
            new Enchantment.EnchantmentDefinition(
                itemLookup.getOrThrow(ModTags.Items.SUPPORTS_SILENCE),
                restraintsSet.map(Function.identity()),
                2, // rare
                1, // max level
                new Enchantment.Cost(1, 0),
                new Enchantment.Cost(51, 0),
                3, // anvil cost
                List.of(EquipmentSlotGroup.MAINHAND)
            )
        ).build(ModEnchantments.SILENCE.location()));

        context.register(ModEnchantments.BUOYANT, Enchantment.enchantment(
            new Enchantment.EnchantmentDefinition(
                itemLookup.getOrThrow(ModTags.Items.SUPPORTS_BUOYANT),
                restraintsSet.map(Function.identity()),
                2, // rare
                1, // max level
                new Enchantment.Cost(1, 0),
                new Enchantment.Cost(51, 0),
                3, // anvil cost
                List.of(EquipmentSlotGroup.MAINHAND)
            )
        ).build(ModEnchantments.BUOYANT.location()));
    }
}
