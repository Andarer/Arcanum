# Arcanum PWA Primary Client Specification v2.3

## Overview
The **PWA (Progressive Web App)** is the primary, canonical client of the Arcanum Evolution platform. The Android application acts as a native container shell (WebView + Native Bridge). All core game features, UI components, render engines, and network protocols are implemented first in the PWA and immediately shared across Web, Mobile, Tablet, Desktop, Console, and TV clients.

---

## Immersive Engine & Web Stack
- **Core Engine**: Pure ES2025+ JavaScript Core (`arcanum-core.js`) with EventBus, Entity Component System (ECS), System Pipeline, Module Registry, and Arcanum Link Protocol (`ArcanumLinkProtocolJS`).
- **Atmosphere & Particle Canvas (`arcanum-atmosphere.js`)**: Interactive 2D Canvas background system rendering floating magical embers/particles, light beams, fog motion, and touch/pointer reaction ripples.
- **Procedural WebAudio Synthesizer (`arcanum-audio.js`)**: WebAudio API spatial sound synthesis generating UI clicks, card flips, combat strikes, crafting chimes, and ambient soundscapes without external media assets.
- **Arcanum UI Web Components (`arcanum-ui.js`)**: 27 custom gaming elements (`<ar-button>`, `<ar-panel>`, `<ar-card>`, `<ar-inventory>`, `<ar-dialog>`, `<ar-dialogue>`, `<ar-quest>`, `<ar-window>`, `<ar-map>`, `<ar-character>`, `<ar-world>`, `<ar-menu>`, `<ar-hud>`, `<ar-notification>`, `<ar-modal>`, `<ar-toast>`, `<ar-context-menu>`, `<ar-tooltip>`, `<ar-craft>`, `<ar-battle>`, `<ar-editor>`, `<ar-dock>`, `<ar-sidebar>`, `<ar-toolbar>`, `<ar-status-bar>`, `<ar-console>`, `<ar-settings>`).
- **Cross-Platform Device Engine (`ArcanumDeviceEngine`)**: Auto-detects capabilities (`touch`, `mouse`, `keyboard`, `gamepad`), screen size, orientation, and device type (`pwa`, `android`, `desktop`, `tablet`, `tv`, `console`, `foldable`).
- **Cinematic World Engine (`ArcanumCinematics`)**: GPU-accelerated screen shake (`screenShake()`), screen wake lock (`requestWakeLock()`), full-screen toggle (`toggleFullscreen()`), and transition animations (`zoom`, `fade`, `slide`, `morph`).
- **Render Engine**: Dynamic Render Profiles (`Fantasy`, `Dark`, `SciFi`, `Cyberpunk`, `Pixel`, `Console`, `Minimal`, `Steam`, `PlayStation`, `Nintendo`, `Material`, `Glass`, `Neon`) applied via `[data-render-profile]` attributes.
- **Offline First**: Service Worker v2.3 (`sw.js`) caching assets with IndexedDB & LocalStorage persistence.
