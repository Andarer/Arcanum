# Arcanum Platform Changelog

## [2.0.0] - 2026-07-25
### Added
- **Arcanum Link Protocol (ALP v2.0)**: Created `ArcanumLinkProtocol.kt` (`ALPMessage`, `ArcanumLinkAdapter`) in Kotlin Core and `ArcanumLinkProtocolJS` in JS PWA Core for universal entity/card/world serialization via QR payloads and `arcanum://link` URLs.
- **Arcanum Studio Link Protocol Tab**: Integrated interactive ALP Payload Generator & Decoder Tab inside `EditorScreen.kt` (Architect's Tower).
- **AI Constitution v2.0 & Continuous Evolution Directives**: Updated `AI_CONSTITUTION.md` establishing Arcanum as a living metaverse platform.
- **Synchronized Documentation Suite**: Updated `AI_CONSTITUTION.md`, `ARCHITECTURE.md`, `MODULES.md`, `API.md`, `ROADMAP.md`, `PROJECT_STATE.md`, `CHANGELOG.md`, `DECISIONS.md`, and `TODO.md`.

## [0.7.0] - 2026-07-25
### Added
- **Arcanum Evolution UI Constitution v1.0**: Implemented `UI_CONSTITUTION.md` establishing Arcanum as a Living Gaming OS.
- **Render Engine & Render Profiles**: Created `RenderEngine.kt` and `LocalRenderProfile` CompositionLocal supporting real-time theme swapping (Fantasy, Dark, SciFi, Cyberpunk, Pixel, Console, Minimal).
- **Arcanum Studio Architect Tower**: Rebuilt `EditorScreen.kt` with live Render Profile switcher, Card & Entity Forge, AI Mechanics Laboratory, and Knowledge Archive specs.
- **In-World OS Navigation**: Replaced plain UI labels in `HomeScreen.kt` with in-world locations (Architect Tower, Guild, Alchemy Forge, Portals).

## [0.6.0] - 2026-07-25
### Added
- **ECS System Pipeline**: Created `System.kt` with `System` interface and `SystemManager` for executing component logic in priority order.
- **Typed Micro-Module Interfaces**: Defined `IBattleModule`, `ICardsModule`, `IInventoryModule`, `IQuestModule`, and `ISaveSyncModule` contracts in `CoreModules.kt` for type-safe cross-module interactions.
