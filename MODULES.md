# Arcanum Micro-Module Specification & Interface Catalog v0.7.0

## Overview
Every game feature in Arcanum operates as an isolated, independent micro-module conforming to `module.json`, `ModuleManifest`, and explicit Kotlin / JS interface contracts.

---

## Active Core Modules & Interfaces (`v0.7.0`)

### 1. `battle` — Battle Core Module
- **ID**: `battle`
- **Interface**: `IBattleModule`
- **Version**: 1.0.0
- **Description**: Turn-based combat calculations, damage resolution, status effects, and arena mechanics.
- **Contract Methods**:
  - `calculateDamage(attackerStr: Int, defenderDef: Int): Int`
  - `executeTurn(playerAction: String, enemyAction: String): Map<String, Any>`
- **Events Published**: `battle_start`, `battle_turn`, `battle_end`
- **Events Subscribed**: `card_played`, `use_item`

### 2. `cards` — Cards & Evolution Module
- **ID**: `cards`
- **Interface**: `ICardsModule`
- **Version**: 1.0.0
- **Description**: Card collection, deck building, leveling, transcending, and special abilities.
- **Contract Methods**:
  - `canEvolveCard(cardLevel: Int, copiesCount: Int): Boolean`
  - `calculateEvolvedStats(baseStr: Int, level: Int, tier: Int): Int`
- **Events Published**: `card_upgraded`, `card_transcended`
- **Events Subscribed**: `reward_claimed`

### 3. `inventory` — Inventory & Crafting Module
- **ID**: `inventory`
- **Interface**: `IInventoryModule`
- **Version**: 1.0.0
- **Description**: Item stacks, alchemy crafting, forge recipes, and chest looting.
- **Contract Methods**:
  - `canCraftItem(recipeId: String, currentIngredients: Map<String, Int>): Boolean`
- **Events Published**: `item_crafted`, `item_used`
- **Events Subscribed**: `loot_dropped`

### 4. `quest` — Quest & Achievements Module
- **ID**: `quest`
- **Interface**: `IQuestModule`
- **Version**: 1.0.0
- **Description**: Diary quests, achievement trackers, progress milestones, and rewards.
- **Contract Methods**:
  - `evaluateProgress(statKey: String, currentVal: Int, targetVal: Int): Boolean`
- **Events Published**: `quest_completed`, `achievement_unlocked`
- **Events Subscribed**: `battle_end`, `item_crafted`

### 5. `save_sync` — Save & Cross-Platform Sync Module
- **ID**: `save_sync`
- **Interface**: `ISaveSyncModule`
- **Version**: 1.0.0
- **Description**: Universal JSON state export/import, Room persistence, and LocalStorage sync.
- **Contract Methods**:
  - `serializeState(stateData: Map<String, Any>): String`
- **Events Published**: `save_exported`, `save_imported`
- **Events Subscribed**: `auto_save`

### 6. `editor` — Arcanum Studio Architect Tower
- **ID**: `editor`
- **Interface**: `IEditorModule`
- **Version**: 1.0.0
- **Description**: Visual card forge, AI mechanics synthesizer, and live preview desktop.
- **Contract Methods**:
  - `validateCardRecipe(cardData: Map<String, Any>): Boolean`
- **Events Published**: `card_created`, `render_profile_changed`
- **Events Subscribed**: `editor_opened`

### 7. `ui_render` — Render Engine & Shaders Module
- **ID**: `ui_render`
- **Interface**: `IUIRenderModule`
- **Version**: 1.0.0
- **Description**: Provides active RenderProfiles (Fantasy, Dark, SciFi, Cyberpunk, Pixel, Console, Minimal) and dynamic theme composition.
- **Contract Methods**:
  - `getRenderStyle(profile: String): RenderStyleSpec`
- **Events Published**: `theme_swapped`, `shader_reloaded`
- **Events Subscribed**: `set_render_profile`

---

## Planned Ecosystem Modules (`v0.8.0`+)
- `world` — World & Location Map System
- `dialog` — Branching Dialogue & NPC System
- `physics` — 2D Collision & Movement Engine
- `ai` — Gemini AI Character & Lore Synthesizer
- `print` — PDF Card Deck & Printable Assets Generator
- `qr` — QR Code Trading & Card Scanning System
- `audio` — Procedural Audio & Sound Effect Manager
