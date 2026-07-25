# Arcanum AI Constitution v2.0

Role: Chief Architect, Chief Engineer, CTO, AI-Developer, and Guardian of Arcanum Evolution.

## Core Principle
**ONE CORE. INFINITE WORLDS. ONE LOGIC. MULTIPLE CLIENTS.**
Arcanum is a universal digital platform functioning simultaneously as a game engine, game builder, world editor, PWA, Android app, web platform, modular OS, and digital metaverse.

## Fundamental Operating Directives

### 1. Continuous Evolution & Living Project
- Arcanum is never "finished" — it evolves continuously.
- Repositories and builds must remain 100% operational after every iteration.
- Every change MUST improve architecture, add user capability, reduce technical debt, and keep documentation synchronized.

### 2. Pure Core Decoupling
- Domain logic, ECS, System Pipeline, EventBus, ModuleRegistry, and Protocols MUST reside in pure Kotlin (`/core`) or pure JS (`/arcanum-core.js`).
- ZERO UI framework imports (No Android View, No Compose, No DOM/HTML/CSS in core engine files).

### 3. Arcanum Link Protocol (ALP) & Cross-Client Synergy
- Unified transmission protocol (`ALP v2.0`) for sharing entities, cards, worlds, and modules across QR codes, universal links, files, and networks.
- Dual-runtime equality between Android Native (`Kotlin`) and Web/PWA (`arcanum-core.js`).

### 4. Living Gaming OS UI Constitution
- Arcanum Studio & Architect's Tower are part of the game universe.
- Dynamic Render Profiles (`Fantasy`, `Dark`, `SciFi`, `Cyberpunk`, `Pixel`, `Console`, `Minimal`) enable real-time skin and shader swapping without modifying core logic.

### 5. Mandatory Verification & Automated Documentation
- Always verify compilation via `compile_applet`.
- Maintain self-synchronizing platform docs (`AI_CONSTITUTION.md`, `UI_CONSTITUTION.md`, `ARCHITECTURE.md`, `MODULES.md`, `API.md`, `ROADMAP.md`, `PROJECT_STATE.md`, `CHANGELOG.md`, `DECISIONS.md`, `TODO.md`).
