package com.smh.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [PortfolioEntity::class], version = 1, exportSchema = false)
abstract class CoinTrackerDb : RoomDatabase() {

    companion object {
        fun createTestDb(
            context: Context
        ): CoinTrackerDb {
            return Room.inMemoryDatabaseBuilder(
                context = context,
                CoinTrackerDb::class.java
            )
                .allowMainThreadQueries()
                .build()
        }
    }

    abstract fun portfolioDao(): PortfolioDao
}