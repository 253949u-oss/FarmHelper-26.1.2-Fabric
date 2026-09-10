package com.jelly.farmhelperv2.antistaff;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class AntiStaffManager {
    private static final Logger LOGGER = LogManager.getLogger("FarmHelper");
    private static AntiStaffManager INSTANCE;
    private final Minecraft mc = Minecraft.getInstance();
    private final List<IAntiStaffCheck> checks = new ArrayList<>();
    private boolean antiStaffEnabled = true;
    private int lastViolationTime = 0;

    private AntiStaffManager() {
        try {
            registerChecks();
            LOGGER.info("AntiStaffManager initialized with {} checks", checks.size());
        } catch (Exception e) {
            LOGGER.error("Error initializing AntiStaffManager", e);
        }
    }

    public static AntiStaffManager getInstance() {
        if (INSTANCE == null) {
            try {
                INSTANCE = new AntiStaffManager();
            } catch (Exception e) {
                LOGGER.error("Failed to create AntiStaffManager instance", e);
            }
        }
        return INSTANCE;
    }

    private void registerChecks() {
        try {
            checks.add(new com.jelly.farmhelperv2.antistaff.impl.MovementSpeedCheck());
            checks.add(new com.jelly.farmhelperv2.antistaff.impl.RotationDetectionCheck());
            checks.add(new com.jelly.farmhelperv2.antistaff.impl.BlockPlacementPatternCheck());
            checks.add(new com.jelly.farmhelperv2.antistaff.impl.PacketFrequencyCheck());
            checks.add(new com.jelly.farmhelperv2.antistaff.impl.MacroSignatureCheck());
        } catch (Exception e) {
            LOGGER.error("Error registering anti-staff checks", e);
        }
    }

    public void updateChecks() {
        if (!antiStaffEnabled || mc.player == null) return;
        
        try {
            if (checks == null || checks.isEmpty()) {
                LOGGER.warn("Anti-staff checks list is empty or null");
                return;
            }

            for (IAntiStaffCheck check : checks) {
                if (check == null) {
                    LOGGER.warn("Null anti-staff check encountered");
                    continue;
                }

                try {
                    check.tick();
                    if (check.isViolating()) {
                        handleViolation(check);
                    }
                } catch (Exception e) {
                    LOGGER.error("Error ticking anti-staff check: {}", check.getCheckName(), e);
                }
            }
        } catch (Exception e) {
            LOGGER.error("Unexpected error in updateChecks", e);
        }
    }

    private void handleViolation(IAntiStaffCheck check) {
        try {
            if (check == null || mc.player == null) return;

            String checkName = check.getCheckName();
            String reason = check.getReason();
            
            if (checkName == null) checkName = "Unknown";
            if (reason == null) reason = "No reason provided";

            String warning = "[AntiStaff] " + checkName + " triggered: " + reason;
            
            // Rate limit warnings to prevent spam
            int currentTime = (int) (System.currentTimeMillis() / 1000);
            if (currentTime - lastViolationTime >= 5) {
                mc.player.displayClientMessage(Component.literal(warning), true);
                lastViolationTime = currentTime;
                LOGGER.warn(warning);
            }
        } catch (Exception e) {
            LOGGER.error("Error handling anti-staff violation", e);
        }
    }

    public void setAntiStaffEnabled(boolean enabled) {
        this.antiStaffEnabled = enabled;
        LOGGER.info("Anti-staff checks {}", enabled ? "enabled" : "disabled");
    }

    public boolean isAntiStaffEnabled() {
        return antiStaffEnabled;
    }
}
