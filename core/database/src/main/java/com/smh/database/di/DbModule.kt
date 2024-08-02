package com.smh.database.di

import android.content.Context
import androidx.room.Room
import com.smh.database.CoinTrackerDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DbModule {

    @Provides
    @Singleton
    fun providesDB(
        @ApplicationContext context: Context
    ): CoinTrackerDb = Room.databaseBuilder(
        context,
        CoinTrackerDb::class.java,
        "coin_tracker_db"
    ).build()

    @Provides
    @Singleton
    fun providesPortfolioDao(
        db: CoinTrackerDb
    ) = db.portfolioDao()
}