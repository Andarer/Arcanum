package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cards")
data class CardEntity(
    @PrimaryKey val id: String,
    val name: String,
    val type: String, // "hero", "creature", "item"
    val rarity: String, // "common", "uncommon", "rare", "epic", "legendary", "mythic"
    val hp: Int,
    val mp: Int,
    val str: Int,
    val def: Int,
    val level: Int = 1,
    val xp: Int = 0,
    val desc: String,
    val art: String, // "warrior", "archer", "mage", "necro", "dragon", "goblin", "sword", "shield", "potion", "ring", "crystal", "pet"
    val abilityName: String? = null,
    val abilityType: String? = null, // "damage", "heal", "drain", "buff", "mana"
    val abilityValue: Int = 0,
    val abilityCost: Int = 0,
    val abilityDesc: String? = null
)

@Entity(tableName = "inventory")
data class InventoryEntity(
    @PrimaryKey val id: String,
    val name: String,
    val count: Int,
    val art: String,
    val useType: String?, // "heal", "mana", "buff_str", "damage"
    val value: Int = 0
)

@Entity(tableName = "player_stats")
data class PlayerStatsEntity(
    @PrimaryKey val id: Int = 1,
    val level: Int = 1,
    val xp: Int = 0,
    val gold: Int = 100,
    val hp: Int = 100,
    val hpMax: Int = 100,
    val mp: Int = 50,
    val mpMax: Int = 50,
    val str: Int = 10,
    val def: Int = 5,
    val battlesWon: Int = 0,
    val battlesLost: Int = 0,
    val cardsCreated: Int = 0,
    val potionsUsed: Int = 0,
    val crits: Int = 0,
    val crafted: Int = 0,
    val chestsOpened: Int = 0,
    val visitedLocations: String = "" // comma separated keys
)

@Entity(tableName = "deck")
data class DeckEntity(
    @PrimaryKey val cardId: String,
    val slotIndex: Int
)

@Entity(tableName = "quests")
data class QuestEntity(
    @PrimaryKey val id: String,
    val name: String,
    val desc: String,
    val icon: String,
    val target: Int,
    val statKey: String,
    val xpReward: Int = 0,
    val goldReward: Int = 0,
    val isCompleted: Boolean = false
)

@Entity(tableName = "achievements")
data class AchievementEntity(
    @PrimaryKey val id: String,
    val name: String,
    val desc: String,
    val icon: String,
    val isUnlocked: Boolean = false
)

@Entity(tableName = "diary")
data class DiaryEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val type: String, // "battle", "reward", "achievement", "system", "craft"
    val icon: String,
    val text: String,
    val time: String,
    val timestamp: Long = System.currentTimeMillis()
)
