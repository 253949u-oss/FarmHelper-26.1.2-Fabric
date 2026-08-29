package com.jelly.farmhelperv2.mixin.client;

import com.jelly.farmhelperv2.event.ReceivePacketEvent;
import com.jelly.farmhelperv2.event.SendPacketEvent;
import net.minecraft.network.packet.Packet;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public class MixinClientPlayNetworkHandler {
    @Inject(method = "sendPacket", at = @At("HEAD"), cancellable = true)
    private void onSendPacket(Packet packet, CallbackInfo ci) {
        SendPacketEvent.EVENT.invoker().onSendPacket(packet);
    }

    @Inject(method = "onPacket", at = @At("HEAD"), cancellable = true)
    private void onReceivePacket(Packet packet, CallbackInfo ci) {
        ReceivePacketEvent.EVENT.invoker().onReceivePacket(packet);
    }
}