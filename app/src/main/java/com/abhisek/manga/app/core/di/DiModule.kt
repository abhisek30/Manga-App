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
import com.abhisek.manga.app.data.remote.service.MangaService
import com.abhisek.manga.app.domain.repository.IMangaRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class BindsModule {

        @Binds
        @Singleton
        abstract fun bindRemoteRepository(
            remoteRepositoryImpl: MangaRemoteRepository
        ): IMangaRemoteRepository

        @Binds
        @Singleton
        abstract fun bindLocalRepository(
            localRepositoryImpl: MangaLocalRepository
        ): IMangaLocalRepository
}

@Module
@InstallIn(SingletonComponent::class)
object ProvidesModule {

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

    @Provides
    @Singleton
    fun provideService(retrofit: Retrofit): MangaService {
        return retrofit.create(MangaService::class.java)
    }
}