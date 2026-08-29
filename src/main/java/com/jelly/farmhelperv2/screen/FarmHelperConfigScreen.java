package com.jelly.farmhelperv2.screen;

import com.jelly.farmhelperv2.config.ConfigManager;
import com.jelly.farmhelperv2.config.FarmHelperConfig;
import com.jelly.farmhelperv2.FarmHelperFabric;
import com.jelly.farmhelperv2.feature.FeatureManager;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;

public class FarmHelperConfigScreen extends Screen {
    private final Screen previousScreen;
    private final ConfigManager configManager = ConfigManager.getInstance();
    private final FeatureManager featureManager = FarmHelperFabric.getFeatureManager();
    private FarmHelperConfig config;
    private int scrollOffset = 0;
    private static final int BUTTON_WIDTH = 200;
    private static final int BUTTON_HEIGHT = 20;
    private static final int ENTRY_HEIGHT = 30;

    public FarmHelperConfigScreen(Screen previousScreen) {
        super(Component.literal("FarmHelper Dashboard"));
        this.previousScreen = previousScreen;
        this.config = configManager.getConfig();
    }

    @Override
    protected void init() {
        super.init();
        this.clearWidgets();

        // Back button
        this.addRenderableWidget(Button.builder(Component.literal("Back"), (btn) -> {
            this.minecraft.setScreen(previousScreen);
        }).bounds(this.width - 110, this.height - 30, 100, 20).build());

        // Save button
        this.addRenderableWidget(Button.builder(Component.literal("Save"), (btn) -> {
            configManager.updateConfig(config);
            this.minecraft.player.displayClientMessage(
                    Component.literal("Config saved!"), false);
        }).bounds(10, this.height - 30, 100, 20).build());

        // Master toggle
        this.addRenderableWidget(Button.builder(
                Component.literal(config.enabled ? "\u2705 Enabled" : "\u274c Disabled"),
                (btn) -> {
                    config.enabled = !config.enabled;
                    btn.setMessage(Component.literal(config.enabled ? "\u2705 Enabled" : "\u274c Disabled"));
                }
        ).bounds(10, 40, 100, 20).build());

        // HUD toggle
        this.addRenderableWidget(Button.builder(
                Component.literal(config.showHUD ? "HUD: ON" : "HUD: OFF"),
                (btn) -> {
                    config.showHUD = !config.showHUD;
                    btn.setMessage(Component.literal(config.showHUD ? "HUD: ON" : "HUD: OFF"));
                }
        ).bounds(120, 40, 100, 20).build());

        // Anti-Staff toggle
        this.addRenderableWidget(Button.builder(
                Component.literal(config.antiStaffEnabled ? "AntiStaff: ON" : "AntiStaff: OFF"),
                (btn) -> {
                    config.antiStaffEnabled = !config.antiStaffEnabled;
                    btn.setMessage(Component.literal(config.antiStaffEnabled ? "AntiStaff: ON" : "AntiStaff: OFF"));
                }
        ).bounds(230, 40, 130, 20).build());

        // Farm macros section
        int yOffset = 80;
        String[] farmTypes = {
                "WheatFarming", "CocoaBeanFarming", "MushroomFarming",
                "SugarcaneFarming", "NetherWartFarming", "CarrotPotatoFarming", "PestFarming"
        };

        for (String farmType : farmTypes) {
            if (yOffset > this.height - 100) break;

            // Farm toggle button
            boolean isEnabled = featureManager.getFeature(farmType).isEnabled();
            String displayName = farmType.replace("Farming", "");
            
            this.addRenderableWidget(Button.builder(
                    Component.literal((isEnabled ? "\u2705 " : "\u274c ") + displayName),
                    (btn) -> {
                        if (isEnabled) {
                            featureManager.disableFeature(farmType);
                        } else {
                            featureManager.enableFeature(farmType);
                        }
                        btn.setMessage(Component.literal(
                                (!isEnabled ? "\u2705 " : "\u274c ") + displayName));
                    }
            ).bounds(10, yOffset, 120, 20).build());

            yOffset += 30;
        }
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(guiGraphics);
        
        // Title
        guiGraphics.drawCenteredString(this.font, "FarmHelper Dashboard", this.width / 2, 15, 0xFFFFFF);
        
        // General settings label
        guiGraphics.drawString(this.font, "General Settings:", 10, 30, 0xAAAAAA);
        
        // Farming section label
        guiGraphics.drawString(this.font, "Farming Macros:", 10, 70, 0xAAAAAA);
        
        // Render farming status
        String[] farmTypes = {
                "WheatFarming", "CocoaBeanFarming", "MushroomFarming",
                "SugarcaneFarming", "NetherWartFarming", "CarrotPotatoFarming", "PestFarming"
        };
        
        int yOffset = 100;
        for (String farmType : farmTypes) {
            if (yOffset > this.height - 100) break;
            
            var feature = featureManager.getFeature(farmType);
            if (feature != null) {
                String status = feature.isEnabled() ? "\u2705 ACTIVE" : "\u274c IDLE";
                int color = feature.isEnabled() ? 0x00FF00 : 0xFF5555;
                guiGraphics.drawString(this.font, status, 150, yOffset + 5, color);
            }
            yOffset += 30;
        }
        
        super.render(guiGraphics, mouseX, mouseY, partialTick);
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return true;
    }
}
