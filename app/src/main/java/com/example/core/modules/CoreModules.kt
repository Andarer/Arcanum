package com.example.core.modules

import com.example.core.engine.*

// --- Module Contract Interfaces ---

interface IBattleModule : ArcanumModule {
    fun calculateDamage(attackerStr: Int, defenderDef: Int): Int
    fun executeTurn(playerAction: String, enemyAction: String): Map<String, Any>
}

interface ICardsModule : ArcanumModule {
    fun canEvolveCard(cardLevel: Int, copiesCount: Int): Boolean
    fun calculateEvolvedStats(baseStr: Int, level: Int, tier: Int): Int
}

interface IInventoryModule : ArcanumModule {
    fun canCraftItem(recipeId: String, currentIngredients: Map<String, Int>): Boolean
}

interface IQuestModule : ArcanumModule {
    fun evaluateProgress(statKey: String, currentVal: Int, targetVal: Int): Boolean
}

interface ISaveSyncModule : ArcanumModule {
    fun serializeState(stateData: Map<String, Any>): String
}


// --- Module Implementations ---

/**
 * Battle Micro-Module
 */
class BattleModule : BaseArcanumModule(
    ModuleManifest(
        id = "battle",
        name = "Battle Core Module",
        version = "1.0.0",
        description = "Handles turn-based battle mechanics and calculations.",
        eventsPublished = listOf("battle_start", "battle_turn", "battle_end"),
        eventsSubscribed = listOf("card_played", "use_item")
    )
), IBattleModule {
    override fun calculateDamage(attackerStr: Int, defenderDef: Int): Int {
        return (attackerStr - defenderDef / 2).coerceAtLeast(1)
    }

    override fun executeTurn(playerAction: String, enemyAction: String): Map<String, Any> {
        return mapOf(
            "playerAction" to playerAction,
            "enemyAction" to enemyAction,
            "timestamp" to java.lang.System.currentTimeMillis()
        )
    }
}

/**
 * Cards Micro-Module
 */
class CardsModule : BaseArcanumModule(
    ModuleManifest(
        id = "cards",
        name = "Cards & Evolution Module",
        version = "1.0.0",
        description = "Manages card entities, decks, leveling, and evolution.",
        eventsPublished = listOf("card_upgraded", "card_transcended"),
        eventsSubscribed = listOf("reward_claimed")
    )
), ICardsModule {
    override fun canEvolveCard(cardLevel: Int, copiesCount: Int): Boolean {
        return cardLevel >= 10 && copiesCount >= 3
    }

    override fun calculateEvolvedStats(baseStr: Int, level: Int, tier: Int): Int {
        return baseStr + (level * 2) + (tier * 10)
    }
}

/**
 * Inventory & Crafting Micro-Module
 */
class InventoryModule : BaseArcanumModule(
    ModuleManifest(
        id = "inventory",
        name = "Inventory & Crafting Module",
        version = "1.0.0",
        description = "Manages items, equipment, and crafting recipes.",
        eventsPublished = listOf("item_crafted", "item_used"),
        eventsSubscribed = listOf("loot_dropped")
    )
), IInventoryModule {
    override fun canCraftItem(recipeId: String, currentIngredients: Map<String, Int>): Boolean {
        return currentIngredients.values.all { it > 0 }
    }
}

/**
 * Quests & Diary Micro-Module
 */
class QuestModule : BaseArcanumModule(
    ModuleManifest(
        id = "quest",
        name = "Quest & Achievements Module",
        version = "1.0.0",
        description = "Tracks quests, achievements, and diary entries.",
        eventsPublished = listOf("quest_completed", "achievement_unlocked"),
        eventsSubscribed = listOf("battle_end", "item_crafted")
    )
), IQuestModule {
    override fun evaluateProgress(statKey: String, currentVal: Int, targetVal: Int): Boolean {
        return currentVal >= targetVal
    }
}

/**
 * Save & Cloud Sync Micro-Module
 */
class SaveSyncModule : BaseArcanumModule(
    ModuleManifest(
        id = "save_sync",
        name = "Save & Cross-Platform Sync Module",
        version = "1.0.0",
        description = "Handles JSON export/import and offline persistence.",
        eventsPublished = listOf("save_exported", "save_imported"),
        eventsSubscribed = listOf("auto_save")
    )
), ISaveSyncModule {
    override fun serializeState(stateData: Map<String, Any>): String {
        return stateData.entries.joinToString(prefix = "{", postfix = "}") { "\"${it.key}\":\"${it.value}\"" }
    }
}
