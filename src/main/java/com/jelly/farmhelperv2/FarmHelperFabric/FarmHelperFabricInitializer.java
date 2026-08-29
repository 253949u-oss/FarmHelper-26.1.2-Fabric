package com.jelly.farmhelperv2.FarmHelperFabric;

import com.jelly.farmhelperv2.antistaff.AntiStaffManager;
import com.jelly.farmhelperv2.command.CommandManager;
import com.jelly.farmhelperv2.event.GameTickEvent;
import com.jelly.farmhelperv2.feature.FeatureManager;
import com.jelly.farmhelperv2.feature.impl.farming.FarmingRegistry;
import com.jelly.farmhelperv2.keybind.KeyBindings;
import com.jelly.farmhelperv2.hud.FarmHUD;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.Minecraft;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FarmHelperFabricInitializer implements ClientModInitializer {
    public static final Logger LOGGER = LogManager.getLogger("FarmHelper");
    public static final String MOD_ID = "farmhelperv2";
    public static final String MOD_NAME = "FarmHelper";
    public static final String VERSION = "3.0.0";

    private static FeatureManager featureManager;
    private static CommandManager commandManager;
    private static AntiStaffManager antiStaffManager;
    private final Minecraft mc = Minecraft.getInstance();

    @Override
    public void onInitializeClient() {
        LOGGER.info("Initializing FarmHelper {} for Minecraft 26.1.2", VERSION);

        // Initialize managers
        featureManager = FeatureManager.getInstance();
        commandManager = new CommandManager();
        antiStaffManager = AntiStaffManager.getInstance();

        // Register farming features
        FarmingRegistry.registerAllFarmingFeatures();

        // Register key bindings
        KeyBindings.register();

        // Register game tick event
        registerTickEvent();

        // Register HUD
        registerHUD();

        LOGGER.info("FarmHelper initialized successfully!");
        LOGGER.info("Press F6 to open dashboard or use /fh commands");
    }

    private void registerTickEvent() {
        GameTickEvent.CLIENT_TICK.register(() -> {
            if (mc.player != null && mc.level != null) {
                featureManager.tickAllFeatures();
                antiStaffManager.updateChecks();
            }
        });
    }

    private void registerHUD() {
        FarmHUD farmHUD = new FarmHUD();
        // Register HUD rendering
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
}
