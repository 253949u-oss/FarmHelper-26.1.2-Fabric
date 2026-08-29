package com.jelly.farmhelperv2.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ConfigManager {
    private static final Logger LOGGER = LogManager.getLogger("FarmHelper");
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static ConfigManager INSTANCE;
    
    private FarmHelperConfig config;
    private final File configFile;

    private ConfigManager() {
        this.configFile = new File("farmhelper_config.json");
        loadConfig();
    }

    public static ConfigManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ConfigManager();
        }
        return INSTANCE;
    }

    private void loadConfig() {
        try {
            if (configFile.exists()) {
                try (FileReader reader = new FileReader(configFile)) {
                    config = GSON.fromJson(reader, FarmHelperConfig.class);
                    LOGGER.info("Config loaded successfully");
                }
            } else {
                config = new FarmHelperConfig();
                saveConfig();
                LOGGER.info("Created default config");
            }
        } catch (IOException e) {
            LOGGER.error("Failed to load config", e);
            config = new FarmHelperConfig();
        }
    }

    public void saveConfig() {
        try (FileWriter writer = new FileWriter(configFile)) {
            GSON.toJson(config, writer);
            LOGGER.info("Config saved successfully");
        } catch (IOException e) {
            LOGGER.error("Failed to save config", e);
        }
    }

    public FarmHelperConfig getConfig() {
        return config;
    }

    public void updateConfig(FarmHelperConfig newConfig) {
        this.config = newConfig;
        saveConfig();
    }
}
