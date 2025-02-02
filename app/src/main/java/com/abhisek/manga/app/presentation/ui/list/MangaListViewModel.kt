package com.abhisek.manga.app.presentation.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abhisek.manga.app.domain.mapper.toEntity
import com.abhisek.manga.app.domain.model.Manga
import com.abhisek.manga.app.domain.repository.IMangaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MangaListViewModel @Inject constructor(
    private val mangaRepository: IMangaRepository,
) : ViewModel() {

    private val _uiState: MutableStateFlow<MangaListState> = MutableStateFlow(MangaListState())
    val uiState: StateFlow<MangaListState> = _uiState
    private var mangaList = mutableListOf<Manga>()

    private val _uiEffect = MutableSharedFlow<MangaListEffect>()
    val uiEffect: SharedFlow<MangaListEffect> = _uiEffect

    init {
        fetchMangaListAsFlow()
    }

    fun handleAction(action: MangaListAction) {
        when (action) {
            is MangaListAction.SortCta -> handleSortAction(action.sortOrder)

            is MangaListAction.FavoriteCta -> handleFavoriteCta(action.manga)

            is MangaListAction.CardCta -> {
                viewModelScope.launch {
                    _uiEffect.emit(MangaListEffect.NavigateToDetails(action.manga.id))
                }
            }

            is MangaListAction.Reload -> fetchMangaListAsFlow()
        }
    }

    private fun handleFavoriteCta(manga: Manga) {
        viewModelScope.launch(Dispatchers.IO) {
            val result =
                mangaRepository.updateManga(manga.toEntity().copy(isFavorite = !manga.isFavorite))
        }
    }

    private fun handleSortAction(sortOrder: SortOrder) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                sortOrder = sortOrder,
                sortedList = mangaList.sortByOrder(sortOrder)
            )
            _uiEffect.emit(MangaListEffect.ResetSortListState)
        }
    }

    private fun List<Manga>.sortByOrder(sortOrder: SortOrder): List<Manga> {
        return when (sortOrder) {
            SortOrder.SCORE_ASC -> this.sortedBy { it.score }
            SortOrder.SCORE_DESC -> this.sortedByDescending { it.score }
            SortOrder.POPULARITY_ASC -> this.sortedBy { it.popularity }
            SortOrder.POPULARITY_DESC -> this.sortedByDescending { it.popularity }
            SortOrder.NONE -> emptyList()
        }
    }

    private fun fetchMangaListAsFlow() {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.value = _uiState.value.copy(
                isError = false,
                mangaContent = null,
                years = emptyList(),
            )
            mangaRepository.getMangaListAsFlow().collect {
                it.onSuccess {
                    mangaList = it.toMutableList()
                    val mangaContent = it.toMangaContentList()
                    _uiState.value = _uiState.value.copy(
                        mangaContent = mangaContent,
                        years = mangaContent.map { it.year },
                        isError = false,
                        sortOrder = _uiState.value.sortOrder,
                        sortedList = mangaList.sortByOrder(_uiState.value.sortOrder)
                    )
                }.onFailure {
                    _uiState.value = _uiState.value.copy(
                        isError = true,
                    )
                }
            }
        }
    }
}