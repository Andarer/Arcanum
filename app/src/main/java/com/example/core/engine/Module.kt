package com.example.core.engine

/**
 * Universal Manifest metadata for an Arcanum Micro-Module.
 * Mirrors module.json specification.
 */
data class ModuleManifest(
    val id: String,
    val name: String,
    val version: String,
    val description: String,
    val author: String = "Arcanum Core Team",
    val dependencies: List<String> = emptyList(),
    val permissions: List<String> = emptyList(),
    val eventsPublished: List<String> = emptyList(),
    val eventsSubscribed: List<String> = emptyList(),
    val category: String = "core"
)

/**
 * State of a micro-module lifecycle.
 */
enum class ModuleState {
    UNINITIALIZED,
    REGISTERED,
    INITIALIZED,
    ENABLED,
    DISABLED,
    DESTROYED
}

/**
 * Universal interface for all Arcanum Micro-Modules.
 * Any system (Battle, Inventory, Cards, Quest, Save, AI, Sound, etc.) implements this interface.
 */
interface ArcanumModule {
    val manifest: ModuleManifest
    val state: ModuleState

    fun onRegister(context: EngineContext)
    fun onInit()
    fun onEnable()
    fun onDisable()
    fun onDestroy()
}

/**
 * Abstract Base Module implementation providing common lifecycle state handling.
 */
abstract class BaseArcanumModule(
    override val manifest: ModuleManifest
) : ArcanumModule {
    override var state: ModuleState = ModuleState.UNINITIALIZED
        protected set

    protected lateinit var engineContext: EngineContext

    override fun onRegister(context: EngineContext) {
        this.engineContext = context
        state = ModuleState.REGISTERED
    }

    override fun onInit() {
        state = ModuleState.INITIALIZED
    }

    override fun onEnable() {
        state = ModuleState.ENABLED
    }

    override fun onDisable() {
        state = ModuleState.DISABLED
    }

    override fun onDestroy() {
        state = ModuleState.DESTROYED
    }
}
