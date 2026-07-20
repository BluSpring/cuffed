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
import net.minecraft.client.KeyboardHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;

@Mixin(KeyboardHandler.class)
public class KeyboardHandlerMixin {
    @Inject(method = "keyPress", at = @At("HEAD"), cancellable = true)
    public void keyPress(long windowId, int keyCode, int scanCode, int action, int f4, CallbackInfo callback) {
        Minecraft inst = Minecraft.getInstance();
        LocalPlayer player = inst.player;

        if(player!= null && inst.screen == null) {
            RestrainableCapability cap = (RestrainableCapability)CuffedAPI.Capabilities.getRestrainableCapability(player);
            if(cap != null && cap.isRestrained()) {
                cap.onKeyInput(player, keyCode, action);
                for (int i : cap.gatherBlockedInputs()) {
                    if(keyCode == i && (action == 1 || action == 2)) {
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
                        if (mapping != null && KeyBindingHelper.getBoundKeyOf(mapping).getValue() == keyCode && (action == 1 || action == 2)) {
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

}
