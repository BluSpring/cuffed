package com.lazrproductions.cuffed.mixin;

import java.util.List;

import com.lazrproductions.cuffed.items.base.AbstractRestraintItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BundleItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

@Mixin(BundleItem.class)
public class BundleItemMixin {
    @Inject(method = "appendHoverText", at = @At("TAIL"))
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> lore, TooltipFlag tooltipFlag, CallbackInfo ci) {
        if (BundleItem.getFullnessDisplay(stack) <= 0) {
            lore.add(Component.translatable("info.cuffed.restraint_type.head").withStyle(ChatFormatting.GRAY));

            lore.add(Component.empty());
            lore.add(
                    Component.translatable("info.cuffed.restraint_type.my_key")
                            .withStyle(ChatFormatting.GRAY)
                            .append(" ")
                            .append(
                                    Component.translatable("info.cuffed.empty_hand")
                                            .withStyle(ChatFormatting.WHITE)));

            AbstractRestraintItem.Client.showExtendedInfo(lore);
        }
    }
}
