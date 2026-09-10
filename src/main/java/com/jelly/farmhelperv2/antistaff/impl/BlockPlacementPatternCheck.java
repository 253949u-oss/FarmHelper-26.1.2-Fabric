package com.jelly.farmhelperv2.antistaff.impl;

import com.jelly.farmhelperv2.antistaff.IAntiStaffCheck;
import net.minecraft.core.BlockPos;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class BlockPlacementPatternCheck implements IAntiStaffCheck {
    private static final Logger LOGGER = LogManager.getLogger("FarmHelper");
    private final List<BlockPos> recentPlacements = new ArrayList<>();
    private final int TIME_WINDOW = 200;
    private final int PATTERN_THRESHOLD = 20;
    private boolean violating = false;

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
        try {
            if (recentPlacements != null && recentPlacements.size() >= PATTERN_THRESHOLD) {
                violating = isPerfectGrid();
            }
        } catch (Exception e) {
            LOGGER.debug("Error in block placement pattern check tick", e);
        }
    }

    public void onBlockPlace(BlockPos pos) {
        try {
            if (pos == null) return;
            if (recentPlacements == null) return;
            
            recentPlacements.add(pos);
            if (recentPlacements.size() > TIME_WINDOW) {
                recentPlacements.remove(0);
            }
        } catch (Exception e) {
            LOGGER.debug("Error in block placement handler", e);
        }
    }

    private boolean isPerfectGrid() {
        try {
            if (recentPlacements == null || recentPlacements.size() < 2) return false;
            
            // Simple check for perfectly aligned placements
            for (int i = 1; i < Math.min(recentPlacements.size(), 10); i++) {
                BlockPos prev = recentPlacements.get(i - 1);
                BlockPos curr = recentPlacements.get(i);
                
                if (prev == null || curr == null) continue;
                
                int dx = Math.abs(curr.getX() - prev.getX());
                int dy = Math.abs(curr.getY() - prev.getY());
                int dz = Math.abs(curr.getZ() - prev.getZ());
                
                // All placements at same level with uniform spacing
                if (dy != 0 || (dx != 1 && dz != 1)) {
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            LOGGER.debug("Error in isPerfectGrid check", e);
            return false;
        }
    }
}
