package com.abhisek.manga.app.data

import com.abhisek.manga.app.data.local.entity.MangaEntity
import com.abhisek.manga.app.data.local.mapper.toDomain
import com.abhisek.manga.app.data.local.repository.IMangaLocalRepository
import com.abhisek.manga.app.data.remote.repository.IMangaRemoteRepository
import com.abhisek.manga.app.domain.mapper.toEntity
import com.abhisek.manga.app.domain.model.Manga
import com.abhisek.manga.app.domain.repository.IMangaRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MangaRepository @Inject constructor(
    private val mangaRemoteRepository: IMangaRemoteRepository,
    private val mangaLocalRepository: IMangaLocalRepository,
) : IMangaRepository {
    override suspend fun getMangaList(): Result<List<Manga>> {
        try {
            val remoteResult = mangaRemoteRepository.getMangaList()
            val localResult = mangaLocalRepository.getMangaList()
            if (remoteResult.isSuccess) {
                remoteResult.getOrNull()?.let { remoteData ->
                    val localData =
                        if (localResult.isSuccess) localResult.getOrNull().orEmpty() else emptyList()
                    val mangaEntities = remoteData.map { remoteManga ->
                        val localManga = localData.find { it.id == remoteManga.id }
                        remoteManga.toEntity().copy(
                            isFavorite = localManga?.isFavorite ?: false,
                            isRead = localManga?.isRead ?: false
                        )
                    }
                    mangaLocalRepository.insertMangaList(mangaEntities)
                    return Result.success(mangaEntities.map { it.toDomain() })
                } ?: run {
                    return localResult
                }
            } else {
                return localResult
            }
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

    override fun getMangaListAsFlow(): Flow<Result<List<Manga>>> {
        return mangaLocalRepository.getMangaListAsFlow()
    }

    override suspend fun updateManga(manga: MangaEntity): Result<Any> {
        return mangaLocalRepository.updateManga(manga)
    }
}