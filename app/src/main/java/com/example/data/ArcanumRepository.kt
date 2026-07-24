package com.example.data

import kotlinx.coroutines.flow.Flow

class ArcanumRepository(private val db: ArcanumDatabase) {
    val cards: Flow<List<CardEntity>> = db.cardDao().getAllCards()
    val inventory: Flow<List<InventoryEntity>> = db.inventoryDao().getAllInventory()
    val playerStats: Flow<PlayerStatsEntity?> = db.playerDao().getPlayerStats()
    val deck: Flow<List<DeckEntity>> = db.deckDao().getDeck()
    val quests: Flow<List<QuestEntity>> = db.questDao().getAllQuests()
    val achievements: Flow<List<AchievementEntity>> = db.achievementDao().getAllAchievements()
    val diaryEntries: Flow<List<DiaryEntity>> = db.diaryDao().getDiaryEntries()

    suspend fun insertCard(card: CardEntity) = db.cardDao().insertCard(card)
    suspend fun insertCards(cards: List<CardEntity>) = db.cardDao().insertCards(cards)
    suspend fun deleteCard(id: String) = db.cardDao().deleteCard(id)

    suspend fun insertItem(item: InventoryEntity) = db.inventoryDao().insertItem(item)
    suspend fun deleteItem(id: String) = db.inventoryDao().deleteItem(id)

    suspend fun updatePlayerStats(stats: PlayerStatsEntity) = db.playerDao().updatePlayerStats(stats)

    suspend fun setDeck(deckList: List<DeckEntity>) {
        db.deckDao().clearDeck()
        db.deckDao().setDeck(deckList)
    }

    suspend fun updateQuest(quest: QuestEntity) = db.questDao().updateQuest(quest)
    suspend fun updateAchievement(achievement: AchievementEntity) = db.achievementDao().updateAchievement(achievement)
    suspend fun addDiaryEntry(entry: DiaryEntity) = db.diaryDao().insertEntry(entry)
    suspend fun clearDiary() = db.diaryDao().deleteAll()

    suspend fun resetAll() {
        db.cardDao().deleteAll()
        db.inventoryDao().deleteAll()
        db.playerDao().deleteAll()
        db.deckDao().clearDeck()
        db.questDao().deleteAll()
        db.achievementDao().deleteAll()
        db.diaryDao().deleteAll()

        db.cardDao().insertCards(DefaultData.initialCards)
        db.inventoryDao().insertInventory(DefaultData.initialInventory)
        db.playerDao().updatePlayerStats(PlayerStatsEntity())
        db.questDao().insertQuests(DefaultData.initialQuests)
        db.achievementDao().insertAchievements(DefaultData.initialAchievements)
    }
}
