package com.lazrproductions.cuffed.items;

import java.util.List;

import com.lazrproductions.cuffed.CuffedMod;
import com.lazrproductions.cuffed.init.ModItems;
import com.lazrproductions.cuffed.items.base.AbstractRestraintItem;
import org.jetbrains.annotations.NotNull;

import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

public class FuzzyHandcuffsItem extends AbstractRestraintItem
{
    public FuzzyHandcuffsItem(Properties p) {
        super(p.component(DataComponents.MAX_DAMAGE, CuffedMod.SERVER_CONFIG.RESTRAINT_DURABILITY_FUZZY_HANDCUFFS.get()));
    }
    
    @Override
    public void appendHoverText(@NotNull ItemStack stack, TooltipContext context, @NotNull List<Component> components,
            @NotNull TooltipFlag tooltipFlag) {
        components.add(Component.translatable("info.cuffed.restraint_type.arm").withStyle(ChatFormatting.GRAY));
        components.add(Component.translatable("info.cuffed.restraint_type.leg").withStyle(ChatFormatting.GRAY));
        
        components.add(Component.empty());
        components.add(
            Component.translatable("info.cuffed.restraint_type.my_key")
            .withStyle(ChatFormatting.GRAY)
            .append(" ")
            .append(
                ModItems.HANDCUFFS_KEY.getDefaultInstance().getHoverName().copy()
                .withStyle(ChatFormatting.WHITE)));

        super.appendHoverText(stack, context, components, tooltipFlag);
    }  
}
