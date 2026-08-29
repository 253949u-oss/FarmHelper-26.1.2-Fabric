package com.jelly.farmhelperv2.feature.impl.farming;

import com.jelly.farmhelperv2.feature.FeatureManager;

public class FarmingRegistry {
    public static void registerAllFarmingFeatures() {
        FeatureManager manager = FeatureManager.getInstance();
        
        manager.registerFeature(new WheatFarmingMacro());
        manager.registerFeature(new CocoaBeanFarmingMacro());
        manager.registerFeature(new MushroomFarmingMacro());
        manager.registerFeature(new SugarcaneFarmingMacro());
        manager.registerFeature(new NetherWartFarmingMacro());
        manager.registerFeature(new CarrotPotatoFarmingMacro());
        manager.registerFeature(new PestFarmingMacro());
    }
}
