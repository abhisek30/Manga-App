package com.abhisek.manga.app.data.local.repository

import com.abhisek.manga.app.data.local.dao.MangaDao
import com.abhisek.manga.app.data.local.entity.MangaEntity
import com.abhisek.manga.app.data.local.mapper.toDomain
import com.abhisek.manga.app.domain.model.Manga
import javax.inject.Inject

class MangaLocalRepository @Inject constructor(
    private val mangaDao: MangaDao,
) : IMangaLocalRepository {

    override suspend fun getMangaList(): Result<List<Manga>> {
        return try {
            val result = mangaDao.getAllManga()
            Result.success(result.toDomain())
        } catch (e: Exception) {
            return Result.failure(e)
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
}