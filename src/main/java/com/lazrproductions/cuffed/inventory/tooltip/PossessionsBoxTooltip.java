package com.lazrproductions.cuffed.inventory.tooltip;

import com.lazrproductions.cuffed.CuffedMod;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;

public record PossessionsBoxTooltip(NonNullList<ItemStack> items) implements ClientTooltipComponent, TooltipComponent {
    public static final ResourceLocation TEXTURE_LOCATION = ResourceLocation.fromNamespaceAndPath(CuffedMod.MODID,
        "textures/gui/container/possessions_box.png");

    public int getHeight() {
        return this.gridSizeY() * 20 + 4;
    }

    public int getWidth(@NotNull Font font) {
        return this.gridSizeX() * 18;
    }

    public void renderImage(@NotNull Font font, int x, int y, @NotNull GuiGraphics gui) {
        int horzontalSlots = this.gridSizeX();
        int verticalSlots = this.gridSizeY();
        int k = 0;

        for (int l = 0; l < verticalSlots; ++l) {
            for (int i1 = 0; i1 < horzontalSlots; ++i1) {
                int j1 = x + i1 * 18 + 1;
                int k1 = y + l * 20 + 1;
                this.renderSlot(j1, k1, k++, gui, font);
            }
        }

        this.drawBorder(x, y, horzontalSlots, verticalSlots, gui);
    }

    private void renderSlot(int x, int y, int index, GuiGraphics gui, Font p_281863_) {
        if (index >= this.items.size()) {
            this.blit(gui, x, y, Texture.SLOT);
        } else {
            ItemStack itemstack = this.items.get(index);
            this.blit(gui, x, y, Texture.SLOT);
            gui.renderItem(itemstack, x + 1, y + 1, index);
            gui.renderItemDecorations(p_281863_, itemstack, x + 1, y + 1);
            if (index == 0) {
                AbstractContainerScreen.renderSlotHighlight(gui, x + 1, y + 1, 0);
            }
        }
    }

    private void drawBorder(int x, int y, int width, int height, GuiGraphics gui) {
        this.blit(gui, x, y, Texture.BORDER_CORNER_TOP);
        this.blit(gui, x + width * 18 + 1, y, Texture.BORDER_CORNER_TOP);

        for (int i = 0; i < width; ++i) {
            this.blit(gui, x + 1 + i * 18, y, Texture.BORDER_HORIZONTAL_TOP);
            this.blit(gui, x + 1 + i * 18, y + height * 20,
                Texture.BORDER_HORIZONTAL_BOTTOM);
        }

        for (int j = 0; j < height; ++j) {
            this.blit(gui, x, y + j * 20 + 1, Texture.BORDER_VERTICAL);
            this.blit(gui, x + width * 18 + 1, y + j * 20 + 1, Texture.BORDER_VERTICAL);
        }

        this.blit(gui, x, y + height * 20, Texture.BORDER_CORNER_BOTTOM);
        this.blit(gui, x + width * 18 + 1, y + height * 20, Texture.BORDER_CORNER_BOTTOM);
    }

    private void blit(GuiGraphics gui, int x, int y, Texture texture) {
        gui.blit(TEXTURE_LOCATION, x, y, 0, (float) texture.x, (float) texture.y, texture.w, texture.h, 128, 128);
    }

    private int gridSizeX() {
        return Math.max(2, (int) Math.ceil(Math.sqrt((double) this.items.size() + 1.0D)));
    }

    private int gridSizeY() {
        return (int) Math.ceil(((double) this.items.size() + 1.0D) / (double) this.gridSizeX());
    }

    @OnlyIn(Dist.CLIENT)
    enum Texture {
        SLOT(0, 0, 18, 20),
        BORDER_VERTICAL(0, 18, 1, 20),
        BORDER_HORIZONTAL_TOP(0, 20, 18, 1),
        BORDER_HORIZONTAL_BOTTOM(0, 60, 18, 1),
        BORDER_CORNER_TOP(0, 20, 1, 1),
        BORDER_CORNER_BOTTOM(0, 60, 1, 1);

        public final int x;
        public final int y;
        public final int w;
        public final int h;

        Texture(int p_169928_, int p_169929_, int p_169930_, int p_169931_) {
            this.x = p_169928_;
            this.y = p_169929_;
            this.w = p_169930_;
            this.h = p_169931_;
        }
    }

}
