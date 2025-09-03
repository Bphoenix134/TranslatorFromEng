package com.example.translatorfromeng.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.translatorfromeng.presentation.ui.screen.FavoritesScreen
import com.example.translatorfromeng.presentation.ui.screen.MainScreen

@Composable
fun NavGraph(navController: NavHostController = rememberNavController()) {
    NavHost(navController = navController, startDestination = "main") {
        composable("main") { MainScreen(navController) }
        composable("favorites") { FavoritesScreen(navController) }
    }
}