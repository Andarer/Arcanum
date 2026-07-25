# Arcanum Decoupled Micro-Modules Specification v2.4

## Module Registry Architecture
Arcanum Evolution features a decoupled micro-module architecture both in pure Kotlin Core (`com.example.core.engine`) and pure JavaScript Core (`arcanum-core.js`).

## Active Modules & Interfaces

### 1. `IBattleModule` / `BattleModule`
- **Purpose**: Handles turn-based Card RPG combat, hero HP, enemy AI strikes, magic spell casting, and battle logs.
- **Events Published**: `combat_started`, `combat_turn_ended`, `combat_ended`, `hero_health_changed`.

### 2. `ICardsModule` / `CardsModule`
- **Purpose**: Manages collectible card decks, elemental affinities (Fire, Water, Earth, Air, Light, Shadow), mana costs, and card stats.
- **Events Published**: `card_drawn`, `card_played`, `deck_shuffled`.

### 3. `IInventoryModule` / `InventoryModule`
- **Purpose**: Manages item slots, gold currency, magical gems, potions, weapons, and armor crafting materials.
- **Events Published**: `item_added`, `item_removed`, `gold_changed`, `gems_changed`.

### 4. `IQuestModule` / `QuestModule`
- **Purpose**: Tracks campaign quests, stage progress, reward payouts, and world map node progression.
- **Events Published**: `quest_accepted`, `quest_completed`, `stage_unlocked`.

### 5. `ISaveSyncModule` / `SaveSyncModule`
- **Purpose**: Handles LocalStorage, IndexedDB, and JSON export/import save state persistence.
- **Events Published**: `save_state_exported`, `save_state_imported`, `auto_saved`.
