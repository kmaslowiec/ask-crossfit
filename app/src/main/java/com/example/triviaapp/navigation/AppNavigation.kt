package com.example.triviaapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.triviaapp.screens.HomeScreen

@Composable
fun AppNavigation(modifier: Modifier) {

    val navController = rememberNavController()
    NavHost(
        modifier = modifier,
        contentAlignment = Alignment.TopCenter,
        navController = navController,
        startDestination = "Home Screen",
    )
    {
        composable(route = "Home Screen") {
            HomeScreen(viewModel = hiltViewModel())
        }
    }
}