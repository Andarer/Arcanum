# Arcanum Universal Core API & Interface Specification v2.0

## 1. Engine Entry Points & Interface Hierarchy

### Kotlin / Android Core (`ArcanumEngine`)
```kotlin
// Initialize and boot engine with composition configuration
ArcanumBootstrapper.boot(BootConfig(activeModules = listOf("battle", "cards", "inventory", "quest", "save_sync", "editor", "ui_render", "alp_link")))

// Access global decoupled Event Bus
ArcanumEngine.context.eventBus.publish(
    ArcanumEvent(type = "battle_start", sourceModuleId = "battle", payload = mapOf("enemy" to "Dragon"))
)

// Retrieve typed micro-modules via contract interfaces
val battleModule = ArcanumEngine.context.registry.getModuleTyped<IBattleModule>("battle")
val damage = battleModule?.calculateDamage(attackerStr = 25, defenderDef = 10)
```

---

## 2. Arcanum Link Protocol (ALP v2.0) API

### Kotlin ALP API (`ArcanumLinkProtocol.kt`)
```kotlin
// Create message
val msg = ALPMessage(
    entityType = "card",
    entityId = "dragon_01",
    payload = mapOf("name" to "Fire Dragon", "hp" to "300", "str" to "50")
)

// Export to QR payload or Universal Link
val qrText = msg.toQrPayload()
val linkUrl = msg.toUniversalLink()

// Convert ECS Entity to ALP and back
val alpMsg = ArcanumLinkAdapter.entityToALP(entity)
val importedEntity = ArcanumLinkAdapter.alpToEntity(alpMsg)
```

### JavaScript ALP API (`ArcanumLinkProtocolJS`)
```javascript
// Export entity to ALP
const alpMsg = ArcanumLinkProtocolJS.exportToALPMessage("card", "hero_1", { name: "Paladin", hp: "150" });

// Convert to Universal Link or QR
const universalLink = ArcanumLinkProtocolJS.toUniversalLink(alpMsg);
const qrPayload = ArcanumLinkProtocolJS.toQrPayload(alpMsg);

// Parse QR Code payload
const parsed = ArcanumLinkProtocolJS.parseQrPayload(qrPayload);
```

---

## 3. Entity Component System (ECS) & Pipeline API

### Entity & Component API
```kotlin
val heroEntity = Entity(id = "player_1", name = "Paladin")
    .addComponent(IdentityComponent(entityType = "hero", rarity = "legendary"))
    .addComponent(StatsComponent(hp = 120, hpMax = 120, mp = 60, mpMax = 60))

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
