package com.example.core.protocol

import com.example.core.engine.Entity
import com.example.core.engine.components.IdentityComponent
import com.example.core.engine.components.StatsComponent
import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

/**
 * Arcanum Link Protocol (ALP) v2.0
 * Universal data serialization, QR payload format, and cross-device sharing protocol for Arcanum Entities,
 * Cards, Worlds, and Modules across Web, PWA, Android, Desktop, and CLI clients.
 */
data class ALPMessage(
    val protocolVersion: String = "2.0",
    val entityType: String,
    val entityId: String,
    val payload: Map<String, String>,
    val timestamp: Long = System.currentTimeMillis()
) {
    fun toUrlEncodedData(): String {
        val rawJson = buildString {
            append("{")
            append("\"v\":\"$protocolVersion\",")
            append("\"type\":\"$entityType\",")
            append("\"id\":\"$entityId\",")
            append("\"payload\":{")
            append(payload.entries.joinToString(",") { "\"${it.key}\":\"${it.value.replace("\"", "\\\"")}\"" })
            append("}")
            append("}")
        }
        return URLEncoder.encode(rawJson, StandardCharsets.UTF_8.toString())
    }

    fun toUniversalLink(): String {
        return "arcanum://link?data=${toUrlEncodedData()}"
    }

    fun toQrPayload(): String {
        return "ALP2:$entityType:$entityId:" + payload.entries.joinToString(";") { "${it.key}=${it.value}" }
    }

    companion object {
        fun fromUrlEncodedData(encoded: String): ALPMessage? {
            return runCatching {
                val decoded = URLDecoder.decode(encoded, StandardCharsets.UTF_8.toString())
                val type = extractJsonField(decoded, "type") ?: "unknown"
                val id = extractJsonField(decoded, "id") ?: "entity_${System.currentTimeMillis()}"
                
                ALPMessage(
                    entityType = type,
                    entityId = id,
                    payload = mapOf("raw" to decoded)
                )
            }.getOrNull()
        }

        fun fromQrPayload(qrText: String): ALPMessage? {
            return runCatching {
                if (!qrText.startsWith("ALP2:")) return null
                val parts = qrText.split(":", limit = 4)
                if (parts.size < 4) return null
                
                val type = parts[1]
                val id = parts[2]
                val kvPairs = parts[3].split(";").mapNotNull {
                    val kv = it.split("=", limit = 2)
                    if (kv.size == 2) kv[0] to kv[1] else null
                }.toMap()

                ALPMessage(
                    entityType = type,
                    entityId = id,
                    payload = kvPairs
                )
            }.getOrNull()
        }

        private fun extractJsonField(json: String, fieldName: String): String? {
            val key = "\"$fieldName\":\""
            val start = json.indexOf(key)
            if (start == -1) return null
            val valueStart = start + key.length
            val valueEnd = json.indexOf("\"", valueStart)
            if (valueEnd == -1) return null
            return json.substring(valueStart, valueEnd)
        }
    }
}

/**
 * Helper to convert Arcanum ECS Entity to ALPMessage and vice-versa.
 */
object ArcanumLinkAdapter {
    fun entityToALP(entity: Entity): ALPMessage {
        val identity = entity.getComponent(IdentityComponent::class.java)
        val stats = entity.getComponent(StatsComponent::class.java)

        val payloadMap = mutableMapOf<String, String>()
        payloadMap["name"] = entity.name
        identity?.let {
            payloadMap["entityType"] = it.entityType
            payloadMap["rarity"] = it.rarity
            payloadMap["artKey"] = it.artKey
            payloadMap["description"] = it.description
        }
        stats?.let {
            payloadMap["hp"] = it.hp.toString()
            payloadMap["mp"] = it.mp.toString()
            payloadMap["str"] = it.str.toString()
            payloadMap["def"] = it.def.toString()
        }

        return ALPMessage(
            entityType = identity?.entityType ?: "entity",
            entityId = entity.id,
            payload = payloadMap
        )
    }

    fun alpToEntity(message: ALPMessage): Entity {
        val entity = Entity(id = message.entityId, name = message.payload["name"] ?: "Shared Entity")
        entity.addComponent(
            IdentityComponent(
                entityType = message.payload["entityType"] ?: message.entityType,
                rarity = message.payload["rarity"] ?: "common",
                artKey = message.payload["artKey"] ?: "sword",
                description = message.payload["description"] ?: "Imported via Arcanum Link Protocol."
            )
        )
        val hp = message.payload["hp"]?.toIntOrNull() ?: 100
        val mp = message.payload["mp"]?.toIntOrNull() ?: 50
        val str = message.payload["str"]?.toIntOrNull() ?: 10
        val def = message.payload["def"]?.toIntOrNull() ?: 5

        entity.addComponent(StatsComponent(hp = hp, hpMax = hp, mp = mp, mpMax = mp, str = str, def = def))
        return entity
    }
}
