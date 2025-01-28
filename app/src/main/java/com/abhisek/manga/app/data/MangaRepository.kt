package com.abhisek.manga.app.data

import com.abhisek.manga.app.data.remote.repository.IMangaRemoteRepository
import com.abhisek.manga.app.domain.model.Manga
import com.abhisek.manga.app.domain.repository.IMangaRepository
import javax.inject.Inject

class MangaRepository @Inject constructor(
    private val mangaRemoteRepository: IMangaRemoteRepository,
) : IMangaRepository {
    override suspend fun getMangaList(): Result<List<Manga>> {
        return mangaRemoteRepository.getMangaList()
    }
}