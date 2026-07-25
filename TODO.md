# Arcanum Active TODO Backlog

## Core Engine & Protocols
- [x] Create Core Micro-Module Engine (`com.example.core.engine`)
- [x] Create PWA Web Engine mirror (`arcanum-core.js`)
- [x] Implement ECS, System Pipeline & Room Entity Adapters (`EntityAdapters.kt`, `System.kt`)
- [x] Implement Typed Module Interfaces (`IBattleModule`, `ICardsModule`, `IInventoryModule`, `IQuestModule`, `ISaveSyncModule`, `IEditorModule`, `IUIRenderModule`, `IProtocolModule`)
- [x] Implement Arcanum Link Protocol ALP v2.0 (`ArcanumLinkProtocol.kt` & `ArcanumLinkProtocolJS`)
- [x] Boot Composition (`boot.json`)

## UI Engine & Arcanum OS
- [x] Adopt Arcanum Evolution UI Constitution v1.0 (`UI_CONSTITUTION.md`)
- [x] Implement `RenderEngine.kt` with Fantasy, Dark, SciFi, Cyberpunk, Pixel, Console, and Minimal profiles
- [x] Rebuild `EditorScreen.kt` as Arcanum Studio Architect's Tower with ALP Decoder tab
- [x] Update `HomeScreen.kt` menu navigation to in-world locations
- [x] Connect `ArcanumViewModel` active render profile state flow to Compose theme

## Documentation & CI/CD Factory
- [x] GitHub Pages deployment workflow (`deploy-pwa-gh-pages.yml`)
- [x] Android Release APK build workflow (`build-release-apk.yml`)
- [x] Complete synchronized platform documentation suite (`AI_CONSTITUTION.md`, `UI_CONSTITUTION.md`, `ARCHITECTURE.md`, `MODULES.md`, `API.md`, `ROADMAP.md`, `CHANGELOG.md`, `DECISIONS.md`, `TODO.md`, `PROJECT_STATE.md`)
