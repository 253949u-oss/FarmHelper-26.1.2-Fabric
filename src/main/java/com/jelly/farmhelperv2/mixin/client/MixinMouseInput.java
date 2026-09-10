package com.jelly.farmhelperv2.mixin.client;

import com.jelly.farmhelperv2.input.MouseLockManager;
import net.minecraft.client.MouseHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MouseHandler.class)
public class MixinMouseInput {
    
    @Inject(method = "onMove", at = @At("HEAD"), cancellable = true)
    private void onMouseMove(long windowHandle, double xpos, double ypos, CallbackInfo ci) {
        try {
            MouseLockManager mouseLock = MouseLockManager.getInstance();
            if (mouseLock != null && mouseLock.isMouseLocked()) {
                mouseLock.validateMousePosition();
            }
        } catch (Exception e) {
            // Silent fail on mouse lock validation
        }
    }
}
