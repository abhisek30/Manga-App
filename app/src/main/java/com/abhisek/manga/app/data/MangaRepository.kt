package com.abhisek.manga.app.data

import com.abhisek.manga.app.data.local.repository.IMangaLocalRepository
import com.abhisek.manga.app.data.remote.repository.IMangaRemoteRepository
import com.abhisek.manga.app.domain.mapper.toEntity
import com.abhisek.manga.app.domain.model.Manga
import com.abhisek.manga.app.domain.repository.IMangaRepository
import javax.inject.Inject

class MangaRepository @Inject constructor(
    private val mangaRemoteRepository: IMangaRemoteRepository,
    private val mangaLocalRepository: IMangaLocalRepository,
) : IMangaRepository {
    override suspend fun getMangaList(): Result<List<Manga>> {
        val remoteResult = mangaRemoteRepository.getMangaList()
        if (remoteResult.isSuccess) {
            remoteResult.getOrNull()?.let { remoteData ->
                val mangaEntities = remoteData.map { it.toEntity() }
                mangaLocalRepository.insertMangaList(mangaEntities)
                return remoteResult
            } ?: kotlin.run {
                return mangaLocalRepository.getMangaList()
            }
        } else {
            return mangaLocalRepository.getMangaList()
        }
    }
}