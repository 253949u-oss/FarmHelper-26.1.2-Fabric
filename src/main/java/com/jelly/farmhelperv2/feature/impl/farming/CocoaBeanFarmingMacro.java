package com.jelly.farmhelperv2.feature.impl.farming;

import com.jelly.farmhelperv2.feature.IFeature;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.core.BlockPos;
import net.minecraft.client.Minecraft;

public class CocoaBeanFarmingMacro implements IFeature {
    private boolean enabled = false;
    private final Minecraft mc = Minecraft.getInstance();
    private BlockPos targetBlock = null;
    private int tickCounter = 0;
    private int harvestCount = 0;
    private final int SCAN_RANGE = 12;
    private final int HARVEST_COOLDOWN = 6;
    private int harvestCooldown = 0;

    @Override
    public String getName() {
        return "CocoaBeanFarming";
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

        if (tickCounter % 8 == 0) {
            scanForCocoaBeans();
        }

        if (targetBlock != null && harvestCooldown == 0) {
            interactWithCocoaBeans(targetBlock);
            harvestCooldown = HARVEST_COOLDOWN;
            harvestCount++;
        }
    }

    private void scanForCocoaBeans() {
        if (mc.player == null || mc.level == null) return;

        BlockPos playerPos = mc.player.blockPosition();
        double closestDistance = Double.MAX_VALUE;
        BlockPos closestCocoaBeans = null;

        for (int x = playerPos.getX() - SCAN_RANGE; x <= playerPos.getX() + SCAN_RANGE; x++) {
            for (int y = playerPos.getY() - 3; y <= playerPos.getY() + 2; y++) {
                for (int z = playerPos.getZ() - SCAN_RANGE; z <= playerPos.getZ() + SCAN_RANGE; z++) {
                    BlockPos pos = new BlockPos(x, y, z);
                    if (isMatureCocoaBeans(pos)) {
                        double distance = mc.player.distanceToSqr(pos.getX(), pos.getY(), pos.getZ());
                        if (distance < closestDistance) {
                            closestDistance = distance;
                            closestCocoaBeans = pos;
                        }
                    }
                }
            }
        }

        targetBlock = closestCocoaBeans;
    }

    private boolean isMatureCocoaBeans(BlockPos pos) {
        if (mc.level == null) return false;
        var blockState = mc.level.getBlockState(pos);
        if (blockState.getBlock() != Blocks.COCOA) return false;
        var ageProperty = blockState.getProperties().stream()
                .filter(p -> p.getName().equals("age"))
                .findFirst();
        if (ageProperty.isPresent()) {
            Object age = blockState.getValue((net.minecraft.world.level.block.state.properties.IntegerProperty) ageProperty.get());
            return age.equals(2); // Mature cocoa beans
        }
        return false;
    }

    private void interactWithCocoaBeans(BlockPos pos) {
        if (mc.player == null || mc.gameMode == null) return;
        mc.gameMode.destroyBlock(pos);
    }

    public int getHarvestCount() {
        return harvestCount;
    }
}
