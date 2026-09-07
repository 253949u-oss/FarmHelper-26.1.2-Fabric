package com.jelly.farmhelperv2;

import com.jelly.farmhelperv2.antistaff.AntiStaffManager;
import com.jelly.farmhelperv2.command.CommandManager;
import com.jelly.farmhelperv2.event.GameTickEvent;
import com.jelly.farmhelperv2.feature.FeatureManager;
import com.jelly.farmhelperv2.feature.impl.farming.FarmingRegistry;
import com.jelly.farmhelperv2.hud.FarmHUD;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.minecraft.client.Minecraft;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FarmHelperFabric implements ClientModInitializer {
    public static final Logger LOGGER = LogManager.getLogger("FarmHelper");
    public static final String MOD_ID = "farmhelperv2";
    public static final String MOD_NAME = "FarmHelper";
    public static final String VERSION = "3.0.0-fabric";

    private static FeatureManager featureManager;
    private static CommandManager commandManager;
    private static AntiStaffManager antiStaffManager;
    private final Minecraft mc = Minecraft.getInstance();

    @Override
    public void onInitializeClient() {
        LOGGER.info("Initializing FarmHelper {} for Minecraft 26.2 Fabric", VERSION);
        
        // Initialize managers
        featureManager = FeatureManager.getInstance();
        commandManager = new CommandManager();
        antiStaffManager = AntiStaffManager.getInstance();

        // Register farming features
        FarmingRegistry.registerAllFarmingFeatures();

        // Register HUD render event
        HudRenderCallback.EVENT.register(new FarmHUD());

        // Register tick event
        registerTickEvent();

        LOGGER.info("FarmHelper initialized successfully!");
        LOGGER.info("Use /fh help for commands");
    }

    private void registerTickEvent() {
        // Register game tick listener using Fabric's built-in event
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (mc.player != null && mc.level != null) {
                // Tick all features
                featureManager.tickAllFeatures();
                
                // Update anti-staff checks
                antiStaffManager.updateChecks();
            }
        });
    }

    public static FeatureManager getFeatureManager() {
        return featureManager;
    }

    public static CommandManager getCommandManager() {
        return commandManager;
    }

    public static AntiStaffManager getAntiStaffManager() {
        return antiStaffManager;
    }

    public static class FarmHelperFabricInitializer implements ClientModInitializer {
        @Override
        public void onInitializeClient() {
            new FarmHelperFabric().onInitializeClient();
        }
    }
}
