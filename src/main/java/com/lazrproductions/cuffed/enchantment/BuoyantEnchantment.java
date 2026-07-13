package com.lazrproductions.cuffed.enchantment;

import com.lazrproductions.cuffed.init.ModItems;
import org.jetbrains.annotations.NotNull;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class BuoyantEnchantment  extends Enchantment {

    public BuoyantEnchantment(Rarity rarity, EnchantmentCategory category, EquipmentSlot... slot) {
        super(rarity, category, slot);
    }


    public int getMinCost(int i) {
        return 1;
    }

    public int getMaxCost(int i) {
        return super.getMinCost(i) + 50;
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

    @Override
	public boolean canApplyAtEnchantingTable(@NotNull ItemStack stack) {
		return stack.is(ModItems.WEIGHTED_ANCHOR_ITEM);
	}
}
