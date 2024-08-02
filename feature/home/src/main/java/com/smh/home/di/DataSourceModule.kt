package com.smh.home.di

import com.smh.home.data.datasource.HomeLocalDataSourceImpl
import com.smh.home.data.datasource.HomeRemoteDataSourceImpl
import com.smh.home.domain.datasource.HomeLocalDataSource
import com.smh.home.domain.datasource.HomeRemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {
    @Binds
    abstract fun bindHomeRemoteDataSource(
        homeRemoteDataSourceImpl: HomeRemoteDataSourceImpl
    ): HomeRemoteDataSource

    @Binds
    abstract fun bindHomeLocalDataSource(
        homeLocalDataSourceImpl: HomeLocalDataSourceImpl
    ): HomeLocalDataSource
}