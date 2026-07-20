package com.lazrproductions.cuffed.mixin;

import java.util.List;

import com.lazrproductions.cuffed.init.ModTags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.Block;

@Mixin(Item.class)
public class ItemMixin {

    @Inject(at = @At("TAIL"), method = "appendHoverText")
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> components, TooltipFlag tooltipFlag, CallbackInfo ci) {
        Block b = Block.byItem(stack.getItem());
        if(b.defaultBlockState().is(ModTags.Blocks.REINFORCED_BLOCKS))
            components.add(Component.translatable("info.cuffed.reinforced_item").withStyle(ChatFormatting.GRAY));
   }
}
