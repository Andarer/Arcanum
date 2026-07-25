# Arcanum Android Container & Native Bridge Specification v2.3

## Overview
In Arcanum Evolution v2.3+, the Android native application serves as a high-performance wrapper container (Jetpack Compose + WebView + Native Bridge). It provides native hardware access to the PWA primary client while retaining standard native Android Compose screens.

---

## Native Bridge Interface (`ArcanumNativeBridge`)
Exposed to JavaScript in WebView via `window.ArcanumNative`:

- `isNativeContainer(): Boolean` — Returns `true` when running inside the Android native shell.
- `vibrate(milliseconds: Long)` — Triggers device haptic vibration.
- `showToast(message: String)` — Displays native Android Toast message.
- `getDeviceInfo(): String` — Returns Android SDK version, build model, and device information.

---

## WebView Integration & Immersive UX (`PwaWebScreen.kt`)
- Edge-to-Edge window insets handling with `enableEdgeToEdge()`.
- Predictive Back / BackHandler integration.
- Embedded inside Jetpack Compose using `AndroidView`.
- Configured with `javaScriptEnabled = true`, `domStorageEnabled = true`, `allowFileAccess = true`.
- Native Bridge attached via `addJavascriptInterface(ArcanumNativeBridge(context), "ArcanumNative")`.
