package com.lazrproductions.cuffed.entity.renderer;

import com.lazrproductions.cuffed.CuffedMod;
import com.lazrproductions.cuffed.entity.ChainKnotEntity;
import com.lazrproductions.cuffed.entity.model.ChainKnotEntityModel;
import com.lazrproductions.cuffed.init.ModModelLayers;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

@OnlyIn(Dist.CLIENT)
public class ChainKnotEntityRenderer extends EntityRenderer<ChainKnotEntity> {
    private static final ResourceLocation TEXTURE_LOCATION = ResourceLocation.fromNamespaceAndPath(CuffedMod.MODID,
            "textures/entity/chain_knot.png");
    private final ChainKnotEntityModel<ChainKnotEntity> model;

    public ChainKnotEntityRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new ChainKnotEntityModel<>(context.bakeLayer(ModModelLayers.CHAIN_KNOT_LAYER));
    }

    public static PoseStack POSESTACK;
    public static MultiBufferSource BUFFER;

    public void render(@NotNull ChainKnotEntity entity, float yaw, float partialTicks, @NotNull PoseStack stack, @NotNull MultiBufferSource buffer,
            int light) {
        super.render(entity, yaw, partialTicks, stack, buffer, light);

        POSESTACK = stack;
        BUFFER = buffer;

        this.model.setOnFence(!entity.isOnFence());

        stack.pushPose();
        this.model.setupAnim(entity, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);

        VertexConsumer vertexconsumer = buffer.getBuffer(this.model.renderType(TEXTURE_LOCATION));
        this.model.renderToBuffer(stack, vertexconsumer, light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F,
                1.0F);
        stack.popPose();
    }

    @Override
    public ResourceLocation getTextureLocation(@NotNull ChainKnotEntity entity) {
        return TEXTURE_LOCATION;
    }
}
