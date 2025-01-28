package com.abhisek.manga.app.data.remote.repository

import com.abhisek.manga.app.domain.model.Manga

interface IMangaRemoteRepository {
    suspend fun getMangaList(): Result<List<Manga>>
}