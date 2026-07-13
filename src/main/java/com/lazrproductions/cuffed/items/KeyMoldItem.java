package com.lazrproductions.cuffed.items;

import java.util.List;

import com.lazrproductions.cuffed.component.CuffedDataComponents;
import com.lazrproductions.cuffed.init.ModItems;
import org.jetbrains.annotations.NotNull;

import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

public class KeyMoldItem extends Item {
    public KeyMoldItem(Properties properties) {
        super(properties);
    }


    public static ItemStack createFromKey(ItemStack keyStack) {
        ItemStack newMold = new ItemStack(ModItems.KEY_MOLD, 1);
        
        if(!keyStack.has(CuffedDataComponents.KEY))
            return newMold;

        if(keyStack.has(CuffedDataComponents.KEY))
            newMold.set(CuffedDataComponents.KEY, keyStack.get(CuffedDataComponents.KEY));
        if (keyStack.has(DataComponents.CUSTOM_NAME))
            newMold.set(CuffedDataComponents.KEY_NAME, keyStack.get(DataComponents.CUSTOM_NAME));

        return newMold;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, TooltipContext context, @NotNull List<Component> pTooltipComponents,
            @NotNull TooltipFlag pIsAdvanced) {
        super.appendHoverText(stack, context, pTooltipComponents, pIsAdvanced);

        if (stack.has(CuffedDataComponents.KEY)) {
            pTooltipComponents.add(Component.translatable("item.cuffed.key_mold.description.bound").withStyle(ChatFormatting.GRAY));
        } else {
            pTooltipComponents.add(Component.translatable("item.cuffed.key_mold.description.unbound").withStyle(ChatFormatting.GRAY));
        }
    }
}
