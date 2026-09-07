package com.jelly.farmhelperv2.feature.impl.falseafs;

import com.jelly.farmhelperv2.feature.IFeature;
import net.minecraft.client.Minecraft;

public class MouseJitterFalseMacro implements IFeature {
    private boolean enabled = false;
    private final Minecraft mc = Minecraft.getInstance();
    private int jitterTickCounter = 0;
    private final int JITTER_INTERVAL = 30; // Apply jitter every 30 ticks
    private final float MAX_JITTER = 0.3f;

    @Override
    public String getName() {
        return "MouseJitter";
    }

    @Override
    public void init() {}

    @Override
    public void enable() {
        enabled = true;
        jitterTickCounter = 0;
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
        if (!enabled || mc.player == null) return;

        jitterTickCounter++;

        if (jitterTickCounter >= JITTER_INTERVAL) {
            // Apply small random mouse movements to simulate human imprecision
            float yawJitter = (float)((Math.random() - 0.5) * MAX_JITTER);
            float pitchJitter = (float)((Math.random() - 0.5) * MAX_JITTER);

            mc.player.setYRot(mc.player.getYRot() + yawJitter);
            mc.player.setXRot(mc.player.getXRot() + pitchJitter);

            jitterTickCounter = 0;
        }
    }
}
