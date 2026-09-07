package com.jelly.farmhelperv2.feature.impl.falseafs;

import com.jelly.farmhelperv2.feature.IFeature;
import net.minecraft.client.Minecraft;

public class PatternVariationFalseMacro implements IFeature {
    private boolean enabled = false;
    private final Minecraft mc = Minecraft.getInstance();
    private int actionCounter = 0;
    private int actionVariation = 0;

    @Override
    public String getName() {
        return "PatternVariation";
    }

    @Override
    public void init() {}

    @Override
    public void enable() {
        enabled = true;
        actionCounter = 0;
        regenerateVariation();
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

        actionCounter++;

        // Every 200 ticks, generate new action variation
        // This changes the macro's behavior pattern to avoid detection
        if (actionCounter >= 200) {
            regenerateVariation();
            actionCounter = 0;
        }
    }

    private void regenerateVariation() {
        // Random values between -50 and 50 to vary timing slightly
        actionVariation = (int)(Math.random() * 100) - 50;
    }

    public int getActionVariation() {
        return actionVariation;
    }
}
