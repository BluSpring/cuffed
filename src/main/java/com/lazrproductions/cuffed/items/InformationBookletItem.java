package com.lazrproductions.cuffed.items;

import java.util.List;

import com.lazrproductions.cuffed.client.gui.screen.InformationBookletScreen;
import org.jetbrains.annotations.NotNull;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

public class InformationBookletItem extends Item {
    public InformationBookletItem(Properties p) {
        super(p);
    }

    
    @Override
    public InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {
        
        if(level.isClientSide()) {
            openBook();
        }

        return InteractionResultHolder.sidedSuccess(player.getItemInHand(hand), level.isClientSide());
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, TooltipContext context, @NotNull List<Component> components,
            @NotNull TooltipFlag flag) {
        components.add(Component.translatable("item.cuffed.information_booklet.desc").withStyle(ChatFormatting.GRAY));
        super.appendHoverText(stack, context, components, flag);
    }

    @Environment(EnvType.CLIENT)
    void openBook() {
        Minecraft.getInstance().setScreen(new InformationBookletScreen(Minecraft.getInstance()));
    }
}
