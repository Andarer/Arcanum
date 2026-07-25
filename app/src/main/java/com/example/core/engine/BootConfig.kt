package com.example.core.engine

/**
 * World / Game composition boot configuration.
 * Allows defining games dynamically via boot.json (e.g. RPG, Shooter, Card Game, Sandbox).
 */
data class BootConfig(
    val gameTitle: String = "Arcanum MMORPG Engine",
    val gameVersion: String = "0.5.0",
    val activeModules: List<String> = listOf("battle", "cards", "inventory", "quest", "save_sync"),
    val customSettings: Map<String, String> = emptyMap()
)

/**
 * Engine Bootstrapper
 */
object ArcanumBootstrapper {
    fun boot(config: BootConfig = BootConfig()): ArcanumEngine {
        // Register requested modules
        if (config.activeModules.contains("battle")) ArcanumEngine.registerModule(com.example.core.modules.BattleModule())
        if (config.activeModules.contains("cards")) ArcanumEngine.registerModule(com.example.core.modules.CardsModule())
        if (config.activeModules.contains("inventory")) ArcanumEngine.registerModule(com.example.core.modules.InventoryModule())
        if (config.activeModules.contains("quest")) ArcanumEngine.registerModule(com.example.core.modules.QuestModule())
        if (config.activeModules.contains("save_sync")) ArcanumEngine.registerModule(com.example.core.modules.SaveSyncModule())

        ArcanumEngine.init()
        return ArcanumEngine
    }
}
