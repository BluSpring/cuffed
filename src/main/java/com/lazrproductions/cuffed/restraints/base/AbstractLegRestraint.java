package com.lazrproductions.cuffed.restraints.base;

import com.lazrproductions.cuffed.CuffedMod;
import com.mojang.blaze3d.platform.Window;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class AbstractLegRestraint extends AbstractRestraint {

    static final ResourceLocation WIDGETS = ResourceLocation.fromNamespaceAndPath(CuffedMod.MODID, "textures/gui/widgets.png");

    static final ScreenTexture ARMS_ICON = new ScreenTexture(WIDGETS, 60, 24, 16, 16, 192, 192);

    public AbstractLegRestraint(){}
    public AbstractLegRestraint(ItemStack stack, ServerPlayer player, ServerPlayer captor) {
        super(stack, player, captor);
    }

    public RestraintType getType() {
        return RestraintType.LEG;
    }

    @Override
    public Collection<String> getBlockedKeyIds() {
        Minecraft inst = Minecraft.getInstance();

        return List.of(
            inst.options.keyUp.getName(),
            inst.options.keyDown.getName(),
            inst.options.keyLeft.getName(),
            inst.options.keyRight.getName(),
            inst.options.keyJump.getName(),
            inst.options.keySprint.getName(),

            "key.parcool.Crawl",
            "key.parcool.Breakfall",
            "key.parcool.WallSlide",
            "key.parcool.Dodge",
            "key.parcool.Vault",
            "key.parcool.Flipping",
            "key.parcool.FastRun",
            "key.parcool.ClingToCliff",
            "key.parcool.HangDown",
            "key.parcool.WallJump",
            "key.parcool.QuickTurn",
            "key.parcool.HorizontalWallRun",
            "key.elenaidodge2.dodge",
            "keybinds.combatroll.roll",
            "key.epicfight.dodge"
        );
    }


    public void renderOverlay(Player player, GuiGraphics graphics, float partialTick, Window window) {
            graphics.setColor(1, 1, 1, 1);

        int screenWidth = (int) (16 * 1.75f);
        int screenHeight = (int) (16 * 1.75f);
        int x = (window.getGuiScaledWidth() / 2) - (screenWidth / 2);
        int y = (window.getGuiScaledHeight() / 2) - (screenHeight) - 30;

        ScreenUtilities.drawTexture(graphics, new BlitCoordinates(x, y, screenWidth, screenHeight), ARMS_ICON);

        ArrayList<Component> c = new ArrayList<>();
        c.add(Component.translatable(getActionBarLabel()));
        ScreenUtilities.renderLabel(Minecraft.getInstance(), graphics, window.getGuiScaledWidth() / 2, y + screenHeight, c, 16579836);

    }
}
