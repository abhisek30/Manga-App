package com.abhisek.manga.app.data.remote.mapper

import com.abhisek.manga.app.data.remote.dto.MangaDto
import com.abhisek.manga.app.domain.model.Manga

fun List<MangaDto>.toDomainList(): List<Manga> {
    return map { it.toDomain() }
}

fun MangaDto.toDomain(isFavorite: Boolean = false, isRead: Boolean = false): Manga {
    return Manga(
        id = id ?: "",
        image = image ?: "",
        score = score ?: 0.0,
        popularity = popularity ?: 0,
        title = title ?: "",
        publishedChapterDate = publishedChapterDate ?: 0,
        category = category ?: "",
        isFavorite = isFavorite,
        isRead = isRead
    )
}