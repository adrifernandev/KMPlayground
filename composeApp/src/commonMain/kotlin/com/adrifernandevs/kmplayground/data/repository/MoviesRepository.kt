package com.adrifernandevs.kmplayground.data.repository

import com.adrifernandevs.kmplayground.data.database.MoviesDao
import com.adrifernandevs.kmplayground.data.remote.service.MoviesService
import com.adrifernandevs.kmplayground.data.response.toMovie
import com.adrifernandevs.kmplayground.data.response.toMovieList
import com.adrifernandevs.kmplayground.domain.model.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach

class MoviesRepository(
    private val moviesService: MoviesService,
    private val moviesDao: MoviesDao,
    private val regionRepository: RegionRepository
) {
    val movies = moviesDao.fetchPopularMovies().onEach { movies ->
        if (movies.isEmpty()) {
            val region = regionRepository.fetchRegion()
            val popularMoviesFromService =
                moviesService.fetchPopularMovies(region).results.toMovieList()
            moviesDao.insertMovies(popularMoviesFromService)
            println("filmss inserted $popularMoviesFromService")
        }
    }

    suspend fun fetchMovieById(id: Int): Flow<Movie?> =
        moviesDao.fetchMovieById(id).onEach { movie ->
            if (movie == null) {
                val movieFromService = moviesService.fetchMovieById(id).toMovie()
                moviesDao.insertMovies(listOf(movieFromService))
            }
    }

    suspend fun toggleFavorite(movie: Movie) {
        moviesDao.saveMovie(movie.copy(isFavorite = !movie.isFavorite))
    }
}