package com.abhisek.manga.app.presentation.ui.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.abhisek.manga.app.presentation.ui.details.component.MangaDetailsView

@Composable
fun MangaDetailsScreen(modifier: Modifier = Modifier, id: String) {

    val viewmodel = hiltViewModel<MangaDetailsViewModel>()
    val uiState = viewmodel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewmodel.handleAction(MangaDetailsAction.Init(id))
    }


    uiState.value.manga?.let { details ->
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp),
        ) {
            item {
                MangaDetailsView(
                    modifier = Modifier.padding(top = 12.dp),
                    manga = details,
                    onFavoriteCta = { viewmodel.handleAction(MangaDetailsAction.FavoriteCta) },
                    onReadCta = { viewmodel.handleAction(MangaDetailsAction.MarkAsReadCta) },
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
private fun MangaDetailsScreenPreview() {
    MangaDetailsScreen(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        id = "",
    )
}