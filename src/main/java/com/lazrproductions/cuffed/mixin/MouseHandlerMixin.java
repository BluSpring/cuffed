package com.lazrproductions.cuffed.mixin;

import java.util.stream.Stream;

import com.lazrproductions.cuffed.api.CuffedAPI;
import com.lazrproductions.cuffed.cap.RestrainableCapability;
import com.lazrproductions.cuffed.entity.base.IDetainableEntity;
import com.lazrproductions.cuffed.restraints.base.AbstractArmRestraint;
import com.lazrproductions.cuffed.restraints.base.AbstractLegRestraint;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHandler;
import net.minecraft.client.player.LocalPlayer;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;

@Mixin(MouseHandler.class)
public class MouseHandlerMixin {
    @Inject(method = "onPress", at = @At("HEAD"), cancellable = true)
    public void onPress(long windowId, int keyCode, int scanCode, int f3, CallbackInfo callback) {
        Minecraft inst = Minecraft.getInstance();
        LocalPlayer player = inst.player;
        if(player!= null && inst.screen == null) {
            if(inst.screen != null)
                return;

            RestrainableCapability cap = (RestrainableCapability)CuffedAPI.Capabilities.getRestrainableCapability(player);
            if(cap != null && cap.isRestrained()) {
                cap.onMouseInput(player, keyCode, scanCode);
                for (int i : cap.gatherBlockedInputs()) {
                    if(keyCode == i) {
                        callback.cancel();
                        return;
                    }
                }
            }
            
            if(player instanceof IDetainableEntity detainable)
                if(detainable.getDetained() > -1) {
                    var detainedKeyIds = Stream.concat(AbstractArmRestraint.BLOCKED_KEY_IDS.get().stream(), AbstractLegRestraint.BLOCKED_KEY_IDS.get().stream()).toList();

                    for (String keyId : detainedKeyIds) {
                        var mapping = cuffed$findKeyMapping(keyId);
                        if (mapping != null && KeyBindingHelper.getBoundKeyOf(mapping).getValue() == keyCode) {
                            callback.cancel();
                            return;
                        }
                    }
                }
        }
    }

    @Unique
    private static KeyMapping cuffed$findKeyMapping(String id) {
        for (KeyMapping keyMapping : Minecraft.getInstance().options.keyMappings) {
            if (keyMapping.getName().equals(id))
                return keyMapping;
        }

        return null;
    }

    @Inject(method = "onScroll", at = @At("HEAD"), cancellable = true)
    private void onScroll(long windowId, double deltaX, double deltaY, CallbackInfo callback) {
        Minecraft inst = Minecraft.getInstance();
        LocalPlayer player = inst.player;
        if(player!= null) {
            if(inst.screen != null)
                return;

            RestrainableCapability cap = (RestrainableCapability)CuffedAPI.Capabilities.getRestrainableCapability(player);
            if(cap != null && cap.armsRestrained()) {
                callback.cancel();
                return;
            }
            
            if(player instanceof IDetainableEntity detainable)
                if(detainable.getDetained() > -1)
                {
                    callback.cancel();
                }
                    
        }
    }
}
