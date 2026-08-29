package com.jelly.farmhelperv2.feature.impl.farming;

import com.jelly.farmhelperv2.feature.IFeature;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.core.BlockPos;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.Creeper;

public class PestFarmingMacro implements IFeature {
    private boolean enabled = false;
    private final Minecraft mc = Minecraft.getInstance();
    private Entity targetPest = null;
    private int tickCounter = 0;
    private int pestKillCount = 0;
    private final int SCAN_RANGE = 15;
    private final int ATTACK_COOLDOWN = 8;
    private int attackCooldown = 0;
    private static final String[] PEST_TYPES = {
        "Flies", "Mosquito", "Worm", "Mite", "Slug"
    };

    @Override
    public String getName() {
        return "PestFarming";
    }

    @Override
    public void init() {}

    @Override
    public void enable() {
        enabled = true;
        tickCounter = 0;
        pestKillCount = 0;
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
        if (!enabled || mc.player == null || mc.level == null) return;

        tickCounter++;
        if (attackCooldown > 0) attackCooldown--;

        if (tickCounter % 4 == 0) {
            scanForPests();
        }

        if (targetPest != null && targetPest.isAlive() && attackCooldown == 0) {
            attackPest(targetPest);
            attackCooldown = ATTACK_COOLDOWN;
            pestKillCount++;
        }
    }

    private void scanForPests() {
        if (mc.player == null || mc.level == null) return;

        Entity closestPest = null;
        double closestDistance = Double.MAX_VALUE;

        // Scan all entities in range
        for (Entity entity : mc.level.getEntities(
                mc.player,
                mc.player.getBoundingBox().inflate(SCAN_RANGE)
        )) {
            if (isPestEntity(entity)) {
                double distance = mc.player.distanceToSqr(entity);
                if (distance < closestDistance) {
                    closestDistance = distance;
                    closestPest = entity;
                }
            }
        }

        targetPest = closestPest;
    }

    private boolean isPestEntity(Entity entity) {
        if (entity == null || entity.equals(mc.player)) return false;

        String entityName = entity.getName().getString().toLowerCase();
        String entityType = entity.getType().toString().toLowerCase();

        // Check against known pest types
        for (String pestType : PEST_TYPES) {
            if (entityName.contains(pestType.toLowerCase()) || 
                entityType.contains(pestType.toLowerCase())) {
                return true;
            }
        }

        // Check for hostile mobs with pest-like behavior
        return entity.isOnFire() || (entity.getHealth() < 5 && entity.getHealth() > 0);
    }

    private void attackPest(Entity pest) {
        if (mc.player == null || mc.gameMode == null) return;

        // Look at pest
        double dx = pest.getX() - mc.player.getX();
        double dy = pest.getEyeY() - mc.player.getEyeY();
        double dz = pest.getZ() - mc.player.getZ();
        double distance = Math.sqrt(dx * dx + dz * dz);

        float yaw = (float) Math.toDegrees(Math.atan2(dz, dx)) - 90f;
        float pitch = (float) -Math.toDegrees(Math.atan2(dy, distance));

        mc.player.setYRot(yaw);
        mc.player.setXRot(pitch);

        // Attack
        mc.gameMode.attack(pest);
    }

    public int getPestKillCount() {
        return pestKillCount;
    }
}
