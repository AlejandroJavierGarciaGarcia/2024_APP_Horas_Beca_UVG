package com.uvg.edu.gt.uvghorasbeca.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.uvg.edu.gt.uvghorasbeca.ui.view.composables.BottomNavigationBar
import com.uvg.edu.gt.uvghorasbeca.ui.view.composables.SetupBottomNav
import com.uvg.edu.gt.uvghorasbeca.ui.view.composables.TopAppBar
import com.uvg.edu.gt.uvghorasbeca.ui.view.screens.admin_views.AdminTasksView
import com.uvg.edu.gt.uvghorasbeca.ui.view.screens.admin_views.AdminTaskDetailsView
import com.uvg.edu.gt.uvghorasbeca.ui.view.screens.admin_views.EditTaskView
import com.uvg.edu.gt.uvghorasbeca.ui.view.screens.user_views.AvailableTasksView
import com.uvg.edu.gt.uvghorasbeca.ui.view.screens.user_views.PendingHoursView
import com.uvg.edu.gt.uvghorasbeca.ui.view.screens.user_views.HoursHistoryView
import com.uvg.edu.gt.uvghorasbeca.ui.view.screens.user_views.ProfileProgressView
import com.uvg.edu.gt.uvghorasbeca.ui.view.screens.user_views.TaskDetailsView
import com.uvg.edu.gt.uvghorasbeca.ui.view.screens.user_views.LoginView

@Composable
fun AppNavigation(modifier: Modifier = Modifier, isAdmin: Boolean) {
    val navController = rememberNavController()

    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = currentBackStackEntry?.destination?.route ?: NavigationState.LoginScreen.route

    Scaffold(
        topBar = {
            if (currentScreen != NavigationState.LoginScreen.route) {
                TopAppBar(
                    onMenuClick = {
                        // Aquí defines qué sucede cuando se hace clic en el botón de menú
                    })
            }
        },
        bottomBar = {
            if (currentScreen != NavigationState.LoginScreen.route) {
                SetupBottomNav(navController = navController, isAdmin = isAdmin)
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = if (isAdmin) NavigationState.AdminTasks.route else NavigationState.LoginScreen.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            // Pantallas de administradores
            composable(route = NavigationState.AdminTasks.route) {
                AdminTasksView(navController = navController)
            }
            composable(route = NavigationState.AdminTaskDetails.route) {
                AdminTaskDetailsView(navController = navController)
            }
            composable(route = NavigationState.EditTask.route) {
                EditTaskView(navController = navController)
            }

            // Pantallas de usuarios normales
            composable(route = NavigationState.AvailableTasks.route) {
                AvailableTasksView(navController = navController)
            }
            composable(route = NavigationState.PendingHours.route) {
                PendingHoursView(navController = navController)
            }
            composable(route = NavigationState.HoursHistory.route) {
                HoursHistoryView(navController = navController)
            }
            composable(route = NavigationState.ProfileProgress.route) {
                ProfileProgressView(navController = navController)
            }
            composable(route = NavigationState.TaskDetails.route) {
                TaskDetailsView(navController = navController)
            }

            // Pantalla de login
            composable(route = NavigationState.LoginScreen.route) {
                LoginView(navController = navController)
            }
        }
    }
}
