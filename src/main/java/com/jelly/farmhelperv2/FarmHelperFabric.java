package com.jelly.farmhelperv2;

import net.fabricmc.api.ClientModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FarmHelperFabric implements ClientModInitializer {
    public static final Logger LOGGER = LogManager.getLogger("FarmHelper");
    public static final String MOD_ID = "farmhelperv2";
    public static final String MOD_NAME = "FarmHelper";
    public static final String VERSION = "3.0.0";

    @Override
    public void onInitializeClient() {
        LOGGER.info("Initializing FarmHelper {} for Minecraft 26.1.2", VERSION);
    }
}