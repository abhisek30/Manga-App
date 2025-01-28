package com.abhisek.manga.app.presentation.ui.list

import com.abhisek.manga.app.domain.model.Manga

sealed class MangaListAction {
    data class CardCta(val manga: Manga) : MangaListAction()
    data class FavoriteCta(val manga: Manga) : MangaListAction()
    data object SortByPopularityCta : MangaListAction()
    data object SortByScoreCta : MangaListAction()
}

data class MangaListState(
    val mangaList: List<Manga>? = null,
    val sort: Sort? = null,
)

enum class Sort {
    POPULARITY,
    SCORE,
}