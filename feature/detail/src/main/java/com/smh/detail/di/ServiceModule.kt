package com.smh.detail.di

import com.smh.detail.data.service.DetailApi
import com.smh.network.createService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {
    @Provides
    @Singleton
    fun provideDetailService(retrofit: Retrofit): DetailApi {
        return retrofit.createService()
    }
}