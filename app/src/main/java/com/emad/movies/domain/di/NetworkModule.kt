package com.emad.movies.domain.di

import android.content.Context
import android.util.Log
import com.emad.movies.BuildConfig
import com.emad.movies.data.remote.ApiService
import com.emad.movies.utils.NetworkHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Qualifier
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Singleton
    @Provides
    fun provideRetrofit(client: OkHttpClient, @BaseUrl baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    @Singleton
    @Provides
    fun provideHeaderInterceptor(): Interceptor {
        return Interceptor { chain ->
            val url = chain.request().url.newBuilder()
                .build()
            val request = chain.request()
                .newBuilder()
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "text/plain")
                .url(url)
                .build()
            chain.proceed(request)
        }
    }

    @Singleton
    @Provides
    @Named("statusInterceptor")
    fun provideStatusInterceptor(): Interceptor {
        return object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                val response = chain.proceed(chain.request())

                if (response.code == 204 || response.code == 205) {
                    return response
                        .newBuilder()
                        .code(200)
                        .body(response.body)
                        .build()
                } else {
                    return response
                }
            }


        }
    }

    @Singleton
    @Provides
    fun provideInterceptor(
        headerInterceptor: Interceptor,
        @Named("statusInterceptor") statusInterceptor: Interceptor
    ): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient().newBuilder().addInterceptor(headerInterceptor)
            .addInterceptor(statusInterceptor)
            .addInterceptor(loggingInterceptor)
            .connectTimeout(2, TimeUnit.MINUTES)
            .writeTimeout(2, TimeUnit.MINUTES)
            .readTimeout(2, TimeUnit.MINUTES)
            .build()
    }

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(
        ApiService::class.java
    )

    @Singleton
    @Provides
    fun provideNetworkHelper(@ApplicationContext context: Context) = NetworkHelper(context)

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class BaseUrl

    @Provides
    @BaseUrl
    fun provideBaseUrl(): String = BuildConfig.BASE_URL

    @Provides
    @Singleton
    fun provideCoroutineContext(): CoroutineContext {
        return Dispatchers.IO
    }
}