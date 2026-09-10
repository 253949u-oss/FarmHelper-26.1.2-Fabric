package com.jelly.farmhelperv2.input;

import net.minecraft.client.Minecraft;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFW;

import java.util.HashSet;
import java.util.Set;

public class KeyboardInputFilter {
    private static final Logger LOGGER = LogManager.getLogger("FarmHelper");
    private static KeyboardInputFilter INSTANCE;
    
    private boolean filterEnabled = false;
    private final Set<Integer> blockedKeys = new HashSet<>();
    private final Set<Integer> allowedKeys = new HashSet<>();
    
    // Default blocked keys (movement, interaction, etc)
    private static final int[] DEFAULT_BLOCKED_KEYS = {
        GLFW.GLFW_KEY_W, GLFW.GLFW_KEY_A, GLFW.GLFW_KEY_S, GLFW.GLFW_KEY_D, // WASD
        GLFW.GLFW_KEY_SPACE, // Jump
        GLFW.GLFW_KEY_LEFT_SHIFT, GLFW.GLFW_KEY_RIGHT_SHIFT, // Sneak
        GLFW.GLFW_KEY_LEFT_CONTROL, GLFW.GLFW_KEY_RIGHT_CONTROL, // Sprint
        GLFW.GLFW_KEY_E, // Inventory
        GLFW.GLFW_KEY_Q, // Drop
        GLFW.GLFW_KEY_F5, // Creative camera
    };
    
    // Always allowed keys (for safety)
    private static final int[] ALWAYS_ALLOWED_KEYS = {
        GLFW.GLFW_KEY_ESCAPE, // Can always open pause menu
        GLFW.GLFW_KEY_F3, // Debug menu (allowed)
    };

    private KeyboardInputFilter() {
        initializeBlockedKeys();
        initializeAllowedKeys();
    }

    public static KeyboardInputFilter getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new KeyboardInputFilter();
        }
        return INSTANCE;
    }

    private void initializeBlockedKeys() {
        for (int key : DEFAULT_BLOCKED_KEYS) {
            blockedKeys.add(key);
        }
    }

    private void initializeAllowedKeys() {
        for (int key : ALWAYS_ALLOWED_KEYS) {
            allowedKeys.add(key);
        }
    }

    /**
     * Enable keyboard input filtering
     */
    public void enableFilter() {
        if (!filterEnabled) {
            filterEnabled = true;
            LOGGER.info("Keyboard input filter enabled");
        }
    }

    /**
     * Disable keyboard input filtering
     */
    public void disableFilter() {
        if (filterEnabled) {
            filterEnabled = false;
            LOGGER.info("Keyboard input filter disabled");
        }
    }

    /**
     * Check if keyboard filter is active
     */
    public boolean isFilterActive() {
        return filterEnabled;
    }

    /**
     * Check if a key press should be blocked
     */
    public boolean shouldBlockKey(int key) {
        if (!filterEnabled) return false;
        
        try {
            // Always allowed keys bypass filter
            if (allowedKeys.contains(key)) {
                return false;
            }
            
            // Check if key is in blocked list
            return blockedKeys.contains(key);
        } catch (Exception e) {
            LOGGER.debug("Error checking key blocking for key: {}", key, e);
            return false;
        }
    }

    /**
     * Add a key to the blocked list
     */
    public void blockKey(int key) {
        try {
            if (!allowedKeys.contains(key)) {
                blockedKeys.add(key);
                LOGGER.debug("Blocked key: {}", key);
            }
        } catch (Exception e) {
            LOGGER.error("Error blocking key", e);
        }
    }

    /**
     * Remove a key from the blocked list
     */
    public void unblockKey(int key) {
        try {
            blockedKeys.remove(key);
            LOGGER.debug("Unblocked key: {}", key);
        } catch (Exception e) {
            LOGGER.error("Error unblocking key", e);
        }
    }

    /**
     * Clear all custom blocked keys (restore defaults)
     */
    public void resetBlockedKeys() {
        try {
            blockedKeys.clear();
            initializeBlockedKeys();
            LOGGER.info("Keyboard filter keys reset to defaults");
        } catch (Exception e) {
            LOGGER.error("Error resetting blocked keys", e);
        }
    }

    /**
     * Get all currently blocked keys
     */
    public Set<Integer> getBlockedKeys() {
        return new HashSet<>(blockedKeys);
    }
}
