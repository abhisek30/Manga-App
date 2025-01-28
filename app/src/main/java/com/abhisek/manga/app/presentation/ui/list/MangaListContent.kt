package com.abhisek.manga.app.presentation.ui.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.abhisek.manga.app.presentation.ui.list.component.MangaContentLazyList
import com.abhisek.manga.app.presentation.ui.list.component.MangaLazyList
import com.abhisek.manga.app.presentation.ui.list.component.TopAppBar
import com.abhisek.manga.app.presentation.ui.list.component.YearTabBar
import com.ahmadhamwi.tabsync_compose.lazyListTabSync


@Composable
fun MangaListContent(modifier: Modifier = Modifier, navigateToDetails: () -> Unit) {
    val viewmodel = hiltViewModel<MangaListViewModel>()
    val uiState = viewmodel.uiState.collectAsState()
    Scaffold(topBar = {
        TopAppBar(onClick = { sort ->
            viewmodel.handleAction(MangaListAction.SortCta(sort))
        })
    }, modifier = modifier.fillMaxSize()) { innerPadding ->
        uiState.value.mangaContent?.let { mangaContent ->
            val (selectedTabIndex, setSelectedTabIndex, lazyListState) = lazyListTabSync(
                mangaContent.indices.toList(),
                smoothScroll = true,
            )
            Column(modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)) {
                if (uiState.value.sortOrder == SortOrder.NONE) {
                    YearTabBar(
                        years = uiState.value.years,
                        selectedTabIndex = selectedTabIndex,
                        onTabClicked = { index, _ -> setSelectedTabIndex(index) }
                    )
                    MangaContentLazyList(mangaContent, lazyListState)
                } else {
                    MangaLazyList(uiState.value.sortedList)
                }
            }
        } ?: run {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator()
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
            .background(Color.White)
    ) {
    }
}