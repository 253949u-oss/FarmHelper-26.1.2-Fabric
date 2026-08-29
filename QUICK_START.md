# FarmHelper 26.1.2 Fabric - Quick Start Guide

## Installation

1. Download the mod JAR from releases
2. Place in your `.minecraft/mods` folder
3. Ensure you have Fabric Loader 0.15.11+ and Fabric API
4. Launch Minecraft 26.1.2

## Quick Start Commands

### Help
```
/fh help
/farmhelper help
```

### Start Macros

**Wheat Farming:**
```
/fh start WheatFarming
/farmhelper start WheatFarming
```

**Cocoa Bean Farming:**
```
/fh start CocoaBeanFarming
```

**Mushroom Farming:**
```
/fh start MushroomFarming
```

**Sugarcane Farming:**
```
/fh start SugarcaneFarming
```

**Nether Wart Farming:**
```
/fh start NetherWartFarming
```

**Carrot & Potato Farming:**
```
/fh start CarrotPotatoFarming
```

**Pest Farming:**
```
/fh start PestFarming
```

### Stop Macros

**Stop Specific Macro:**
```
/fh stop WheatFarming
/fh stop CocoaBeanFarming
/fh stop MushroomFarming
/fh stop SugarcaneFarming
/fh stop NetherWartFarming
/fh stop CarrotPotatoFarming
/fh stop PestFarming
```

### List Available Macros
```
/fh list
/farmhelper list
```

### Check Status
```
/fh status
/farmhelper status
```

## Multi-Macro Setup

**Combine crops for efficiency:**
```
/fh start WheatFarming
/fh start CocoaBeanFarming
/fh start MushroomFarming
```

**Add pest control:**
```
/fh start PestFarming
```

**Stop all at once:**
```
/fh stop WheatFarming
/fh stop CocoaBeanFarming
/fh stop MushroomFarming
/fh stop PestFarming
```

## Hotkeys (Coming Soon)

Key bindings for quick macro toggle (will be added in next update).

## Features

✅ **Auto-Farming**
- Scans for mature crops
- Prioritizes closest target
- Configurable ranges

✅ **Pest Control**
- Detects and kills pests
- Auto-aiming
- Entity tracking

✅ **Anti-Staff Detection**
- Movement speed checking
- Rotation analysis
- Packet frequency monitoring
- Macro signature detection

✅ **Statistics HUD**
- Harvest counters
- Pest kill tracker
- Real-time status

## Troubleshooting

**Commands not working?**
- Make sure you're in chat mode (T key)
- Check the message format: `/fh start MacroName`
- Macro names are case-sensitive

**Macro not starting?**
- Check farm location is loaded
- Verify crops are mature
- Check anti-staff isn't triggering

**Performance issues?**
- Reduce number of active macros
- Increase scan cooldown intervals
- Check your frame rate

## Safety Tips

⚠️ **Use at your own risk:**
- Anti-staff detection helps but isn't 100% safe
- Don't AFK farm for extended periods
- Mix macro usage with manual farming
- Vary your patterns

## Configuration (Future)

Detailed configuration file support coming in v3.1

## Support

For issues, check:
- GitHub: https://github.com/253949u-oss/FarmHelper-26.1.2-Fabric
- Discord: [Coming Soon]
