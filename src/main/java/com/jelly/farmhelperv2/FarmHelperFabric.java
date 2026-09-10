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
    public static final String VERSION = "3.0.1-fabric";

    private static FeatureManager featureManager;
    private static CommandManager commandManager;
    private static AntiStaffManager antiStaffManager;
    private final Minecraft mc = Minecraft.getInstance();
    private static boolean initialized = false;

    @Override
    public void onInitializeClient() {
        try {
            LOGGER.info("Initializing FarmHelper {} for Minecraft 26.2 Fabric", VERSION);
            
            // Initialize managers with null checks
            try {
                featureManager = FeatureManager.getInstance();
                if (featureManager == null) {
                    LOGGER.error("Failed to initialize FeatureManager");
                    return;
                }
            } catch (Exception e) {
                LOGGER.error("Error initializing FeatureManager", e);
                return;
            }

            try {
                commandManager = new CommandManager();
                if (commandManager == null) {
                    LOGGER.error("Failed to initialize CommandManager");
                    return;
                }
            } catch (Exception e) {
                LOGGER.error("Error initializing CommandManager", e);
                return;
            }

            try {
                antiStaffManager = AntiStaffManager.getInstance();
                if (antiStaffManager == null) {
                    LOGGER.error("Failed to initialize AntiStaffManager");
                    return;
                }
            } catch (Exception e) {
                LOGGER.error("Error initializing AntiStaffManager", e);
                return;
            }

            // Register farming features with error handling
            try {
                FarmingRegistry.registerAllFarmingFeatures();
                LOGGER.info("Farming features registered successfully");
            } catch (Exception e) {
                LOGGER.error("Error registering farming features", e);
            }

            // Register HUD render event with error handling
            try {
                HudRenderCallback.EVENT.register(new FarmHUD());
                LOGGER.info("HUD render callback registered");
            } catch (Exception e) {
                LOGGER.error("Error registering HUD render callback", e);
            }

            // Register tick event
            try {
                registerTickEvent();
                LOGGER.info("Tick event registered successfully");
            } catch (Exception e) {
                LOGGER.error("Error registering tick event", e);
            }

            initialized = true;
            LOGGER.info("FarmHelper initialized successfully!");
            LOGGER.info("Use /fh help for commands");
        } catch (Exception e) {
            LOGGER.error("Fatal error during FarmHelper initialization", e);
            initialized = false;
        }
    }

    private void registerTickEvent() {
        // Register game tick listener using Fabric's built-in event
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            try {
                if (mc.player != null && mc.level != null) {
                    // Tick all features with null check
                    if (featureManager != null) {
                        try {
                            featureManager.tickAllFeatures();
                        } catch (Exception e) {
                            LOGGER.error("Error ticking features", e);
                        }
                    }
                    
                    // Update anti-staff checks with null check
                    if (antiStaffManager != null) {
                        try {
                            antiStaffManager.updateChecks();
                        } catch (Exception e) {
                            LOGGER.error("Error updating anti-staff checks", e);
                        }
                    }
                }
            } catch (Exception e) {
                LOGGER.error("Unexpected error in tick event", e);
            }
        });
    }

    public static FeatureManager getFeatureManager() {
        if (featureManager == null) {
            LOGGER.warn("FeatureManager is null, returning null");
        }
        return featureManager;
    }

    public static CommandManager getCommandManager() {
        if (commandManager == null) {
            LOGGER.warn("CommandManager is null, returning null");
        }
        return commandManager;
    }

    public static AntiStaffManager getAntiStaffManager() {
        if (antiStaffManager == null) {
            LOGGER.warn("AntiStaffManager is null, returning null");
        }
        return antiStaffManager;
    }

    public static boolean isInitialized() {
        return initialized;
    }

    public static class FarmHelperFabricInitializer implements ClientModInitializer {
        @Override
        public void onInitializeClient() {
            new FarmHelperFabric().onInitializeClient();
        }
    }
}
