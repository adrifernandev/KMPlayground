package com.adrifernandevs.kmplayground.ui.screens.home.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adrifernandevs.kmplayground.data.response.toMovie
import com.adrifernandevs.kmplayground.data.service.MoviesService
import com.adrifernandevs.kmplayground.domain.model.Movie
import kotlinx.coroutines.launch

class HomeViewModel(
private val moviesService: MoviesService
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
            val movies = moviesService.fetchPopularMovies()
            state = state.copy(
                isLoading = false,
                movies = movies.results.toMovie()
            )
        }
    }

}