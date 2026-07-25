package com.example.core.engine

/**
 * Universal System Interface in Arcanum ECS.
 * Operates on entities with specific components during game loop or turn steps.
 */
interface System {
    val name: String
    val priority: Int get() = 0
    fun update(entities: List<Entity>, deltaTime: Float = 0f)
}

/**
 * System Manager managing ECS Systems execution order and lifecycle.
 */
class SystemManager {
    private val systems = mutableListOf<System>()

    fun addSystem(system: System): SystemManager {
        systems.add(system)
        systems.sortBy { it.priority }
        return this
    }

    fun removeSystem(system: System): Boolean {
        return systems.remove(system)
    }

    fun updateAll(entities: List<Entity>, deltaTime: Float = 0f) {
        systems.forEach { system ->
            try {
                system.update(entities, deltaTime)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun clear() {
        systems.clear()
    }
}
