package com.lazrproductions.cuffed.restraints.base;

import net.minecraft.core.Holder;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.ItemEnchantments;

public interface IEnchantableRestraint {
    ItemEnchantments getEnchantments();
    void setEnchantments(ItemEnchantments enchantments);

    /** Get whether or not this restraint has the give enchantment */
    boolean hasEnchantment(Holder<Enchantment> enchantment);
    /** Get the amplifier of the given enchantment. */
    int getEnchantmentLevel(Holder<Enchantment> enchantment);
    /** Apply an enchantment to this restraint. */
    void enchant(Holder<Enchantment> enchantment, int value);
}
