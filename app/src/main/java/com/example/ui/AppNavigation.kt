package com.example.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.viewmodel.MainViewModel

@Composable
fun AppNavigation(viewModel: MainViewModel, modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home", modifier = modifier) {
        composable("home") {
            HomeScreen(viewModel = viewModel, onNavigateToPlayer = { navController.navigate("player") })
        }
        composable("player") {
            PlayerScreen(viewModel = viewModel, onBack = { navController.popBackStack() })
        }
    }
}
