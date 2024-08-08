package com.smh.cointracker.di

import com.smh.cointracker.fake.HomeRemoteDataSourceFake
import com.smh.home.data.repository.HomeRepositoryImpl
import com.smh.home.di.HomeRepositoryModule
import com.smh.home.domain.datasource.HomeLocalDataSource
import com.smh.home.domain.repository.HomeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [HomeRepositoryModule::class]
)
object TestHomeRepositoryModule {
    @Singleton
    @Provides
    fun providesHomeRepository(
        homeLocalDataSource: HomeLocalDataSource
    ): HomeRepository = HomeRepositoryImpl(
        homeLocalDataSource = homeLocalDataSource,
        homeRemoteDataSource = HomeRemoteDataSourceFake()
    )
}