package com.uvg.edu.gt.uvghorasbeca.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController

import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.uvg.edu.gt.uvghorasbeca.ui.view.screens.LoginScreen
import com.uvg.edu.gt.uvghorasbeca.ui.view.screens.WelcomeScreen

@Composable
fun Navigation(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()

    // Get the current backstack entry as a state
    val currentBackStackEntry by navController.currentBackStackEntryAsState()

    // Access the route of the current destination
    val currentScreen = currentBackStackEntry?.destination?.route ?: NavigationState.WelcomeScreen.route
    NavHost(
        navController = navController,
        startDestination = NavigationState.WelcomeScreen.route,

    ){
        composable(route = NavigationState.WelcomeScreen.route){
            WelcomeScreen(navController = navController)
        }
        composable(route = NavigationState.LoginScreen.route){
            LoginScreen(navController = navController)
        }
    }
}