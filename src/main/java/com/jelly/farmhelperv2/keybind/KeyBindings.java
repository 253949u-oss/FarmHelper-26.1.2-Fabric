package com.jelly.farmhelperv2.keybind;

import com.jelly.farmhelperv2.screen.FarmHelperConfigScreen;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import org.lwjgl.glfw.GLFW;

public class KeyBindings {
    public static final KeyMapping OPEN_CONFIG = KeyBindingHelper.registerKeyBinding(
            new KeyMapping(
                    "key.farmhelper.open_config",
                    GLFW.GLFW_KEY_F6,
                    "category.farmhelper.main"
            )
    );

    public static void register() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (OPEN_CONFIG.consumeClick()) {
                Minecraft mc = Minecraft.getInstance();
                mc.setScreen(new FarmHelperConfigScreen(mc.screen));
            }
        });
    }
}
