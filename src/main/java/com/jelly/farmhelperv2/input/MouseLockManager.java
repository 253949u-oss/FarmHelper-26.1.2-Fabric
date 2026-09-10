package com.jelly.farmhelperv2.input;

import net.minecraft.client.Minecraft;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFW;

public class MouseLockManager {
    private static final Logger LOGGER = LogManager.getLogger("FarmHelper");
    private static MouseLockManager INSTANCE;
    private final Minecraft mc = Minecraft.getInstance();
    
    private boolean mouseLocked = false;
    private boolean keyboardLocked = false;
    private double lastMouseX = 0;
    private double lastMouseY = 0;
    private boolean captureMouseInput = false;
    private long lockStartTime = 0;
    private int lockDuration = 0; // 0 = infinite lock

    private MouseLockManager() {
        LOGGER.info("MouseLockManager initialized");
    }

    public static MouseLockManager getInstance() {
        if (INSTANCE == null) {
            try {
                INSTANCE = new MouseLockManager();
            } catch (Exception e) {
                LOGGER.error("Failed to create MouseLockManager", e);
            }
        }
        return INSTANCE;
    }

    /**
     * Lock mouse input to prevent external interference
     */
    public void lockMouse() {
        try {
            if (mouseLocked) return;
            
            if (mc.screen != null) {
                LOGGER.warn("Cannot lock mouse while screen is open");
                return;
            }
            
            mouseLocked = true;
            captureMouseInput = true;
            lockStartTime = System.currentTimeMillis();
            
            // Store current mouse position
            long windowHandle = mc.getWindow().getWindow();
            double[] xpos = new double[1];
            double[] ypos = new double[1];
            GLFW.glfwGetCursorPos(windowHandle, xpos, ypos);
            lastMouseX = xpos[0];
            lastMouseY = ypos[0];
            
            LOGGER.info("Mouse locked at position ({}, {})", lastMouseX, lastMouseY);
        } catch (Exception e) {
            LOGGER.error("Error locking mouse", e);
            mouseLocked = false;
        }
    }

    /**
     * Lock keyboard input to prevent manual commands
     */
    public void lockKeyboard() {
        try {
            if (keyboardLocked) return;
            keyboardLocked = true;
            LOGGER.info("Keyboard locked");
        } catch (Exception e) {
            LOGGER.error("Error locking keyboard", e);
        }
    }

    /**
     * Unlock both mouse and keyboard input
     */
    public void unlockAll() {
        try {
            if (mouseLocked) {
                mouseLocked = false;
                captureMouseInput = false;
                LOGGER.info("Mouse unlocked");
            }
            if (keyboardLocked) {
                keyboardLocked = false;
                LOGGER.info("Keyboard unlocked");
            }
        } catch (Exception e) {
            LOGGER.error("Error unlocking input", e);
        }
    }

    /**
     * Lock mouse for a specific duration (milliseconds)
     */
    public void lockMouseForDuration(int durationMs) {
        try {
            lockDuration = durationMs;
            lockMouse();
        } catch (Exception e) {
            LOGGER.error("Error locking mouse for duration", e);
        }
    }

    /**
     * Check if lock duration has expired
     */
    public void updateLockDuration() {
        try {
            if (!mouseLocked || lockDuration <= 0) return;
            
            long elapsedTime = System.currentTimeMillis() - lockStartTime;
            if (elapsedTime >= lockDuration) {
                unlockAll();
            }
        } catch (Exception e) {
            LOGGER.debug("Error updating lock duration", e);
        }
    }

    /**
     * Restore mouse to locked position (called from mouse move callback)
     */
    public void validateMousePosition() {
        try {
            if (!mouseLocked || !captureMouseInput) return;
            
            long windowHandle = mc.getWindow().getWindow();
            double[] xpos = new double[1];
            double[] ypos = new double[1];
            GLFW.glfwGetCursorPos(windowHandle, xpos, ypos);
            
            // If mouse moved beyond threshold, reset to locked position
            double deltaX = Math.abs(xpos[0] - lastMouseX);
            double deltaY = Math.abs(ypos[0] - lastMouseY);
            
            if (deltaX > 1.0 || deltaY > 1.0) {
                GLFW.glfwSetCursorPos(windowHandle, lastMouseX, lastMouseY);
                LOGGER.debug("Mouse repositioned: moved by ({}, {})", deltaX, deltaY);
            }
        } catch (Exception e) {
            LOGGER.debug("Error validating mouse position", e);
        }
    }

    /**
     * Check if mouse is locked
     */
    public boolean isMouseLocked() {
        return mouseLocked;
    }

    /**
     * Check if keyboard is locked
     */
    public boolean isKeyboardLocked() {
        return keyboardLocked;
    }

    /**
     * Get locked mouse position
     */
    public double[] getLockedPosition() {
        return new double[]{lastMouseX, lastMouseY};
    }

    /**
     * Update mouse lock status (call from tick)
     */
    public void tick() {
        try {
            updateLockDuration();
            validateMousePosition();
        } catch (Exception e) {
            LOGGER.debug("Error in mouse lock manager tick", e);
        }
    }
}
