package com.example.core.engine

/**
 * Shared Engine Context passed to all Arcanum Micro-Modules.
 */
class EngineContext(
    val eventBus: EventBus = EventBus(),
    val registry: ModuleRegistry = ModuleRegistry()
) {
    private val globalStorage = mutableMapOf<String, Any>()

    fun setGlobal(key: String, value: Any) {
        globalStorage[key] = value
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> getGlobal(key: String): T? {
        return globalStorage[key] as? T
    }
}

/**
 * Registry managing registration, dependency checking, and lifecycle of all modules.
 */
class ModuleRegistry {
    private val modules = mutableMapOf<String, ArcanumModule>()

    fun register(module: ArcanumModule, context: EngineContext): Boolean {
        if (modules.containsKey(module.manifest.id)) return false
        modules[module.manifest.id] = module
        module.onRegister(context)
        return true
    }

    fun unregister(id: String): Boolean {
        val module = modules[id] ?: return false
        if (module.state == ModuleState.ENABLED) {
            module.onDisable()
        }
        module.onDestroy()
        modules.remove(id)
        return true
    }

    fun getModule(id: String): ArcanumModule? = modules[id]

    @Suppress("UNCHECKED_CAST")
    fun <T : ArcanumModule> getModuleTyped(id: String): T? = modules[id] as? T

    fun getAllModules(): List<ArcanumModule> = modules.values.toList()

    fun initializeAll() {
        // Topological sort or simple dependency initialization
        modules.values.filter { it.state == ModuleState.REGISTERED }.forEach { module ->
            module.onInit()
            module.onEnable()
        }
    }

    fun disableAll() {
        modules.values.filter { it.state == ModuleState.ENABLED }.forEach { module ->
            module.onDisable()
        }
    }
}

/**
 * Main Singleton Facade for Arcanum Core Universal Engine.
 */
object ArcanumEngine {
    val context = EngineContext()

    fun registerModule(module: ArcanumModule): Boolean {
        return context.registry.register(module, context)
    }

    fun getModule(id: String): ArcanumModule? {
        return context.registry.getModule(id)
    }

    fun init() {
        context.registry.initializeAll()
        context.eventBus.publish(ArcanumEvent(type = "engine_initialized", sourceModuleId = "core"))
    }

    fun shutdown() {
        context.registry.disableAll()
        context.eventBus.publish(ArcanumEvent(type = "engine_shutdown", sourceModuleId = "core"))
    }
}
