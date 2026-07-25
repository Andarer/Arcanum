# Arcanum Active TODO Backlog

## Core Engine & Interfaces
- [x] Create Core Micro-Module Engine (`com.example.core.engine`)
- [x] Create PWA Web Engine mirror (`arcanum-core.js`)
- [x] Implement ECS, System Pipeline & Room Entity Adapters (`EntityAdapters.kt`, `System.kt`)
- [x] Implement Typed Module Interfaces (`IBattleModule`, `ICardsModule`, `IInventoryModule`, `IQuestModule`, `ISaveSyncModule`, `IEditorModule`, `IUIRenderModule`)
- [x] Boot Composition (`boot.json`)
- [ ] Implement dynamic JSON world loader/parser for runtime custom worlds

## UI Engine & Arcanum OS
- [x] Adopt Arcanum Evolution UI Constitution v1.0 (`UI_CONSTITUTION.md`)
- [x] Implement `RenderEngine.kt` with Fantasy, Dark, SciFi, Cyberpunk, Pixel, Console, and Minimal profiles
- [x] Rebuild `EditorScreen.kt` as Arcanum Studio Architect's Tower
- [x] Update `HomeScreen.kt` menu navigation to in-world locations (Architect Tower, Forge, Guild, Knowledge Archive, Portals)
- [x] Connect `ArcanumViewModel` active render profile state flow to Compose theme

## PWA & Web Client
- [x] Inject `arcanum-core.js` into PWA index
- [x] Boot `ArcanumEngineJS` on PWA startup
- [ ] Create PWA module status modal in web interface

## CI/CD Factory & Documentation
- [x] GitHub Pages deployment workflow (`deploy-pwa-gh-pages.yml`)
- [x] Android Release APK build workflow (`build-release-apk.yml`)
- [x] Complete platform documentation suite (`UI_CONSTITUTION.md`, `ARCHITECTURE.md`, `MODULES.md`, `API.md`, `ROADMAP.md`, `CHANGELOG.md`, `DECISIONS.md`, `TODO.md`, `PROJECT_STATE.md`)
