package com.jelly.farmhelperv2.antistaff.impl;

import com.jelly.farmhelperv2.antistaff.IAntiStaffCheck;
import net.minecraft.client.Minecraft;
import net.minecraft.world.phys.Vec3;

public class MovementSpeedCheck implements IAntiStaffCheck {
    private final Minecraft mc = Minecraft.getInstance();
    private Vec3 lastPos = Vec3.ZERO;
    private int violationCount = 0;
    private final double MAX_SPEED = 0.31;
    private boolean violating = false;

    @Override
    public String getCheckName() {
        return "MovementSpeed";
    }

    @Override
    public boolean isViolating() {
        return violating;
    }

    @Override
    public String getReason() {
        return "Movement speed exceeded: " + String.format("%.2f", lastPos.length()) + " blocks/tick";
    }

    @Override
    public void tick() {
        if (mc.player == null) return;
        Vec3 currentPos = mc.player.position();
        double distance = currentPos.distanceTo(lastPos);
        if (distance > MAX_SPEED && mc.player.isOnGround()) {
            violationCount++;
            violating = violationCount > 3;
        } else {
            violationCount = Math.max(0, violationCount - 1);
            violating = false;
        }
        lastPos = currentPos;
    }
}