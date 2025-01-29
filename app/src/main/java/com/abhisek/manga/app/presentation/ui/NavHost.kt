package com.abhisek.manga.app.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.abhisek.manga.app.presentation.ui.details.MangaDetailsScreen
import com.abhisek.manga.app.presentation.ui.list.MangaListScreen
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
            MangaListScreen(navigateToDetails = {
                navController.navigate(Screen.MangaDetailsScreen(it))
            })
        }

        composable<Screen.MangaDetailsScreen> { backStackRoute ->
            val mangaDetailsScreen = backStackRoute.toRoute<Screen.MangaDetailsScreen>()
            MangaDetailsScreen(
                modifier = Modifier,
                id = mangaDetailsScreen.id
            )
        }
    }
}

@Serializable
sealed class Screen {
    @Serializable
    data object MangaListScreen : Screen()

    @Serializable
    data class MangaDetailsScreen(val id: String) : Screen()
}