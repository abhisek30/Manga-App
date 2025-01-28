package com.abhisek.manga.app.domain.repository

import com.abhisek.manga.app.domain.model.Manga

interface IMangaRepository {

    suspend fun getMangaList(): Result<List<Manga>>
}