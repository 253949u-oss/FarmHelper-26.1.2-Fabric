package com.jelly.farmhelperv2.config;

import java.util.HashMap;
import java.util.Map;

public class FarmHelperConfig {
    // General Settings
    public boolean enabled = true;
    public boolean showHUD = true;
    public boolean antiStaffEnabled = true;
    public int hudX = 10;
    public int hudY = 10;

    // Farming Settings
    public Map<String, FarmingSettings> farmingSettings = new HashMap<>();

    // Anti-Staff Settings
    public AntiStaffSettings antiStaffSettings = new AntiStaffSettings();

    public FarmHelperConfig() {
        // Initialize default farming settings
        farmingSettings.put("WheatFarming", new FarmingSettings(true, 10, 5, 10));
        farmingSettings.put("CocoaBeanFarming", new FarmingSettings(true, 12, 6, 8));
        farmingSettings.put("MushroomFarming", new FarmingSettings(true, 10, 4, 6));
        farmingSettings.put("SugarcaneFarming", new FarmingSettings(true, 10, 5, 10));
        farmingSettings.put("NetherWartFarming", new FarmingSettings(true, 10, 5, 10));
        farmingSettings.put("CarrotPotatoFarming", new FarmingSettings(true, 10, 4, 8));
        farmingSettings.put("PestFarming", new FarmingSettings(true, 15, 8, 4));
    }

    public static class FarmingSettings {
        public boolean enabled;
        public int scanRange;
        public int harvestCooldown;
        public int scanInterval;

        public FarmingSettings(boolean enabled, int scanRange, int harvestCooldown, int scanInterval) {
            this.enabled = enabled;
            this.scanRange = scanRange;
            this.harvestCooldown = harvestCooldown;
            this.scanInterval = scanInterval;
        }
    }

    public static class AntiStaffSettings {
        public boolean enabled = true;
        public boolean checkMovementSpeed = true;
        public boolean checkRotation = true;
        public boolean checkBlockPlacement = true;
        public boolean checkPacketFrequency = true;
        public boolean checkMacroSignature = true;
        public int movementSpeedThreshold = 31;
        public int packetFrequencyThreshold = 100;
    }
}
