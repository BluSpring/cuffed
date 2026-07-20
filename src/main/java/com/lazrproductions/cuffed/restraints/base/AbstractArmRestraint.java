package com.lazrproductions.cuffed.restraints.base;

import com.lazrproductions.cuffed.CuffedMod;
import com.mojang.blaze3d.platform.Window;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Stream;

public abstract class AbstractArmRestraint extends AbstractRestraint {
    public AbstractArmRestraint(){}
    public AbstractArmRestraint(ItemStack stack, ServerPlayer player, ServerPlayer captor) {
        super(stack, player, captor);
    }

    public RestraintType getType() {
        return RestraintType.ARM;
    }

    @Override
    public Collection<String> getBlockedKeyIds() {
        Minecraft inst = Minecraft.getInstance();

        return Stream.concat(
            Stream.of(
                inst.options.keyAttack.getName(),
                inst.options.keyUse.getName(),
                inst.options.keyInventory.getName(),
                inst.options.keyDrop.getName(),
                inst.options.keyPickItem.getName(),
                inst.options.keySwapOffhand.getName(),
                "key.parcool.Crawl",
                "key.parcool.Breakfall",
                "key.parcool.WallSlide",
                "key.parcool.Vault",
                "key.parcool.Flipping",
                "key.parcool.FastRun",
                "key.parcool.ClingToCliff",
                "key.parcool.HangDown",
                "key.parcool.WallJump",
                "key.parcool.HorizontalWallRun"
            ),
            Arrays.stream(inst.options.keyHotbarSlots).map(KeyMapping::getName)
        ).toList();
    }

    @Environment(EnvType.CLIENT)
    public void renderOverlay(Player player, GuiGraphics graphics, float partialTick, Window window) {
        graphics.setColor(1, 1, 1, 1);

        int screenWidth = (int) (16 * 1.75f);
        int screenHeight = (int) (16 * 1.75f);
        int x = (window.getGuiScaledWidth() / 2) - (screenWidth / 2);
        int y = (window.getGuiScaledHeight() / 2) - (screenHeight) - 65;
        graphics.blitSprite(CuffedMod.id("restraints/arm"), x, y, 16, 16);

        ArrayList<Component> c = new ArrayList<>();
        c.add(Component.translatable(getActionBarLabel()));
        graphics.drawString(Minecraft.getInstance().font, Component.translatable(getActionBarLabel()), window.getGuiScaledWidth() / 2, y + screenHeight, 16579836);
    }
}
