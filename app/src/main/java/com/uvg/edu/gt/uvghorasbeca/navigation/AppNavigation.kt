package com.uvg.edu.gt.uvghorasbeca.navigation

import com.uvg.edu.gt.uvghorasbeca.ui.view.screens.old_views.AssignedActivitiesScreen
import com.uvg.edu.gt.uvghorasbeca.ui.view.screens.old_views.UserView
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.uvg.edu.gt.uvghorasbeca.ui.view.composables.BottomNavigationBar
import com.uvg.edu.gt.uvghorasbeca.ui.view.composables.DrawerContent
import com.uvg.edu.gt.uvghorasbeca.ui.view.composables.TopAppBar
import com.uvg.edu.gt.uvghorasbeca.ui.view.screens.old_views.AdminController
import com.uvg.edu.gt.uvghorasbeca.ui.view.screens.old_views.HistoryView
import com.uvg.edu.gt.uvghorasbeca.ui.view.screens.old_views.LoginScreen
import com.uvg.edu.gt.uvghorasbeca.ui.view.screens.old_views.WelcomeScreen
import kotlinx.coroutines.launch

@Composable
fun Navigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    // Get current back stack entry
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = currentBackStackEntry?.destination?.route ?: NavigationState.WelcomeScreen.route

    // Envuelve el Scaffold dentro del ModalNavigationDrawer
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(navController = navController, onClose = {
                scope.launch { drawerState.close() }
            })
        }
    ) {
        Scaffold(
            topBar = {
                if (currentScreen != NavigationState.WelcomeScreen.route && currentScreen != NavigationState.LoginScreen.route) {
                    TopAppBar(
                        modifier = Modifier.statusBarsPadding(),
                        onMenuClick = {
                            scope.launch {
                                drawerState.open() // Abre el Drawer al hacer clic en el ícono del menú
                            }
                        }
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
