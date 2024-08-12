package com.adrifernandevs.kmplayground.data.remote.service

import com.adrifernandevs.kmplayground.data.response.MovieResponse
import com.adrifernandevs.kmplayground.data.response.MovieResponseResult
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class MoviesService(
    private val client: HttpClient
) {
    suspend fun fetchPopularMovies(
        region: String
    ): MovieResponseResult {
        return client
            .get("/3/discover/movie") {
                parameter("sort_by", "popularity.desc")
                parameter("region", region)
            }
            .body<MovieResponseResult>()
    }

    suspend fun fetchMovieById(id: Int): MovieResponse {
        return client
            .get("/3/movie/$id")
            .body<MovieResponse>()
    }
}