package com.adrifernandevs.kmplayground.ui.screens.home.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adrifernandevs.kmplayground.Movie
import com.adrifernandevs.kmplayground.movies
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeViewModel(

) : ViewModel() {

    var state by mutableStateOf(UiState())
        private set

    init {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            delay(2000)
            state = state.copy(
                isLoading = false,
                movies = movies
            )
        }
    }

    data class UiState(
        val isLoading: Boolean = false,
        val movies: List<Movie> = emptyList(),
    )

}