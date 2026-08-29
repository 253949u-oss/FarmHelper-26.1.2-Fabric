# FarmHelper 26.1.2 Fabric - Dashboard & Config Guide

## Opening the Dashboard

### Method 1: Keyboard Shortcut (Recommended)
```
Press F6 to instantly open the FarmHelper Dashboard
```

### Method 2: Chat Commands
```
/fh config
/farmhelper config
```

---

## Dashboard Features

### 1. **Master Controls** (Top Section)

**Enabled/Disabled Toggle**
- Turns the entire mod on/off
- Shows: ✅ Enabled or ❌ Disabled

**HUD Toggle**
- Show/hide the harvest counter display
- Shows: HUD: ON or HUD: OFF

**Anti-Staff Toggle**
- Enable/disable anti-staff detection
- Shows: AntiStaff: ON or AntiStaff: OFF

---

### 2. **Farming Macros Section**

All 7 farming types with quick toggles:

#### 🌾 **Wheat Farming**
- Status indicator: ✅ ACTIVE or ❌ IDLE
- One-click enable/disable
- Auto-harvests mature wheat

#### 🥥 **Cocoa Bean Farming**
- Detects cocoa pods on jungle wood
- Efficient harvesting
- Status display

#### 🍄 **Mushroom Farming**
- Both brown & red mushrooms
- Fast scan interval
- Real-time status

#### 🍬 **Sugarcane Farming**
- Water-based farm compatible
- Stacks harvesting
- Active indicator

#### 🌶️ **Nether Wart Farming**
- Soul sand detection
- Nether-friendly
- Status tracking

#### 🥕 **Carrot & Potato Farming**
- Dual-crop support
- Combined harvesting
- Live status

#### 🐛 **Pest Farming**
- Automatic pest detection
- Kill counter tracking
- Real-time status

---

## Configuration File

### Location
```
./farmhelper_config.json
```

### Example Config Structure
```json
{
  "enabled": true,
  "showHUD": true,
  "antiStaffEnabled": true,
  "hudX": 10,
  "hudY": 10,
  "farmingSettings": {
    "WheatFarming": {
      "enabled": true,
      "scanRange": 10,
      "harvestCooldown": 5,
      "scanInterval": 10
    },
    "PestFarming": {
      "enabled": true,
      "scanRange": 15,
      "harvestCooldown": 8,
      "scanInterval": 4
    }
  },
  "antiStaffSettings": {
    "enabled": true,
    "checkMovementSpeed": true,
    "checkRotation": true,
    "checkBlockPlacement": true,
    "checkPacketFrequency": true,
    "checkMacroSignature": true,
    "movementSpeedThreshold": 31,
    "packetFrequencyThreshold": 100
  }
}
```

---

## Customization

### Adjusting Scan Range

Edit `farmhelper_config.json`:
```json
"WheatFarming": {
  "scanRange": 15  // Change from 10 to 15 blocks
}
```

### Changing Harvest Cooldown

```json
"CocoaBeanFarming": {
  "harvestCooldown": 8  // Slower harvesting (lower = faster)
}
```

### HUD Position

In dashboard or config:
```json
"hudX": 100,  // X coordinate on screen
"hudY": 50    // Y coordinate on screen
```

---

## Dashboard Button Layout

```
┌─────────────────────────────────────────────┐
│     FarmHelper Dashboard (F6)               │
├─────────────────────────────────────────────┤
│                                             │
│ ✅ Enabled    HUD: ON    AntiStaff: ON     │
│                                             │
│ Farming Macros:                             │
│ ✅ Wheat              ✅ ACTIVE              │
│ ❌ Cocoa              ❌ IDLE                │
│ ✅ Mushroom           ✅ ACTIVE              │
│ ✅ Sugarcane          ✅ ACTIVE              │
│ ❌ NetherWart         ❌ IDLE                │
│ ✅ CarrotPotato       ✅ ACTIVE              │
│ ✅ Pest               ✅ ACTIVE              │
│                                             │
├─────────────────────────────────────────────┤
│    Save              Back                   │
└─────────────────────────────────────────────┘
```

---

## Quick Actions

| Action | Key | Result |
|--------|-----|--------|
| Open Dashboard | F6 | Opens config GUI |
| Start Farming | Click Toggle | Enables macro |
| Stop Farming | Click Toggle | Disables macro |
| Save Config | Save Button | Persists settings |
| Reload Config | Restart Game | Loads saved settings |

---

## Troubleshooting

### Dashboard Won't Open
- Check if F6 is bound to another program
- Try `/fh config` command instead
- Verify mod is loaded (check logs)

### Changes Not Saving
- Make sure to click the "Save" button
- Check file permissions on `farmhelper_config.json`
- Ensure game folder is writable

### Macros Not Toggling
- Verify you're in-game (not in menu)
- Check if master "Enabled" toggle is ON
- Review anti-staff settings

---

## Advanced Configuration

### Anti-Staff Custom Thresholds

Edit detection sensitivity:
```json
"antiStaffSettings": {
  "movementSpeedThreshold": 31,      // Blocks/tick
  "packetFrequencyThreshold": 100    // Packets/sec
}
```

### Per-Farm Customization

Each farm can have unique settings:
```json
"farmingSettings": {
  "WheatFarming": {
    "scanRange": 12,
    "harvestCooldown": 6,
    "scanInterval": 8
  },
  "PestFarming": {
    "scanRange": 20,
    "harvestCooldown": 4,
    "scanInterval": 2
  }
}
```

---

## API Usage (For Developers)

```java
import com.jelly.farmhelperv2.FarmHelperFabric.FarmHelperAPI;

// Enable farming
FarmHelperAPI.enableFarming("WheatFarming");

// Check if active
boolean isActive = FarmHelperAPI.isFarmingActive("PestFarming");

// Get manager
var featureManager = FarmHelperAPI.getFeatureManager();
```

---

## Support

For issues or questions:
- Check `latest.log` in `.minecraft/logs/`
- Review `farmhelper_config.json` format
- Visit GitHub: https://github.com/253949u-oss/FarmHelper-26.1.2-Fabric
