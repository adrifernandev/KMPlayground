package com.adrifernandevs.kmplayground.ui.screens.home.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adrifernandevs.kmplayground.data.repository.MoviesRepository
import com.adrifernandevs.kmplayground.domain.model.Movie
import kotlinx.coroutines.launch

class HomeViewModel(
    private val moviesRepository: MoviesRepository
) : ViewModel() {

    data class UiState(
        val isLoading: Boolean = true,
        val movies: List<Movie> = emptyList(),
    )

    var state by mutableStateOf(UiState())
        private set

    init {
        fetchPopularMovies()
    }

    private fun fetchPopularMovies() {
        viewModelScope.launch {
            moviesRepository.movies.collect {
                state = state.copy(
                    isLoading = false,
                    movies = it
                )
            }
        }
    }

}