package com.abhisek.manga.app.presentation.ui.list.component

import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun YearTabBar(
    years: List<Int>,
    selectedTabIndex: Int,
    onTabClicked: (index: Int, category: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    ScrollableTabRow(
        selectedTabIndex = selectedTabIndex,
        edgePadding = 0.dp,
        containerColor = Color.White,
        contentColor = Color.Black,
        modifier = modifier,
    ) {
        years.forEachIndexed { index, category ->
            Tab(
                selected = index == selectedTabIndex,
                onClick = { onTabClicked(index, category) },
                text = { Text(category.toString()) }
            )
        }
    }
}