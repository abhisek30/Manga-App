package com.abhisek.manga.app.presentation.ui.details

import com.abhisek.manga.app.domain.model.Manga

sealed class MangaDetailsAction {
    data class Init(val id: String) : MangaDetailsAction()
    data object FavoriteCta : MangaDetailsAction()
    data object MarkAsReadCta : MangaDetailsAction()
}

data class MangaDetailsState(
    val manga: Manga? = null,
)