package com.jelly.farmhelperv2.antistaff.impl;

import com.jelly.farmhelperv2.antistaff.IAntiStaffCheck;
import net.minecraft.client.Minecraft;

public class RotationDetectionCheck implements IAntiStaffCheck {
    private final Minecraft mc = Minecraft.getInstance();
    private float lastYaw = 0f;
    private float lastPitch = 0f;
    private int unaturalRotations = 0;
    private boolean violating = false;

    @Override
    public String getCheckName() {
        return "RotationDetection";
    }

    @Override
    public boolean isViolating() {
        return violating;
    }

    @Override
    public String getReason() {
        return "Unnatural rotation detected: " + unaturalRotations + " occurrences";
    }

    @Override
    public void tick() {
        if (mc.player == null) return;
        float currentYaw = mc.player.getYRot();
        float currentPitch = mc.player.getXRot();
        float yawDelta = Math.abs(currentYaw - lastYaw);
        float pitchDelta = Math.abs(currentPitch - lastPitch);
        yawDelta = yawDelta > 180 ? 360 - yawDelta : yawDelta;
        if (yawDelta % 45f < 0.5f || pitchDelta < 0.1f) {
            unaturalRotations++;
            violating = unaturalRotations > 10;
        } else {
            unaturalRotations = Math.max(0, unaturalRotations - 1);
        }
        lastYaw = currentYaw;
        lastPitch = currentPitch;
    }
}