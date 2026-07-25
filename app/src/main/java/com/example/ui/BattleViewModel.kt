package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.engine.ArcanumEngine
import com.example.core.engine.ArcanumEvent
import com.example.audio.SoundManager
import com.example.data.CardEntity
import com.example.data.InventoryEntity
import com.example.data.PlayerStatsEntity
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID

class BattleViewModel(application: Application) : AndroidViewModel(application) {

    private val soundManager = SoundManager(application)

    private val _battleState = MutableStateFlow<BattleState?>(null)
    val battleState: StateFlow<BattleState?> = _battleState.asStateFlow()

    private val _floatingDamages = MutableStateFlow<List<FloatingDamage>>(emptyList())
    val floatingDamages: StateFlow<List<FloatingDamage>> = _floatingDamages.asStateFlow()

    private val defaultEnemies = listOf(
        EnemyModel("Гоблин-разбойник", "goblin", 65, 65, 12, 4, 35, 30),
        EnemyModel("Теневой Огр", "orc", 110, 110, 18, 8, 70, 60),
        EnemyModel("Ледяной Дракон", "dragon", 160, 160, 24, 12, 120, 100),
        EnemyModel("Некромант", "lich", 90, 90, 16, 6, 85, 75)
    )

    fun startBattle(playerStats: PlayerStatsEntity, enemyOverride: EnemyModel? = null) {
        val enemy = enemyOverride ?: defaultEnemies.random()
        ArcanumEngine.context.eventBus.publish(
            ArcanumEvent(type = "battle_start", sourceModuleId = "battle", payload = mapOf("enemy" to enemy.name))
        )
        _battleState.value = BattleState(
            enemy = enemy,
            playerHp = playerStats.hp,
            playerHpMax = playerStats.hpMax,
            playerMp = playerStats.mp,
            playerMpMax = playerStats.mpMax,
            enemyHp = enemy.hp,
            enemyHpMax = enemy.hp,
            isPlayerTurn = true,
            turnNumber = 1,
            log = listOf(
                BattleLogEntry(type = "system", text = "⚔ Сражение началось! Появляется ${enemy.name}."),
                BattleLogEntry(type = "system", text = "⌛ Ход 1: Ваш ход. Выберите действие или разыграйте карту.")
            )
        )
    }

    private fun triggerFloatingDamage(text: String, colorHex: String, isPlayerTarget: Boolean) {
        val fd = FloatingDamage(text = text, colorHex = colorHex, isPlayerTarget = isPlayerTarget)
        _floatingDamages.value = _floatingDamages.value + fd
        viewModelScope.launch {
            delay(1100)
            _floatingDamages.value = _floatingDamages.value.filter { it.id != fd.id }
        }
    }

