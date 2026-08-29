package com.jelly.farmhelperv2.feature;

public interface IFeature {
    String getName();
    void init();
    void enable();
    void disable();
    boolean isEnabled();
    void tick();
}