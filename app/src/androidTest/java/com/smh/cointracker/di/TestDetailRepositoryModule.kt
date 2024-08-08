package com.smh.cointracker.di

import com.smh.cointracker.fake.DetailRemoteDataSourceFake
import com.smh.detail.data.repository.DetailRepositoryImpl
import com.smh.detail.di.DetailRepositoryModule
import com.smh.detail.domain.repository.DetailRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DetailRepositoryModule::class]
)
object TestDetailRepositoryModule {
    @Singleton
    @Provides
    fun providesDetailRepository(): DetailRepository = DetailRepositoryImpl(
        detailRemoteDataSource = DetailRemoteDataSourceFake()
    )
}