    fun performPlayerAction(
        action: String,
        playerStats: PlayerStatsEntity,
        inventory: List<InventoryEntity>,
        onUpdateInventory: (String, Int) -> Unit
    ) {
        val b = _battleState.value ?: return
        if (b.isFinished || !b.isPlayerTurn) return

        val updatedLog = b.log.toMutableList()
        var pDmg = 0

        when (action) {
            "attack" -> {
                val base = playerStats.str + (0..5).random()
                val isCrit = (1..100).random() <= 18
                pDmg = if (isCrit) base * 2 else base
                pDmg = maxOf(1, pDmg - b.enemy.def / 2)

                if (isCrit) {
                    soundManager.playCrit()
                    updatedLog.add(BattleLogEntry(type = "crit", text = "⚡ КРИТИЧЕСКИЙ УДАР! Нанесено $pDmg урона!"))
                    triggerFloatingDamage("$pDmg!", "#E0A84A", isPlayerTarget = false)
                } else {
                    soundManager.playAttack()
                    updatedLog.add(BattleLogEntry(type = "damage", text = "⚔ Вы наносите $pDmg урона противнику."))
                    triggerFloatingDamage(pDmg.toString(), "#E05A6A", isPlayerTarget = false)
                }
            }
            "heavy" -> {
                if ((1..100).random() <= 20) {
                    soundManager.playClick()
                    updatedLog.add(BattleLogEntry(type = "miss", text = "💨 Мощный удар промахивается!"))
                } else {
                    val base = (playerStats.str * 1.8).toInt() + (0..8).random()
                    val isCrit = (1..100).random() <= 22
                    pDmg = if (isCrit) base * 2 else base
                    pDmg = maxOf(1, pDmg - b.enemy.def / 2)

                    if (isCrit) {
                        soundManager.playCrit()
                        updatedLog.add(BattleLogEntry(type = "crit", text = "⚡ СОКРУШИТЕЛЬНЫЙ КРИТ! $pDmg урона!"))
                        triggerFloatingDamage("$pDmg!", "#E0A84A", isPlayerTarget = false)
                    } else {
                        soundManager.playAttack()
                        updatedLog.add(BattleLogEntry(type = "damage", text = "⚡ Тяжелая атака! $pDmg урона."))
                        triggerFloatingDamage(pDmg.toString(), "#E05A6A", isPlayerTarget = false)
                    }
                }
            }
            "defend" -> {
                soundManager.playClick()
                updatedLog.add(BattleLogEntry(type = "system", text = "🛡 Вы принимаете защитную стойку (+50% к блоку)."))
                _battleState.value = b.copy(isDefending = true, log = updatedLog)
                endTurn()
                return
            }
            "potion" -> {
                val potionItem = inventory.find { it.id == "inv-potion" && it.count > 0 }
                if (potionItem == null) {
                    return
                }
                soundManager.playHeal()
                val healVal = 35
                val newPlayerHp = minOf(b.playerHpMax, b.playerHp + healVal)
                updatedLog.add(BattleLogEntry(type = "heal", text = "✚ Вы выпили зелье здоровья: +$healVal HP."))
                triggerFloatingDamage("+$healVal", "#6FD49A", isPlayerTarget = true)
                onUpdateInventory(potionItem.id, potionItem.count - 1)

                _battleState.value = b.copy(playerHp = newPlayerHp, log = updatedLog)
                endTurn()
                return
            }
        }

        val newEnemyHp = maxOf(0, b.enemyHp - pDmg)
        if (newEnemyHp <= 0) {
            updatedLog.add(BattleLogEntry(type = "system", text = "🏆 ${b.enemy.name} повержен! Победа!"))
            _battleState.value = b.copy(
                enemyHp = 0,
                log = updatedLog,
                isFinished = true,
                playerWon = true
            )
            return
        }

        _battleState.value = b.copy(enemyHp = newEnemyHp, log = updatedLog)
    }

    fun useDeckCard(
        index: Int,
        deck: List<CardEntity>
    ) {
        val b = _battleState.value ?: return
        if (b.isFinished || !b.isPlayerTurn || b.usedDeckIndices.contains(index)) return
        if (index !in deck.indices) return

        val card = deck[index]
        val abilityName = card.abilityName ?: card.name
        if (b.playerMp < card.abilityCost) return

        soundManager.playMagic()
        val newMp = b.playerMp - card.abilityCost
        val updatedLog = b.log.toMutableList()
        val used = b.usedDeckIndices + index

        var currentEnemyHp = b.enemyHp
        var currentPlayerHp = b.playerHp
        var tempDef = b.tempDefBuff

        // Trigger card movement/scaling animation state
        _battleState.value = b.copy(playedCardAnimation = card)

        viewModelScope.launch {
            delay(500)
            val stateAfterAnim = _battleState.value ?: return@launch
            _battleState.value = stateAfterAnim.copy(playedCardAnimation = null)
        }

        when (card.abilityType) {
            "damage" -> {
                currentEnemyHp = maxOf(0, currentEnemyHp - card.abilityValue)
                updatedLog.add(BattleLogEntry(type = "card", text = "✦ Карта [${card.name}]: $abilityName! ${card.abilityValue} урона!"))
                triggerFloatingDamage("${card.abilityValue}", "#8B6FD4", isPlayerTarget = false)
            }
            "heal" -> {
                currentPlayerHp = minOf(b.playerHpMax, currentPlayerHp + card.abilityValue)
                updatedLog.add(BattleLogEntry(type = "heal", text = "✚ Карта [${card.name}]: $abilityName! +${card.abilityValue} HP!"))
                triggerFloatingDamage("+${card.abilityValue}", "#6FD49A", isPlayerTarget = true)
            }
            "drain" -> {
                currentEnemyHp = maxOf(0, currentEnemyHp - card.abilityValue)
                val healAmt = card.abilityValue / 2
                currentPlayerHp = minOf(b.playerHpMax, currentPlayerHp + healAmt)
                updatedLog.add(BattleLogEntry(type = "card", text = "✦ Карта [${card.name}]: $abilityName! ${card.abilityValue} урона и +$healAmt HP."))
                triggerFloatingDamage("${card.abilityValue}", "#8B6FD4", isPlayerTarget = false)
            }
            "buff" -> {
                tempDef += card.abilityValue
                updatedLog.add(BattleLogEntry(type = "system", text = "🛡 Карта [${card.name}]: $abilityName! Защита +${card.abilityValue}."))
            }
            "mana" -> {
                val mpBoost = minOf(b.playerMpMax, newMp + card.abilityValue)
                updatedLog.add(BattleLogEntry(type = "heal", text = "✦ Карта [${card.name}]: $abilityName! +${card.abilityValue} MP."))
                _battleState.value = b.copy(
                    playerMp = mpBoost,
                    enemyHp = currentEnemyHp,
                    playerHp = currentPlayerHp,
                    tempDefBuff = tempDef,
                    usedDeckIndices = used,
                    log = updatedLog
                )
                return
            }
            else -> {
                currentEnemyHp = maxOf(0, currentEnemyHp - card.abilityValue)
                updatedLog.add(BattleLogEntry(type = "card", text = "✦ Карта [${card.name}]: $abilityName! ${card.abilityValue} урона."))
                triggerFloatingDamage("${card.abilityValue}", "#8B6FD4", isPlayerTarget = false)
            }
        }

        if (currentEnemyHp <= 0) {
            updatedLog.add(BattleLogEntry(type = "system", text = "🏆 ${b.enemy.name} повержен заклинанием! Победа!"))
            _battleState.value = b.copy(
                playerMp = newMp,
                enemyHp = 0,
                playerHp = currentPlayerHp,
                tempDefBuff = tempDef,
                usedDeckIndices = used,
                log = updatedLog,
                isFinished = true,
                playerWon = true
            )
            return
        }

        _battleState.value = b.copy(
            playerMp = newMp,
            enemyHp = currentEnemyHp,
            playerHp = currentPlayerHp,
            tempDefBuff = tempDef,
            usedDeckIndices = used,
            log = updatedLog
        )
    }

