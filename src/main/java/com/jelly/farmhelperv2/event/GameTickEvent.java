package com.jelly.farmhelperv2.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

public interface GameTickEvent {
    Event<ClientTick> CLIENT_TICK = EventFactory.createArrayBacked(ClientTick.class,
            (listeners) -> () -> {
                for (ClientTick listener : listeners) {
                    listener.onClientTick();
                }
            });

    interface ClientTick {
        void onClientTick();
    }
}
