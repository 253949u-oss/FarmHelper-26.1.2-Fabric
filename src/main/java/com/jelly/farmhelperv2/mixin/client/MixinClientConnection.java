package com.jelly.farmhelperv2.mixin.client;

import com.jelly.farmhelperv2.event.SendPacketEvent;
import net.minecraft.network.Connection;
import net.minecraft.network.packet.Packet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Connection.class)
public class MixinClientConnection {
    @Inject(method = "send(Lnet/minecraft/network/packet/Packet;)V", at = @At("HEAD"), cancellable = true)
    private void onPacketSend(Packet packet, CallbackInfo ci) {
        SendPacketEvent.EVENT.invoker().onSendPacket(packet);
    }
}