<p align="center">
  <img src="docs/images/icon.svg" width="120" alt="CarDeck Neo 图标">
</p>

<h1 align="center">CarDeck Neo</h1>

<p align="center">
  让 Android 手机以无线 CarPlay 连上车机，显示地图、音乐与 App 的车机桌面<br>
  <a href="README.md">English</a> · <a href="README.zh-TW.md">繁體中文</a> · <b>简体中文</b>
</p>

---

CarDeck Neo 是 [CarDeck](https://github.com/shaonialife/cardeck) 的界面改版。手机（无需 root）扮演 CarPlay 发送端，把自绘的车机桌面与手机上的 App 投到支持无线 CarPlay 的车机上，车机触摸再回传到手机操作。

> **非官方项目**：与 Apple Inc. 及任何汽车制造商无关，未获授权或认证，**不是 MFi 产品**。CarPlay / AirPlay 是 Apple Inc. 的商标，仅用于说明兼容目标。
>
> **驾驶安全**：只在停车时连接与设置；行驶中可能断连，请以原厂导航作为安全冗余。完整条款见 [DISCLAIMER.md](DISCLAIMER.md)。

## 界面预览

### 车机

<p align="center">
  <img src="docs/screenshots/car-1920x720.png" alt="车机仪表盘 1920×720">
  <br><sub>仪表盘：左边地图可直接操作，右边音乐控制（1920×720）</sub>
</p>

<p align="center">
  <img src="docs/screenshots/car-gemini-1920x720.png" alt="车机上的 Gemini">
  <br><sub>说「Hey Google」，Gemini 直接显示在车机上，可触摸操作（实验性）</sub>
</p>

### 不同分辨率与比例

版面会按车机分辨率自动调整：宽屏左右分屏，窄屏把 Dock 移到底部，让地图有更多空间。

| 1280×720（16:9） | 960×540（16:9） |
| :---: | :---: |
| <img src="docs/screenshots/car-1280x720.png" alt="1280×720"> | <img src="docs/screenshots/car-960x540.png" alt="960×540"> |
| **800×480（5:3）** | **1280×488（8:3）** |
| <img src="docs/screenshots/car-800x480.png" alt="800×480"> | <img src="docs/screenshots/car-1280x488.png" alt="1280×488"> |

### 手机

<p align="center">
  <img src="docs/screenshots/phone-home.png" width="300" alt="手机主页">
  <br><sub>手机主页（Material Design 3，深浅色跟随手机、主色跟随系统壁纸）</sub>
</p>

## 功能

- **仪表盘**：连接后直接进入地图与音乐分屏。地图卡可以直接拖动、缩放；从仪表盘往左拖可翻到应用页，页面跟着手指移动。翻页时不会误触音乐卡。
- **按分辨率自动排版**：宽屏（如 1920×720）左右分屏；窄屏（如 800×480、16:9）默认 Dock 在底部，也可以在设置改回左右分屏。
- **地图**：记住最后使用的地图 App，下次连接沿用；也可以在 **车机设置 → 默认地图** 手动选择任何已安装的 App。
- **音乐**：点音乐卡打开音乐 App；方向盘媒体键可切歌。
- **声音走车机**：Shizuku 就绪时直接采集系统播放的声音，不需要录屏授权弹窗；只采集播放的声音，不会为此录制手机麦克风。
- **Gemini 上车（实验性）**：说「Hey Google」时，Gemini 的浮层与动画直接显示在车机上，可触摸操作。
- **Dock**：显示时间、信号、Wi-Fi 与电量（充电时显示绿色），后台应用可从主屏幕的「后台应用」切换或关闭。
- **车机壁纸**：内置多款壁纸，深色与浅色模式可分别设置，跟随手机自动切换；也能从相册选图。
- **手机界面**：Material Design 3，底部分为「连接 / 车机 / 诊断」三页；深浅色跟随手机，主色跟随系统。
- **多语言**：繁体中文、简体中文、英文，可在 **车机设置 → 显示 → 语言** 切换。
- **车机画面截图**：在「诊断」页一键把当前车机画面存成 PNG（存到「下载/CarDeck」）。
- **车机麦克风**：任何 App 开始录音（语音搜索、语音消息、Gemini、WhatsApp / Telegram / 微信通话等）时，自动改用车机麦克风收音，结束后还原手机麦克风（已在 WinPlay 验证，真车待测）。
- **车机接听电话**：手机来电与通话中（普通电话、WhatsApp、Telegram、微信）会在仪表盘的音乐卡位置显示来电者名称、头像，以及接听 / 拒接或静音 / 挂断按钮；WhatsApp、Telegram、微信主动拨出也会显示。没有通话按钮的 App（微信）会在车机上打开它自己的通话界面。网络通话时对方的声音从车机播出，并改用车机麦克风（已在 WinPlay 验证，尚未实车测试）。
- **车辆仪表与 HUD**：把 Google 地图、Waze、高德地图的转弯导航（转弯方向、距离、道路名称、预计到达时间与车道指示）以及正在播放的歌曲与专辑封面，发送到支持在仪表或抬头显示器显示 CarPlay 信息的车上。在 **快捷设置 → 仪表盘导航资讯** 开启；Waze 与高德需要的无障碍服务由本应用通过 Shizuku 开启（已在 WinPlay 验证，尚未实车测试）。
- **辅助小白点**：车机画面最上层的小圆点，可拖动，点开有返回 / 主页 / 后台三键；在仪表盘时「返回」会发给最后操作的地图或音乐 App。大小可在 **车机设置 → 显示** 调整，深浅色跟随手机。
- **手机快捷设置**：在手机主页的 **快捷设置** 可开关辅助小白点、仪表盘导航资讯及车机通知横幅。
- **车机通知**：CarPlay 风格横幅，显示 App 图标，从上方滑入，可点 × 或往上滑关闭；可另外指定只显示哪些 App 的通知。
- **平板版面**：WhatsApp、Telegram、LINE、Instagram、ChatGPT、YouTube 等在车机上改用平板双栏版面；Discord 以手机密度渲染，修正文字被截断。
- **连接自动化**：连接成功 / 断开时发出自定义 Intent（类似 MacroDroid「发送 Intent」：广播 / Activity / 服务、Action、包名、Extras 等），可搭配 Tasker 使用。
- **连接页开关**：「车机声音」决定是否推送声音，不再每次连接前询问；「车机断开后等待回连」默认关闭，车机断开即收起蓝牙与热点。断线后会自动清除 App 存入系统的车机热点，手机最近任务也不会留下车机上的 App。

## 要求

- Android 12 及以上的手机（「应用横屏」功能需要 Android 14 及以上）
- 已安装并启动 [Shizuku](https://shizuku.rikka.app/)（通过无线调试激活，无需 root）。虚拟屏、触摸注入与声音采集都经由它完成，未就绪时无法连接
- 支持无线 CarPlay 的车机，且手机已与车机完成蓝牙配对

## 使用方法

1. 安装 CarDeck Neo，首次打开时阅读并接受免责声明。
2. 启动 Shizuku，并在 CarDeck Neo 主页授权。
3. 在车机上打开无线 CarPlay（配对模式），回到手机点 **启动连接**。
4. 连接后车机会显示仪表盘。车机相关设置可在手机「车机」页或车机上的设置 App 调整。

## 从源码构建

需要 JDK 17 与 Android SDK。在项目根目录执行：

```powershell
.\gradlew.bat :app:assembleStandardDebug
```

```powershell
.\gradlew.bat :app:testStandardDebugUnitTest :app:lintStandardDebug
```

Debug 版 APK 位于 `app/build/outputs/apk/standard/debug/`。安装前请先停止正在运行的 App（连接中直接安装，系统确认窗口可能会跑到车机画面上）：

```bash
adb shell am force-stop io.github.ezn24.cardeck.neo
```

```bash
adb install -r app/build/outputs/apk/standard/debug/app-standard-debug.apk
```

详细操作与验证方式见 [仪表盘使用说明](docs/DASHBOARD-UX.md)；给开发者与 AI 助手的项目约定见 [AGENTS.md](AGENTS.md)。

## 致谢与上游

- 本项目基于 [shaonialife/cardeck](https://github.com/shaonialife/cardeck)。上游的协议研究、开发记录与测试台搭建说明保存在 [docs/UPSTREAM-README.md](docs/UPSTREAM-README.md)，协议细节见 [docs/PROTOCOL-NOTES.md](docs/PROTOCOL-NOTES.md)。
- 原版 CarDeck 可在[上游 Releases 页面](https://github.com/shaonialife/cardeck/releases/latest)下载。
- 无线 CarPlay 转接盒（smartBox / Carlinkit）支持移植自 [zz85/cardeck](https://github.com/zz85/cardeck)（MIT）。

### 协议参考

CarDeck 为独立实现。以下项目仅用于对照协议事实与时序；GPL 授权或未声明授权的项目，均未复制其代码。

| 项目 | 角色 | 参考内容 |
| --- | --- | --- |
| [shilapi/xcertplay](https://github.com/shilapi/xcertplay)（GPL-3.0） | Android 上的 CarPlay 接收端 | iAP2 链路层、Bonjour、AirPlay 配对 |
| [shihabal3amri/DiPlay](https://github.com/shihabal3amri/DiPlay)（GPL-3.0） | 接收端，xcertplay 衍生版 | 开发用的测试台 |
| [catplay-labs/catplay](https://github.com/catplay-labs/catplay) | Rust 实现的 CarPlay | 发送端会话顺序、画面与音频流格式 |
| [HaToan/carplay-wifi-extractor](https://github.com/HaToan/carplay-wifi-extractor) | 无线 CarPlay 工具 | 无线控制时序 |
| [Nitsuya/AADisplay](https://github.com/Nitsuya/AADisplay)（GPL-3.0） | 虚拟屏启动器 | 奥迪 MIB3 的 WiFi iAP2 隧道时序 |
| [amineross/showcase](https://github.com/amineross/showcase)（GPL-3.0）、[wiomoc/iap2](https://github.com/wiomoc/iap2) | 接收端实现 | 交叉核对 iAP2 消息清单 |
| [Genymobile/scrcpy](https://github.com/Genymobile/scrcpy)（Apache-2.0） | 屏幕镜像 | 虚拟屏保持运行时关闭手机面板 |

### 依赖库

- [Shizuku](https://github.com/RikkaApps/Shizuku)：提供 shell 权限，用于虚拟屏与触控注入
- [Bouncy Castle](https://www.bouncycastle.org/)：SRP 配对与流加密
- [AndroidX](https://developer.android.com/jetpack/androidx)

## 许可证

[MIT License](LICENSE)。使用前请阅读 [DISCLAIMER.md](DISCLAIMER.md)。
