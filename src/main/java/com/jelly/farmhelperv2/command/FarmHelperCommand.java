package com.jelly.farmhelperv2.command;

import com.jelly.farmhelperv2.feature.FeatureManager;
import com.jelly.farmhelperv2.feature.impl.farming.FarmingRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

public class FarmHelperCommand {
    private final Minecraft mc = Minecraft.getInstance();
    private final FeatureManager featureManager = FeatureManager.getInstance();

    public void execute(String[] args) {
        if (args.length == 0) {
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
    }

    private void handleStart(String[] args) {
        if (args.length < 2) {
            sendMessage("Usage: /fh start <macro_name>");
            return;
        }

        String macroName = args[1].toLowerCase();
        
        try {
            featureManager.enableFeature(macroName);
            sendMessage("Started: " + macroName);
        } catch (Exception e) {
            sendMessage("Failed to start: " + macroName);
        }
    }

    private void handleStop(String[] args) {
        if (args.length < 2) {
            sendMessage("Usage: /fh stop <macro_name>");
            return;
        }

        String macroName = args[1].toLowerCase();
        
        try {
            featureManager.disableFeature(macroName);
            sendMessage("Stopped: " + macroName);
        } catch (Exception e) {
            sendMessage("Failed to stop: " + macroName);
        }
    }

    private void listFeatures() {
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
    }

    private void showStatus() {
        sendMessage("========== Farm Status ==========");
        sendMessage("WheatFarming: " + getStatus("WheatFarming"));
        sendMessage("CocoaBeanFarming: " + getStatus("CocoaBeanFarming"));
        sendMessage("MushroomFarming: " + getStatus("MushroomFarming"));
        sendMessage("SugarcaneFarming: " + getStatus("SugarcaneFarming"));
        sendMessage("NetherWartFarming: " + getStatus("NetherWartFarming"));
        sendMessage("CarrotPotatoFarming: " + getStatus("CarrotPotatoFarming"));
        sendMessage("PestFarming: " + getStatus("PestFarming"));
        sendMessage("================================");
    }

    private String getStatus(String featureName) {
        var feature = featureManager.getFeature(featureName);
        return feature != null && feature.isEnabled() ? "§aON" : "§cOFF";
    }

    private void sendHelp() {
        sendMessage("========== FarmHelper Commands ==========");
        sendMessage("/fh start <macro> - Start a farming macro");
        sendMessage("/fh stop <macro> - Stop a farming macro");
        sendMessage("/fh list - List all available macros");
        sendMessage("/fh status - Show macro status");
        sendMessage("========================================");
    }

    private void sendMessage(String message) {
        if (mc.player != null) {
            mc.player.displayClientMessage(Component.literal(message), false);
        }
    }
}
