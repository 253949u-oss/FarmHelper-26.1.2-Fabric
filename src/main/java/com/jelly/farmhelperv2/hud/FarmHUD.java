package com.jelly.farmhelperv2.hud;

import com.jelly.farmhelperv2.FarmHelperFabric;
import com.jelly.farmhelperv2.feature.FeatureManager;
import com.jelly.farmhelperv2.feature.impl.farming.*;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.Minecraft;

public class FarmHUD implements HudRenderCallback {
    private final Minecraft mc = Minecraft.getInstance();
    private final FeatureManager featureManager = FarmHelperFabric.getFeatureManager();
    private int displayX = 10;
    private int displayY = 10;
    private boolean enabled = true;

    @Override
    public void onHudRender(GuiGraphics guiGraphics, float partialTick) {
        if (!enabled || mc.player == null) return;

        int y = displayY;
        int color = 0x00FF00; // Green

        // Title
        guiGraphics.drawString(mc.font, "FarmHelper", displayX, y, 0xFFFFFF);
        y += 12;

        // Show active macros
        WheatFarmingMacro wheat = (WheatFarmingMacro) featureManager.getFeature("WheatFarming");
        if (wheat != null && wheat.isEnabled()) {
            guiGraphics.drawString(mc.font, "Wheat: " + wheat.getHarvestCount(), displayX, y, color);
            y += 10;
        }

        CocoaBeanFarmingMacro cocoa = (CocoaBeanFarmingMacro) featureManager.getFeature("CocoaBeanFarming");
        if (cocoa != null && cocoa.isEnabled()) {
            guiGraphics.drawString(mc.font, "Cocoa: " + cocoa.getHarvestCount(), displayX, y, color);
            y += 10;
        }

        MushroomFarmingMacro mushroom = (MushroomFarmingMacro) featureManager.getFeature("MushroomFarming");
        if (mushroom != null && mushroom.isEnabled()) {
            guiGraphics.drawString(mc.font, "Mushroom: " + mushroom.getHarvestCount(), displayX, y, color);
            y += 10;
        }

        SugarcaneFarmingMacro sugarcane = (SugarcaneFarmingMacro) featureManager.getFeature("SugarcaneFarming");
        if (sugarcane != null && sugarcane.isEnabled()) {
            guiGraphics.drawString(mc.font, "Sugarcane: " + sugarcane.getHarvestCount(), displayX, y, color);
            y += 10;
        }

        NetherWartFarmingMacro netherWart = (NetherWartFarmingMacro) featureManager.getFeature("NetherWartFarming");
        if (netherWart != null && netherWart.isEnabled()) {
            guiGraphics.drawString(mc.font, "Nether Wart: " + netherWart.getHarvestCount(), displayX, y, color);
            y += 10;
        }

        CarrotPotatoFarmingMacro carrotPotato = (CarrotPotatoFarmingMacro) featureManager.getFeature("CarrotPotatoFarming");
        if (carrotPotato != null && carrotPotato.isEnabled()) {
            guiGraphics.drawString(mc.font, "Carrot/Potato: " + carrotPotato.getHarvestCount(), displayX, y, color);
            y += 10;
        }

        PestFarmingMacro pest = (PestFarmingMacro) featureManager.getFeature("PestFarming");
        if (pest != null && pest.isEnabled()) {
            guiGraphics.drawString(mc.font, "Pests Killed: " + pest.getPestKillCount(), displayX, y, 0xFF5500);
            y += 10;
        }
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setPosition(int x, int y) {
        this.displayX = x;
        this.displayY = y;
    }
}
