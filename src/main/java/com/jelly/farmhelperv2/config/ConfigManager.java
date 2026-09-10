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
            try {
                INSTANCE = new ConfigManager();
            } catch (Exception e) {
                LOGGER.error("Failed to create ConfigManager instance", e);
                // Return a fallback instance
                INSTANCE = new ConfigManager();
            }
        }
        return INSTANCE;
    }

    private void loadConfig() {
        try {
            if (configFile.exists()) {
                try (FileReader reader = new FileReader(configFile)) {
                    config = GSON.fromJson(reader, FarmHelperConfig.class);
                    if (config == null) {
                        LOGGER.warn("Config loaded as null, creating default");
                        config = new FarmHelperConfig();
                    }
                    LOGGER.info("Config loaded successfully");
                }
            } else {
                config = new FarmHelperConfig();
                try {
                    saveConfig();
                    LOGGER.info("Created default config");
                } catch (Exception e) {
                    LOGGER.error("Failed to save default config", e);
                }
            }
        } catch (IOException e) {
            LOGGER.error("Failed to load config", e);
            config = new FarmHelperConfig();
        } catch (Exception e) {
            LOGGER.error("Unexpected error loading config", e);
            config = new FarmHelperConfig();
        }
    }

    public void saveConfig() {
        try {
            if (config == null) {
                LOGGER.warn("Cannot save null config, creating default");
                config = new FarmHelperConfig();
            }
            
            try (FileWriter writer = new FileWriter(configFile)) {
                GSON.toJson(config, writer);
                LOGGER.info("Config saved successfully");
            }
        } catch (IOException e) {
            LOGGER.error("Failed to save config", e);
        } catch (Exception e) {
            LOGGER.error("Unexpected error saving config", e);
        }
    }

    public FarmHelperConfig getConfig() {
        if (config == null) {
            LOGGER.warn("Config is null, returning new default config");
            config = new FarmHelperConfig();
        }
        return config;
    }

    public void updateConfig(FarmHelperConfig newConfig) {
        try {
            if (newConfig == null) {
                LOGGER.warn("Attempted to update with null config");
                return;
            }
            this.config = newConfig;
            saveConfig();
        } catch (Exception e) {
            LOGGER.error("Error updating config", e);
        }
    }
}
