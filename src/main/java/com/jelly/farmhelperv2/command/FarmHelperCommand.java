package com.jelly.farmhelperv2.command;

import com.jelly.farmhelperv2.feature.FeatureManager;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FarmHelperCommand {
    private static final Logger LOGGER = LogManager.getLogger("FarmHelper");
    private final Minecraft mc = Minecraft.getInstance();
    private final FeatureManager featureManager = FeatureManager.getInstance();

    public void execute(String[] args) {
        try {
            if (args == null || args.length == 0) {
                sendHelp();
                return;
            }

            String command = args[0].toLowerCase();

            switch (command) {
                case "help":
                    sendHelp();
                    break;
                case "start":
                    handleStart(args);
                    break;
                case "stop":
                    handleStop(args);
                    break;
                case "list":
                    listFeatures();
                    break;
                case "status":
                    showStatus();
                    break;
                default:
                    sendMessage("Unknown command: " + command);
            }
        } catch (Exception e) {
            LOGGER.error("Error executing command", e);
            sendMessage("Error executing command: " + e.getMessage());
        }
    }

    private void handleStart(String[] args) {
        try {
            if (args == null || args.length < 2) {
                sendMessage("Usage: /fh start <macro_name>");
                return;
            }

            String macroName = args[1].toLowerCase();
            
            if (featureManager == null) {
                sendMessage("Feature manager not initialized");
                LOGGER.warn("FeatureManager is null in handleStart");
                return;
            }

            try {
                featureManager.enableFeature(macroName);
                sendMessage("Started: " + macroName);
                LOGGER.info("Started macro: {}", macroName);
            } catch (Exception e) {
                sendMessage("Failed to start: " + macroName + " (" + e.getMessage() + ")");
                LOGGER.error("Error starting macro: {}", macroName, e);
            }
        } catch (Exception e) {
            LOGGER.error("Unexpected error in handleStart", e);
            sendMessage("Unexpected error: " + e.getMessage());
        }
    }

    private void handleStop(String[] args) {
        try {
            if (args == null || args.length < 2) {
                sendMessage("Usage: /fh stop <macro_name>");
                return;
            }

            String macroName = args[1].toLowerCase();
            
            if (featureManager == null) {
                sendMessage("Feature manager not initialized");
                LOGGER.warn("FeatureManager is null in handleStop");
                return;
            }

            try {
                featureManager.disableFeature(macroName);
                sendMessage("Stopped: " + macroName);
                LOGGER.info("Stopped macro: {}", macroName);
            } catch (Exception e) {
                sendMessage("Failed to stop: " + macroName + " (" + e.getMessage() + ")");
                LOGGER.error("Error stopping macro: {}", macroName, e);
            }
        } catch (Exception e) {
            LOGGER.error("Unexpected error in handleStop", e);
            sendMessage("Unexpected error: " + e.getMessage());
        }
    }

    private void listFeatures() {
        try {
            sendMessage("========== Available Macros ==========");
            sendMessage("Crop Farming:");
            sendMessage("  - WheatFarming");
            sendMessage("  - CocoaBeanFarming");
            sendMessage("  - MushroomFarming");
            sendMessage("  - SugarcaneFarming");
            sendMessage("  - NetherWartFarming");
            sendMessage("  - CarrotPotatoFarming");
            sendMessage("Pest Control:");
            sendMessage("  - PestFarming");
            sendMessage("======================================");
        } catch (Exception e) {
            LOGGER.error("Error listing features", e);
        }
    }

    private void showStatus() {
        try {
            if (featureManager == null) {
                sendMessage("Feature manager not initialized");
                return;
            }

            sendMessage("========== Farm Status ==========");
            sendMessage("WheatFarming: " + getStatus("WheatFarming"));
            sendMessage("CocoaBeanFarming: " + getStatus("CocoaBeanFarming"));
            sendMessage("MushroomFarming: " + getStatus("MushroomFarming"));
            sendMessage("SugarcaneFarming: " + getStatus("SugarcaneFarming"));
            sendMessage("NetherWartFarming: " + getStatus("NetherWartFarming"));
            sendMessage("CarrotPotatoFarming: " + getStatus("CarrotPotatoFarming"));
            sendMessage("PestFarming: " + getStatus("PestFarming"));
            sendMessage("================================");
        } catch (Exception e) {
            LOGGER.error("Error showing status", e);
        }
    }

    private String getStatus(String featureName) {
        try {
            if (featureManager == null) return "§cNOT_INITIALIZED";
            
            var feature = featureManager.getFeature(featureName);
            return feature != null && feature.isEnabled() ? "§aON" : "§cOFF";
        } catch (Exception e) {
            LOGGER.error("Error getting status for {}", featureName, e);
            return "§cERROR";
        }
    }

    private void sendHelp() {
        try {
            sendMessage("========== FarmHelper Commands ==========");
            sendMessage("/fh start <macro> - Start a farming macro");
            sendMessage("/fh stop <macro> - Stop a farming macro");
            sendMessage("/fh list - List all available macros");
            sendMessage("/fh status - Show macro status");
            sendMessage("========================================");
        } catch (Exception e) {
            LOGGER.error("Error sending help", e);
        }
    }

    private void sendMessage(String message) {
        try {
            if (mc == null || mc.player == null) {
                LOGGER.debug("Cannot send message, Minecraft or player is null: {}", message);
                return;
            }
            mc.player.displayClientMessage(Component.literal(message), false);
        } catch (Exception e) {
            LOGGER.error("Error sending message: {}", message, e);
        }
    }
}
