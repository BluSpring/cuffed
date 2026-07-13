package com.lazrproductions.cuffed.client.particle;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.SimpleParticleType;

@OnlyIn(Dist.CLIENT)
public class BloodDripParticle extends TextureSheetParticle {
    private final SpriteSet sprites;

    BloodDripParticle(ClientLevel level, double x, double y, double z, SpriteSet sprites) {
        super(level, x, y, z);
        this.setSize(0.01F, 0.01F);
        this.gravity = 0.06F;
        this.sprites = sprites;
        this.setSprite(this.sprites.get(0, 1));
    }

    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        this.preMoveUpdate();
        if (!this.removed) {
            this.yd -= this.gravity;
            this.move(this.xd, this.yd, this.zd);
            this.postMoveUpdate();
            if (!this.removed) {
                this.xd *= 0.98F;
                this.yd *= 0.98F;
                this.zd *= 0.98F;
            }
        }

        if(this.onGround) {
            this.setSprite(this.sprites.get(1, 1));
        }
    }

    protected void preMoveUpdate() {
        if (this.lifetime-- <= 0) {
            this.remove();
        }

    }

    protected void postMoveUpdate() {
    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public Provider(SpriteSet set) {
            this.sprites = set;
        }

        @Override
        @Nullable
        public Particle createParticle(@NotNull SimpleParticleType type, @NotNull ClientLevel level, double x,
                double y, double z, double f, double f1, double f2) {
            BloodDripParticle dripparticle = new BloodDripParticle(level, x, y, z, sprites);
            dripparticle.setColor(0.74F, 0.074F, 0.074F);
            return dripparticle;
        }
    }
}
