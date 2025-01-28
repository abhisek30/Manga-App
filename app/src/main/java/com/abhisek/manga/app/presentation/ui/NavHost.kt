package com.abhisek.manga.app.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.abhisek.manga.app.presentation.ui.list.MangaListContent
import kotlinx.serialization.Serializable

@Composable
fun NavHost(modifier: Modifier = Modifier) {

    val navController = rememberNavController()

    androidx.navigation.compose.NavHost(
        navController = navController,
        startDestination = Screen.MangaListScreen,
        modifier = modifier
    ) {
        composable<Screen.MangaListScreen> {
            MangaListContent(navigateToDetails = { navController.navigate(Screen.MangaDetailsScreen) })
        }

        composable<Screen.MangaDetailsScreen> {

        }
    }
}

@Serializable
sealed class Screen {
    @Serializable
    data object MangaListScreen : Screen()

    @Serializable
    data object MangaDetailsScreen : Screen()
}