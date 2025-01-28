package com.abhisek.manga.app.presentation.ui.list.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.abhisek.manga.app.presentation.ui.list.SortOrder

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(modifier: Modifier = Modifier, onClick: (SortOrder) -> Unit) {
    var menuExpanded by remember { mutableStateOf(false) }
    androidx.compose.material3.TopAppBar(
        title = { Text("Manga List") },
        actions = {
            IconButton(onClick = { menuExpanded = true }) {
                Icon(Icons.Filled.MoreVert, contentDescription = "Sort Options")
            }
            DropdownMenu(
                expanded = menuExpanded,
                onDismissRequest = { menuExpanded = false }
            ) {
                DropdownMenuItem(onClick = {
                    onClick(SortOrder.SCORE_ASC)
                    menuExpanded = false
                }, text = { Text("Score Ascending") })
                DropdownMenuItem(onClick = {
                    onClick(SortOrder.SCORE_DESC)
                    menuExpanded = false
                }, text = { Text("Score Descending") })
                DropdownMenuItem(onClick = {
                    onClick(SortOrder.POPULARITY_ASC)
                    menuExpanded = false
                }, text = { Text("Popularity Ascending") })
                DropdownMenuItem(onClick = {
                    onClick(SortOrder.POPULARITY_DESC)
                    menuExpanded = false
                }, text = { Text("Popularity Descending") })
                DropdownMenuItem(onClick = {
                    onClick(SortOrder.NONE)
                    menuExpanded = false
                }, text = { Text("Reset") })
            }
        },
        modifier = modifier
    )
}