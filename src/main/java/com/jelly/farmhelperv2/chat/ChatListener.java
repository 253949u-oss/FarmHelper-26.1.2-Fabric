package com.jelly.farmhelperv2.chat;

import com.jelly.farmhelperv2.FarmHelperFabric;
import com.jelly.farmhelperv2.command.CommandManager;
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents;
import net.minecraft.network.chat.Component;

public class ChatListener {
    public static void register() {
        // Listen for client chat messages
        ClientReceiveMessageEvents.CHAT.register((message, overlay) -> {
            String text = message.getString();
            
            // Check if it's a command
            if (text.startsWith("/fh ") || text.startsWith("/farmhelper ")) {
                CommandManager commandManager = FarmHelperFabric.getCommandManager();
                commandManager.handleChatCommand(text);
            }
        });
    }
}
