package com.jelly.farmhelperv2.mixin.client;

import net.minecraft.client.KeyMapping;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(KeyMapping.class)
public interface MixinKeyBinding {
    @Accessor("pressed")
    void setPressed(boolean pressed);

    @Accessor("pressed")
    boolean isPressed();
}