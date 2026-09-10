package com.jelly.farmhelperv2.antistaff.impl;

import com.jelly.farmhelperv2.antistaff.IAntiStaffCheck;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PacketFrequencyCheck implements IAntiStaffCheck {
    private static final Logger LOGGER = LogManager.getLogger("FarmHelper");
    private int packetCount = 0;
    private long lastResetTime = System.currentTimeMillis();
    private int violationThreshold = 100;
    private boolean violating = false;

    @Override
    public String getCheckName() {
        return "PacketFrequency";
    }

    @Override
    public boolean isViolating() {
        return violating;
    }

    @Override
    public String getReason() {
        return "Abnormal packet frequency: " + packetCount + " packets/sec";
    }

    @Override
    public void tick() {
        try {
            long now = System.currentTimeMillis();
            if (now - lastResetTime >= 1000) {
                violating = packetCount > violationThreshold;
                packetCount = 0;
                lastResetTime = now;
            }
        } catch (Exception e) {
            LOGGER.debug("Error in packet frequency check tick", e);
        }
    }

    public void onPacketSend() {
        try {
            packetCount++;
        } catch (Exception e) {
            LOGGER.debug("Error incrementing packet count", e);
        }
    }
}
