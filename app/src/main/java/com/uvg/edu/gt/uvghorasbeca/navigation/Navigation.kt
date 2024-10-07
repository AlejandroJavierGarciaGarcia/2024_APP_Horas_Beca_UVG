package com.uvg.edu.gt.uvghorasbeca.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController

import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.uvg.edu.gt.uvghorasbeca.ui.view.composables.BottomNavigationBar
import com.uvg.edu.gt.uvghorasbeca.ui.view.composables.TopAppBar
import com.uvg.edu.gt.uvghorasbeca.ui.view.screens.AdminController
import com.uvg.edu.gt.uvghorasbeca.ui.view.screens.HistoryView
import com.uvg.edu.gt.uvghorasbeca.ui.view.screens.LoginScreen
import com.uvg.edu.gt.uvghorasbeca.ui.view.screens.UserView
import com.uvg.edu.gt.uvghorasbeca.ui.view.screens.WelcomeScreen

@Composable
fun Navigation(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()

    // Obtener la entrada actual de la pila de navegación
    val currentBackStackEntry by navController.currentBackStackEntryAsState()

    // Obtener la ruta de la pantalla actual
    val currentScreen = currentBackStackEntry?.destination?.route ?: NavigationState.WelcomeScreen.route

    Scaffold(
        topBar = {

            if (currentScreen != NavigationState.WelcomeScreen.route && currentScreen != NavigationState.LoginScreen.route) {
                TopAppBar(modifier = Modifier.statusBarsPadding())
            } },
        bottomBar = {
            if (currentScreen != NavigationState.WelcomeScreen.route && currentScreen != NavigationState.LoginScreen.route) {
                BottomNavigationBar(modifier = Modifier.statusBarsPadding(),navController = navController)
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = NavigationState.WelcomeScreen.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = NavigationState.WelcomeScreen.route) {
                WelcomeScreen(navController = navController)
            }
            composable(route = NavigationState.LoginScreen.route) {
                LoginScreen(navController = navController)
            }
            composable(route = NavigationState.HistoryScreen.route) {
                HistoryView(navController = navController)
            }
            composable(route = NavigationState.AdminController.route) {
                AdminController(navController = navController)
            }
            composable(route = NavigationState.UserController.route) {
                UserView(navController = navController)
            }
        }
    }
}
