package com.jelly.farmhelperv2.screen;

import com.jelly.farmhelperv2.FarmHelperFabric;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.PauseScreen;

public class ScreenHelper {
    public static void register() {
        ScreenEvents.AFTER_INIT.register((client, screen, scaledWidth, scaledHeight) -> {
            // Add FarmHelper button to pause menu
            if (screen instanceof PauseScreen && !(screen instanceof PauseScreen)) {
                // Will add button to pause menu in next update
            }
        });
    }

    public static void openFarmHelperConfig() {
        Minecraft mc = Minecraft.getInstance();
        mc.setScreen(new FarmHelperConfigScreen(mc.screen));
    }
}
