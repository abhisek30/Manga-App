package com.abhisek.manga.app.domain.mapper

import com.abhisek.manga.app.data.local.entity.MangaEntity
import com.abhisek.manga.app.domain.model.Manga

fun List<Manga>.toEntity() : List<MangaEntity> {
    return this.map { it.toEntity() }
}

fun Manga.toEntity(): MangaEntity {
    return MangaEntity(
        id = this.id,
        image = this.image,
        score = this.score,
        popularity = this.popularity,
        title = this.title,
        publishedChapterDate = this.publishedChapterDate,
        category = this.category,
        isFavorite = this.isFavorite,
        isRead = this.isRead
    )
}