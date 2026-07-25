package com.example.core.engine.components

import com.example.core.engine.Component

/**
 * Base Identity Component for Entities in Arcanum Engine.
 */
data class IdentityComponent(
    val entityType: String, // "hero", "creature", "item", "quest", "location"
    val rarity: String = "common", // "common", "rare", "epic", "legendary", "mythic"
    val artKey: String = "default",
    val description: String = ""
) : Component

/**
 * Core Stats Component (HP, MP, Strength, Defense, Level, XP)
 */
data class StatsComponent(
    var hp: Int = 100,
    var hpMax: Int = 100,
    var mp: Int = 50,
    var mpMax: Int = 50,
    var str: Int = 10,
    var def: Int = 5,
    var level: Int = 1,
    var xp: Int = 0
) : Component {
    val isAlive: Boolean get() = hp > 0
    fun takeDamage(amount: Int): Int {
        val actualDamage = (amount - def).coerceAtLeast(1)
        hp = (hp - actualDamage).coerceAtLeast(0)
        return actualDamage
    }
    fun heal(amount: Int) {
        hp = (hp + amount).coerceAtMost(hpMax)
    }
    fun consumeMp(cost: Int): Boolean {
        if (mp < cost) return false
        mp -= cost
        return true
    }
}

/**
 * Card Special Ability Component
 */
data class AbilityComponent(
    val name: String,
    val type: String, // "damage", "heal", "drain", "buff", "mana"
    val value: Int = 0,
    val cost: Int = 0,
    val description: String = ""
) : Component

/**
 * Inventory / Item Stack Component
 */
data class ItemComponent(
    var count: Int = 1,
    val useType: String? = null, // "heal", "mana", "buff_str", "damage"
    val effectValue: Int = 0
) : Component

/**
 * Quest / Achievement Component
 */
data class QuestComponent(
    val target: Int,
    val statKey: String,
    val xpReward: Int = 0,
    val goldReward: Int = 0,
    var currentProgress: Int = 0,
    var isCompleted: Boolean = false
) : Component