    fun endTurn() {
        val b = _battleState.value ?: return
        if (b.isFinished || !b.isPlayerTurn) return

        val updatedLog = b.log.toMutableList()
        updatedLog.add(BattleLogEntry(type = "system", text = "⌛ Ход ${b.turnNumber} завершен. Ход Противника..."))

        _battleState.value = b.copy(
            isPlayerTurn = false,
            log = updatedLog
        )

        viewModelScope.launch {
            delay(600)
            executeEnemyTurn()
        }
    }

    private fun executeEnemyTurn() {
        val b = _battleState.value ?: return
        if (b.isFinished || b.enemyHp <= 0) return

        val updatedLog = b.log.toMutableList()
        val baseDmg = b.enemy.str + (0..6).random()
        val totalDef = b.tempDefBuff
        var enemyDmg = maxOf(1, baseDmg - totalDef / 2)

        if (b.isDefending) {
            enemyDmg = maxOf(1, enemyDmg / 2)
        }

        val isCrit = (1..100).random() <= 12
        if (isCrit) enemyDmg *= 2

        val newPlayerHp = maxOf(0, b.playerHp - enemyDmg)

        soundManager.playHit()
        if (isCrit) {
            updatedLog.add(BattleLogEntry(type = "crit", text = "💥 ${b.enemy.name} наносит КРИТ! $enemyDmg урона!"))
        } else {
            updatedLog.add(BattleLogEntry(type = "damage", text = "🔮 ${b.enemy.name} атакует на $enemyDmg урона."))
        }
        triggerFloatingDamage(enemyDmg.toString(), "#E05A6A", isPlayerTarget = true)

        if (newPlayerHp <= 0) {
            updatedLog.add(BattleLogEntry(type = "system", text = "💀 Здоровье героя достигло 0. Поражение в бою..."))
            _battleState.value = b.copy(
                playerHp = 0,
                isDefending = false,
                tempDefBuff = 0,
                log = updatedLog,
                isFinished = true,
                playerWon = false
            )
        } else {
            val nextTurn = b.turnNumber + 1
            val restoredMp = minOf(b.playerMpMax, b.playerMp + 12)
            updatedLog.add(BattleLogEntry(type = "system", text = "⚔ Ход $nextTurn: Ход Игрока. Восстановлено +12 MP."))

            _battleState.value = b.copy(
                playerHp = newPlayerHp,
                playerMp = restoredMp,
                isPlayerTurn = true,
                turnNumber = nextTurn,
                isDefending = false,
                tempDefBuff = 0,
                usedDeckIndices = emptySet(), // Refresh cards in hand for new turn
                log = updatedLog
            )
        }
    }
}
