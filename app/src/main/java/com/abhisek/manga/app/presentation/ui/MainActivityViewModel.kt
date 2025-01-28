package com.abhisek.manga.app.presentation.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abhisek.manga.app.domain.repository.IMangaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val mangaRepository: IMangaRepository,
) : ViewModel() {

    init {
        fetchMangaList()
    }

    private fun fetchMangaList() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = mangaRepository.getMangaList()
        }
    }
}