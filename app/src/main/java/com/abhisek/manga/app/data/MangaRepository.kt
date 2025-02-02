package com.abhisek.manga.app.data

import com.abhisek.manga.app.data.local.entity.MangaEntity
import com.abhisek.manga.app.data.local.mapper.toDomain
import com.abhisek.manga.app.data.local.repository.IMangaLocalRepository
import com.abhisek.manga.app.data.remote.repository.IMangaRemoteRepository
import com.abhisek.manga.app.domain.mapper.toEntity
import com.abhisek.manga.app.domain.model.Manga
import com.abhisek.manga.app.domain.repository.IMangaRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class MangaRepository @Inject constructor(
    private val mangaRemoteRepository: IMangaRemoteRepository,
    private val mangaLocalRepository: IMangaLocalRepository,
) : IMangaRepository {
    override suspend fun getMangaListAsFlow(): Flow<Result<List<Manga>>> {
        return try {
            val remoteResult = mangaRemoteRepository.getMangaList()
            remoteResult.onSuccess { remoteData ->
                val localData =
                    mangaLocalRepository.getMangaListAsFlow().first().getOrNull().orEmpty()
                val mangaEntities = remoteData.map { remoteManga ->
                    val localManga = localData.find { it.id == remoteManga.id }
                    remoteManga.toEntity().copy(
                        isFavorite = localManga?.isFavorite ?: false,
                        isRead = localManga?.isRead ?: false
                    )
                }
                mangaLocalRepository.insertMangaList(mangaEntities)
            }
            mangaLocalRepository.getMangaListAsFlow()
        } catch (e: Exception) {
            mangaLocalRepository.getMangaListAsFlow()
        }
    }

    override suspend fun updateManga(manga: MangaEntity): Result<Any> {
        return mangaLocalRepository.updateManga(manga)
    }

    override fun getMangaDetails(id: String): Flow<Result<Manga>> {
        return mangaLocalRepository.getMangaDetails(id)
    }
}