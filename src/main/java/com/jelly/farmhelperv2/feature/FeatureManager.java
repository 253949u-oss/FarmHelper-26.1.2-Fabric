package com.jelly.farmhelperv2.feature;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FeatureManager {
    private static FeatureManager INSTANCE;
    private final Map<String, IFeature> features = new HashMap<>();
    private final List<IFeature> enabledFeatures = new ArrayList<>();

    private FeatureManager() {}

    public static FeatureManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new FeatureManager();
        }
        return INSTANCE;
    }

    public void registerFeature(IFeature feature) {
        feature.init();
        features.put(feature.getName(), feature);
    }

    public void enableFeature(String name) {
        IFeature feature = features.get(name);
        if (feature != null && !feature.isEnabled()) {
            feature.enable();
            enabledFeatures.add(feature);
        }
    }

    public void disableFeature(String name) {
        IFeature feature = features.get(name);
        if (feature != null && feature.isEnabled()) {
            feature.disable();
            enabledFeatures.remove(feature);
        }
    }

    public IFeature getFeature(String name) {
        return features.get(name);
    }

    public void tickAllFeatures() {
        for (IFeature feature : enabledFeatures) {
            if (feature.isEnabled()) {
                feature.tick();
            }
        }
    }
}