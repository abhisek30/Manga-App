package com.abhisek.manga.app.presentation.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abhisek.manga.app.domain.repository.IMangaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MangaListViewModel @Inject constructor(
    private val mangaRepository: IMangaRepository,
) : ViewModel() {

    private val _uiState: MutableStateFlow<MangaListState> = MutableStateFlow(MangaListState())
    val uiState: StateFlow<MangaListState> = _uiState

    init {
        fetchMangaList()
    }

    private fun fetchMangaList() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = mangaRepository.getMangaList()
            result.onSuccess {
                _uiState.value = _uiState.value.copy(
                    mangaList = it
                )
            }
        }
    }
}