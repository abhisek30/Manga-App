package com.abhisek.manga.app.presentation.ui.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.abhisek.manga.app.presentation.ui.list.component.MangaCard

@Composable
fun MangaListContent(modifier: Modifier = Modifier, navigateToDetails: () -> Unit) {
    val viewmodel = hiltViewModel<MangaListViewModel>()
    val uiState = viewmodel.uiState.collectAsState()

    uiState.value.mangaList?.let { mangaList ->
        LazyColumn(
            modifier = modifier.padding(horizontal = 12.dp),
        ) {
            items(mangaList) { manga ->
                MangaCard(
                    modifier = Modifier.padding(top = 12.dp),
                    manga = manga,
                    onClick = {

                    }
                )
            }
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

@Preview
@Composable
private fun MangaListContentPreview() {
    MangaListContent(Modifier.fillMaxSize().background(Color.White)) {
    }
}