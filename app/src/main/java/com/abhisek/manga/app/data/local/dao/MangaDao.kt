package com.abhisek.manga.app.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.abhisek.manga.app.data.local.entity.MangaEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface MangaDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMangaList(mangaList: List<MangaEntity>)

    @Query("SELECT * FROM manga_table")
    fun getAllManga(): Flow<List<MangaEntity>>

    @Update
    suspend fun updateManga(manga: MangaEntity)

    @Query("SELECT * FROM manga_table WHERE id = :id")
    fun getMangaById(id: String): Flow<MangaEntity?>
}