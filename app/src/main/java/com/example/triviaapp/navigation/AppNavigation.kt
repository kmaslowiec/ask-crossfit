package com.example.triviaapp.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.triviaapp.screens.HomeScreen

@Composable
fun AppNavigation() {

    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "Home Screen",
    )
    {
        composable(route = "Home Screen") {
            HomeScreen(viewModel = hiltViewModel())
        }
    }
}
