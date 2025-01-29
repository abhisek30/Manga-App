package com.abhisek.manga.app.data.local.repository

import com.abhisek.manga.app.data.local.dao.MangaDao
import com.abhisek.manga.app.data.local.entity.MangaEntity
import com.abhisek.manga.app.data.local.mapper.toDomain
import com.abhisek.manga.app.domain.model.Manga
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MangaLocalRepository @Inject constructor(
    private val mangaDao: MangaDao,
) : IMangaLocalRepository {
    override suspend fun getMangaList(): Result<List<Manga>> {
        return try {
            val result = mangaDao.getAllManga().first()
            Result.success(result.toDomain())
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

    override fun getMangaListAsFlow(): Flow<Result<List<Manga>>> =
        mangaDao.getAllManga().map { mangaEntities ->
            try {
                Result.success(mangaEntities.map { it.toDomain() })
            } catch (e: Exception) {
                Result.failure(e)
            }
        }

    override suspend fun insertMangaList(mangaList: List<MangaEntity>): Result<Any> {
        return try {
            val result = mangaDao.insertMangaList(mangaList)
            Result.success(result)
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

    override suspend fun updateManga(manga: MangaEntity): Result<Any> {
        return try {
            val result = mangaDao.updateManga(manga)
            Result.success(result)
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

    override fun getMangaDetails(id: String): Flow<Result<Manga>> =
        mangaDao.getMangaById(id).map { mangaEntity ->
            try {
                mangaEntity?.let {
                    Result.success(it.toDomain())
                } ?: Result.failure(Exception("Manga not found"))
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
}