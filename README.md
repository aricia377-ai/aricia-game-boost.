# ARICIA Game Boost

**Shizuku-powered system configuration & game boost app for Android (No Root required).**

Clean gray-toned Material 3 UI. Adaptive features for maximum compatibility across brands and models (Samsung, Xiaomi, Pixel, Oppo, Motorola, etc.).

> **Important**: This repository contains **source code and documentation only**.  
> No pre-built APK is provided. You must build the APK yourself using Android Studio.  
> I (the original author assistant) cannot generate or host binary APKs.

## Features
- Shizuku permission integration (shell/ADB level)
- Display over other apps + Do Not Disturb access requests
- Screen refresh rate (60 / 90 / 120 / 144 Hz) – adaptive, with device detection
- Screen resolution (720 / 1080 / 1440 + reset)
- Touch & swipe sensitivity (1x–5x)
- Network optimization (Private DNS etc.)
- CPU/GPU-inspired performance modes: Battery Saver / Balanced / Maximum
- Boost modes (General / Medium / Maximum) + RAM cleaning
- RAM Plus / virtual RAM controls (Samsung-focused, graceful on others)
- Frame generation toggle (disclaimer: real 2x FG not possible without root/modules)
- Universal compatibility with runtime device detection and fallbacks
- Big Reset buttons for safety

## Requirements
- Android 8.0+ (API 26+)
- Shizuku (official or compatible fork) installed and running
- User must grant Shizuku permission to the app

## Quick Start
1. Clone this repo
2. Open in Android Studio
3. Follow [docs/02-project-setup.md](docs/02-project-setup.md)
4. Build signed APK (see [docs/07-building-the-apk.md](docs/07-building-the-apk.md))

## Documentation (Topics)
- [01 – Overview & Limitations](docs/01-overview-and-limitations.md)
- [02 – Project Setup](docs/02-project-setup.md)
- [03 – Shizuku Integration (UserService)](docs/03-shizuku-integration.md)
- [04 – UI Design (Gray-toned Compose)](docs/04-ui-design.md)
- [05 – Features Implementation](docs/05-features-implementation.md)
- [06 – Device Compatibility](docs/06-device-compatibility.md)
- [07 – Building the APK](docs/07-building-the-apk.md)
- [08 – Testing & Troubleshooting](docs/08-testing-and-troubleshooting.md)
- [09 – Samsung Galaxy A15 5G Specific Notes](docs/09-samsung-a15-5g-notes.md)

## Warnings
- Wrong display commands can make the screen unusable until reset/reboot. Always test Reset first.
- Some features (real frame gen, deep CPU governors, full ZRAM control) require root or are OEM-locked.
- Aggressive battery savers on Xiaomi/Oppo/Vivo/Samsung can kill Shizuku – whitelist it.
- Not responsible for any damage. Use at your own risk.

## License
MIT (or choose your own)
