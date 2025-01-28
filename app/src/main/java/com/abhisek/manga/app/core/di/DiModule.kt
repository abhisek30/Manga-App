package com.abhisek.manga.app.core.di

import com.abhisek.manga.app.data.MangaRepository
import com.abhisek.manga.app.data.remote.repository.IMangaRemoteRepository
import com.abhisek.manga.app.data.remote.repository.MangaRemoteRepository
import com.abhisek.manga.app.domain.repository.IMangaRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DiModule {

    @Provides
    @Singleton
    fun provideRemoteRepository(): IMangaRemoteRepository {
        return MangaRemoteRepository()
    }

    @Provides
    @Singleton
    fun provideRepository(mangaRemoteRepository: IMangaRemoteRepository): IMangaRepository {
        return MangaRepository(mangaRemoteRepository)
    }
}