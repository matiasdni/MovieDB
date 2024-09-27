package com.example.moviedb

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.moviedb.ui.components.TopBar
import com.example.moviedb.ui.screens.MovieInfoScreen
import com.example.moviedb.ui.screens.MovieListScreen
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieApp() {
    val navController: NavHostController = rememberNavController()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    val appName: String = stringResource(R.string.app_name)
    val movieList: String = stringResource(R.string.movie_list)
    val movieInfoRoute: String = stringResource(R.string.movie_info_route)
    val movieIdString: String = stringResource(R.string.movieid)
    val popularMovies: String = stringResource(R.string.popular_movies)
    val movieDetails: String = stringResource(R.string.movie_details)

    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStackEntry?.destination?.route ?: movieList

    val title = when (currentDestination) {
        movieList -> popularMovies
        movieInfoRoute -> movieDetails
        else -> appName
    }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopBar(
                title,
                canNavigateBack = navController.previousBackStackEntry != null,
                navController,
                scrollBehavior
            )
        },
        containerColor = colorScheme.primaryContainer,
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = movieList,
            modifier = Modifier.padding(paddingValues),
        ) {
            composable(movieList) {
                MovieListScreen(navController = navController)
            }
            composable(movieInfoRoute) { backStackEntry ->
                val movieId = backStackEntry.arguments?.getString(movieIdString)
                MovieInfoScreen(navController = navController, movieId = movieId)
            }
        }
    }
}
