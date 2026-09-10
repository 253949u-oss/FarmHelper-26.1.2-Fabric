package com.jelly.farmhelperv2.antistaff.impl;

import com.jelly.farmhelperv2.antistaff.IAntiStaffCheck;
import net.minecraft.client.Minecraft;
import net.minecraft.world.phys.Vec3;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MovementSpeedCheck implements IAntiStaffCheck {
    private static final Logger LOGGER = LogManager.getLogger("FarmHelper");
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
        try {
            if (lastPos == null) return "Speed: unknown";
            return "Movement speed exceeded: " + String.format("%.2f", lastPos.length()) + " blocks/tick";
        } catch (Exception e) {
            LOGGER.debug("Error getting movement speed reason", e);
            return "Speed check error";
        }
    }

    @Override
    public void tick() {
        try {
            if (mc == null || mc.player == null) return;
            
            Vec3 currentPos = mc.player.position();
            if (currentPos == null) return;
            
            if (lastPos == null) lastPos = currentPos;
            
            double distance = currentPos.distanceTo(lastPos);
            if (distance > MAX_SPEED && mc.player.isOnGround()) {
                violationCount++;
                violating = violationCount > 3;
            } else {
                violationCount = Math.max(0, violationCount - 1);
                violating = false;
            }
            lastPos = currentPos;
        } catch (Exception e) {
            LOGGER.debug("Error in movement speed check tick", e);
        }
    }
}
