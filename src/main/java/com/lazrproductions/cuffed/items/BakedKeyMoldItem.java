package com.lazrproductions.cuffed.items;

import java.util.List;

import com.lazrproductions.cuffed.component.CuffedDataComponents;
import com.lazrproductions.cuffed.init.ModItems;

import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

public class BakedKeyMoldItem extends Item {
    public BakedKeyMoldItem(Properties properties) {
        super(properties.component(CuffedDataComponents.QUALITY, 5));
    }

    public static ItemStack createFromRawMold(ItemStack oldMold, RandomSource random) {
        ItemStack newMold = new ItemStack(ModItems.BAKED_KEY_MOLD, 1);

        newMold.set(CuffedDataComponents.KEY, oldMold.get(CuffedDataComponents.KEY));
        newMold.set(CuffedDataComponents.KEY_NAME, oldMold.get(CuffedDataComponents.KEY_NAME));
        newMold.set(CuffedDataComponents.QUALITY, random.nextInt(1) + 4);

        return newMold;
    }

    public static ItemStack createKeyFrom(ItemStack moldStack, int amount) {
        ItemStack newKey = new ItemStack(ModItems.KEY, amount);

        if(!moldStack.has(CuffedDataComponents.KEY))
            return newKey;

        newKey.set(CuffedDataComponents.KEY, moldStack.get(CuffedDataComponents.KEY));

        if (moldStack.has(CuffedDataComponents.KEY_NAME)) {
            newKey.set(DataComponents.CUSTOM_NAME, moldStack.get(CuffedDataComponents.KEY_NAME));
        }

        return newKey;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
        int quality = stack.getOrDefault(CuffedDataComponents.QUALITY, 0);
        tooltipComponents.add(Component.translatable("item.cuffed.baked_key_mold.description.quality_"+quality).withStyle(ChatFormatting.DARK_GRAY));
    }
}
