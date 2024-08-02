package com.smh.network.di

import android.content.Context
import com.smh.network.BuildConfig
import com.smh.network.createJson
import com.smh.network.createOkHttpClient
import com.smh.network.createRetrofitClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun providesNetworkJson(): Json = createJson()

    @Provides
    @Singleton
    fun provideOkHttpClient(
        @ApplicationContext
        context: Context,
    ): OkHttpClient {
        return createOkHttpClient(
            context,
            HttpLoggingInterceptor(),
        )
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, networkJson: Json): Retrofit {
        return createRetrofitClient(BuildConfig.BASE_URL, okHttpClient, networkJson)
    }
}