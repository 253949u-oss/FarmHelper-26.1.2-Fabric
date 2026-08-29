package com.jelly.farmhelperv2.event;

import net.minecraft.network.packet.Packet;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

public interface SendPacketEvent {
    Event<SendPacketEvent> EVENT = EventFactory.createArrayBacked(SendPacketEvent.class,
            (listeners) -> (packet) -> {
                for (SendPacketEvent listener : listeners) {
                    listener.onSendPacket(packet);
                }
            });

    void onSendPacket(Packet<?> packet);
}