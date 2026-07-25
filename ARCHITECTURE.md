# Arcanum Engine Architecture Specification v3.2 (GitHub Evolution Platform Era)

## Vision: "ARCANUM EVOLUTION :: GITHUB EVOLUTION PLATFORM. LIVING REPOSITORY & DIGITAL FACTORY. ONE ENGINE. INFINITE WORLDS."

Arcanum Evolution is a self-contained, self-documenting, self-monitoring digital gaming operating system engineered for decadal longevity (1 to 10 year scale). It integrates Card RPG combat, world creation tools, interactive specifications, digital twin metrics, microkernel maturity tracking, `.apkg` Package Format Manager, Living Universe Map, Explainability Engine, embedded Academy, Experimental Laboratory, and GitHub Digital Factory (Multi-Channel Version Switcher, GitHub Actions Workflows, Release Packaging) inside a 100% offline-first PWA primary client, backed by an Android container and automated GitHub Actions CI/CD workflows.

---

## Architectural Pillars

1. **GitHub Digital Factory & Version Manager (`arcanum-git.js`)**: Interactive GitHub Project Dashboard in PWA with Multi-Channel Version Switcher (`Stable`, `Beta`, `Nightly`, `Experimental`), GitHub Actions status telemetry, and Release APK artifact manager.
2. **Modular GitHub Actions CI/CD Pipelines (`.github/workflows/`)**:
   - `arcanum-self-validation.yml`: Pre-deployment structure, PWA manifest, service worker, and Android build verification.
   - `arcanum-multi-channel-deploy.yml`: Multi-channel build & GitHub Pages publishing with `build-info.json` & SHA256 generation.
   - `deploy-pwa-gh-pages.yml` & `build-release-apk.yml`: APK artifact packaging and release deployment.
3. **Arcanum Package Format (.apkg) & Package Engine (`arcanum-package.js`)**: Universal package installer and isolation manager for modules, cards, worlds, themes, localization, audio, and AI configs.
4. **Living Universe Map**: Hierarchical visual explorer (Universe -> Platform -> Client -> Module -> Component -> Class -> Event -> Line of Docs).
5. **Explainability Engine (`ArExplainable`)**: Self-describing inspection engine revealing element purpose, dependencies, and impact if removed.
6. **Arcanum Academy & Laboratory**: In-app learning academy with interactive tutorials and isolated beta experiment toggles.
7. **Arcanum Kernel & Microkernel Architecture (`arcanum-kernel.js`)**: Decoupled microkernel managing module lifecycle and maturity tracking across Evolution Levels L0 (Idea) to L10 (Platform Benchmark).
8. **Arcanum Digital Twin & Architecture Score Engine**: Real-time telemetry computing the platform Architecture Score (100/100), AI Council Advisory Board metrics, Project Memory Ledger, and Version Time Machine matrix.
9. **AI Council Advisory System**: 12 specialized AI personas evaluating platform health.
10. **Omega Unified Interface**: Single-page PWA universe (`index.html`) hosting Game, Card/World Editor, Specs Reader, Architecture Score Dashboard, Package Manager, Universe Map, GitHub & Digital Factory, Academy, and AI Assistant.
11. **Interactive Documentation Reader (`arcanum-docs.js`)**: Real-time Markdown rendering with full-text search, Table of Contents, categories, and interactive module dependency canvas visualizer.
12. **32 Custom Ar* Web Components (`arcanum-ui.js`)**: Custom gaming UI elements driven by 13 dynamic Render Profiles.
13. **PWA Canonical Core Client**: Primary codebase running 100% offline-first with Service Worker v3.2 (`sw.js`).
14. **Android Container Shell**: Android native app wrapper (Jetpack Compose + WebView + `@JavascriptInterface` Native Bridge).
15. **Atmosphere Particle Canvas (`arcanum-atmosphere.js`)**: Interactive 2D Canvas ambient renderer with floating embers, light beams, and click ripples.
16. **Procedural WebAudio Synthesizer (`arcanum-audio.js`)**: Spatial audio frequency generator producing UI clicks, card flips, combat strikes, and crafting chimes without media files.
17. **Arcanum Link Protocol (ALP v2.0)**: Cross-device serialization protocol (`ALPMessage`, QR payloads, `arcanum://link` links).
