# Arcanum Universal Core API & Interface Specification v0.7.0

## 1. Engine Entry Points & Interface Hierarchy

### Kotlin / Android Core (`ArcanumEngine`)
```kotlin
// Initialize and boot engine with composition configuration
ArcanumBootstrapper.boot(BootConfig(activeModules = listOf("battle", "cards", "inventory", "quest", "save_sync", "editor", "ui_render")))

// Access global decoupled Event Bus
ArcanumEngine.context.eventBus.publish(
    ArcanumEvent(type = "battle_start", sourceModuleId = "battle", payload = mapOf("enemy" to "Dragon"))
)

// Retrieve typed micro-modules via contract interfaces
val battleModule = ArcanumEngine.context.registry.getModuleTyped<IBattleModule>("battle")
val damage = battleModule?.calculateDamage(attackerStr = 25, defenderDef = 10)
```

### JavaScript / PWA Web Core (`ArcanumEngineJS`)
```javascript
// Boot engine from boot.json
await window.ArcanumEngine.boot("./boot.json");

// Subscribe to events
window.ArcanumEngine.context.eventBus.subscribe("battle_start", (event) => {
    console.log("Battle started:", event.payload);
});
```

---

## 2. Entity Component System (ECS) & Pipeline API

### Entity & Component API
```kotlin
// Create a universal entity
val heroEntity = Entity(id = "player_1", name = "Paladin")
    .addComponent(IdentityComponent(entityType = "hero", rarity = "legendary"))
    .addComponent(StatsComponent(hp = 120, hpMax = 120, mp = 60, mpMax = 60))

// Query components
val stats = heroEntity.getComponent(StatsComponent::class.java)
stats?.takeDamage(15)
```

### ECS System API
```kotlin
interface System {
    val name: String
    val priority: Int
    fun update(entities: List<Entity>, deltaTime: Float)
}

val systemManager = SystemManager()
systemManager.addSystem(object : System {
    override val name = "RegenerationSystem"
    override val priority = 1
    override fun update(entities: List<Entity>, deltaTime: Float) {
        entities.mapNotNull { it.getComponent(StatsComponent::class.java) }
            .forEach { it.heal(1) }
    }
})
```

---

## 3. Micro-Module Lifecycle & Typed Contract APIs

```kotlin
// Base Module Lifecycle Interface
interface ArcanumModule {
    val manifest: ModuleManifest
    val state: ModuleState

    fun onRegister(context: EngineContext)
    fun onInit()
    fun onEnable()
    fun onDisable()
    fun onDestroy()
}

// Typed Module Interfaces
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

interface IEditorModule : ArcanumModule {
    fun validateCardRecipe(cardData: Map<String, Any>): Boolean
}

interface IUIRenderModule : ArcanumModule {
    fun getRenderStyle(profile: String): RenderStyleSpec
}
```
