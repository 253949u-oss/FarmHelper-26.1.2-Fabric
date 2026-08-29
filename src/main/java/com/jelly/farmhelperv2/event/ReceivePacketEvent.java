package com.jelly.farmhelperv2.event;

import net.minecraft.network.packet.Packet;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

public interface ReceivePacketEvent {
    Event<ReceivePacketEvent> EVENT = EventFactory.createArrayBacked(ReceivePacketEvent.class,
            (listeners) -> (packet) -> {
                for (ReceivePacketEvent listener : listeners) {
                    listener.onReceivePacket(packet);
                }
            });

    void onReceivePacket(Packet<?> packet);
}