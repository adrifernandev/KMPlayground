package com.adrifernandevs.kmplayground.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.adrifernandevs.kmplayground.movies
import com.adrifernandevs.kmplayground.ui.screens.detail.DetailScreen
import com.adrifernandevs.kmplayground.ui.screens.home.HomeScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            HomeScreen(
                onMovieClicked = { movie ->
                    navController.navigate("detail/${movie.id}")
                }
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