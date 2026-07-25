# Arcanum Architectural Decisions Record (ADR)

## ADR-001: PWA as Canonical Primary Client Platform
- **Date**: 2026-07-25
- **Status**: Accepted & Implemented
- **Context**: Arcanum needs to run seamlessly on Web, Android, Desktop, Tablet, TV, Console, and Foldables.
- **Decision**: Treat the PWA codebase inside `/app/src/main/assets/pwa/` as the single source of truth for all game logic and UI. The Android app acts as a native WebView container shell with `@JavascriptInterface` bridge.

## ADR-002: Arcanum UI Web Components & Dynamic Render Profiles
- **Date**: 2026-07-25
- **Status**: Accepted & Implemented
- **Context**: UI must support 13 distinct visual themes (Fantasy, SciFi, Cyberpunk, Pixel, etc.) without altering component markup or business logic.
- **Decision**: Build a custom Web Components library (`arcanum-ui.js`) with 27 gaming tags driven by CSS variables and `[data-render-profile]` attributes.

## ADR-003: Procedural Audio Synthesizer & Living Particle Canvas
- **Date**: 2026-07-25
- **Status**: Accepted & Implemented
- **Context**: The game requires spatial sound and atmospheric visuals without inflating APK/bundle size with heavy audio/video assets.
- **Decision**: Use pure WebAudio API frequency synthesis (`arcanum-audio.js`) and an HTML5 2D particle canvas (`arcanum-atmosphere.js`).
