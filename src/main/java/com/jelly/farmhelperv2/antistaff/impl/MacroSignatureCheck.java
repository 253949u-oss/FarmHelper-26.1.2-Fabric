package com.jelly.farmhelperv2.antistaff.impl;

import com.jelly.farmhelperv2.antistaff.IAntiStaffCheck;
import net.minecraft.client.Minecraft;

public class MacroSignatureCheck implements IAntiStaffCheck {
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
        violating = rightClickCount > 10 && (double) perfectTimingClicks / rightClickCount > 0.75;
        if (rightClickCount >= 20) {
            rightClickCount = 0;
            perfectTimingClicks = 0;
        }
    }

    public void onRightClick() {
        long currentTime = System.currentTimeMillis();
        long timeSinceLastClick = currentTime - lastClickTime;
        if (lastClickTime > 0 && Math.abs(timeSinceLastClick - 100) < 10) {
            perfectTimingClicks++;
        }
        rightClickCount++;
        lastClickTime = currentTime;
    }
}