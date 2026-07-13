package com.lazrproductions.cuffed.items;

import java.util.List;

import com.lazrproductions.cuffed.items.base.AbstractRestraintItem;
import org.jetbrains.annotations.NotNull;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

public class DuckTapeItem extends AbstractRestraintItem {

    public DuckTapeItem(Properties p) {
        super(p);
    }
    
    @Override
    public void appendHoverText(@NotNull ItemStack stack, TooltipContext context, @NotNull List<Component> components,
            @NotNull TooltipFlag tooltipFlag) {
        components.add(Component.translatable("info.cuffed.restraint_type.head").withStyle(ChatFormatting.GRAY));
        components.add(Component.translatable("info.cuffed.restraint_type.arm").withStyle(ChatFormatting.GRAY));
        components.add(Component.translatable("info.cuffed.restraint_type.leg").withStyle(ChatFormatting.GRAY));
        
        components.add(Component.empty());
        components.add(
            Component.translatable("info.cuffed.restraint_type.my_key")
            .withStyle(ChatFormatting.GRAY)
            .append(" ")
            .append(
                Component.translatable("info.cuffed.empty_hand")
                .withStyle(ChatFormatting.WHITE)));
        

        super.appendHoverText(stack, context, components, tooltipFlag);
    }
}
