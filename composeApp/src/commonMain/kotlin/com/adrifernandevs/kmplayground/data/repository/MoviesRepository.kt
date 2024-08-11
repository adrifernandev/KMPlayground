package com.adrifernandevs.kmplayground.data.repository

import com.adrifernandevs.kmplayground.data.response.toMovie
import com.adrifernandevs.kmplayground.data.response.toMovieList
import com.adrifernandevs.kmplayground.data.service.MoviesService
import com.adrifernandevs.kmplayground.domain.model.Movie

class MoviesRepository(
    private val moviesService: MoviesService
) {
    suspend fun fetchPopularMovies(): List<Movie>{
        return moviesService.fetchPopularMovies().results.toMovieList()
    }

    suspend fun fetchMovieById(id: Int): Movie {
        return moviesService.fetchMovieById(id).toMovie()
    }
}