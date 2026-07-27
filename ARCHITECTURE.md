# Arcanum Engine Architecture Specification v4.4 (MULTIPLAYER & P2P NETWORKING ERA)

## Vision: "ARCANUM EVOLUTION :: MULTIPLAYER, WEBRTC & P2P NETWORKING ARCHITECTURE. ONE ENGINE. INFINITE WORLDS."

Arcanum Evolution is a self-contained, self-documenting, self-monitoring digital gaming operating system engineered for decadal longevity (1 to 10 year scale). It integrates Card RPG combat, world creation tools, interactive specifications, digital twin metrics, microkernel maturity tracking, `.apkg` Package Format Manager, Living Universe Map, Explainability Engine, embedded Academy, Experimental Laboratory, GitHub Digital Factory, 17-Stage **OMEGA PIPELINE (`.github/workflows/pipeline.yml`)**, **ArDesign System (32 Web Components)**, **Floating Window Manager**, **Splash Loading Engine**, **Command Palette Console**, **Omega UI Architecture**, **Frontend Runtime Engine**, **Visual Engine**, **Meta Ecosystem Engine (`arcanum-meta.js`)**, **Universal Game Engine (`arcanum-game-engine.js`)**, **Modular AI Subsystem (`arcanum-ai.js`)**, **Telemetry & Performance Subsystem (`arcanum-telemetry.js`)**, **Virtual IDE Subsystem (`arcanum-ide.js`)**, and **Multiplayer & P2P Network Subsystem (`arcanum-network.js`)** inside a 100% offline-first PWA primary client, backed by an Android native container shell.

---

## Architectural Pillars

1. **Multiplayer, WebRTC & P2P Network Subsystem v4.4 (`arcanum-network.js`)**: P2P Network Engine (`ArcanumP2PNetworkEngine`), Room Lobby Manager (`ArcanumRoomLobbyManager`), Delta State Syncer (`ArcanumStateSyncer`), and Multiplayer Studio (`ArcanumNetworkStudioUI`).
2. **Virtual IDE & File System Subsystem v4.3 (`arcanum-ide.js`)**: Virtual File System (`ArcanumVirtualFS`), In-Browser Code Editor (`ArcanumCodeEditor`), Project Compiler & Hot-Reloader (`ArcanumProjectCompiler`), and Code IDE Studio (`ArcanumIDEStudioUI`).
2. **Telemetry & Performance Analytics Subsystem v4.2 (`arcanum-telemetry.js`)**: Realtime Telemetry Monitor (`ArcanumTelemetryEngine`), Automated Benchmark Suite (`ArcanumBenchmarkSuite`), Heuristic Optimization Advisor (`ArcanumOptimizationAdvisor`), and Telemetry Studio (`ArcanumTelemetryStudioUI`).
2. **AI Subsystem & Neural Copilot Engine v4.1 (`arcanum-ai.js`)**: Master AI Controller (`ArcanumAIEngine`), Local Semantic Vector Index (`ArcanumSemanticIndex`), Procedural Content Reasoner (`ArcanumRuleReasoner`), Multi-Provider LLM Adapter Framework (`ArcanumLLMAdapter`), and AI Copilot Studio (`ArcanumCopilotStudioUI`).
2. **Universal Game Construction Engine v4.0 (`arcanum-game-engine.js`)**: Universal Game Loop Controller (`ArcanumGameEngine`), Entity Component System (`ArcanumEntityComponentSystem`), Visual RPG Scene Builder (`ArcanumSceneBuilder`), Declarative Logic Rule Graph (`ArcanumRuleGraphEngine`), Game Package Exporter (`ArcanumGamePublisher`), and Game Studio Laboratory (`ArcanumGameStudioUI`).
2. **Meta Ecosystem Engine v3.9 (`arcanum-meta.js`)**: Meta Registry (`ArcanumMetaRegistry`), Digital Passports (`ArcanumDigitalPassport`), Meta Links Relationship Engine (`ArcanumMetaLinks`), Object Lifecycle Manager (`ArcanumMetaLifecycle`), Global Search & Timeline Engine (`ArcanumMetaSearch`, `ArcanumMetaTimeline`), Self-Evolution & Consciousness Auditor (`ArcanumSelfEvolution`), and Meta Dashboard (`ArcanumMetaDashboard`).
3. **Visual Engine & Immersive System v3.7 (`arcanum-visual.js`)**: UI Effect Engine (`ArcanumUIEffectEngine`), Vector SVG Iconography Engine (`ArcanumVectorIconEngine`), Content Studio & Markdown Lore Engine (`ArcanumContentEngine`), Interaction Microinteraction Engine (`ArcanumInteractionEngine`), and Visual Engine Laboratory (`ArcanumVisualLab`).
4. **Frontend Runtime Engine v3.6 (`arcanum-frontend.js`)**: Runtime Lifecycle Controller (`ArcanumFrontendRuntime`), Dynamic Scene Engine (`ArcanumSceneEngine`), Render & Layout Engine, Multi-Input Gesture Engine (`ArcanumInputEngine`), Realtime Performance Engine (`ArcanumPerformanceEngine`), Interactive Frontend Laboratory & Debugger (`ArcanumFrontendLab`), Reactive State Store (`ArcanumStateStore`), and Snapshot Persistence (`ArcanumStatePersistence`).
2. **ArDesign System v3.4 (`arcanum-ui.js`)**: 32 Custom Web Components, 13 Render Profiles, Touch Gestures, Spatial D-Pad Navigation, and WebAudio procedural feedback.
3. **Floating Window Workspace Manager (`ArWindowManager`)**: Draggable, resizable, minimizable applet window manager supporting desktop floating windows layout with state persistence.
4. **Interactive Splash Loading Engine (`ArSplashEngine`)**: Dynamic splash mini-scene with logo animations, game tips, module initialization telemetry, and sound effects.
5. **Command Palette Console (`ArCommandPalette`)**: Global terminal (`Ctrl+K`) for instant command execution (`/theme`, `/window`, `/audio`, `/fullscreen`, `/help`).
6. **17-Stage OMEGA PIPELINE (`.github/workflows/pipeline.yml`)**: Master orchestrator executing 17 isolated pipeline stages: `01_validate` through `17_finalize`.
2. **GitHub Digital Factory & Version Manager (`arcanum-git.js`)**: Interactive GitHub Project Dashboard in PWA with Multi-Channel Version Switcher (`Stable`, `Beta`, `Nightly`, `Experimental`), OMEGA PIPELINE telemetry, and Release APK artifact manager.
3. **Modular GitHub Actions Workflows (`.github/workflows/`)**:
   - `pipeline.yml`: Master Orchestrator.
   - `arcanum-self-validation.yml`: Self-validation & structure audit.
   - `arcanum-multi-channel-deploy.yml`: Multi-channel build & deployment.
   - `deploy-pwa-gh-pages.yml` & `build-release-apk.yml`: Release packaging.
