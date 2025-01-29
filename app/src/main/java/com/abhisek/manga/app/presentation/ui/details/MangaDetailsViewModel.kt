package com.abhisek.manga.app.presentation.ui.details

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
class MangaDetailsViewModel @Inject constructor(
    private val mangaRepository: IMangaRepository,
) : ViewModel() {

    private val _uiState: MutableStateFlow<MangaDetailsState> = MutableStateFlow(MangaDetailsState())
    val uiState: StateFlow<MangaDetailsState> = _uiState

    private lateinit var id: String
    private lateinit var manga: Manga

    fun handleAction(action: MangaDetailsAction) {
        when (action) {
            is MangaDetailsAction.Init -> {
                id = action.id
                getMangaDetails(id)
            }

            is MangaDetailsAction.MarkAsReadCta -> handleReadCta(manga)

            is MangaDetailsAction.FavoriteCta -> handleFavoriteCta(manga)
        }
    }

    private fun handleFavoriteCta(manga: Manga) {
        viewModelScope.launch(Dispatchers.IO) {
            val result =
                mangaRepository.updateManga(manga.toEntity().copy(isFavorite = !manga.isFavorite))
        }
    }


    private fun handleReadCta(manga: Manga) {
        viewModelScope.launch(Dispatchers.IO) {
            val result =
                mangaRepository.updateManga(manga.toEntity().copy(isRead = !manga.isRead))
        }
    }

    private fun getMangaDetails(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = mangaRepository.getMangaDetails(id)
            result.collect { mangaResult ->
                mangaResult.onSuccess { manga ->
                    this@MangaDetailsViewModel.manga = manga
                    _uiState.value = _uiState.value.copy(manga = manga)
                }
            }
        }
    }


}