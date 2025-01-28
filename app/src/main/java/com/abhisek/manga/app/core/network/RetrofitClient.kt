package com.abhisek.manga.app.core.network

import com.abhisek.manga.app.data.remote.service.MangaService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private val interceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val okHttpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()

    val apiService: MangaService =
        Retrofit.Builder().client(okHttpClient).addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://www.jsonkeeper.com").build().create(MangaService::class.java)
}