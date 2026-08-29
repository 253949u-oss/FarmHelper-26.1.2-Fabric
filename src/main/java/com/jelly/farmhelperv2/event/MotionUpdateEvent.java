package com.jelly.farmhelperv2.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

public class MotionUpdateEvent {
    public static class Pre {
        public static final Event<Callback> EVENT = EventFactory.createArrayBacked(Callback.class, (listeners) -> (yaw, pitch) -> {
            for (Callback listener : listeners) {
                listener.onMotionUpdatePre(yaw, pitch);
            }
        });
        public interface Callback {
            void onMotionUpdatePre(float yaw, float pitch);
        }
    }

    public static class Post {
        public static final Event<Callback> EVENT = EventFactory.createArrayBacked(Callback.class, (listeners) -> (yaw, pitch) -> {
            for (Callback listener : listeners) {
                listener.onMotionUpdatePost(yaw, pitch);
            }
        });
        public interface Callback {
            void onMotionUpdatePost(float yaw, float pitch);
        }
    }
}