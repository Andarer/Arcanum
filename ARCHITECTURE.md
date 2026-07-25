# Arcanum Engine Architecture Specification v0.7.0

## Vision: "ONE CORE. INFINITE WORLDS. LIVING GAMING OS."

Arcanum is a universal, multi-platform gaming operating system designed to assemble Card RPGs, MMORPGs, Shooters, Quests, Sandbox, Clickers, and Arcanum Studio tools across Android, PWA, Web, Desktop, and CLI runtimes using a shared fractal micro-module architecture and dynamic Render Profiles.

---

## Architectural Principles

1. **One Core Engine**: Pure business logic, ECS (Entity Component System), System Pipeline, EventBus, and ModuleRegistry completely decoupled from UI frameworks.
2. **Typed Interface Contracts**: Every micro-module implements explicit domain interfaces (`IBattleModule`, `ICardsModule`, `IInventoryModule`, `IQuestModule`, `ISaveSyncModule`) for strict compile-time safety across clients.
3. **Living Gaming Operating System (UI Constitution v1.0)**: Arcanum is an in-world OS. Users navigate locations (Architect's Tower, Forge, Laboratory, Knowledge Archive, Portals) rather than standard apps.
4. **Dynamic Render Profiles**: Runtime swap of visual shaders/renderers (`FantasyRenderer`, `DarkRenderer`, `SciFiRenderer`, `CyberpunkRenderer`, `PixelRenderer`, `ConsoleRenderer`, `MinimalRenderer`) without changing underlying core state.
5. **Micro-Module Isolation**: Every feature operates as an independent micro-module containing its manifest metadata, event contracts, dependencies, and lifecycle hooks (`onRegister`, `onInit`, `onEnable`, `onDisable`, `onDestroy`).
6. **Composition via `boot.json`**: Worlds and game modes are assembled by composing modules in `boot.json`. Switching or swapping modules requires zero changes to the rest of the engine.

---

## Engine Hierarchy

### 1. Kotlin / Android Engine (`/app/src/main/java/com/example/core/`)
```
/com.example.core/
  ├── engine/
  │    ├── EntityComponent.kt   (ECS Base: Entity, Component)
  │    ├── System.kt            (ECS System Interface & SystemManager)
  │    ├── Module.kt            (ArcanumModule, BaseArcanumModule, ModuleManifest)
  │    ├── EventBus.kt          (ArcanumEvent, EventBus)
  │    ├── EngineContext.kt     (EngineContext, ModuleRegistry)
  │    ├── ArcanumEngine.kt     (Singleton Engine Facade)
  │    ├── BootConfig.kt        (BootConfig, ArcanumBootstrapper)
  │    ├── components/
  │    │    └── Components.kt   (IdentityComponent, StatsComponent, AbilityComponent, ItemComponent, QuestComponent)
  │    └── adapters/
  │         └── EntityAdapters.kt (Room Entity -> Core ECS Entity Adapters)
  └── modules/
       └── CoreModules.kt       (IBattleModule, ICardsModule, IInventoryModule, IQuestModule, ISaveSyncModule)
```

### 2. UI Engine & Render Profiles (`/app/src/main/java/com/example/ui/theme/`)
```
/com.example.ui.theme/
  ├── RenderEngine.kt        (RenderProfile enum & getRenderStyle specification)
  ├── Theme.kt               (ArcanumTheme composition local & dynamic color scheme)
  └── Color.kt               (Elemental palettes, rarity colors, obsidian surfaces)
```

### 3. JavaScript / PWA Web Engine (`/app/src/main/assets/pwa/arcanum-core.js`)
```
ArcanumEngineJS
  ├── EventBusJS (Publish / Subscribe Event System)
  ├── ModuleRegistryJS (Register / Lifecycle Control)
  ├── EngineContextJS (Global State & Event Bus)
  └── BaseArcanumModuleJS (Micro-module interface)
```

---

## ECS & Entity Adapters

In Arcanum Engine, cards, player heroes, enemies, items, and quests are all instances of `Entity` with attached `Component`s operated on by `System`s:
- `IdentityComponent`: `entityType`, `rarity`, `artKey`, `description`
- `StatsComponent`: `hp`, `hpMax`, `mp`, `mpMax`, `str`, `def`, `level`, `xp`
- `AbilityComponent`: `name`, `type`, `value`, `cost`, `description`
- `ItemComponent`: `count`, `useType`, `effectValue`
- `QuestComponent`: `target`, `statKey`, `xpReward`, `goldReward`, `currentProgress`, `isCompleted`

Data adapters (`EntityAdapters`) convert database persistence objects (Room entities) into runtime ECS Entities without breaking database schema or existing ViewModel integrations.

---

## Micro-Module Contract & Interface Hierarchy

Every module defines its metadata and implements domain interfaces:
```json
{
  "id": "battle",
  "name": "Battle Core Module",
  "version": "1.0.0",
  "description": "Handles turn-based battle mechanics and calculations.",
  "dependencies": [],
  "eventsPublished": ["battle_start", "battle_turn", "battle_end"],
  "eventsSubscribed": ["card_played", "use_item"]
}
```

Lifecycle & API Interfaces:
- `ArcanumModule`: `manifest`, `state`, `onRegister`, `onInit`, `onEnable`, `onDisable`, `onDestroy`
- `IBattleModule`: `calculateDamage()`, `executeTurn()`
- `ICardsModule`: `canEvolveCard()`, `calculateEvolvedStats()`
- `IInventoryModule`: `canCraftItem()`
- `IQuestModule`: `evaluateProgress()`
- `ISaveSyncModule`: `serializeState()`

---

## Active Engine Boot Composition (`boot.json`)

```json
{
  "engine": "Arcanum Universal Core Engine",
  "version": "0.7.0",
  "activeModules": [
    "battle",
    "cards",
    "inventory",
    "quest",
    "save_sync"
  ],
  "renderProfiles": [
    "fantasy",
    "dark",
    "scifi",
    "cyberpunk",
    "pixel",
    "console",
    "minimal"
  ],
  "supportedClients": [
    "Android Native",
    "PWA Web App",
    "Desktop Electron",
    "CLI"
  ]
}
```

---

## Clients Architecture
1. **Android Native Client**: Jetpack Compose wrapper connecting `ArcanumViewModel` & `RenderEngine` to `ArcanumEngine`.
2. **PWA Client**: Service Worker offline PWA powered by `arcanum-core.js` and `app.js`.
3. **GitHub CI/CD Factory**: Automated Android APK build & GitHub Pages PWA deployment.
