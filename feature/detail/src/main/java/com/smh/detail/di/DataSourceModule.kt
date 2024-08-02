package com.smh.detail.di

import com.smh.detail.data.datasource.DetailRemoteDataSourceImpl
import com.smh.detail.domain.datasource.DetailRemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {
    @Binds
    abstract fun bindDetailRemoteDataSource(
        detailRemoteDataSourceImpl: DetailRemoteDataSourceImpl
    ): DetailRemoteDataSource
}