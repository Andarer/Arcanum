package com.example.core.engine

import java.util.UUID

/**
 * Interface marker for ECS Components.
 */
interface Component

/**
 * Universal Entity in Arcanum Core Engine.
 * Represents Cards, Characters, Items, Locations, Quests, or Game Objects.
 */
open class Entity(
    val id: String = UUID.randomUUID().toString(),
    var name: String = "Unnamed Entity"
) {
    private val components = mutableMapOf<Class<out Component>, Component>()

    fun <T : Component> addComponent(component: T): Entity {
        components[component.javaClass] = component
        return this
    }

    @Suppress("UNCHECKED_CAST")
    fun <T : Component> getComponent(componentClass: Class<T>): T? {
        return components[componentClass] as? T
    }

    fun <T : Component> hasComponent(componentClass: Class<T>): Boolean {
        return components.containsKey(componentClass)
    }

    fun <T : Component> removeComponent(componentClass: Class<T>): Component? {
        return components.remove(componentClass)
    }

    fun getAllComponents(): List<Component> = components.values.toList()
}
