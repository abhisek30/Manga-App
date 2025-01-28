package com.abhisek.manga.app.data.remote.repository

import com.abhisek.manga.app.core.network.RetrofitClient
import com.abhisek.manga.app.data.remote.mapper.toDomainList
import com.abhisek.manga.app.domain.model.Manga

class MangaRemoteRepository : IMangaRemoteRepository {
    override suspend fun getMangaList(): Result<List<Manga>> {
        return try {
            val result = RetrofitClient.apiService.getMangaList()
            if (result.isSuccessful) {
                result.body()?.let { mangaDtoList ->
                    Result.success(mangaDtoList.toDomainList())
                } ?: run {
                    Result.failure(Exception("Failed to fetch data, ${result.message()}, ${result.body()}"))
                }
            } else {
                Result.failure(Exception("Failed to fetch data, ${result.message()}, ${result.body()}"))
            }
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }
}