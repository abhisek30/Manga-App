package com.abhisek.manga.app.domain.repository

import com.abhisek.manga.app.data.local.entity.MangaEntity
import com.abhisek.manga.app.domain.model.Manga
import kotlinx.coroutines.flow.Flow

interface IMangaRepository {
    suspend fun getMangaList(): Result<List<Manga>>
    fun getMangaListAsFlow(): Flow<Result<List<Manga>>>
    suspend fun updateManga(manga: MangaEntity): Result<Any>
    fun getMangaDetails(id: String): Flow<Result<Manga>>
}