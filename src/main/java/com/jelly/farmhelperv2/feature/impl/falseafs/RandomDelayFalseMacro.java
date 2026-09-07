package com.jelly.farmhelperv2.feature.impl.falseafs;

import com.jelly.farmhelperv2.feature.IFeature;
import net.minecraft.client.Minecraft;

public class RandomDelayFalseMacro implements IFeature {
    private boolean enabled = false;
    private final Minecraft mc = Minecraft.getInstance();
    private int nextActionDelay = 0;
    private int currentDelay = 0;

    @Override
    public String getName() {
        return "RandomDelay";
    }

    @Override
    public void init() {}

    @Override
    public void enable() {
        enabled = true;
    }

    @Override
    public void disable() {
        enabled = false;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public void tick() {
        if (!enabled) return;

        if (currentDelay > 0) {
            currentDelay--;
            return;
        }

        // Generate random delays between 50-300 ticks (2.5-15 seconds)
        // This mimics human reaction time and prevents pattern detection
        nextActionDelay = 50 + (int)(Math.random() * 250);
        currentDelay = nextActionDelay;
    }

    public int getCurrentDelay() {
        return currentDelay;
    }

    public void resetDelay() {
        currentDelay = 0;
    }
}
