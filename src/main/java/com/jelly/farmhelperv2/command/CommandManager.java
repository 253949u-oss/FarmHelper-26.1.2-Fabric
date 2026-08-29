package com.jelly.farmhelperv2.command;

import net.minecraft.client.Minecraft;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CommandManager {
    private static final Logger LOGGER = LogManager.getLogger("FarmHelper");
    private final FarmHelperCommand farmHelperCommand = new FarmHelperCommand();

    public void handleChatCommand(String input) {
        // Check if message starts with /fh or /farmhelper
        if (!input.startsWith("/fh ") && !input.startsWith("/farmhelper ")) {
            return;
        }

        // Remove the command prefix
        String commandInput = input.startsWith("/fh ") 
            ? input.substring(4) 
            : input.substring(12);

        // Parse arguments
        String[] args = commandInput.trim().split(" ");
        
        if (args.length == 0) {
            return;
        }

        try {
            farmHelperCommand.execute(args);
        } catch (Exception e) {
            LOGGER.error("Error executing command", e);
        }
    }
}
