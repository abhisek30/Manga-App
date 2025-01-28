package com.abhisek.manga.app.presentation.ui.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.abhisek.manga.app.presentation.ui.list.component.MangaCard
import com.ahmadhamwi.tabsync_compose.lazyListTabSync


@Composable
fun MangaListContent(modifier: Modifier = Modifier, navigateToDetails: () -> Unit) {
    val viewmodel = hiltViewModel<MangaListViewModel>()
    val uiState = viewmodel.uiState.collectAsState()

    uiState.value.mangaContent?.let { mangaContent ->

        val (selectedTabIndex, setSelectedTabIndex, lazyListState) = lazyListTabSync(
            mangaContent.indices.toList(),
            smoothScroll = true,
        )

        Column {
            YearTabBar(
                year = mangaContent.map { it.year },
                selectedTabIndex = selectedTabIndex,
                onTabClicked = { index, _ -> setSelectedTabIndex(index) }
            )

            MangaLazyList(mangaContent, lazyListState)
        }
    } ?: run {
        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator()
        }
    }
}


@Composable
fun YearTabBar(
    year: List<Int>,
    selectedTabIndex: Int,
    onTabClicked: (index: Int, category: Int) -> Unit
) {
    ScrollableTabRow(
        selectedTabIndex = selectedTabIndex,
        edgePadding = 0.dp,
        containerColor = Color.White,
        contentColor = Color.Black
    ) {
        year.forEachIndexed { index, category ->
            Tab(
                selected = index == selectedTabIndex,
                onClick = { onTabClicked(index, category) },
                text = { Text(category.toString()) }
            )
        }
    }
}

@Composable
fun MangaLazyList(
    manga: List<MangaContent>,
    listState: LazyListState = rememberLazyListState(),
) {
    LazyColumn(
        state = listState,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        itemsIndexed(manga) { _, mangaContent ->
            Text(
                text = mangaContent.year.toString(),
                modifier = Modifier.padding(12.dp),
            )
            mangaContent.mangaList.forEach {
                MangaCard(
                    modifier = Modifier.padding(start = 12.dp, end = 12.dp, bottom = 12.dp),
                    manga = it,
                    onClick = {

                    }
                )
            }
        }
    }
}

@Preview
@Composable
private fun MangaListContentPreview() {
    MangaListContent(
        Modifier
            .fillMaxSize()
            .background(Color.White)) {
    }
}