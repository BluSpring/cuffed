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
    }
}
