package com.abhisek.manga.app.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.abhisek.manga.app.data.local.entity.MangaEntity


@Dao
interface MangaDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMangaList(mangaList: List<MangaEntity>)

    @Query("SELECT * FROM manga_table")
    suspend fun getAllManga(): List<MangaEntity>
}