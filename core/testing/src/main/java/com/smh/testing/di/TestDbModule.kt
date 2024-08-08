package com.smh.testing.di

import android.content.Context
import androidx.room.Room
import com.smh.database.CoinTrackerDb
import com.smh.database.di.DbModule
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DbModule::class]
)
object TestDbModule {
    @Provides
    @Singleton
    fun providesDB(
        @ApplicationContext context: Context
    ): CoinTrackerDb = CoinTrackerDb.createTestDb(context)

    @Provides
    @Singleton
    fun providesPortfolioDao(
        db: CoinTrackerDb
    ) = db.portfolioDao()
}