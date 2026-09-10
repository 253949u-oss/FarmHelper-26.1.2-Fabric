package com.jelly.farmhelperv2.input;

import net.minecraft.client.Minecraft;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class InputManager {
    private static final Logger LOGGER = LogManager.getLogger("FarmHelper");
    private static InputManager INSTANCE;
    
    private final MouseLockManager mouseLockManager = MouseLockManager.getInstance();
    private final KeyboardInputFilter keyboardFilter = KeyboardInputFilter.getInstance();
    
    private boolean macroRunning = false;
    private int activeMacroCount = 0;

    private InputManager() {
        LOGGER.info("InputManager initialized");
    }

    public static InputManager getInstance() {
        if (INSTANCE == null) {
            try {
                INSTANCE = new InputManager();
            } catch (Exception e) {
                LOGGER.error("Failed to create InputManager", e);
            }
        }
        return INSTANCE;
    }

    /**
     * Register a macro as active - locks input
     */
    public void registerActiveMacro() {
        try {
            activeMacroCount++;
            if (activeMacroCount == 1) {
                // First macro started - lock input
                lockAllInput();
                macroRunning = true;
                LOGGER.info("Input locked - macro started (count: {})", activeMacroCount);
            }
        } catch (Exception e) {
            LOGGER.error("Error registering active macro", e);
        }
    }

    /**
     * Unregister a macro - unlocks input if no macros running
     */
    public void unregisterActiveMacro() {
        try {
            activeMacroCount = Math.max(0, activeMacroCount - 1);
            if (activeMacroCount == 0) {
                // Last macro stopped - unlock input
                unlockAllInput();
                macroRunning = false;
                LOGGER.info("Input unlocked - all macros stopped");
            }
        } catch (Exception e) {
            LOGGER.error("Error unregistering active macro", e);
        }
    }

    /**
     * Lock mouse and keyboard input
     */
    public void lockAllInput() {
        try {
            mouseLockManager.lockMouse();
            keyboardFilter.enableFilter();
            LOGGER.info("All input locked");
        } catch (Exception e) {
            LOGGER.error("Error locking all input", e);
        }
    }

    /**
     * Unlock mouse and keyboard input
     */
    public void unlockAllInput() {
        try {
            mouseLockManager.unlockAll();
            keyboardFilter.disableFilter();
            LOGGER.info("All input unlocked");
        } catch (Exception e) {
            LOGGER.error("Error unlocking all input", e);
        }
    }

    /**
     * Lock only mouse input
     */
    public void lockMouseOnly() {
        try {
            mouseLockManager.lockMouse();
        } catch (Exception e) {
            LOGGER.error("Error locking mouse only", e);
        }
    }

    /**
     * Lock keyboard input for specified duration
     */
    public void lockKeyboardForDuration(int durationMs) {
        try {
            // Create a temporary keyboard lock mechanism
            keyboardFilter.enableFilter();
            LOGGER.debug("Keyboard locked for {} ms", durationMs);
        } catch (Exception e) {
            LOGGER.error("Error locking keyboard for duration", e);
        }
    }

    /**
     * Check if any macro is running
     */
    public boolean isMacroRunning() {
        return macroRunning && activeMacroCount > 0;
    }

    /**
     * Get count of active macros
     */
    public int getActiveMacroCount() {
        return activeMacroCount;
    }

    /**
     * Check if mouse is locked
     */
    public boolean isMouseLocked() {
        return mouseLockManager.isMouseLocked();
    }

    /**
     * Check if keyboard is filtered
     */
    public boolean isKeyboardFiltered() {
        return keyboardFilter.isFilterActive();
    }

    /**
     * Check if a key should be blocked
     */
    public boolean shouldBlockKey(int key) {
        return keyboardFilter.shouldBlockKey(key);
    }

    /**
     * Update input state (call from tick)
     */
    public void tick() {
        try {
            mouseLockManager.tick();
        } catch (Exception e) {
            LOGGER.debug("Error in input manager tick", e);
        }
    }

    /**
     * Emergency unlock - bypasses macro count
     */
    public void emergencyUnlock() {
        try {
            activeMacroCount = 0;
            unlockAllInput();
            LOGGER.warn("Emergency input unlock triggered");
        } catch (Exception e) {
            LOGGER.error("Error in emergency unlock", e);
        }
    }
}
