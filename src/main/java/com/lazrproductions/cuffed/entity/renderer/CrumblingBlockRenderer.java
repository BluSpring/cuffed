package com.lazrproductions.cuffed.entity.renderer;

import com.lazrproductions.cuffed.CuffedMod;
import com.lazrproductions.cuffed.entity.CrumblingBlockEntity;
import com.lazrproductions.cuffed.entity.model.CrumblingBlockModel;
import com.lazrproductions.cuffed.init.ModModelLayers;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

@OnlyIn(Dist.CLIENT)
public class CrumblingBlockRenderer extends EntityRenderer<CrumblingBlockEntity> {
    public static ResourceLocation TEXTURE_LOCATION_1 = ResourceLocation.fromNamespaceAndPath(CuffedMod.MODID,
            "textures/entity/crumbling_block_1.png");
    public static ResourceLocation TEXTURE_LOCATION_2 = ResourceLocation.fromNamespaceAndPath(CuffedMod.MODID,
            "textures/entity/crumbling_block_2.png");
    public static ResourceLocation TEXTURE_LOCATION_3 = ResourceLocation.fromNamespaceAndPath(CuffedMod.MODID,
            "textures/entity/crumbling_block_3.png");
    public static ResourceLocation TEXTURE_LOCATION_4 = ResourceLocation.fromNamespaceAndPath(CuffedMod.MODID,
            "textures/entity/crumbling_block_4.png");

    public CrumblingBlockModel<?> model;

    public CrumblingBlockRenderer(EntityRendererProvider.Context ctx) {
        super(ctx);
        model = new CrumblingBlockModel<>(ctx.bakeLayer(ModModelLayers.CRUMBLING_BLOCK_LAYER));
    }

    
    @Override
    public void render(@NotNull CrumblingBlockEntity entity, float p_114486_, float deltaTick, @NotNull PoseStack stack,
            @NotNull MultiBufferSource buffer,
            int bakedLight) {
        super.render(entity, p_114486_, deltaTick, stack, buffer, bakedLight);
        model.renderToBuffer(stack, buffer.getBuffer(RenderType.entityTranslucent(getTextureLocation(entity))), 15,
                getOverlayCoords(entity, 0), 1.0f, 1.0f, 1.0f, 1.0f);
    }

    @Override
    public ResourceLocation getTextureLocation(@NotNull CrumblingBlockEntity entity) {
        switch (entity.getCrumbleProgress()) {
            case 1:
                return TEXTURE_LOCATION_1;
            case 2:
                return TEXTURE_LOCATION_2;
            case 3:
                return TEXTURE_LOCATION_3;
            case 4:
                return TEXTURE_LOCATION_4;
            case 5:
                return TEXTURE_LOCATION_4;
            default:
                return TEXTURE_LOCATION_1;
        }
    }

    public static int getOverlayCoords(CrumblingBlockEntity entity, float f) {
        return OverlayTexture.pack(OverlayTexture.u(f), OverlayTexture.v(false));
    }

}
