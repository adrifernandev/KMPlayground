package com.adrifernandevs.kmplayground.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.adrifernandevs.kmplayground.BuildConfig
import com.adrifernandevs.kmplayground.data.database.MoviesDao
import com.adrifernandevs.kmplayground.data.repository.MoviesRepository
import com.adrifernandevs.kmplayground.data.service.MoviesService
import com.adrifernandevs.kmplayground.ui.screens.detail.DetailScreen
import com.adrifernandevs.kmplayground.ui.screens.detail.viewmodel.DetailViewModel
import com.adrifernandevs.kmplayground.ui.screens.home.HomeScreen
import com.adrifernandevs.kmplayground.ui.screens.home.viewmodel.HomeViewModel
import io.ktor.client.HttpClient
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

@Composable
fun Navigation(
    moviesDao: MoviesDao
) {
    val navController = rememberNavController()
    val moviesRepository = rememberMoviesRepository(moviesDao = moviesDao)

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            HomeScreen(
                onMovieClicked = { movie ->
                    navController.navigate("detail/${movie.id}")
                },
                viewModel = viewModel {
                    HomeViewModel(moviesRepository)
                }
            )
        }
        composable(
            route = "detail/{movieId}",
            arguments = listOf(navArgument("movieId") { type = NavType.IntType })
        ) { backStackEntry ->
            val movieId = checkNotNull(backStackEntry.arguments?.getInt("movieId"))
            DetailScreen(
                viewModel = viewModel {
                    DetailViewModel(
                        movieId = movieId,
                        moviesRepository
                    )
                },
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}

@Composable
private fun rememberMoviesRepository(
    moviesDao: MoviesDao
): MoviesRepository = remember {
    val apiKey = BuildConfig.TMDB_API_KEY
    val client =
        HttpClient {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                })
            }
            install(DefaultRequest) {
                url {
                    protocol = URLProtocol.HTTPS
                    host = "api.themoviedb.org"
                    parameters.append("api_key", apiKey)
                }
            }
        }
    val repository = MoviesRepository(
        moviesService = MoviesService(
            client = client
        ),
        moviesDao = moviesDao
    )
    repository
}