package com.jelly.farmhelperv2.mixin.client;

import com.jelly.farmhelperv2.event.MotionUpdateEvent;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LocalPlayer.class)
public class MixinLocalPlayer {
    @Inject(method = "tick", at = @At("HEAD"))
    private void onMotionUpdatePre(CallbackInfo ci) {
        LocalPlayer player = (LocalPlayer) (Object) this;
        MotionUpdateEvent.Pre.EVENT.invoker().onMotionUpdatePre(player.getYRot(), player.getXRot());
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void onMotionUpdatePost(CallbackInfo ci) {
        LocalPlayer player = (LocalPlayer) (Object) this;
        MotionUpdateEvent.Post.EVENT.invoker().onMotionUpdatePost(player.getYRot(), player.getXRot());
    }
}