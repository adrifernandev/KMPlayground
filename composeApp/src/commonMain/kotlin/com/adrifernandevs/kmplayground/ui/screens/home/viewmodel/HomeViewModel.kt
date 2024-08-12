package com.adrifernandevs.kmplayground.ui.screens.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adrifernandevs.kmplayground.data.repository.MoviesRepository
import com.adrifernandevs.kmplayground.domain.model.Movie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val moviesRepository: MoviesRepository
) : ViewModel() {

    data class UiState(
        val isLoading: Boolean = true,
        val movies: List<Movie> = emptyList(),
    )

    sealed class UiEvent {
        data object OnUiReady : UiEvent()
    }

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state

    fun onEvent(event: UiEvent) {
        when (event) {
            UiEvent.OnUiReady -> fetchPopularMovies()
        }
    }

    private fun fetchPopularMovies() {
        viewModelScope.launch {
            moviesRepository.movies.collect {
                _state.value = _state.value.copy(
                    isLoading = false,
                    movies = it
                )
            }
        }
    }

}