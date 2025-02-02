package com.abhisek.manga.app.core.util

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class TabSyncState(
    var selectedTabIndex: Int,
    val lazyListState: LazyListState,
    private val coroutineScope: CoroutineScope,
    private val syncedIndices: List<Int>
) {

    val onTabClicked: (Int) -> Unit = { index ->
        selectedTabIndex = index
        coroutineScope.launch {
            lazyListState.scrollToItem(syncedIndices[selectedTabIndex])
        }
    }

    fun updateSelectedTabIndex(itemPosition: Int) {
        val index = syncedIndices.indexOfFirst { it >= itemPosition }
        if (index != -1 && index != selectedTabIndex) {
            selectedTabIndex = index
        }
    }
}

@Composable
fun syncTabsWithLazyList(
    syncedIndices: List<Int>,
    lazyListState: LazyListState = rememberLazyListState()
): TabSyncState {
    val coroutineScope = rememberCoroutineScope()
    return TabSyncState(0, lazyListState, coroutineScope, syncedIndices).also { state ->
        state.updateSelectedTabIndex(lazyListState.firstVisibleItemIndex)
    }
}