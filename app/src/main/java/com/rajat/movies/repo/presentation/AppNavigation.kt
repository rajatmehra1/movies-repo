package com.rajat.movies.repo.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.gson.Gson
import com.rajat.movies.repo.data.MovieItem

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "movieListScreen") {
        composable("movieListScreen") { MovieListScreen(navController = navController) }
        composable("movieDetailsScreen/{movieJson}") { backStackEntry ->
            val movieJson = backStackEntry.arguments?.getString("movieJson")
            val movieItem = Gson().fromJson(movieJson, MovieItem::class.java)
            MovieDetailScreen(navHostController = navController, movieItem)
        }
    }
}