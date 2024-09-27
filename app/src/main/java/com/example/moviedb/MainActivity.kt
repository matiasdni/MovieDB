package com.example.moviedb

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.moviedb.ui.MovieInfoScreen
import com.example.moviedb.ui.MovieListScreen
import com.example.moviedb.ui.theme.MovieDBTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MovieDBTheme {
                MovieApp()
            }
        }
    }
}

@Composable
fun MovieApp() {
    val navController: NavHostController = rememberNavController()
    val movieList = stringResource(R.string.movie_list)
    val movieInfoRoute = stringResource(R.string.movie_info_route)

    NavHost(navController = navController, startDestination = movieList) {
        composable(movieList) {
            MovieListScreen(navController = navController)
        }
        composable(movieInfoRoute) { backStackEntry ->
            val movieId = backStackEntry.arguments?.getString(stringResource(R.string.movieid))
            MovieInfoScreen(navController = navController, movieId = movieId)
        }
    }
}
