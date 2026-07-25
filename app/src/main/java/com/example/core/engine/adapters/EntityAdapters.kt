package com.example.core.engine.adapters

import com.example.core.engine.Entity
import com.example.core.engine.components.*
import com.example.data.*

/**
 * Adapter mapping Room Database Entities to Universal Core Engine ECS Entities.
 * Fulfills the Fractal Principle: Every game object is a Core Entity with Components.
 */
object EntityAdapters {

    fun CardEntity.toCoreEntity(): Entity {
        val entity = Entity(id = id, name = name)
            .addComponent(IdentityComponent(entityType = type, rarity = rarity, artKey = art, description = desc))
            .addComponent(StatsComponent(hp = hp, hpMax = hp, mp = mp, mpMax = mp, str = str, def = def, level = level, xp = xp))

        if (!abilityName.isNullOrEmpty()) {
            entity.addComponent(
                AbilityComponent(
                    name = abilityName,
                    type = abilityType ?: "damage",
                    value = abilityValue,
                    cost = abilityCost,
                    description = abilityDesc ?: ""
                )
            )
        }
        return entity
    }

    fun InventoryEntity.toCoreEntity(): Entity {
        return Entity(id = id, name = name)
            .addComponent(IdentityComponent(entityType = "item", artKey = art))
            .addComponent(ItemComponent(count = count, useType = useType, effectValue = value))
    }

    fun PlayerStatsEntity.toCoreEntity(): Entity {
        return Entity(id = "player_hero", name = "Player Hero")
            .addComponent(IdentityComponent(entityType = "hero", artKey = "warrior"))
            .addComponent(StatsComponent(hp = hp, hpMax = hpMax, mp = mp, mpMax = mpMax, str = str, def = def, level = level, xp = xp))
    }

    fun QuestEntity.toCoreEntity(): Entity {
        return Entity(id = id, name = name)
            .addComponent(IdentityComponent(entityType = "quest", artKey = icon, description = desc))
            .addComponent(
                QuestComponent(
                    target = target,
                    statKey = statKey,
                    xpReward = xpReward,
                    goldReward = goldReward,
                    isCompleted = isCompleted
                )
            )
    }
}
