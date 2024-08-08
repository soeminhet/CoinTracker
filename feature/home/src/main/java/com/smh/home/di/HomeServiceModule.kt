package com.smh.home.di

import com.smh.home.data.service.HomeApi
import com.smh.network.createService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HomeServiceModule {
    @Provides
    @Singleton
    fun provideHomeService(retrofit: Retrofit): HomeApi {
        return retrofit.createService()
    }
}
