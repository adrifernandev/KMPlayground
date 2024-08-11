package com.adrifernandevs.kmplayground.data.service

import com.adrifernandevs.kmplayground.data.response.MovieResponse
import com.adrifernandevs.kmplayground.data.response.MovieResponseResult
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class MoviesService(
    private val client: HttpClient
) {
    suspend fun fetchPopularMovies(): MovieResponseResult {
        return client
            .get("/discover/movie?sort_by=popularity.desc")
            .body<MovieResponseResult>()
    }

    suspend fun fetchMovieById(id: Int): MovieResponse {
        return client
            .get("/movie/$id")
            .body<MovieResponse>()
    }
}