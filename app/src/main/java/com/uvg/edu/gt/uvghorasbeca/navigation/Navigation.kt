package com.uvg.edu.gt.uvghorasbeca.navigation

import AssignedActivitiesScreen
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController

import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.uvg.edu.gt.uvghorasbeca.ui.view.composables.BottomNavigationBar
import com.uvg.edu.gt.uvghorasbeca.ui.view.composables.DrawerContent
import com.uvg.edu.gt.uvghorasbeca.ui.view.composables.TopAppBar
import com.uvg.edu.gt.uvghorasbeca.ui.view.screens.AdminController
import com.uvg.edu.gt.uvghorasbeca.ui.view.screens.HistoryView
import com.uvg.edu.gt.uvghorasbeca.ui.view.screens.LoginScreen
import com.uvg.edu.gt.uvghorasbeca.ui.view.screens.UserView
import com.uvg.edu.gt.uvghorasbeca.ui.view.screens.WelcomeScreen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Navigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    // Get current back stack entry
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = currentBackStackEntry?.destination?.route ?: NavigationState.WelcomeScreen.route

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(navController) {
                scope.launch { drawerState.close() } // Close drawer after item is clicked
            }
        }
    ) {
        Scaffold(
            topBar = {
                if (currentScreen != NavigationState.WelcomeScreen.route && currentScreen != NavigationState.LoginScreen.route) {
                    TopAppBar(
                        onMenuClick = { scope.launch { drawerState.open() } }, // Open drawer
                        modifier = Modifier.statusBarsPadding()
                    )
                }
            },
            bottomBar = {
                if (currentScreen != NavigationState.WelcomeScreen.route && currentScreen != NavigationState.LoginScreen.route) {
                    BottomNavigationBar(modifier = Modifier.statusBarsPadding(), navController = navController)
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
                composable(route = NavigationState.AssignedActivitiesScreen.route) {
                    AssignedActivitiesScreen(navController = navController)
                }
            }
        }
    }
}
