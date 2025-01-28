package com.abhisek.manga.app.data.local.mapper

import com.abhisek.manga.app.data.local.entity.MangaEntity
import com.abhisek.manga.app.domain.model.Manga


fun List<MangaEntity>.toDomain() : List<Manga> {
    return this.map { it.toDomain() }
}

fun MangaEntity.toDomain(): Manga {
    return Manga(
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