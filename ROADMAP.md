# Arcanum Platform Evolution Roadmap

## Phase 1: Core Decoupling, Micro-Module Architecture & Interfaces (COMPLETED v0.6.0)
- [x] Create pure Kotlin core engine (`com.example.core.engine`)
- [x] Implement ECS (Entity Component System: Entity, Component, System, SystemManager)
- [x] Implement Decoupled EventBus
- [x] Implement ModuleRegistry and universal `ArcanumModule` lifecycle
- [x] Implement Typed Module Interfaces (`IBattleModule`, `ICardsModule`, `IInventoryModule`, `IQuestModule`, `ISaveSyncModule`)
- [x] Implement `boot.json` composition mechanism
- [x] Mirror JavaScript Core Engine in PWA (`arcanum-core.js`)
- [x] Create Entity Adapters mapping Room entities to Core ECS Entities
- [x] Runtime image resolution safety and procedural graphics fallback

## Phase 2: Arcanum Studio & Living Gaming OS UI Constitution (COMPLETED v0.7.0)
- [x] Adopt Arcanum Evolution UI Constitution v1.0 (`UI_CONSTITUTION.md`)
- [x] Transform UI into Living Gaming Operating System (Architect's Tower, Forge, Guild, Knowledge Archive, Portals)
- [x] Implement `RenderEngine` & `LocalRenderProfile` CompositionLocal
- [x] Live runtime Render Profile swapping (Fantasy, Dark, SciFi, Cyberpunk, Pixel, Console, Minimal)
- [x] Arcanum Studio Architect's Tower desktop (`EditorScreen.kt`) with Render Profile Switcher, Card Forge, AI Laboratory, and Docs Archive
- [x] Dynamic JSON save/world export and import in Arcanum Studio

## Phase 3: Infinite Worlds & Multi-Genre Presets (PLANNED v0.8.0)
- [ ] Shooter / Arcade Preset (`shooter` + `physics` + `inventory`)
- [ ] Sandbox / Clicker Preset (`clicker` + `craft` + `marketplace`)
- [ ] Print & QR Card Generator (`print` + `qr` modules)

## Phase 4: AI & Distributed Multi-Client Ecosystem (PLANNED v0.9.0)
- [ ] Gemini AI Module for dynamic lore, quest generation, and NPC dialogues
- [ ] Telegram Bot Client engine binding
- [ ] Desktop Electron runner setup
