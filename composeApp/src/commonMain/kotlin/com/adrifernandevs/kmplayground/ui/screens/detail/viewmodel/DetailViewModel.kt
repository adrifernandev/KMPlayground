package com.adrifernandevs.kmplayground.ui.screens.detail.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adrifernandevs.kmplayground.data.repository.MoviesRepository
import com.adrifernandevs.kmplayground.domain.model.Movie
import kotlinx.coroutines.launch

class DetailViewModel(
    private val movieId: Int,
    private val moviesRepository: MoviesRepository,
) : ViewModel() {

    data class UiState(
        val isLoading: Boolean = true,
        val movie: Movie? = null
    )

    var state by mutableStateOf(UiState())
        private set

    init {
        fetchMovieById()
    }

    private fun fetchMovieById() {
        viewModelScope.launch {
            moviesRepository.fetchMovieById(movieId).collect {
                it?.let {
                    state = state.copy(
                        isLoading = false,
                        movie = it
                    )
                }
            }
        }
    }
}