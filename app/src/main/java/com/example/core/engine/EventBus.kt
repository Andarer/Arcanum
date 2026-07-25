package com.example.core.engine

/**
 * Base Event class for Arcanum Core Event System.
 */
open class ArcanumEvent(
    val type: String,
    val sourceModuleId: String = "system",
    val payload: Map<String, Any> = emptyMap(),
    val timestamp: Long = java.lang.System.currentTimeMillis()
)

typealias EventListener = (ArcanumEvent) -> Unit

/**
 * Decoupled Event Bus for micro-module communication.
 */
class EventBus {
    private val listeners = mutableMapOf<String, MutableList<EventListener>>()

    fun subscribe(eventType: String, listener: EventListener) {
        listeners.getOrPut(eventType) { mutableListOf() }.add(listener)
    }

    fun unsubscribe(eventType: String, listener: EventListener) {
        listeners[eventType]?.remove(listener)
    }

    fun publish(event: ArcanumEvent) {
        listeners[event.type]?.forEach { listener ->
            try {
                listener(event)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun clear() {
        listeners.clear()
    }
}
