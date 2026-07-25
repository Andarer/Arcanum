# Arcanum Platform Changelog

## [0.7.0] - 2026-07-25
### Added
- **Arcanum Evolution UI Constitution v1.0**: Implemented `UI_CONSTITUTION.md` establishing Arcanum as a Living Gaming OS.
- **Render Engine & Render Profiles**: Created `RenderEngine.kt` and `LocalRenderProfile` CompositionLocal supporting real-time theme swapping (Fantasy, Dark, SciFi, Cyberpunk, Pixel, Console, Minimal).
- **Arcanum Studio Architect Tower**: Rebuilt `EditorScreen.kt` with live Render Profile switcher, Card & Entity Forge, AI Mechanics Laboratory, and Knowledge Archive specs.
- **In-World OS Navigation**: Replaced plain UI labels in `HomeScreen.kt` with in-world locations (Architect Tower, Guild, Alchemy Forge, Portals).
- **Updated Complete Documentation Suite**: Updated `UI_CONSTITUTION.md`, `ARCHITECTURE.md`, `MODULES.md`, `API.md`, `ROADMAP.md`, `DECISIONS.md`, `TODO.md`, `CHANGELOG.md`, and `PROJECT_STATE.md`.

## [0.6.0] - 2026-07-25
### Added
- **ECS System Pipeline**: Created `System.kt` with `System` interface and `SystemManager` for executing component logic in priority order.
- **Typed Micro-Module Interfaces**: Defined `IBattleModule`, `ICardsModule`, `IInventoryModule`, `IQuestModule`, and `ISaveSyncModule` contracts in `CoreModules.kt` for type-safe cross-module interactions.
- **Documentation Overhaul**: Synchronized `ARCHITECTURE.md`, `MODULES.md`, `API.md`, `ROADMAP.md`, `DECISIONS.md`, `TODO.md`, and `PROJECT_STATE.md` with complete interface specifications.

## [0.5.1] - 2026-07-25
### Fixed
- **Runtime Image Resolution Crash**: Wrapped `painterResource` calls in `HomeScreen.kt` and `ChestScreen.kt` with `runCatching` blocks and procedural `CardArtGraphic` fallbacks to eliminate `ResourceResolutionException` during APK execution.

## [0.5.0] - 2026-07-25
### Added
- **Core Engine Architecture**: Introduced `com.example.core.engine` package with `Entity`, `Component`, `ArcanumModule`, `BaseArcanumModule`, `EventBus`, `ModuleRegistry`, `EngineContext`, and `ArcanumEngine`.
- **ECS Components**: Introduced `IdentityComponent`, `StatsComponent`, `AbilityComponent`, `ItemComponent`, and `QuestComponent`.
- **Entity Adapters**: Added `EntityAdapters` mapping database Room models to Core ECS Entities.
- **Micro-Modules**: Implemented `BattleModule`, `CardsModule`, `InventoryModule`, `QuestModule`, and `SaveSyncModule`.
- **Boot Configuration**: Added `boot.json` and `ArcanumBootstrapper` for dynamic game composition.
- **PWA Core Mirroring**: Implemented `arcanum-core.js` in `/app/src/main/assets/pwa/` mirroring Kotlin Core Engine on Web/PWA client.
