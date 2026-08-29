package com.jelly.farmhelperv2.FarmHelperFabric;

import com.jelly.farmhelperv2.antistaff.AntiStaffManager;
import com.jelly.farmhelperv2.command.CommandManager;
import com.jelly.farmhelperv2.feature.FeatureManager;

public class FarmHelperAPI {
    /**
     * Get the feature manager for programmatic control
     */
    public static FeatureManager getFeatureManager() {
        return FeatureManager.getInstance();
    }

    /**
     * Get the command manager for executing commands
     */
    public static CommandManager getCommandManager() {
        return new CommandManager();
    }

    /**
     * Get the anti-staff manager
     */
    public static AntiStaffManager getAntiStaffManager() {
        return AntiStaffManager.getInstance();
    }

    /**
     * Enable a farming macro
     */
    public static void enableFarming(String farmingType) {
        getFeatureManager().enableFeature(farmingType);
    }

    /**
     * Disable a farming macro
     */
    public static void disableFarming(String farmingType) {
        getFeatureManager().disableFeature(farmingType);
    }

    /**
     * Check if a farming macro is active
     */
    public static boolean isFarmingActive(String farmingType) {
        var feature = getFeatureManager().getFeature(farmingType);
        return feature != null && feature.isEnabled();
    }
}
