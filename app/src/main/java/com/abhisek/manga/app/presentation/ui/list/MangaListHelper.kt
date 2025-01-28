package com.abhisek.manga.app.presentation.ui.list

import com.abhisek.manga.app.core.util.epochToYear
import com.abhisek.manga.app.domain.model.Manga

sealed class MangaListAction {
    data class CardCta(val manga: Manga) : MangaListAction()
    data class FavoriteCta(val manga: Manga) : MangaListAction()
    data object SortByPopularityCta : MangaListAction()
    data object SortByScoreCta : MangaListAction()
}

data class MangaListState(
    val mangaContent: List<MangaContent>? = null,
)

data class MangaContent(
    val year : Int,
    val mangaList: List<Manga>,
)

fun List<Manga>.toMangaContentList(): List<MangaContent> {
    return this.groupBy { manga ->
        epochToYear(manga.publishedChapterDate)
    }.entries.map { entry ->
        MangaContent(
            year = entry.key,
            mangaList = entry.value
        )
    }.sortedBy { it.year }
}