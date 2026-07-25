# Arcanum Engine Architecture Specification v3.1 (Meta Constitution Era)

## Vision: "ARCANUM EVOLUTION :: META CONSTITUTION. ETERNAL COMPATIBILITY. ONE ENGINE. INFINITE WORLDS."

Arcanum Evolution is a self-contained, self-documenting, self-monitoring digital gaming operating system engineered for decadal longevity (1 to 10 year scale). It integrates Card RPG combat, world creation tools, interactive specifications, digital twin metrics, microkernel maturity tracking, `.apkg` Package Format Manager, Living Universe Map, Explainability Engine, embedded Academy, and Experimental Laboratory inside a 100% offline-first PWA primary client, backed by an Android container and automated GitHub Actions CI/CD workflows.

---

## Architectural Pillars

1. **Arcanum Package Format (.apkg) & Package Engine (`arcanum-package.js`)**: Universal package installer and isolation manager for modules, cards, worlds, themes, localization, audio, and AI configs.
2. **Living Universe Map**: Hierarchical visual explorer (Universe -> Platform -> Client -> Module -> Component -> Class -> Event -> Line of Docs).
3. **Explainability Engine (`ArExplainable`)**: Self-describing inspection engine revealing element purpose, dependencies, and impact if removed.
4. **Arcanum Academy & Laboratory**: In-app learning academy with interactive tutorials and isolated beta experiment toggles.
5. **Arcanum Kernel & Microkernel Architecture (`arcanum-kernel.js`)**: Decoupled microkernel managing module lifecycle and maturity tracking across Evolution Levels L0 (Idea) to L10 (Platform Benchmark).
6. **Arcanum Digital Twin & Architecture Score Engine**: Real-time telemetry computing the platform Architecture Score (99/100), AI Council Advisory Board metrics, Project Memory Ledger, and Version Time Machine matrix.
7. **AI Council Advisory System**: 12 specialized AI personas (Architect, Engine, UI, UX, Performance, Security, Documentation, Testing, etc.) evaluating platform health.
8. **Omega Unified Interface**: Single-page PWA universe (`index.html`) hosting Game, Card/World Editor, Specs Reader, Architecture Score Dashboard, Package Manager, Universe Map, Academy, and AI Assistant.
9. **Interactive Documentation Reader (`arcanum-docs.js`)**: Real-time Markdown rendering with full-text search, Table of Contents, categories, and interactive module dependency canvas visualizer.
10. **32 Custom Ar* Web Components (`arcanum-ui.js`)**: Custom gaming UI elements driven by 13 dynamic Render Profiles (`Fantasy`, `Dark`, `SciFi`, `Cyberpunk`, `Pixel`, `Console`, `Minimal`, `Steam`, `PlayStation`, `Nintendo`, `Material`, `Glass`, `Neon`).
11. **PWA Canonical Core Client**: Primary codebase running 100% offline-first with Service Worker v3.1 (`sw.js`).
12. **Android Container Shell**: Android native app wrapper (Jetpack Compose + WebView + `@JavascriptInterface` Native Bridge).
13. **Atmosphere Particle Canvas (`arcanum-atmosphere.js`)**: Interactive 2D Canvas ambient renderer with floating embers, light beams, and click ripples.
14. **Procedural WebAudio Synthesizer (`arcanum-audio.js`)**: Spatial audio frequency generator producing UI clicks, card flips, combat strikes, and crafting chimes without media files.
15. **Arcanum Link Protocol (ALP v2.0)**: Cross-device serialization protocol (`ALPMessage`, QR payloads, `arcanum://link` links).
16. **Automated GitHub Actions Evolution Pipeline**: Automated Gradle build, APK packaging, PWA bundle deployment to GitHub Pages, and release reports.
