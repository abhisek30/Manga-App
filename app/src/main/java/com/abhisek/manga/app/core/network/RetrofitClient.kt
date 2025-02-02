package com.abhisek.manga.app.core.network

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitClient {

    private val interceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val okHttpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()


    @Provides
    @Singleton
    fun provideRetrofitClient() : Retrofit {
        return Retrofit.Builder().client(okHttpClient).addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://www.jsonkeeper.com").build()
    }
}