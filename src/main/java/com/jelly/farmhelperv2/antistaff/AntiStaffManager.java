package com.jelly.farmhelperv2.antistaff;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import java.util.ArrayList;
import java.util.List;

public class AntiStaffManager {
    private static AntiStaffManager INSTANCE;
    private final Minecraft mc = Minecraft.getInstance();
    private final List<IAntiStaffCheck> checks = new ArrayList<>();
    private boolean antiStaffEnabled = true;

    private AntiStaffManager() {
        registerChecks();
    }

    public static AntiStaffManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new AntiStaffManager();
        }
        return INSTANCE;
    }

    private void registerChecks() {
        checks.add(new com.jelly.farmhelperv2.antistaff.impl.MovementSpeedCheck());
        checks.add(new com.jelly.farmhelperv2.antistaff.impl.RotationDetectionCheck());
        checks.add(new com.jelly.farmhelperv2.antistaff.impl.BlockPlacementPatternCheck());
        checks.add(new com.jelly.farmhelperv2.antistaff.impl.PacketFrequencyCheck());
        checks.add(new com.jelly.farmhelperv2.antistaff.impl.MacroSignatureCheck());
    }

    public void updateChecks() {
        if (!antiStaffEnabled || mc.player == null) return;
        for (IAntiStaffCheck check : checks) {
            check.tick();
            if (check.isViolating()) {
                handleViolation(check);
            }
        }
    }

    private void handleViolation(IAntiStaffCheck check) {
        String warning = "[AntiStaff] " + check.getCheckName() + " triggered: " + check.getReason();
        if (mc.player != null) {
            mc.player.displayClientMessage(Component.literal(warning), true);
        }
    }

    public void setAntiStaffEnabled(boolean enabled) {
        antiStaffEnabled = enabled;
    }
}