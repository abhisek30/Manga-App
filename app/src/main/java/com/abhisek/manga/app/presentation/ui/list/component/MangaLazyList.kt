package com.abhisek.manga.app.presentation.ui.list.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.abhisek.manga.app.domain.model.Manga
import com.abhisek.manga.app.presentation.ui.list.MangaContent

@Composable
fun MangaContentLazyList(
    mangaContent: List<MangaContent>,
    listState: LazyListState = rememberLazyListState(),
    modifier: Modifier = Modifier,
    onFavoriteCta: (Manga) -> Unit,
    onClickCta: (Manga) -> Unit,
) {
    LazyColumn(
        state = listState,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier
    ) {
        itemsIndexed(mangaContent) { _, mangaContent ->
            mangaContent.mangaList.forEach {
                MangaCard(
                    modifier = Modifier.padding(12.dp),
                    manga = it,
                    onClick = {onClickCta(it)},
                    onFavoriteCta = {onFavoriteCta(it)}
                )
            }
        }
    }
}

@Composable
fun MangaLazyList(
    manga: List<Manga>,
    listState: LazyListState = rememberLazyListState(),
    modifier: Modifier = Modifier,
    onFavoriteCta: (Manga) -> Unit,
    onClickCta: (Manga) -> Unit,
) {
    LazyColumn(
        state = listState,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier
    ) {
        itemsIndexed(manga) { _, mangaContent ->
            MangaCard(
                modifier = Modifier.padding(start = 12.dp, end = 12.dp, bottom = 12.dp),
                manga = mangaContent,
                onClick = {onClickCta(mangaContent)},
                onFavoriteCta = {onFavoriteCta(mangaContent)}
            )
        }
    }
}