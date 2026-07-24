package com.example.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [
        CardEntity::class,
        InventoryEntity::class,
        PlayerStatsEntity::class,
        DeckEntity::class,
        QuestEntity::class,
        AchievementEntity::class,
        DiaryEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class ArcanumDatabase : RoomDatabase() {
    abstract fun cardDao(): CardDao
    abstract fun inventoryDao(): InventoryDao
    abstract fun playerDao(): PlayerDao
    abstract fun deckDao(): DeckDao
    abstract fun questDao(): QuestDao
    abstract fun achievementDao(): AchievementDao
    abstract fun diaryDao(): DiaryDao

    companion object {
        @Volatile
        private var INSTANCE: ArcanumDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): ArcanumDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ArcanumDatabase::class.java,
                    "arcanum_database"
                )
                    .addCallback(ArcanumDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

    private class ArcanumDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch(Dispatchers.IO) {
                    populateDatabase(database)
                }
            }
        }

        suspend fun populateDatabase(db: ArcanumDatabase) {
            db.cardDao().insertCards(DefaultData.initialCards)
            db.inventoryDao().insertInventory(DefaultData.initialInventory)
            db.playerDao().updatePlayerStats(PlayerStatsEntity())
            db.questDao().insertQuests(DefaultData.initialQuests)
            db.achievementDao().insertAchievements(DefaultData.initialAchievements)
        }
    }
}
