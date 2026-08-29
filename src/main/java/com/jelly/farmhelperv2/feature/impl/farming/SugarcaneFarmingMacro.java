package com.jelly.farmhelperv2.feature.impl.farming;

import com.jelly.farmhelperv2.feature.IFeature;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.core.BlockPos;
import net.minecraft.client.Minecraft;

public class SugarcaneFarmingMacro implements IFeature {
    private boolean enabled = false;
    private final Minecraft mc = Minecraft.getInstance();
    private BlockPos targetBlock = null;
    private int tickCounter = 0;
    private int harvestCount = 0;
    private final int SCAN_RANGE = 10;
    private final int HARVEST_COOLDOWN = 5;
    private int harvestCooldown = 0;

    @Override
    public String getName() {
        return "SugarcaneFarming";
    }

    @Override
    public void init() {}

    @Override
    public void enable() {
        enabled = true;
        tickCounter = 0;
        harvestCount = 0;
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
        if (harvestCooldown > 0) harvestCooldown--;

        if (tickCounter % 10 == 0) {
            scanForSugarcane();
        }

        if (targetBlock != null && harvestCooldown == 0) {
            harvestSugarcane(targetBlock);
            harvestCooldown = HARVEST_COOLDOWN;
            harvestCount++;
        }
    }

    private void scanForSugarcane() {
        if (mc.player == null || mc.level == null) return;

        BlockPos playerPos = mc.player.blockPosition();
        double closestDistance = Double.MAX_VALUE;
        BlockPos closestSugarcane = null;

        for (int x = playerPos.getX() - SCAN_RANGE; x <= playerPos.getX() + SCAN_RANGE; x++) {
            for (int y = playerPos.getY() - 1; y <= playerPos.getY() + 3; y++) {
                for (int z = playerPos.getZ() - SCAN_RANGE; z <= playerPos.getZ() + SCAN_RANGE; z++) {
                    BlockPos pos = new BlockPos(x, y, z);
                    if (isSugarcane(pos)) {
                        double distance = mc.player.distanceToSqr(pos.getX(), pos.getY(), pos.getZ());
                        if (distance < closestDistance) {
                            closestDistance = distance;
                            closestSugarcane = pos;
                        }
                    }
                }
            }
        }

        targetBlock = closestSugarcane;
    }

    private boolean isSugarcane(BlockPos pos) {
        if (mc.level == null) return false;
        var block = mc.level.getBlockState(pos).getBlock();
        return block == Blocks.SUGAR_CANE;
    }

    private void harvestSugarcane(BlockPos pos) {
        if (mc.player == null || mc.gameMode == null) return;
        // Break top block of sugarcane
        mc.gameMode.destroyBlock(pos);
    }

    public int getHarvestCount() {
        return harvestCount;
    }
}
