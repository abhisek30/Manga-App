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
        fetchMangaList()
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

            is MangaListAction.Reload -> fetchMangaList()
        }
    }

    private fun handleFavoriteCta(manga: Manga) {
        viewModelScope.launch(Dispatchers.IO) {
            val result =
                mangaRepository.updateManga(manga.toEntity().copy(isFavorite = !manga.isFavorite))
        }
    }

    private fun handleSortAction(sortOrder: SortOrder) {
        when (sortOrder) {
            SortOrder.SCORE_ASC -> {
                _uiState.value = _uiState.value.copy(
                    sortOrder = sortOrder,
                    sortedList = mangaList.sortedBy { it.score }
                )
            }

            SortOrder.SCORE_DESC -> {
                _uiState.value = _uiState.value.copy(
                    sortOrder = sortOrder,
                    sortedList = mangaList.sortedByDescending { it.score }
                )
            }

            SortOrder.POPULARITY_ASC -> {
                _uiState.value = _uiState.value.copy(
                    sortOrder = sortOrder,
                    sortedList = mangaList.sortedBy { it.popularity }
                )
            }

            SortOrder.POPULARITY_DESC -> {
                _uiState.value = _uiState.value.copy(
                    sortOrder = sortOrder,
                    sortedList = mangaList.sortedByDescending { it.popularity }
                )
            }

            SortOrder.NONE -> {
                _uiState.value = _uiState.value.copy(
                    sortOrder = sortOrder,
                    sortedList = emptyList()
                )
            }
        }
    }

    private fun fetchMangaList() {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.value = _uiState.value.copy(
                isError = false,
                mangaContent = null,
                years = emptyList(),
            )
            val result = mangaRepository.getMangaList()
            result.onSuccess {
                mangaList = it.toMutableList()
                val mangaContent = it.toMangaContentList()
                _uiState.value = _uiState.value.copy(
                    mangaContent = mangaContent,
                    years = mangaContent.map { it.year },
                    isError = false,
                )
                fetchMangaListAsFlow()
            }.onFailure {
                _uiState.value = _uiState.value.copy(
                    isError = true,
                )
            }
        }
    }

    private fun fetchMangaListAsFlow() {
        viewModelScope.launch(Dispatchers.IO) {
            mangaRepository.getMangaListAsFlow().collect {
                it.onSuccess {
                    mangaList = it.toMutableList()
                    val mangaContent = it.toMangaContentList()
                    _uiState.value = _uiState.value.copy(
                        mangaContent = mangaContent,
                        years = mangaContent.map { it.year },
                        isError = false,
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