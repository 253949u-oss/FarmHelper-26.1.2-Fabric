package com.jelly.farmhelperv2.mixin.client;

import com.jelly.farmhelperv2.input.KeyboardInputFilter;
import net.minecraft.client.KeyboardHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(KeyboardHandler.class)
public class MixinKeyboardInput {
    
    @Inject(method = "keyPress", at = @At("HEAD"), cancellable = true)
    private static void onKeyPress(long windowHandle, int key, int scancode, int action, int mods, CallbackInfo ci) {
        try {
            KeyboardInputFilter filter = KeyboardInputFilter.getInstance();
            if (filter != null && filter.shouldBlockKey(key) && action != 0) { // action 0 = release
                ci.cancel();
            }
        } catch (Exception e) {
            // Silent fail on key filter validation
        }
    }
}
