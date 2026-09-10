package com.jelly.farmhelperv2.hud;

import com.jelly.farmhelperv2.FarmHelperFabric;
import com.jelly.farmhelperv2.feature.FeatureManager;
import com.jelly.farmhelperv2.feature.impl.farming.*;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.Minecraft;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FarmHUD implements HudRenderCallback {
    private static final Logger LOGGER = LogManager.getLogger("FarmHelper");
    private final Minecraft mc = Minecraft.getInstance();
    private int displayX = 10;
    private int displayY = 10;
    private boolean enabled = true;

    @Override
    public void onHudRender(GuiGraphics guiGraphics, float partialTick) {
        try {
            if (!enabled || mc.player == null) return;

            // Get feature manager safely
            FeatureManager featureManager = FarmHelperFabric.getFeatureManager();
            if (featureManager == null) {
                LOGGER.debug("FeatureManager is null in HUD render");
                return;
            }

            int y = displayY;
            int color = 0x00FF00; // Green

            // Title
            if (mc.font != null && guiGraphics != null) {
                guiGraphics.drawString(mc.font, "FarmHelper", displayX, y, 0xFFFFFF);
                y += 12;
            }

            // Show active macros with null checks
            try {
                WheatFarmingMacro wheat = (WheatFarmingMacro) featureManager.getFeature("WheatFarming");
                if (wheat != null && wheat.isEnabled()) {
                    guiGraphics.drawString(mc.font, "Wheat: " + wheat.getHarvestCount(), displayX, y, color);
                    y += 10;
                }
            } catch (Exception e) {
                LOGGER.debug("Error rendering wheat farming HUD", e);
            }

            try {
                CocoaBeanFarmingMacro cocoa = (CocoaBeanFarmingMacro) featureManager.getFeature("CocoaBeanFarming");
                if (cocoa != null && cocoa.isEnabled()) {
                    guiGraphics.drawString(mc.font, "Cocoa: " + cocoa.getHarvestCount(), displayX, y, color);
                    y += 10;
                }
            } catch (Exception e) {
                LOGGER.debug("Error rendering cocoa bean farming HUD", e);
            }

            try {
                MushroomFarmingMacro mushroom = (MushroomFarmingMacro) featureManager.getFeature("MushroomFarming");
                if (mushroom != null && mushroom.isEnabled()) {
                    guiGraphics.drawString(mc.font, "Mushroom: " + mushroom.getHarvestCount(), displayX, y, color);
                    y += 10;
                }
            } catch (Exception e) {
                LOGGER.debug("Error rendering mushroom farming HUD", e);
            }

            try {
                SugarcaneFarmingMacro sugarcane = (SugarcaneFarmingMacro) featureManager.getFeature("SugarcaneFarming");
                if (sugarcane != null && sugarcane.isEnabled()) {
                    guiGraphics.drawString(mc.font, "Sugarcane: " + sugarcane.getHarvestCount(), displayX, y, color);
                    y += 10;
                }
            } catch (Exception e) {
                LOGGER.debug("Error rendering sugarcane farming HUD", e);
            }

            try {
                NetherWartFarmingMacro netherWart = (NetherWartFarmingMacro) featureManager.getFeature("NetherWartFarming");
                if (netherWart != null && netherWart.isEnabled()) {
                    guiGraphics.drawString(mc.font, "Nether Wart: " + netherWart.getHarvestCount(), displayX, y, color);
                    y += 10;
                }
            } catch (Exception e) {
                LOGGER.debug("Error rendering nether wart farming HUD", e);
            }

            try {
                CarrotPotatoFarmingMacro carrotPotato = (CarrotPotatoFarmingMacro) featureManager.getFeature("CarrotPotatoFarming");
                if (carrotPotato != null && carrotPotato.isEnabled()) {
                    guiGraphics.drawString(mc.font, "Carrot/Potato: " + carrotPotato.getHarvestCount(), displayX, y, color);
                    y += 10;
                }
            } catch (Exception e) {
                LOGGER.debug("Error rendering carrot/potato farming HUD", e);
            }

            try {
                PestFarmingMacro pest = (PestFarmingMacro) featureManager.getFeature("PestFarming");
                if (pest != null && pest.isEnabled()) {
                    guiGraphics.drawString(mc.font, "Pest: " + pest.getHarvestCount(), displayX, y, color);
                }
            } catch (Exception e) {
                LOGGER.debug("Error rendering pest farming HUD", e);
            }
        } catch (Exception e) {
            LOGGER.error("Unexpected error in HUD render", e);
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
