package com.adrifernandevs.kmplayground.ui.screens.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adrifernandevs.kmplayground.data.repository.MoviesRepository
import com.adrifernandevs.kmplayground.domain.model.Movie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class HomeViewModel : ViewModel(), KoinComponent {

    private val moviesRepository: MoviesRepository by inject()

    data class UiState(
        val isLoading: Boolean = true,
        val movies: List<Movie> = emptyList(),
    )

    sealed class UiEvent {
        data object OnUiReady : UiEvent()
    }

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

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