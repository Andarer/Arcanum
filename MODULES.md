# Arcanum Micro-Module Specification & Interface Catalog v2.0

## Overview
Every game feature in Arcanum operates as an isolated, independent micro-module conforming to `module.json`, `ModuleManifest`, and explicit Kotlin / JS interface contracts.

---

## Active Core Modules & Interfaces (`v2.0`)

### 1. `battle` — Battle Core Module
- **ID**: `battle`
- **Interface**: `IBattleModule`
- **Version**: 1.0.0
- **Description**: Turn-based combat calculations, damage resolution, status effects, and arena mechanics.
- **Contract Methods**:
  - `calculateDamage(attackerStr: Int, defenderDef: Int): Int`
  - `executeTurn(playerAction: String, enemyAction: String): Map<String, Any>`

### 2. `cards` — Cards & Evolution Module
- **ID**: `cards`
- **Interface**: `ICardsModule`
- **Version**: 1.0.0
- **Description**: Card collection, deck building, leveling, transcending, and special abilities.
- **Contract Methods**:
  - `canEvolveCard(cardLevel: Int, copiesCount: Int): Boolean`
  - `calculateEvolvedStats(baseStr: Int, level: Int, tier: Int): Int`

### 3. `inventory` — Inventory & Crafting Module
- **ID**: `inventory`
- **Interface**: `IInventoryModule`
- **Version**: 1.0.0
- **Description**: Item stacks, alchemy crafting, forge recipes, and chest looting.
- **Contract Methods**:
  - `canCraftItem(recipeId: String, currentIngredients: Map<String, Int>): Boolean`

### 4. `quest` — Quest & Achievements Module
- **ID**: `quest`
- **Interface**: `IQuestModule`
- **Version**: 1.0.0
- **Description**: Diary quests, achievement trackers, progress milestones, and rewards.
- **Contract Methods**:
  - `evaluateProgress(statKey: String, currentVal: Int, targetVal: Int): Boolean`

### 5. `save_sync` — Save & Cross-Platform Sync Module
- **ID**: `save_sync`
- **Interface**: `ISaveSyncModule`
- **Version**: 1.0.0
- **Description**: Universal JSON state export/import, Room persistence, and LocalStorage sync.
- **Contract Methods**:
  - `serializeState(stateData: Map<String, Any>): String`

### 6. `editor` — Arcanum Studio Architect Tower
- **ID**: `editor`
- **Interface**: `IEditorModule`
- **Version**: 1.0.0
- **Description**: Visual card forge, AI mechanics synthesizer, and live preview desktop.
- **Contract Methods**:
  - `validateCardRecipe(cardData: Map<String, Any>): Boolean`

### 7. `ui_render` — Render Engine & Shaders Module
- **ID**: `ui_render`
- **Interface**: `IUIRenderModule`
- **Version**: 1.0.0
- **Description**: Provides active RenderProfiles (Fantasy, Dark, SciFi, Cyberpunk, Pixel, Console, Minimal) and dynamic theme composition.
- **Contract Methods**:
  - `getRenderStyle(profile: String): RenderStyleSpec`

### 8. `alp_link` — Arcanum Link Protocol Module (v2.0)
- **ID**: `alp_link`
- **Interface**: `IProtocolModule`
- **Version**: 2.0.0
- **Description**: Encodes, serializes, and exchanges entities, cards, and worlds via QR codes, files, and `arcanum://link` URLs.
- **Contract Methods**:
  - `exportToALPString(entity: Entity): String`
  - `importFromALPString(alpJson: String): Entity`
