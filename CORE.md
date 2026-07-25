# Arcanum Core Engine Specification v2.4

## Dual Core Architecture
Arcanum Evolution implements a mirrored core architecture:
1. **Kotlin Core Engine** (`com.example.core.engine`): Pure Kotlin engine for native Android execution and unit testing.
2. **JavaScript Core Engine** (`arcanum-core.js`): Pure ES2025+ JS engine for PWA, Web, Desktop, TV, and WebWorker execution.

## Core Components
- **EventBus**: Thread-safe / asynchronous pub-sub event dispatcher.
- **Entity Component System (ECS)**: Pure composition system with `Entity`, `Component` (`HealthComponent`, `StatsComponent`, `InventoryComponent`, `CardComponent`), `System`, and `SystemManager`.
- **System Pipeline**: Sequential tick/update execution loop for physics, rendering, atmosphere, AI, and state sync.
- **ModuleRegistry**: Dynamic registry for registering, starting, stopping, and hot-swapping game modules.