4. **Arcanum Package Format (.apkg) & Package Engine (`arcanum-package.js`)**: Universal package installer and isolation manager for modules, cards, worlds, themes, localization, audio, and AI configs.
5. **Living Universe Map**: Hierarchical visual explorer (Universe -> Platform -> Client -> Module -> Component -> Class -> Event -> Line of Docs).
6. **Explainability Engine (`ArExplainable`)**: Self-describing inspection engine revealing element purpose, dependencies, and impact if removed.
7. **Arcanum Academy & Laboratory**: In-app learning academy with interactive tutorials and isolated beta experiment toggles.
8. **Arcanum Kernel & Microkernel Architecture (`arcanum-kernel.js`)**: Decoupled microkernel managing module lifecycle and maturity tracking across Evolution Levels L0 (Idea) to L10 (Platform Benchmark).
9. **Arcanum Digital Twin & Architecture Score Engine**: Real-time telemetry computing the platform Architecture Score (100/100), AI Council Advisory Board metrics, Project Memory Ledger, and Version Time Machine matrix.
10. **AI Council Advisory System**: 12 specialized AI personas evaluating platform health.
11. **Omega Unified Interface**: Single-page PWA universe (`index.html`) hosting Game, Card/World Editor, Specs Reader, Architecture Score Dashboard, Package Manager, Universe Map, GitHub & Digital Factory, Academy, and AI Assistant.
12. **Interactive Documentation Reader (`arcanum-docs.js`)**: Real-time Markdown rendering with full-text search, Table of Contents, categories, and interactive module dependency canvas visualizer.
13. **32 Custom Ar* Web Components (`arcanum-ui.js`)**: Custom gaming UI elements driven by 13 dynamic Render Profiles.
14. **PWA Canonical Core Client**: Primary codebase running 100% offline-first with Service Worker v3.3 (`sw.js`).
15. **Android Container Shell**: Android native app wrapper (Jetpack Compose + WebView + `@JavascriptInterface` Native Bridge).
16. **Atmosphere Particle Canvas (`arcanum-atmosphere.js`)**: Interactive 2D Canvas ambient renderer with floating embers, light beams, and click ripples.
17. **Procedural WebAudio Synthesizer (`arcanum-audio.js`)**: Spatial audio frequency generator producing UI clicks, card flips, combat strikes, and crafting chimes without media files.
18. **Arcanum Link Protocol (ALP v2.0)**: Cross-device serialization protocol (`ALPMessage`, QR payloads, `arcanum://link` links).
