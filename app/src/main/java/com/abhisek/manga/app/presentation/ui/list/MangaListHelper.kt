package com.abhisek.manga.app.presentation.ui.list

import com.abhisek.manga.app.core.util.epochToYear
import com.abhisek.manga.app.domain.model.Manga

sealed class MangaListAction {
    data class CardCta(val manga: Manga) : MangaListAction()
    data class FavoriteCta(val manga: Manga) : MangaListAction()
    data class SortCta(val sortOrder: SortOrder): MangaListAction()
    data object Reload: MangaListAction()
}

data class MangaListState(
    val mangaContent: List<MangaContent>? = null,
    val years: List<Int> = emptyList(),
    val sortedList: List<Manga> = emptyList(),
    val sortOrder: SortOrder = SortOrder.NONE,
    val isError: Boolean = false,
)

sealed class MangaListEffect {
    data class NavigateToDetails(val id: String): MangaListEffect()
}

enum class SortOrder {
    NONE, SCORE_ASC, SCORE_DESC, POPULARITY_ASC, POPULARITY_DESC
}

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