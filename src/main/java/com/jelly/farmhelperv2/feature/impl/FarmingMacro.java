package com.jelly.farmhelperv2.feature.impl;

import com.jelly.farmhelperv2.feature.IFeature;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.core.BlockPos;
import net.minecraft.client.Minecraft;

public class FarmingMacro implements IFeature {
    private boolean enabled = false;
    private final Minecraft mc = Minecraft.getInstance();
    private BlockPos targetBlock = null;
    private int tickCounter = 0;

    @Override
    public String getName() {
        return "FarmingMacro";
    }

    @Override
    public void init() {}

    @Override
    public void enable() {
        enabled = true;
        tickCounter = 0;
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
        tickCounter++;
        scanForCrops();
        if (targetBlock != null) {
            pathToBlock(targetBlock);
        }
    }

    private void scanForCrops() {
        if (mc.player == null || mc.level == null) return;
        BlockPos playerPos = mc.player.blockPosition();
        int range = 8;
        for (int x = playerPos.getX() - range; x <= playerPos.getX() + range; x++) {
            for (int y = playerPos.getY() - 1; y <= playerPos.getY() + 1; y++) {
                for (int z = playerPos.getZ() - range; z <= playerPos.getZ() + range; z++) {
                    BlockPos pos = new BlockPos(x, y, z);
                    if (isHarvestableCrop(pos)) {
                        targetBlock = pos;
                        return;
                    }
                }
            }
        }
    }

    private boolean isHarvestableCrop(BlockPos pos) {
        if (mc.level == null) return false;
        var block = mc.level.getBlockState(pos).getBlock();
        return block == Blocks.WHEAT || block == Blocks.CARROTS || block == Blocks.POTATOES;
    }

    private void pathToBlock(BlockPos target) {
        if (mc.player == null) return;
    }
}