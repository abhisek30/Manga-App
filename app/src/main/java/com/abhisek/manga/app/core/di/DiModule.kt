package com.abhisek.manga.app.core.di

import android.content.Context
import androidx.room.Room
import com.abhisek.manga.app.data.MangaRepository
import com.abhisek.manga.app.data.local.dao.MangaDao
import com.abhisek.manga.app.data.local.db.MangaDatabase
import com.abhisek.manga.app.data.local.repository.IMangaLocalRepository
import com.abhisek.manga.app.data.local.repository.MangaLocalRepository
import com.abhisek.manga.app.data.remote.repository.IMangaRemoteRepository
import com.abhisek.manga.app.data.remote.repository.MangaRemoteRepository
import com.abhisek.manga.app.domain.repository.IMangaRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
    fun provideLocalRepository(dao: MangaDao): IMangaLocalRepository {
        return MangaLocalRepository(dao)
    }

    @Provides
    @Singleton
    fun provideRepository(
        mangaRemoteRepository: IMangaRemoteRepository,
        mangaLocalRepository: MangaLocalRepository
    ): IMangaRepository {
        return MangaRepository(mangaRemoteRepository, mangaLocalRepository)
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): MangaDatabase {
        return Room.databaseBuilder(
            appContext,
            MangaDatabase::class.java,
            "manga_database"
        ).build()
    }


    @Provides
    @Singleton
    fun provideMangaDao(database: MangaDatabase): MangaDao {
        return database.mangaDao()
    }
}