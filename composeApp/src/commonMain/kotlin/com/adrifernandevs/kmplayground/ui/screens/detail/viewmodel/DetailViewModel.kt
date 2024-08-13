package com.adrifernandevs.kmplayground.ui.screens.detail.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adrifernandevs.kmplayground.data.repository.MoviesRepository
import com.adrifernandevs.kmplayground.domain.model.Movie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class DetailViewModel(
    private val movieId: Int,
) : ViewModel(), KoinComponent {

    private val moviesRepository: MoviesRepository by inject()

    data class UiState(
        val isLoading: Boolean = true,
        val movie: Movie? = null
    )

    sealed class UiEvent {
        data object OnFavouriteClicked : UiEvent()
    }

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state

    init {
        fetchMovieById()
    }

    fun onEvent(event: UiEvent) {
        when (event) {
            is UiEvent.OnFavouriteClicked -> {
                toggleFavorite()
            }
        }
    }

    private fun fetchMovieById() {
        viewModelScope.launch {
            moviesRepository.fetchMovieById(movieId).collect {
                it?.let {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        movie = it
                    )
                }
            }
        }
    }

    private fun toggleFavorite() {
        _state.value.movie?.let {
            viewModelScope.launch {
                moviesRepository.toggleFavorite(it)
            }
        }
    }
}