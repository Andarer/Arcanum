package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.audio.SoundManager
import com.example.data.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import org.json.JSONArray
import org.json.JSONObject

data class EnemyModel(
    val name: String,
    val art: String,
    val hp: Int,
    val maxHp: Int,
    val str: Int,
    val def: Int,
    val xpReward: Int,
    val goldReward: Int
)

data class BattleLogEntry(
    val id: String = UUID.randomUUID().toString(),
    val type: String, // "system", "damage", "heal", "crit", "miss", "card"
    val text: String
)

data class BattleState(
    val enemy: EnemyModel,
    val playerHp: Int,
    val playerHpMax: Int,
    val playerMp: Int,
    val playerMpMax: Int,
    val enemyHp: Int,
    val enemyHpMax: Int,
    val isPlayerTurn: Boolean = true,
    val turnNumber: Int = 1,
    val isDefending: Boolean = false,
    val tempDefBuff: Int = 0,
    val usedDeckIndices: Set<Int> = emptySet(),
    val log: List<BattleLogEntry> = emptyList(),
    val isFinished: Boolean = false,
    val playerWon: Boolean = false,
    val playedCardAnimation: CardEntity? = null
)

data class FloatingDamage(
    val id: String = UUID.randomUUID().toString(),
    val text: String,
    val colorHex: String,
    val isPlayerTarget: Boolean
)

data class ShopItem(
    val id: String,
    val name: String,
    val desc: String,
    val art: String,
    val price: Int,
    val stock: Int,
    val givesItem: InventoryEntity
)

class ArcanumViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: ArcanumRepository
    val soundManager = SoundManager(application)

    // UI Settings
    val isLoading = MutableStateFlow(true)
    var isDarkTheme = MutableStateFlow(true)
    var isSoundEnabled = MutableStateFlow(true)
    var toastMessage = MutableStateFlow<String?>(null)

    // Room Flows
    val cards = MutableStateFlow<List<CardEntity>>(emptyList())
    val inventory = MutableStateFlow<List<InventoryEntity>>(emptyList())
    val playerStats = MutableStateFlow(PlayerStatsEntity())
    val deck = MutableStateFlow<List<CardEntity>>(emptyList())
    val quests = MutableStateFlow<List<QuestEntity>>(emptyList())
    val achievements = MutableStateFlow<List<AchievementEntity>>(emptyList())
    val diaryEntries = MutableStateFlow<List<DiaryEntity>>(emptyList())

    // Battle State
    val battleState = MutableStateFlow<BattleState?>(null)
    val floatingDamages = MutableStateFlow<List<FloatingDamage>>(emptyList())

    // Crafting selection
    val craftSelection = MutableStateFlow<List<CardEntity>>(emptyList())

    // Shop stock local state
    val shopStockMap = MutableStateFlow<Map<String, Int>>(
        mapOf(
            "shop-potion" to 10,
            "shop-mana" to 8,
            "shop-food" to 20,
            "shop-scroll" to 5,
            "shop-bomb" to 3,
            "shop-gem" to 2,
            "shop-key" to 1,
            "shop-elixir" to 1
        )
    )

    private val enemiesList = listOf(
        EnemyModel("ТЁМНЫЙ МАГ", "necro", 80, 80, 12, 6, 50, 40),
        EnemyModel("ГОБЛИН-ВОЖАК", "goblin", 60, 60, 10, 4, 30, 25),
        EnemyModel("ДРАКОНЧИК", "dragon", 120, 120, 16, 10, 80, 70),
        EnemyModel("СКЕЛЕТ-ВОИН", "warrior", 70, 70, 11, 8, 40, 30),
        EnemyModel("ЛЕСНОЙ ДУХ", "pet", 90, 90, 14, 5, 60, 50),
        EnemyModel("МОРСКОЙ ЗМЕЙ", "dragon", 150, 150, 18, 12, 100, 90)
    )

    init {
        val db = ArcanumDatabase.getDatabase(application, viewModelScope)
        repository = ArcanumRepository(db)

        viewModelScope.launch {
            repository.cards.collect {
                cards.value = it
                if (isLoading.value) {
                    isLoading.value = false
                }
            }
        }
        viewModelScope.launch {
            repository.inventory.collect { inventory.value = it }
        }
        viewModelScope.launch {
            repository.playerStats.collect {
                if (it != null) playerStats.value = it
            }
        }
        viewModelScope.launch {
            combine(repository.deck, repository.cards) { deckEntities, cardEntities ->
                deckEntities.mapNotNull { deckEntity -> cardEntities.find { c -> c.id == deckEntity.cardId } }
            }.collect { deck.value = it }
        }
        viewModelScope.launch {
            repository.quests.collect { quests.value = it }
        }
        viewModelScope.launch {
            repository.achievements.collect { achievements.value = it }
        }
        viewModelScope.launch {
            repository.diaryEntries.collect { diaryEntries.value = it }
        }

        resetBattle(silent = true)
    }

    fun showToast(msg: String) {
        toastMessage.value = msg
    }

    fun clearToast() {
        toastMessage.value = null
    }

    fun toggleTheme() {
        soundManager.playClick()
        isDarkTheme.value = !isDarkTheme.value
    }

    fun toggleSound() {
        isSoundEnabled.value = !isSoundEnabled.value
        soundManager.isSoundEnabled = isSoundEnabled.value
        if (isSoundEnabled.value) soundManager.playClick()
    }

    fun resetBattle(silent: Boolean = false) {
        val enemy = enemiesList.random()
        val stats = playerStats.value
        battleState.value = BattleState(
            enemy = enemy,
            playerHp = stats.hp,
            playerHpMax = stats.hpMax,
            playerMp = stats.mp,
            playerMpMax = stats.mpMax,
            enemyHp = enemy.hp,
            enemyHpMax = enemy.hp,
            log = listOf(BattleLogEntry(type = "system", text = "⚔ Появляется ${enemy.name}! Сражение начинается."))
        )
        if (!silent) {
            soundManager.playClick()
            showToast("↺ Новый бой")
        }
    }

    fun triggerFloatingDmg(text: String, colorHex: String, isPlayerTarget: Boolean) {
        val fd = FloatingDamage(text = text, colorHex = colorHex, isPlayerTarget = isPlayerTarget)
        floatingDamages.value = floatingDamages.value + fd
        viewModelScope.launch {
            kotlinx.coroutines.delay(1200)
            floatingDamages.value = floatingDamages.value.filter { it.id != fd.id }
        }
    }

    fun battleAction(action: String) {
        val b = battleState.value ?: return
        if (b.isFinished) return

        var pDmg = 0
        var updatedLog = b.log.toMutableList()

        if (action == "attack") {
            val base = playerStats.value.str + (0..5).random()
            val isCrit = (1..100).random() <= 15
            pDmg = if (isCrit) base * 2 else base
            pDmg = maxOf(1, pDmg - b.enemy.def / 2)

            if (isCrit) {
                soundManager.playCrit()
                updatedLog.add(BattleLogEntry(type = "crit", text = "⚡ КРИТ! $pDmg урона!"))
                triggerFloatingDmg(text = pDmg.toString(), colorHex = "#E0A84A", isPlayerTarget = false)
                incrementStat("crits")
            } else {
                soundManager.playAttack()
                updatedLog.add(BattleLogEntry(type = "damage", text = "⚔ Ты наносишь $pDmg урона."))
                triggerFloatingDmg(text = pDmg.toString(), colorHex = "#E05A6A", isPlayerTarget = false)
            }
        } else if (action == "heavy") {
            if ((1..100).random() <= 25) {
                soundManager.playClick()
                updatedLog.add(BattleLogEntry(type = "miss", text = "⚡ Мощный удар промахился!"))
            } else {
                val base = (playerStats.value.str * 1.8).toInt() + (0..9).random()
                val isCrit = (1..100).random() <= 20
                pDmg = if (isCrit) base * 2 else base
                pDmg = maxOf(1, pDmg - b.enemy.def / 2)

                if (isCrit) {
                    soundManager.playCrit()
                    updatedLog.add(BattleLogEntry(type = "crit", text = "⚡ КРИТ. МОЩНЫЙ УДАР! $pDmg урона!"))
                    triggerFloatingDmg(text = pDmg.toString(), colorHex = "#E0A84A", isPlayerTarget = false)
                    incrementStat("crits")
                } else {
                    soundManager.playAttack()
                    updatedLog.add(BattleLogEntry(type = "damage", text = "⚡ Мощный удар! $pDmg урона!"))
                    triggerFloatingDmg(text = pDmg.toString(), colorHex = "#E0A84A", isPlayerTarget = false)
                }
            }
        } else if (action == "defend") {
            soundManager.playClick()
            updatedLog.add(BattleLogEntry(type = "system", text = "🛡 Ты принимаешь защитную стойку."))
            battleState.value = b.copy(isDefending = true, log = updatedLog)
            enemyTurn()
            return
        } else if (action == "potion") {
            val potionItem = inventory.value.find { it.id == "inv-potion" && it.count > 0 }
            if (potionItem == null) {
                showToast("✚ Нет зелий!")
                return
            }
            soundManager.playHeal()
            val healVal = 30
            val newPlayerHp = minOf(b.playerHpMax, b.playerHp + healVal)
            updatedLog.add(BattleLogEntry(type = "heal", text = "✚ Зелье! +$healVal HP."))
            triggerFloatingDmg(text = "+$healVal", colorHex = "#6FD49A", isPlayerTarget = true)

            viewModelScope.launch {
                val newCount = potionItem.count - 1
                if (newCount <= 0) repository.deleteItem(potionItem.id)
                else repository.insertItem(potionItem.copy(count = newCount))
            }
            incrementStat("potionsUsed")

            battleState.value = b.copy(playerHp = newPlayerHp, log = updatedLog)
            enemyTurn()
            return
        }

        val newEnemyHp = maxOf(0, b.enemyHp - pDmg)
        if (newEnemyHp <= 0) {
            battleState.value = b.copy(enemyHp = 0, log = updatedLog)
            finishBattle(won = true)
            return
        }

        battleState.value = b.copy(enemyHp = newEnemyHp, log = updatedLog)
        enemyTurn()
    }

    fun useDeckCardInBattle(index: Int) {
        val b = battleState.value ?: return
        if (b.isFinished || b.usedDeckIndices.contains(index)) return

        val activeDeck = deck.value
        if (index !in activeDeck.indices) return
        val card = activeDeck[index]
        if (card.abilityName == null) return

        if (b.playerMp < card.abilityCost) {
            showToast("Недостаточно маны (${card.abilityCost} MP)")
            return
        }

        soundManager.playMagic()
        val newMp = b.playerMp - card.abilityCost
        val updatedLog = b.log.toMutableList()
        val used = b.usedDeckIndices + index

        var currentEnemyHp = b.enemyHp
        var currentPlayerHp = b.playerHp
        var tempDef = b.tempDefBuff

        when (card.abilityType) {
            "damage" -> {
                currentEnemyHp = maxOf(0, currentEnemyHp - card.abilityValue)
                updatedLog.add(BattleLogEntry(type = "card", text = "✦ ${card.name}: ${card.abilityName}! ${card.abilityValue} урона."))
                triggerFloatingDmg(card.abilityValue.toString(), "#8B6FD4", isPlayerTarget = false)
            }
            "heal" -> {
                currentPlayerHp = minOf(b.playerHpMax, currentPlayerHp + card.abilityValue)
                updatedLog.add(BattleLogEntry(type = "heal", text = "✚ ${card.name}: ${card.abilityName}! +${card.abilityValue} HP."))
                triggerFloatingDmg("+${card.abilityValue}", "#6FD49A", isPlayerTarget = true)
            }
            "drain" -> {
                currentEnemyHp = maxOf(0, currentEnemyHp - card.abilityValue)
                val healAmt = card.abilityValue / 2
                currentPlayerHp = minOf(b.playerHpMax, currentPlayerHp + healAmt)
                updatedLog.add(BattleLogEntry(type = "card", text = "✦ ${card.name}: ${card.abilityName}! ${card.abilityValue} урона, +$healAmt HP."))
                triggerFloatingDmg(card.abilityValue.toString(), "#8B6FD4", isPlayerTarget = false)
            }
            "buff" -> {
                tempDef += card.abilityValue
                updatedLog.add(BattleLogEntry(type = "system", text = "🛡 ${card.name}: ${card.abilityName}! +${card.abilityValue} защиты."))
            }
            "mana" -> {
                val newMpBoost = minOf(b.playerMpMax, newMp + card.abilityValue)
                updatedLog.add(BattleLogEntry(type = "heal", text = "✦ ${card.name}: ${card.abilityName}! +${card.abilityValue} MP."))
                battleState.value = b.copy(
                    playerMp = newMpBoost,
                    enemyHp = currentEnemyHp,
                    playerHp = currentPlayerHp,
                    tempDefBuff = tempDef,
                    usedDeckIndices = used,
                    log = updatedLog
                )
                return
            }
        }

        if (currentEnemyHp <= 0) {
            battleState.value = b.copy(
                playerMp = newMp,
                enemyHp = 0,
                playerHp = currentPlayerHp,
                tempDefBuff = tempDef,
                usedDeckIndices = used,
                log = updatedLog
            )
            finishBattle(won = true)
            return
        }

        battleState.value = b.copy(
            playerMp = newMp,
            enemyHp = currentEnemyHp,
            playerHp = currentPlayerHp,
            tempDefBuff = tempDef,
            usedDeckIndices = used,
            log = updatedLog
        )
        enemyTurn()
    }

    private fun enemyTurn() {
        viewModelScope.launch {
            kotlinx.coroutines.delay(400)
            val b = battleState.value ?: return@launch
            if (b.isFinished || b.enemyHp <= 0) return@launch

            val baseDmg = b.enemy.str + (0..5).random()
            val totalDef = playerStats.value.def + b.tempDefBuff
            var eDmg = maxOf(1, baseDmg - totalDef / 2)
            if (b.isDefending) eDmg = maxOf(1, eDmg / 2)

            val isCrit = (1..100).random() <= 10
            if (isCrit) eDmg *= 2

            val newPlayerHp = maxOf(0, b.playerHp - eDmg)
            val updatedLog = b.log.toMutableList()

            soundManager.playHit()
            if (isCrit) {
                updatedLog.add(BattleLogEntry(type = "crit", text = "💥 ${b.enemy.name} наносит КРИТ! $eDmg урона!"))
            } else {
                updatedLog.add(BattleLogEntry(type = "damage", text = "🔮 ${b.enemy.name} наносит $eDmg урона."))
            }
            triggerFloatingDmg(eDmg.toString(), "#E05A6A", isPlayerTarget = true)

            if (newPlayerHp <= 0) {
                battleState.value = b.copy(
                    playerHp = 0,
                    isDefending = false,
                    tempDefBuff = 0,
                    log = updatedLog,
                    isFinished = true,
                    playerWon = false
                )
                finishBattle(won = false)
            } else {
                val nextTurn = b.turnNumber + 1
                val restoredMp = minOf(b.playerMpMax, b.playerMp + 12)
                updatedLog.add(BattleLogEntry(type = "system", text = "⚔ Ход $nextTurn: Ваш ход! Восстановлено +12 MP."))
                battleState.value = b.copy(
                    playerHp = newPlayerHp,
                    playerMp = restoredMp,
                    isPlayerTurn = true,
                    turnNumber = nextTurn,
                    isDefending = false,
                    tempDefBuff = 0,
                    usedDeckIndices = emptySet(),
                    log = updatedLog
                )
            }
        }
    }

    fun endTurn() {
        val b = battleState.value ?: return
        if (b.isFinished || !b.isPlayerTurn) return

        val updatedLog = b.log.toMutableList()
        updatedLog.add(BattleLogEntry(type = "system", text = "⌛ Игрок завершил ход. Ход Противника..."))

        battleState.value = b.copy(
            isPlayerTurn = false,
            log = updatedLog
        )
        enemyTurn()
    }

    private fun finishBattle(won: Boolean) {
        val b = battleState.value ?: return
        val updatedLog = b.log.toMutableList()

        if (won) {
            soundManager.playVictory()
            updatedLog.add(BattleLogEntry(type = "system", text = "🏆 Победа! +${b.enemy.xpReward} XP, +${b.enemy.goldReward} ◉."))
            showToast("🏆 Победа! +${b.enemy.xpReward} XP, +${b.enemy.goldReward} ◉")
            addDiaryEntry("battle", "🏆", "Победа над ${b.enemy.name}: +${b.enemy.xpReward} XP, +${b.enemy.goldReward} ◉")

            addGoldAndXp(gold = b.enemy.goldReward, xp = b.enemy.xpReward)
            incrementStat("battlesWon")
        } else {
            soundManager.playDefeat()
            updatedLog.add(BattleLogEntry(type = "system", text = "💀 Поражение. Герой пал в бою."))
            showToast("💀 Поражение")
            addDiaryEntry("battle", "💀", "Поражение от ${b.enemy.name}")

            incrementStat("battlesLost")
        }

        battleState.value = b.copy(isFinished = true, playerWon = won, log = updatedLog)
        checkAchievementsAndQuests()
    }

    fun addGold(gold: Int) {
        viewModelScope.launch {
            val curr = playerStats.value
            val newGold = (curr.gold + gold).coerceAtLeast(0)
            repository.updatePlayerStats(curr.copy(gold = newGold))
        }
    }

    private fun addGoldAndXp(gold: Int, xp: Int) {
        viewModelScope.launch {
            val curr = playerStats.value
            var newGold = curr.gold + gold
            var newXp = curr.xp + xp
            var newLevel = curr.level
            var newHpMax = curr.hpMax
            var newMpMax = curr.mpMax
            var newStr = curr.str
            var newDef = curr.def

            var xpNeeded = newLevel * 100
            while (newXp >= xpNeeded) {
                newXp -= xpNeeded
                newLevel++
                newHpMax += 20
                newMpMax += 10
                newStr += 2
                newDef += 1
                xpNeeded = newLevel * 100
                soundManager.playLevelUp()
                showToast("★ Уровень $newLevel!")
                addDiaryEntry("achievement", "★", "Достигнут уровень $newLevel")
            }

            val updatedStats = curr.copy(
                gold = newGold,
                xp = newXp,
                level = newLevel,
                hpMax = newHpMax,
                mpMax = newMpMax,
                str = newStr,
                def = newDef,
                hp = newHpMax,
                mp = newMpMax
            )
            repository.updatePlayerStats(updatedStats)
        }
    }

    private fun incrementStat(key: String, count: Int = 1) {
        viewModelScope.launch {
            val curr = playerStats.value
            val updated = when (key) {
                "battlesWon" -> curr.copy(battlesWon = curr.battlesWon + count)
                "battlesLost" -> curr.copy(battlesLost = curr.battlesLost + count)
                "cardsCreated" -> curr.copy(cardsCreated = curr.cardsCreated + count)
                "potionsUsed" -> curr.copy(potionsUsed = curr.potionsUsed + count)
                "crits" -> curr.copy(crits = curr.crits + count)
                "crafted" -> curr.copy(crafted = curr.crafted + count)
                "chestsOpened" -> curr.copy(chestsOpened = curr.chestsOpened + count)
                else -> curr
            }
            repository.updatePlayerStats(updated)
        }
    }

    fun visitLocation(key: String) {
        viewModelScope.launch {
            val curr = playerStats.value
            val visited = curr.visitedLocations.split(",").filter { it.isNotBlank() }.toSet()
            if (!visited.contains(key)) {
                val newVisited = (visited + key).joinToString(",")
                repository.updatePlayerStats(curr.copy(visitedLocations = newVisited))
                checkAchievementsAndQuests()
            }
        }
    }

    fun upgradeCard(card: CardEntity) {
        val cost = (card.level + 1) * 50
        if (playerStats.value.gold < cost) {
            showToast("Недостаточно золота ($cost ◉)")
            return
        }

        soundManager.playLevelUp()
        viewModelScope.launch {
            repository.updatePlayerStats(playerStats.value.copy(gold = playerStats.value.gold - cost))
            val updated = card.copy(
                level = card.level + 1,
                hp = card.hp + 10,
                mp = card.mp + 5,
                str = card.str + 2,
                def = card.def + 1,
                abilityValue = (card.abilityValue * 1.15).toInt()
            )
            repository.insertCard(updated)
            addDiaryEntry("reward", "⬆", "Улучшена карта \"${card.name}\" до уровня ${updated.level}")
            showToast("⬆ ${card.name} → Уровень ${updated.level}")
        }
    }

    fun createCard(
        name: String,
        type: String,
        rarity: String,
        hp: Int,
        mp: Int,
        str: Int,
        def: Int,
        desc: String,
        art: String
    ) {
        if (name.isBlank()) {
            showToast("Введите название")
            return
        }
        val id = "ARC-${System.currentTimeMillis().toString(36).uppercase()}-${(1000..9999).random()}"
        val newCard = CardEntity(
            id = id, name = name, type = type, rarity = rarity,
            hp = hp, mp = mp, str = str, def = def, level = 1, xp = 0,
            desc = if (desc.isBlank()) "Новая карта." else desc, art = art,
            abilityName = "Базовая атака", abilityType = "damage", abilityValue = 15, abilityCost = 8, abilityDesc = "Простая атака"
        )

        soundManager.playClick()
        viewModelScope.launch {
            repository.insertCard(newCard)
            incrementStat("cardsCreated")
            addDiaryEntry("craft", "✎", "Создана новая карта \"$name\"")
            showToast("✦ Карта \"$name\" создана")
            checkAchievementsAndQuests()
        }
    }

    fun addToDeck(card: CardEntity) {
        val currentDeck = deck.value
        if (currentDeck.size >= 5) {
            showToast("Колода полна (макс. 5)")
            return
        }
        if (currentDeck.any { it.id == card.id }) {
            showToast("Уже в колоде")
            return
        }
        soundManager.playClick()
        val newDeck = currentDeck + card
        saveDeckEntities(newDeck)
    }

    fun removeFromDeck(index: Int) {
        soundManager.playClick()
        val currentDeck = deck.value.toMutableList()
        if (index in currentDeck.indices) {
            currentDeck.removeAt(index)
            saveDeckEntities(currentDeck)
        }
    }

    fun autoDeck() {
        soundManager.playClick()
        val sorted = cards.value.filter { it.abilityName != null }
            .sortedByDescending { getRarityTier(it.rarity) }
            .take(5)
        saveDeckEntities(sorted)
        showToast("⟳ Колода собрана автоматически")
    }

    fun clearDeck() {
        soundManager.playClick()
        saveDeckEntities(emptyList())
        showToast("Колода очищена")
    }

    private fun saveDeckEntities(list: List<CardEntity>) {
        viewModelScope.launch {
            val entities = list.mapIndexed { i, c -> DeckEntity(cardId = c.id, slotIndex = i) }
            repository.setDeck(entities)
            checkAchievementsAndQuests()
        }
    }

    private fun getRarityTier(rarity: String): Int {
        return when (rarity.lowercase()) {
            "common" -> 0
            "uncommon" -> 1
            "rare" -> 2
            "epic" -> 3
            "legendary" -> 4
            "mythic" -> 5
            else -> 0
        }
    }

    // Crafting
    fun toggleCraftSelection(card: CardEntity) {
        val current = craftSelection.value.toMutableList()
        if (current.any { it.id == card.id }) {
            current.removeAll { it.id == card.id }
        } else {
            if (current.size < 3) current.add(card)
        }
        craftSelection.value = current
    }

    fun performCraft() {
        val sel = craftSelection.value
        if (sel.size != 3) return
        val sameType = sel.all { it.type == sel[0].type }
        val sameRarity = sel.all { it.rarity == sel[0].rarity }
        if (!sameType || !sameRarity) {
            showToast("Карты должны быть одного типа и редкости")
            return
        }

        val rarities = listOf("common", "uncommon", "rare", "epic", "legendary", "mythic")
        val currentIndex = rarities.indexOf(sel[0].rarity)
        if (currentIndex == -1 || currentIndex >= rarities.size - 1) {
            showToast("Достигнута максимальная редкость")
            return
        }

        val newRarity = rarities[currentIndex + 1]
        val base = sel[0]
        val newCard = CardEntity(
            id = "ARC-${System.currentTimeMillis().toString(36).uppercase()}-CRAFT",
            name = "${base.name} +",
            type = base.type,
            rarity = newRarity,
            hp = (base.hp * 1.5).toInt(),
            mp = (base.mp * 1.5).toInt(),
            str = (base.str * 1.5).toInt(),
            def = (base.def * 1.5).toInt(),
            level = base.level + 2,
            xp = 0,
            desc = "Улучшенная версия карты \"${base.name}\", созданная через алхимию.",
            art = base.art,
            abilityName = base.abilityName?.let { "$it+" },
            abilityType = base.abilityType,
            abilityValue = (base.abilityValue * 1.6).toInt(),
            abilityCost = base.abilityCost,
            abilityDesc = base.abilityDesc?.let { "$it (улучшено)" }
        )

        soundManager.playCraft()
        viewModelScope.launch {
            sel.forEach { repository.deleteCard(it.id) }
            repository.insertCard(newCard)
            craftSelection.value = emptyList()
            incrementStat("crafted")
            addDiaryEntry("craft", "⚗", "Создана карта \"${newCard.name}\" ($newRarity)")
            showToast("✦ Создано: ${newCard.name}")
            checkAchievementsAndQuests()
        }
    }

    // Chest opening
    fun openChest(chestType: String, price: Int) {
        if (playerStats.value.gold < price) {
            showToast("Недостаточно золота ($price ◉)")
            return
        }

        soundManager.playChest()
        viewModelScope.launch {
            repository.updatePlayerStats(playerStats.value.copy(gold = playerStats.value.gold - price))
            incrementStat("chestsOpened")

            val goldReward = when (chestType) {
                "common" -> (20..50).random()
                "rare" -> (50..120).random()
                else -> (150..350).random()
            }
            val xpReward = when (chestType) {
                "common" -> (10..30).random()
                "rare" -> (30..80).random()
                else -> (100..250).random()
            }

            addGoldAndXp(gold = goldReward, xp = xpReward)

            // Random card reward
            val matchingCards = cards.value.filter {
                when (chestType) {
                    "common" -> it.rarity in listOf("common", "uncommon")
                    "rare" -> it.rarity in listOf("uncommon", "rare", "epic")
                    else -> it.rarity in listOf("rare", "epic", "legendary", "mythic")
                }
            }
            if (matchingCards.isNotEmpty() && (1..100).random() <= 70) {
                val template = matchingCards.random()
                val dropped = template.copy(
                    id = "ARC-${System.currentTimeMillis().toString(36).uppercase()}-CHEST",
                    name = "${template.name} ★"
                )
                repository.insertCard(dropped)
                showToast("📦 Награда: +$goldReward ◉, +$xpReward XP, Карта \"${dropped.name}\"")
                addDiaryEntry("reward", "📦", "Открыт сундук: +$goldReward ◉, +$xpReward XP, карта \"${dropped.name}\"")
            } else {
                showToast("📦 Награда: +$goldReward ◉, +$xpReward XP")
                addDiaryEntry("reward", "📦", "Открыт сундук: +$goldReward ◉, +$xpReward XP")
            }

            checkAchievementsAndQuests()
        }
    }

    // Inventory usage
    fun useInventoryItem(item: InventoryEntity) {
        if (item.count <= 0) return
        soundManager.playClick()

        viewModelScope.launch {
            val stats = playerStats.value
            when (item.useType) {
                "heal" -> {
                    soundManager.playHeal()
                    val newHp = minOf(stats.hpMax, stats.hp + item.value)
                    repository.updatePlayerStats(stats.copy(hp = newHp))
                    incrementStat("potionsUsed")
                    showToast("✚ +${item.value} HP")
                    addDiaryEntry("reward", "✚", "Использовано \"${item.name}\" (+${item.value} HP)")
                }
                "mana" -> {
                    soundManager.playMagic()
                    val newMp = minOf(stats.mpMax, stats.mp + item.value)
                    repository.updatePlayerStats(stats.copy(mp = newMp))
                    showToast("✦ +${item.value} MP")
                    addDiaryEntry("reward", "✦", "Использовано \"${item.name}\" (+${item.value} MP)")
                }
                "buff_str" -> {
                    soundManager.playLevelUp()
                    repository.updatePlayerStats(stats.copy(str = stats.str + item.value))
                    showToast("⚔ +${item.value} к силе (постоянно)")
                    addDiaryEntry("reward", "⚔", "Использовано \"${item.name}\" (+${item.value} силы)")
                }
                else -> {
                    showToast("Этот предмет можно использовать в бою")
                    return@launch
                }
            }

            val newCount = item.count - 1
            if (newCount <= 0) repository.deleteItem(item.id)
            else repository.insertItem(item.copy(count = newCount))

            checkAchievementsAndQuests()
        }
    }

    // Shop purchase
    fun buyShopItem(item: ShopItem) {
        val currentStock = shopStockMap.value[item.id] ?: item.stock
        if (currentStock <= 0) {
            showToast("Распродано")
            return
        }
        if (playerStats.value.gold < item.price) {
            showToast("Недостаточно золота (${item.price} ◉)")
            return
        }

        soundManager.playBuy()
        viewModelScope.launch {
            repository.updatePlayerStats(playerStats.value.copy(gold = playerStats.value.gold - item.price))
            shopStockMap.value = shopStockMap.value + (item.id to currentStock - 1)

            val existing = inventory.value.find { it.id == item.givesItem.id }
            if (existing != null) {
                repository.insertItem(existing.copy(count = existing.count + 1))
            } else {
                repository.insertItem(item.givesItem.copy(count = 1))
            }

            addDiaryEntry("reward", "◉", "Куплено \"${item.name}\" за ${item.price} ◉")
            showToast("✓ Куплено: ${item.name}")
            checkAchievementsAndQuests()
        }
    }

    // Quests & Achievements
    fun claimQuestReward(quest: QuestEntity) {
        if (quest.isCompleted) return
        soundManager.playVictory()
        viewModelScope.launch {
            repository.updateQuest(quest.copy(isCompleted = true))
            addGoldAndXp(gold = quest.goldReward, xp = quest.xpReward)
            addDiaryEntry("achievement", "📜", "Квест \"${quest.name}\" выполнен!")
            showToast("📜 Квест выполнен: ${quest.name}")
            checkAchievementsAndQuests()
        }
    }

    private fun checkAchievementsAndQuests() {
        val p = playerStats.value
        val visitedCount = p.visitedLocations.split(",").filter { it.isNotBlank() }.size

        val updatedAchievements = achievements.value.map { ach ->
            val unlocked = when (ach.id) {
                "first_card" -> p.cardsCreated >= 1
                "collector_15" -> cards.value.size >= 15
                "first_blood" -> p.battlesWon >= 1
                "warrior_10" -> p.battlesWon >= 10
                "rich" -> p.gold >= 500
                "level_5" -> p.level >= 5
                "explorer" -> visitedCount >= 6
                "alchemist" -> p.crafted >= 3
                "crit_master" -> p.crits >= 5
                "mythic" -> cards.value.any { it.rarity == "mythic" }
                "chest_master" -> p.chestsOpened >= 5
                "deck_master" -> deck.value.size >= 5
                else -> ach.isUnlocked
            }
            if (!ach.isUnlocked && unlocked) {
                soundManager.playVictory()
                showToast("★ Достижение: ${ach.name}")
                addDiaryEntry("achievement", "★", "Получено достижение \"${ach.name}\"")
                ach.copy(isUnlocked = true)
            } else ach
        }

        viewModelScope.launch {
            updatedAchievements.forEach { if (it.isUnlocked) repository.updateAchievement(it) }
        }
    }

    private fun addDiaryEntry(type: String, icon: String, text: String) {
        val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
        val timeStr = sdf.format(Date())
        viewModelScope.launch {
            repository.addDiaryEntry(
                DiaryEntity(type = type, icon = icon, text = text, time = timeStr)
            )
        }
    }

    fun clearDiary() {
        soundManager.playClick()
        viewModelScope.launch {
            repository.clearDiary()
            showToast("Дневник очищен")
        }
    }

    fun transcendCard(card: CardEntity) {
        val nextRarity = when (card.rarity.lowercase()) {
            "common" -> "uncommon"
            "uncommon" -> "rare"
            "rare" -> "epic"
            "epic" -> "legendary"
            "legendary" -> "mythic"
            else -> null
        }
        if (nextRarity == null) {
            showToast("Карта уже имеет мифический статус!")
            return
        }
        val cost = 300 * card.level
        if (playerStats.value.gold < cost) {
            showToast("Необходимо $cost ◉ золота для эволюции")
            return
        }

        viewModelScope.launch {
            repository.updatePlayerStats(playerStats.value.copy(gold = playerStats.value.gold - cost))
            val evolved = card.copy(
                rarity = nextRarity,
                hp = card.hp + 30,
                str = card.str + 10,
                def = card.def + 8,
                abilityValue = (card.abilityValue * 1.3).toInt()
            )
            repository.insertCard(evolved)
            soundManager.playVictory()
            addDiaryEntry("reward", "✨", "Эволюция карты \"${card.name}\" → ${nextRarity.uppercase()}")
            showToast("✨ Эволюция! \"${card.name}\" → ${nextRarity.uppercase()}")
            checkAchievementsAndQuests()
        }
    }

    fun exportSaveDataJson(): String {
        return try {
            val stats = playerStats.value
            val root = JSONObject()

            val pObj = JSONObject()
            pObj.put("level", stats.level)
            pObj.put("xp", stats.xp)
            pObj.put("gold", stats.gold)
            pObj.put("hp", stats.hp)
            pObj.put("hpMax", stats.hpMax)
            pObj.put("mp", stats.mp)
            pObj.put("mpMax", stats.mpMax)
            pObj.put("str", stats.str)
            pObj.put("def", stats.def)
            pObj.put("battlesWon", stats.battlesWon)
            pObj.put("battlesLost", stats.battlesLost)
            pObj.put("crafted", stats.crafted)
            pObj.put("chestsOpened", stats.chestsOpened)
            pObj.put("visitedLocations", stats.visitedLocations)
            root.put("player", pObj)

            val cardsArr = JSONArray()
            cards.value.forEach { c ->
                val cObj = JSONObject()
                cObj.put("id", c.id)
                cObj.put("name", c.name)
                cObj.put("type", c.type)
                cObj.put("rarity", c.rarity)
                cObj.put("hp", c.hp)
                cObj.put("mp", c.mp)
                cObj.put("str", c.str)
                cObj.put("def", c.def)
                cObj.put("level", c.level)
                cObj.put("desc", c.desc)
                cObj.put("art", c.art)
                cObj.put("abilityName", c.abilityName ?: "")
                cObj.put("abilityType", c.abilityType ?: "")
                cObj.put("abilityValue", c.abilityValue)
                cObj.put("abilityCost", c.abilityCost)
                cardsArr.put(cObj)
            }
            root.put("cards", cardsArr)

            val invArr = JSONArray()
            inventory.value.forEach { i ->
                val iObj = JSONObject()
                iObj.put("id", i.id)
                iObj.put("name", i.name)
                iObj.put("count", i.count)
                iObj.put("art", i.art)
                invArr.put(iObj)
            }
            root.put("inventory", invArr)

            root.toString(2)
        } catch (e: Exception) {
            "{}"
        }
    }

    fun importSaveDataJson(jsonStr: String): Boolean {
        return try {
            val root = JSONObject(jsonStr)
            if (root.has("player")) {
                val pObj = root.getJSONObject("player")
                val curr = playerStats.value
                val newStats = curr.copy(
                    level = pObj.optInt("level", curr.level),
                    xp = pObj.optInt("xp", curr.xp),
                    gold = pObj.optInt("gold", curr.gold),
                    hp = pObj.optInt("hp", curr.hp),
                    hpMax = pObj.optInt("hpMax", curr.hpMax),
                    mp = pObj.optInt("mp", curr.mp),
                    mpMax = pObj.optInt("mpMax", curr.mpMax),
                    str = pObj.optInt("str", curr.str),
                    def = pObj.optInt("def", curr.def),
                    battlesWon = pObj.optInt("battlesWon", curr.battlesWon),
                    battlesLost = pObj.optInt("battlesLost", curr.battlesLost),
                    crafted = pObj.optInt("crafted", curr.crafted),
                    chestsOpened = pObj.optInt("chestsOpened", curr.chestsOpened),
                    visitedLocations = pObj.optString("visitedLocations", curr.visitedLocations)
                )
                viewModelScope.launch {
                    repository.updatePlayerStats(newStats)
                }
            }

            if (root.has("cards")) {
                val cardsArr = root.getJSONArray("cards")
                val newCards = mutableListOf<CardEntity>()
                for (idx in 0 until cardsArr.length()) {
                    val cObj = cardsArr.getJSONObject(idx)
                    newCards.add(
                        CardEntity(
                            id = cObj.optString("id", UUID.randomUUID().toString()),
                            name = cObj.optString("name", "Импортированная Карта"),
                            type = cObj.optString("type", "hero"),
                            rarity = cObj.optString("rarity", "rare"),
                            hp = cObj.optInt("hp", 80),
                            mp = cObj.optInt("mp", 20),
                            str = cObj.optInt("str", 15),
                            def = cObj.optInt("def", 8),
                            level = cObj.optInt("level", 1),
                            desc = cObj.optString("desc", ""),
                            art = cObj.optString("art", "warrior"),
                            abilityName = cObj.optString("abilityName", "Удар"),
                            abilityType = cObj.optString("abilityType", "damage"),
                            abilityValue = cObj.optInt("abilityValue", 20),
                            abilityCost = cObj.optInt("abilityCost", 10)
                        )
                    )
                }
                if (newCards.isNotEmpty()) {
                    viewModelScope.launch {
                        newCards.forEach { repository.insertCard(it) }
                    }
                }
            }

            soundManager.playVictory()
            showToast("✓ Сохранение импортировано!")
            addDiaryEntry("system", "📥", "Импортированы данные сохранения JSON")
            true
        } catch (e: Exception) {
            showToast("Ошибка импорта JSON сохранения")
            false
        }
    }

    fun resetAllData() {
        soundManager.playClick()
        viewModelScope.launch {
            repository.resetAll()
            resetBattle(silent = true)
            showToast("Все сохранения сброшены")
        }
    }
}
