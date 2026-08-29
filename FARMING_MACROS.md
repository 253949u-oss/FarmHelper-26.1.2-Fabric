# FarmHelper 26.1.2 Fabric - Farming Macros Documentation

## Available Farming Macros

### 1. Wheat Farming (`WheatFarmingMacro`)
- **Target Block:** Mature Wheat (Age 7)
- **Scan Range:** 10 blocks
- **Harvest Cooldown:** 5 ticks (0.25 seconds)
- **Auto-scan Interval:** Every 10 ticks
- **Features:**
  - Scans for mature wheat blocks
  - Prioritizes closest wheat
  - Automatic destruction
  - Harvest counter

### 2. Cocoa Bean Farming (`CocoaBeanFarmingMacro`)
- **Target Block:** Mature Cocoa Pods (Age 2)
- **Scan Range:** 12 blocks
- **Harvest Cooldown:** 6 ticks
- **Auto-scan Interval:** Every 8 ticks
- **Features:**
  - Detects cocoa pods on jungle wood
  - Age-based maturity detection
  - Vertical range: -3 to +2 from player

### 3. Mushroom Farming (`MushroomFarmingMacro`)
- **Target Blocks:** Brown & Red Mushrooms
- **Scan Range:** 10 blocks
- **Harvest Cooldown:** 4 ticks
- **Auto-scan Interval:** Every 6 ticks
- **Features:**
  - Detects both mushroom types
  - Fast harvest rate
  - Mycelium-friendly

### 4. Sugarcane Farming (`SugarcaneFarmingMacro`)
- **Target Block:** Sugarcane
- **Scan Range:** 10 blocks
- **Harvest Cooldown:** 5 ticks
- **Auto-scan Interval:** Every 10 ticks
- **Vertical Range:** -1 to +3 from player
- **Features:**
  - Harvests top blocks of sugarcane
  - Water-based farming support

### 5. Nether Wart Farming (`NetherWartFarmingMacro`)
- **Target Block:** Mature Nether Wart (Age 3)
- **Scan Range:** 10 blocks
- **Harvest Cooldown:** 5 ticks
- **Auto-scan Interval:** Every 10 ticks
- **Features:**
  - Soul sand detection
  - Age-based maturity
  - Nether farming support

### 6. Carrot & Potato Farming (`CarrotPotatoFarmingMacro`)
- **Target Blocks:** Carrots & Potatoes (Age 7)
- **Scan Range:** 10 blocks
- **Harvest Cooldown:** 4 ticks
- **Auto-scan Interval:** Every 8 ticks
- **Features:**
  - Dual crop support
  - Fast detection
  - Efficient harvesting

### 7. Pest Farming (`PestFarmingMacro`)
- **Target:** Pest entities
- **Scan Range:** 15 blocks
- **Attack Cooldown:** 8 ticks
- **Auto-scan Interval:** Every 4 ticks
- **Detected Pests:**
  - Flies
  - Mosquito
  - Worm
  - Mite
  - Slug
- **Features:**
  - Entity-based detection
  - Automatic aiming
  - Kill counter
  - Health-based detection fallback

## Usage Example

```java
FeatureManager manager = FeatureManager.getInstance();
FarmingRegistry.registerAllFarmingFeatures();

// Enable specific farming
manager.enableFeature("WheatFarming");
manager.enableFeature("PestFarming");

// During game tick
manager.tickAllFeatures();

// Disable when done
manager.disableFeature("WheatFarming");
```

## Configuration

Each macro has adjustable parameters:
- `SCAN_RANGE` - How far to search for crops
- `HARVEST_COOLDOWN` - Delay between harvests
- Scan interval in `tick()` method

## Performance Notes

- Macros scan every N ticks to reduce lag
- Closest target prioritization
- Efficient block state checking
- Anti-staff compliant movement patterns
