<p align="center">
  <img src="docs/images/icon.svg" width="120" alt="CarDeck Neo icon">
</p>

<h1 align="center">CarDeck Neo</h1>

<p align="center">
  Connect an Android phone to your car over wireless CarPlay and get a car launcher with maps, music and apps<br>
  <b>English</b> · <a href="README.zh-TW.md">繁體中文</a> · <a href="README.zh-CN.md">简体中文</a>
</p>

---

CarDeck Neo is a UI fork of [CarDeck](https://github.com/shaonialife/cardeck). The phone (no root needed) acts as a CarPlay sender: it streams its own car launcher and the apps on your phone to a head unit that supports wireless CarPlay, and touches on the head unit are sent back to the phone.

> **Unofficial project**: not affiliated with Apple Inc. or any car manufacturer, not authorized or certified, and **not an MFi product**. CarPlay and AirPlay are trademarks of Apple Inc., mentioned only to describe compatibility.
>
> **Driving safety**: connect and change settings only while parked. The connection can drop while driving, so keep the factory navigation as a safety backup. Full terms in [DISCLAIMER.md](DISCLAIMER.md).

## Screenshots

### Head unit

<p align="center">
  <img src="docs/screenshots/car-1920x720.png" alt="Dashboard at 1920×720">
  <br><sub>Dashboard: the map on the left is directly usable, music controls on the right (1920×720)</sub>
</p>

<p align="center">
  <img src="docs/screenshots/car-gemini-1920x720.png" alt="Gemini on the head unit">
  <br><sub>Say "Hey Google" and Gemini appears right on the head unit, touch included (experimental)</sub>
</p>

### Different resolutions and aspect ratios

The layout adapts to the head unit's resolution: wide screens split side by side, narrow screens move the Dock to the bottom to give the map more room.

| 1280×720 (16:9) | 960×540 (16:9) |
| :---: | :---: |
| <img src="docs/screenshots/car-1280x720.png" alt="1280×720"> | <img src="docs/screenshots/car-960x540.png" alt="960×540"> |
| **800×480 (5:3)** | **1280×488 (8:3)** |
| <img src="docs/screenshots/car-800x480.png" alt="800×480"> | <img src="docs/screenshots/car-1280x488.png" alt="1280×488"> |

### Phone

<p align="center">
  <img src="docs/screenshots/phone-home.png" width="300" alt="Phone home screen">
  <br><sub>Phone home screen (Material Design 3; follows the phone's dark/light mode and the system accent color)</sub>
</p>

## Features

- **Dashboard**: after connecting you land on a map + music split view. The map card can be dragged and zoomed directly; drag left from the dashboard to reach the app pages, and the page follows your finger. Swiping no longer opens the music app by accident.
- **Layout adapts to resolution**: wide screens (e.g. 1920×720) split side by side; narrow screens (e.g. 800×480, 16:9) default to a bottom Dock, which you can switch back to side by side in settings.
- **Maps**: remembers the last map app you used and keeps it for the next connection; you can also pick any installed app under **Car settings → Default map**.
- **Music**: tap the music card to open your music app; steering-wheel media keys skip tracks.
- **Audio through the car**: with Shizuku ready, system playback audio is captured directly, with no screen-recording consent dialog. Only playback audio is captured; the phone's microphone is not recorded for this.
- **Gemini in the car (experimental)**: when you say "Hey Google", the Gemini overlay and animation show up on the head unit and respond to touch.
- **Dock**: shows time, signal, Wi-Fi and battery (green while charging); running apps can be switched or closed from "Recent apps" on the home grid.
- **Car wallpapers**: built-in wallpapers, set separately for dark and light mode and switched automatically with the phone; you can also pick your own photo.
- **Phone UI**: Material Design 3 with three tabs at the bottom — Connect / Car / Diagnostics; follows the phone's dark/light mode and system accent color.
- **Languages**: Traditional Chinese, Simplified Chinese and English, switchable under **Car settings → Display → Language**.
- **Car screen capture**: one tap on the Diagnostics tab saves the current head-unit screen as a PNG (in Download/CarDeck).
- **Car microphone**: when any app starts recording (voice search, voice messages, Gemini, WhatsApp / Telegram / WeChat calls…), audio comes from the car's microphone instead, and the phone mic is restored afterwards (verified with WinPlay; not yet tested in a real car).
- **Calls on the car**: incoming and ongoing calls (phone, WhatsApp, Telegram, WeChat) replace the dashboard music card with the caller's name and photo and answer / decline or mute / hang-up buttons. Outgoing calls in WhatsApp, Telegram and WeChat show up too; apps without call buttons (WeChat) open their own call screen on the car. During internet calls the other party's voice plays through the car and the car microphone is used (verified with WinPlay; not yet tested in a real car).
- **Instrument cluster and HUD**: turn-by-turn guidance from Google Maps, Waze and Amap (maneuver, distance, road name, arrival time and lane guidance) and the current track with album art are sent to cars that show CarPlay info on the cluster or head-up display. Turn it on with **Quick settings → Cluster navigation info**; Waze and Amap need the accessibility service the app grants through Shizuku (verified with WinPlay; not yet tested in a real car).
- **Assistive dot**: a small draggable dot on top of the car screen with Back / Home / Recents. On the dashboard, Back goes to the map or music app you touched last. Size is adjustable in **Car settings → Display**; follows the phone's dark/light mode.
- **Phone quick settings**: On the phone home screen, use **Quick settings** to toggle Assistive Dot, cluster navigation info and car notification banners.
- **Car notifications**: CarPlay-style banners with the app icon that slide in from the top; dismiss with × or by swiping up. You can limit notifications to selected apps.
- **Tablet layouts**: WhatsApp, Telegram, LINE, Instagram, ChatGPT, YouTube and others use their tablet layout on the car screen; Discord renders at the phone's density so text is no longer clipped.
- **Connection automation**: send your own Intents when the car connects or disconnects (MacroDroid-style: broadcast / activity / service, action, package, extras…), e.g. for Tasker.
- **Connect-tab switches**: "Car audio" decides whether audio is sent, with no prompt before each connection; "Wait for the car to reconnect" is off by default, so a disconnect from the car releases Bluetooth and the hotspot. The car hotspot the app saved is forgotten on disconnect, and car apps no longer pile up in the phone's recents.

## Requirements

- An Android 12+ phone ("Force landscape" needs Android 14+)
- [Shizuku](https://shizuku.rikka.app/) installed and running (started via wireless debugging, no root). Virtual displays, touch injection and audio capture all go through it; the app can't connect without it
- A head unit with wireless CarPlay, already paired with the phone over Bluetooth

## How to use

1. Install CarDeck Neo, then read and accept the disclaimer on first launch.
2. Start Shizuku and grant access from the CarDeck Neo home screen.
3. Turn on wireless CarPlay (pairing mode) on the head unit, then tap **Start connection** on the phone.
4. Once connected the head unit shows the dashboard. Car-side settings are on the phone's Car tab or in the settings app on the head unit.

## Building from source

Requires JDK 17 and the Android SDK. From the project root:

```powershell
.\gradlew.bat :app:assembleStandardDebug
```

```powershell
.\gradlew.bat :app:testStandardDebugUnitTest :app:lintStandardDebug
```

The debug APK is in `app/build/outputs/apk/standard/debug/`. Stop the running app before installing (if you install mid-session, the system confirmation dialog can end up on the car screen):

```bash
adb shell am force-stop io.github.ezn24.cardeck.neo
```

```bash
adb install -r app/build/outputs/apk/standard/debug/app-standard-debug.apk
```

See the [dashboard guide](docs/DASHBOARD-UX.md) for detailed usage and testing; project conventions for developers and AI assistants are in [AGENTS.md](AGENTS.md). Most project docs are written in Chinese.

## Credits and upstream

- Based on [shaonialife/cardeck](https://github.com/shaonialife/cardeck). The upstream protocol research, development log and test-bench setup are archived in [docs/UPSTREAM-README.md](docs/UPSTREAM-README.md); protocol details are in [docs/PROTOCOL-NOTES.md](docs/PROTOCOL-NOTES.md).
- The original CarDeck builds are on the [upstream Releases page](https://github.com/shaonialife/cardeck/releases/latest).
- Wireless CarPlay adapter (smartBox / Carlinkit) support is ported from [zz85/cardeck](https://github.com/zz85/cardeck) (MIT).

### Protocol references

CarDeck is an independent implementation. The projects below were studied for protocol facts and timing only; no code from the GPL-licensed or unlicensed ones is copied.

| Project | Role | Used for |
| --- | --- | --- |
| [shilapi/xcertplay](https://github.com/shilapi/xcertplay) (GPL-3.0) | CarPlay receiver on Android | iAP2 link layer, Bonjour, AirPlay pairing |
| [shihabal3amri/DiPlay](https://github.com/shihabal3amri/DiPlay) (GPL-3.0) | Receiver, derived from xcertplay | The test bench CarDeck is developed against |
| [catplay-labs/catplay](https://github.com/catplay-labs/catplay) | Rust CarPlay implementation | Sender-side session order, screen/audio stream formats |
| [HaToan/carplay-wifi-extractor](https://github.com/HaToan/carplay-wifi-extractor) | Wireless CarPlay tooling | Wireless control timing |
| [Nitsuya/AADisplay](https://github.com/Nitsuya/AADisplay) (GPL-3.0) | Virtual-display launcher | Audi MIB3 Wi-Fi iAP2 tunnel timing |
| [amineross/showcase](https://github.com/amineross/showcase) (GPL-3.0), [wiomoc/iap2](https://github.com/wiomoc/iap2) | Receiver implementations | Cross-checking iAP2 message lists |
| [Genymobile/scrcpy](https://github.com/Genymobile/scrcpy) (Apache-2.0) | Screen mirroring | Turning off the phone panel while displays stay on |

### Libraries

- [Shizuku](https://github.com/RikkaApps/Shizuku) — shell-level access for virtual displays and input injection
- [Bouncy Castle](https://www.bouncycastle.org/) — SRP pairing and stream encryption
- [AndroidX](https://developer.android.com/jetpack/androidx)

## License

[MIT License](LICENSE). Please read [DISCLAIMER.md](DISCLAIMER.md) before use.
