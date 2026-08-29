package com.jelly.farmhelperv2.feature.impl;

import com.jelly.farmhelperv2.feature.IFeature;
import net.minecraft.core.BlockPos;
import net.minecraft.client.Minecraft;

public class RotationHandler implements IFeature {
    private boolean enabled = false;
    private final Minecraft mc = Minecraft.getInstance();
    private float targetYaw = 0f;
    private float targetPitch = 0f;
    private float rotationSpeed = 0.5f;

    @Override
    public String getName() {
        return "RotationHandler";
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
        if (!enabled || mc.player == null) return;
        mc.player.setYRot(mc.player.getYRot() + (targetYaw - mc.player.getYRot()) * rotationSpeed);
        mc.player.setXRot(mc.player.getXRot() + (targetPitch - mc.player.getXRot()) * rotationSpeed);
    }

    public void lookAtBlock(BlockPos pos) {
        if (mc.player == null) return;
        double dx = pos.getX() + 0.5 - mc.player.getX();
        double dy = pos.getY() + 0.5 - mc.player.getY() - 1.62;
        double dz = pos.getZ() + 0.5 - mc.player.getZ();
        double distance = Math.sqrt(dx * dx + dz * dz);
        targetYaw = (float) Math.toDegrees(Math.atan2(dz, dx)) - 90f;
        targetPitch = (float) -Math.toDegrees(Math.atan2(dy, distance));
    }
}