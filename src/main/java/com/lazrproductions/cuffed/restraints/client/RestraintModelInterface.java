package com.lazrproductions.cuffed.restraints.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

@Environment(EnvType.CLIENT)
public abstract class RestraintModelInterface {
    public abstract Class<? extends HumanoidModel<? extends LivingEntity>> getRenderedModel();
    public abstract ModelLayerLocation getRenderedModelLayer();
    public abstract ResourceLocation getRenderedModelTexture();
}
