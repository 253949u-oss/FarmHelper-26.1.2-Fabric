package com.jelly.farmhelperv2.antistaff.impl;

import com.jelly.farmhelperv2.antistaff.IAntiStaffCheck;

public class PacketFrequencyCheck implements IAntiStaffCheck {
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
        long now = System.currentTimeMillis();
        if (now - lastResetTime >= 1000) {
            violating = packetCount > violationThreshold;
            packetCount = 0;
            lastResetTime = now;
        }
    }

    public void onPacketSend() {
        packetCount++;
    }
}