# Arcanum Engine Architecture Specification v2.0

## Vision: "ONE CORE. INFINITE WORLDS. LIVING GAMING OS."

Arcanum Evolution is a universal digital platform designed to assemble Card RPGs, MMORPGs, Shooters, Quests, Sandboxes, Clickers, and Arcanum Studio tools across Android, PWA, Web, Desktop, and CLI runtimes using a shared fractal micro-module architecture, dynamic Render Profiles, and the Arcanum Link Protocol (ALP).

---

## Architectural Principles

1. **One Core Engine**: Pure business logic, ECS (Entity Component System), System Pipeline, EventBus, and ModuleRegistry completely decoupled from UI frameworks.
2. **Typed Interface Contracts**: Every micro-module implements explicit domain interfaces (`IBattleModule`, `ICardsModule`, `IInventoryModule`, `IQuestModule`, `ISaveSyncModule`, `IEditorModule`, `IUIRenderModule`) for compile-time safety.
3. **Arcanum Link Protocol (ALP v2.0)**: Universal cross-device serialization format (`ALPMessage`, QR payloads, `arcanum://link` universal links) for sharing game entities and world states.
4. **Living Gaming Operating System (UI Constitution v1.0)**: Arcanum is an in-world OS where users navigate physical/magical locations (Architect's Tower, Forge, Guild, Laboratory, Knowledge Archive, Portals) rather than standard application screens.
5. **Dynamic Render Profiles**: Runtime swap of visual themes and shaders (`Fantasy`, `Dark`, `SciFi`, `Cyberpunk`, `Pixel`, `Console`, `Minimal`) without altering underlying game state.
6. **Composition via `boot.json`**: Worlds and game modes are assembled dynamically by composing micro-modules in `boot.json`.

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
  │    └── BootConfig.kt        (BootConfig, ArcanumBootstrapper)
  ├── protocol/
  │    └── ArcanumLinkProtocol.kt (ALP v2.0 Protocol, ALPMessage, ArcanumLinkAdapter)
  └── modules/
       └── CoreModules.kt       (IBattleModule, ICardsModule, IInventoryModule, IQuestModule, ISaveSyncModule)
```

### 2. UI Engine & Render Profiles (`/app/src/main/java/com/example/ui/theme/`)
```
/com.example.ui.theme/
  ├── RenderEngine.kt        (RenderProfile enum & getRenderStyle spec)
  ├── Theme.kt               (ArcanumTheme composition local & dynamic color scheme)
  └── Color.kt               (Elemental palettes, rarity colors, obsidian surfaces)
```

### 3. JavaScript / PWA Web Engine (`/app/src/main/assets/pwa/arcanum-core.js`)
```
ArcanumEngineJS
  ├── EventBusJS (Publish / Subscribe Event System)
  ├── ModuleRegistryJS (Register / Lifecycle Control)
  ├── EngineContextJS (Global State & Event Bus)
  ├── ArcanumLinkProtocolJS (ALP v2.0 Mirror for PWA)
  └── BaseArcanumModuleJS (Micro-module interface)
```

---

## Arcanum Link Protocol (ALP v2.0)

ALP is the unified data exchange protocol for Arcanum:
- **QR Payload Format**: `ALP2:<entityType>:<entityId>:<key1>=<val1>;<key2>=<val2>`
- **Universal Link Format**: `arcanum://link?data=<urlEncodedJson>`
- **Entity Adapter**: Maps Arcanum ECS `Entity` & `Component`s directly to `ALPMessage` and back.

---

## Active Engine Boot Composition (`boot.json`)

```json
{
  "engine": "Arcanum Universal Core Engine",
  "version": "2.0.0",
  "activeModules": [
    "battle",
    "cards",
    "inventory",
    "quest",
    "save_sync",
    "editor",
    "ui_render"
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
