package com.abhisek.manga.app.domain.model

data class Manga(
    val id: String,
    val image: String,
    val score: Double,
    val popularity: Int,
    val title: String,
    val publishedChapterDate: Int,
    val category: String,
    val isFavorite: Boolean,
    val isRead: Boolean,
)
