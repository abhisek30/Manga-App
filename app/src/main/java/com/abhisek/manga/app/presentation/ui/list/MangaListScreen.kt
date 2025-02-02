package com.abhisek.manga.app.presentation.ui.list

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.abhisek.manga.app.core.util.syncTabsWithLazyList
import com.abhisek.manga.app.presentation.ui.list.component.MangaContentLazyList
import com.abhisek.manga.app.presentation.ui.list.component.MangaLazyList
import com.abhisek.manga.app.presentation.ui.list.component.TopAppBar
import com.abhisek.manga.app.presentation.ui.list.component.YearTabBar
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch


@Composable
fun MangaListScreen(modifier: Modifier = Modifier, navigateToDetails: (String) -> Unit) {
    val viewmodel = hiltViewModel<MangaListViewModel>()
    val uiState = viewmodel.uiState.collectAsState()
    val sortListState: LazyListState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    Scaffold(topBar = {
        TopAppBar(onClick = { sort ->
            viewmodel.handleAction(MangaListAction.SortCta(sort))
        })
    }, modifier = modifier.fillMaxSize()) { innerPadding ->
        if (uiState.value.isError) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = {
                        viewmodel.handleAction(MangaListAction.Reload)
                    },
                    modifier = Modifier
                        .fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = Color.Black,
                    ),
                    border = BorderStroke(1.dp, Color.Black),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(text = "Reload")
                }
            }
        } else {
            uiState.value.mangaContent?.takeIf { it.isNotEmpty() }?.let { mangaContent ->
                val tabSyncState = syncTabsWithLazyList(
                    mangaContent.indices.toList()
                )
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    if (uiState.value.sortOrder == SortOrder.NONE) {
                        YearTabBar(
                            years = uiState.value.years,
                            selectedTabIndex = tabSyncState.selectedTabIndex,
                            onTabClicked = { index, _ -> tabSyncState.onTabClicked(index) }
                        )
                        MangaContentLazyList(
                            mangaContent,
                            tabSyncState.lazyListState,
                            onFavoriteCta = { manga ->
                                viewmodel.handleAction(MangaListAction.FavoriteCta(manga))
                            },
                            onClickCta = {
                                viewmodel.handleAction(MangaListAction.CardCta(it))
                            })
                    } else {
                        MangaLazyList(uiState.value.sortedList, onFavoriteCta = { manga ->
                            viewmodel.handleAction(MangaListAction.FavoriteCta(manga))
                        }, onClickCta = {
                            viewmodel.handleAction(MangaListAction.CardCta(it))
                        },
                            listState = sortListState
                        )
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
                    if (uiState.value.mangaContent == null) {
                        CircularProgressIndicator()
                    } else if (uiState.value.mangaContent?.isEmpty() == true) {
                        Text("Empty list found, please try later")
                    }
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        viewmodel.uiEffect.onEach {
            when (it) {
                is MangaListEffect.NavigateToDetails -> navigateToDetails(it.id)
                is MangaListEffect.ResetSortListState -> {
                    if(uiState.value.sortedList.isNotEmpty()) {
                        scope.launch {
                            sortListState.scrollToItem(0)
                        }
                    }
                }
            }
        }.collect()
    }
}


@Preview
@Composable
private fun MangaListScreenPreview() {
    MangaListScreen(
        Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
    }
}