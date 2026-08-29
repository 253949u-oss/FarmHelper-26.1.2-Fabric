package com.jelly.farmhelperv2.feature.impl.farming;

import com.jelly.farmhelperv2.feature.IFeature;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.core.BlockPos;
import net.minecraft.client.Minecraft;

public class NetherWartFarmingMacro implements IFeature {
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
        return "NetherWartFarming";
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
            scanForNetherWart();
        }

        if (targetBlock != null && harvestCooldown == 0) {
            interactWithNetherWart(targetBlock);
            harvestCooldown = HARVEST_COOLDOWN;
            harvestCount++;
        }
    }

    private void scanForNetherWart() {
        if (mc.player == null || mc.level == null) return;

        BlockPos playerPos = mc.player.blockPosition();
        double closestDistance = Double.MAX_VALUE;
        BlockPos closestWart = null;

        for (int x = playerPos.getX() - SCAN_RANGE; x <= playerPos.getX() + SCAN_RANGE; x++) {
            for (int y = playerPos.getY() - 1; y <= playerPos.getY() + 2; y++) {
                for (int z = playerPos.getZ() - SCAN_RANGE; z <= playerPos.getZ() + SCAN_RANGE; z++) {
                    BlockPos pos = new BlockPos(x, y, z);
                    if (isMatureNetherWart(pos)) {
                        double distance = mc.player.distanceToSqr(pos.getX(), pos.getY(), pos.getZ());
                        if (distance < closestDistance) {
                            closestDistance = distance;
                            closestWart = pos;
                        }
                    }
                }
            }
        }

        targetBlock = closestWart;
    }

    private boolean isMatureNetherWart(BlockPos pos) {
        if (mc.level == null) return false;
        var blockState = mc.level.getBlockState(pos);
        if (blockState.getBlock() != Blocks.NETHER_WART) return false;
        var ageProperty = blockState.getProperties().stream()
                .filter(p -> p.getName().equals("age"))
                .findFirst();
        if (ageProperty.isPresent()) {
            Object age = blockState.getValue((net.minecraft.world.level.block.state.properties.IntegerProperty) ageProperty.get());
            return age.equals(3); // Mature nether wart
        }
        return false;
    }

    private void interactWithNetherWart(BlockPos pos) {
        if (mc.player == null || mc.gameMode == null) return;
        mc.gameMode.destroyBlock(pos);
    }

    public int getHarvestCount() {
        return harvestCount;
    }
}
