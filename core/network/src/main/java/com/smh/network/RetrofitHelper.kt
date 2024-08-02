package com.smh.network

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.xml.transform.OutputKeys

inline fun <reified T> Retrofit.createService(): T = create(T::class.java)

fun createRetrofitClient(
    url: String,
    okHttpClient: OkHttpClient,
    networkJson: Json
): Retrofit = createRetrofitBuilder(okHttpClient, networkJson)
    .baseUrl(url)
    .build()

fun createRetrofitBuilder(
    okHttpClient: OkHttpClient,
    networkJson: Json
): Retrofit.Builder {
    return Retrofit.Builder()
        .client(okHttpClient)
        .addConverterFactory(NullOnEmptyConverterFactory())
        .addConverterFactory(networkJson.asConverterFactory("application/json".toMediaType()))
}

@OptIn(ExperimentalSerializationApi::class)
fun createJson(): Json {
    return Json {
        ignoreUnknownKeys = true
        explicitNulls = false
    }
}

fun createOkHttpClient(
    context: Context,
    httpLoggingInterceptor: HttpLoggingInterceptor,
) = OkHttpClient.Builder()
    .also {
        if (BuildConfig.DEBUG) {
            it.addInterceptor(ChuckerInterceptor.Builder(context).build())
            it.addInterceptor(
                httpLoggingInterceptor.apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )
        }
    }
    .connectTimeout(60L, TimeUnit.SECONDS)
    .readTimeout(60L, TimeUnit.SECONDS)
    .writeTimeout(60L, TimeUnit.SECONDS)
    .build()

