package com.abhisek.manga.app.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.abhisek.manga.app.data.local.dao.MangaDao
import com.abhisek.manga.app.data.local.entity.MangaEntity


@Database(entities = [MangaEntity::class], version = 1, exportSchema = false)
abstract class MangaDatabase : RoomDatabase() {
    abstract fun mangaDao(): MangaDao
}