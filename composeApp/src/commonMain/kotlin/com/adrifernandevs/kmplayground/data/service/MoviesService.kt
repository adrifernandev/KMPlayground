package com.adrifernandevs.kmplayground.data.service

import com.adrifernandevs.kmplayground.data.response.MovieResponseResult
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class MoviesService(
    private val apiKey: String,
    private val client: HttpClient
) {
    suspend fun fetchPopularMovies(): MovieResponseResult {
        return client.get("https://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key=$apiKey")
            .body()
    }
}