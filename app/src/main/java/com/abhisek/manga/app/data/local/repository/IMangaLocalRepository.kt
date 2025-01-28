package com.abhisek.manga.app.data.local.repository

import com.abhisek.manga.app.data.local.entity.MangaEntity
import com.abhisek.manga.app.domain.model.Manga

interface IMangaLocalRepository {
    suspend fun getMangaList(): Result<List<Manga>>
    suspend fun insertMangaList(mangaList: List<MangaEntity>): Result<Any>
}