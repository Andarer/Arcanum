# Arcanum Architectural Decision Records (ADRs)

## ADR 001: Separation of Pure Core and Platform Clients
- **Status**: Accepted
- **Context**: Arcanum is evolving into a universal game engine. Mixing Android Compose or HTML/DOM code inside business domain logic creates tight coupling and prevents platform cross-compilation.
- **Decision**: All domain entities, rules, ECS components, modules, event buses, and registries MUST reside in pure Kotlin (`/core`) or pure JS (`/arcanum-core.js`) without UI framework imports. Android, PWA, Desktop, and CLI operate purely as wrapper clients.

## ADR 002: Composition via `boot.json`
- **Status**: Accepted
- **Context**: Games are specific compositions of modules (e.g. RPG = Battle + Cards + Quest; Shooter = Physics + AI + Weapons).
- **Decision**: Engine bootstrapper reads `boot.json` to instantiate and initialize active micro-modules dynamically.

## ADR 003: Entity Component System (ECS) for Game Objects
- **Status**: Accepted
- **Context**: Rigid class inheritance (e.g. `Card`, `Hero`, `Item`) prevents multi-genre flexibility.
- **Decision**: All game objects inherit from universal `Entity` and attach decoupled data-first `Component`s.

## ADR 004: Typed Interface Contracts for Micro-Modules
- **Status**: Accepted
- **Context**: Accessing modules by generic string IDs without type contracts leads to unsafe casting and fragile module inter-communications.
- **Decision**: Every micro-module MUST implement an explicit Kotlin interface (`IBattleModule`, `ICardsModule`, `IInventoryModule`, etc.) extending `ArcanumModule`.

## ADR 005: System Pipeline Execution in ECS
- **Status**: Accepted
- **Context**: Business logic needs a uniform way to process entity components in priority order.
- **Decision**: Introduce `System` interface and `SystemManager` pipeline to iterate over entities and update component state deterministically.

## ADR 006: Arcanum Evolution UI Constitution
- **Status**: Accepted
- **Context**: The user interface must feel like an in-world gaming operating system rather than a plain app or IDE.
- **Decision**: Adopt the Arcanum Evolution UI Constitution (`UI_CONSTITUTION.md`). Every UI screen represents a physical or magical location in the Arcanum universe (e.g., Architect's Tower, Forge, Guild, Portals). Standard plain forms are strictly replaced by game-styled components.

## ADR 007: Dynamic Render Profiles System
- **Status**: Accepted
- **Context**: Different realms and themes require distinct visual styles without rewriting UI screens or breaking engine logic.
- **Decision**: Create `RenderEngine.kt` and `LocalRenderProfile` CompositionLocal to support live, real-time theme swapping across Fantasy, Dark, SciFi, Cyberpunk, Pixel, Console, and Minimal render profiles.
