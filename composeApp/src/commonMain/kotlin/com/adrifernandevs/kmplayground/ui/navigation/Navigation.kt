package com.adrifernandevs.kmplayground.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.adrifernandevs.kmplayground.data.service.MoviesService
import com.adrifernandevs.kmplayground.domain.model.movies
import com.adrifernandevs.kmplayground.ui.screens.detail.DetailScreen
import com.adrifernandevs.kmplayground.ui.screens.home.HomeScreen
import com.adrifernandevs.kmplayground.ui.screens.home.viewmodel.HomeViewModel
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kmplayground.composeapp.generated.resources.Res
import kmplayground.composeapp.generated.resources.tmdb_api_key
import kotlinx.serialization.json.Json
import org.jetbrains.compose.resources.stringResource

@Composable
fun Navigation() {
    val navController = rememberNavController()
    val client = remember {
        HttpClient {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                })
            }
        }
    }
    val apiKey = stringResource(Res.string.tmdb_api_key)
    val viewModel = viewModel {
        HomeViewModel(
            moviesService = MoviesService(
                apiKey = apiKey,
                client = client
            )
        )
    }

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            HomeScreen(
                onMovieClicked = { movie ->
                    navController.navigate("detail/${movie.id}")
                },
                viewModel = viewModel
            )
        }
        composable(
            route = "detail/{movieId}",
            arguments = listOf(navArgument("movieId") { type = NavType.IntType })
        ) { backStackEntry ->
            val movieId = backStackEntry.arguments?.getInt("movieId")
            val movie = movies.first { it.id == movieId }
            DetailScreen(
                movie = movie,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}