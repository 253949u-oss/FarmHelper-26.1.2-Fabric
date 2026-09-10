package com.jelly.farmhelperv2.antistaff.impl;

import com.jelly.farmhelperv2.antistaff.IAntiStaffCheck;
import net.minecraft.client.Minecraft;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MacroSignatureCheck implements IAntiStaffCheck {
    private static final Logger LOGGER = LogManager.getLogger("FarmHelper");
    private final Minecraft mc = Minecraft.getInstance();
    private int rightClickCount = 0;
    private int perfectTimingClicks = 0;
    private boolean violating = false;
    private long lastClickTime = 0;
    private final int PERFECT_TIMING_THRESHOLD = 15;

    @Override
    public String getCheckName() {
        return "MacroSignature";
    }

    @Override
    public boolean isViolating() {
        return violating;
    }

    @Override
    public String getReason() {
        return "Macro-like clicking pattern: " + perfectTimingClicks + "/" + rightClickCount + " ticks";
    }

    @Override
    public void tick() {
        try {
            if (rightClickCount > 10) {
                violating = (double) perfectTimingClicks / rightClickCount > 0.75;
            } else {
                violating = false;
            }
            
            if (rightClickCount >= 20) {
                rightClickCount = 0;
                perfectTimingClicks = 0;
            }
        } catch (Exception e) {
            LOGGER.debug("Error in macro signature check tick", e);
        }
    }

    public void onRightClick() {
        try {
            long currentTime = System.currentTimeMillis();
            if (lastClickTime > 0) {
                long timeSinceLastClick = currentTime - lastClickTime;
                if (Math.abs(timeSinceLastClick - 100) < 10) {
                    perfectTimingClicks++;
                }
            }
            rightClickCount++;
            lastClickTime = currentTime;
        } catch (Exception e) {
            LOGGER.debug("Error in right click handler", e);
        }
    }
}
