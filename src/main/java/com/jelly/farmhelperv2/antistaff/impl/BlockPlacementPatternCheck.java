package com.jelly.farmhelperv2.antistaff.impl;

import com.jelly.farmhelperv2.antistaff.IAntiStaffCheck;
import net.minecraft.core.BlockPos;
import java.util.ArrayList;
import java.util.List;

public class BlockPlacementPatternCheck implements IAntiStaffCheck {
    private final List<BlockPos> recentPlacements = new ArrayList<>();
    private boolean violating = false;
    private final int PATTERN_THRESHOLD = 5;
    private final int TIME_WINDOW = 20;

    @Override
    public String getCheckName() {
        return "BlockPlacementPattern";
    }

    @Override
    public boolean isViolating() {
        return violating;
    }

    @Override
    public String getReason() {
        return "Suspicious block placement pattern detected";
    }

    @Override
    public void tick() {
        if (recentPlacements.size() >= PATTERN_THRESHOLD) {
            violating = isPerfectGrid();
        }
    }

    public void onBlockPlace(BlockPos pos) {
        recentPlacements.add(pos);
        if (recentPlacements.size() > TIME_WINDOW) {
            recentPlacements.remove(0);
        }
    }

    private boolean isPerfectGrid() {
        if (recentPlacements.size() < 3) return false;
        int yLevel = recentPlacements.get(0).getY();
        for (BlockPos pos : recentPlacements) {
            if (pos.getY() != yLevel) return false;
        }
        for (BlockPos pos : recentPlacements) {
            if (pos.getX() % 1 != 0 || pos.getZ() % 1 != 0) return false;
        }
        return true;
    }
}