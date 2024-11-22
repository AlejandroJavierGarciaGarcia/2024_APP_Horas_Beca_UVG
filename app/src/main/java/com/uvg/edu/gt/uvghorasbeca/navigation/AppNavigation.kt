package com.uvg.edu.gt.uvghorasbeca.navigation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.uvg.edu.gt.uvghorasbeca.ui.view.composables.SetupBottomNav
import com.uvg.edu.gt.uvghorasbeca.ui.view.composables.TopAppBar
import com.uvg.edu.gt.uvghorasbeca.ui.view.screens.admin_views.AddTaskScreen
import com.uvg.edu.gt.uvghorasbeca.ui.view.screens.admin_views.AdminTaskDetailsView
import com.uvg.edu.gt.uvghorasbeca.ui.view.screens.admin_views.AdminTasksView
import com.uvg.edu.gt.uvghorasbeca.ui.view.screens.user_views.AvailableTasksView
import com.uvg.edu.gt.uvghorasbeca.ui.view.screens.user_views.HoursHistoryView
import com.uvg.edu.gt.uvghorasbeca.ui.view.screens.user_views.LoginView
import com.uvg.edu.gt.uvghorasbeca.ui.view.screens.user_views.PendingHoursView
import com.uvg.edu.gt.uvghorasbeca.ui.view.screens.user_views.ProfileProgressView
import com.uvg.edu.gt.uvghorasbeca.ui.view.screens.user_views.TaskDetailsView
import com.uvg.edu.gt.uvghorasbeca.ui.view.viewmodel.AuthViewModel
import com.uvg.edu.gt.uvghorasbeca.ui.view.viewmodels.TaskDataViewModel
import kotlinx.coroutines.launch

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun AppNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    isAdmin: Boolean,
    isLoggedIn: Boolean,
    authViewModel: AuthViewModel,
    taskViewModel: TaskDataViewModel
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = currentBackStackEntry?.destination?.route ?: NavigationState.LoginScreen.route

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            if (isLoggedIn) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .zIndex(1f)
                ) {
                    ProfileProgressView(navController = navController, authViewModel = authViewModel)
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                if (currentScreen != NavigationState.LoginScreen.route) {
                    TopAppBar(
                        onMenuClick = {
                            coroutineScope.launch {
                                if (drawerState.isClosed) drawerState.open() else drawerState.close()
                            }
                        }
                    )
                }
            },
            bottomBar = {
                if (currentScreen != NavigationState.LoginScreen.route) {
                    SetupBottomNav(navController = navController, isAdmin = isAdmin)
                }
            },
            content = { paddingValues ->
                NavHost(
                    navController = navController,
                    startDestination = if (isAdmin) NavigationState.AdminTasks.route else NavigationState.AvailableTasks.route,
                    modifier = Modifier.padding(paddingValues)
                ) {
                    // Admin Screens
                    composable(route = NavigationState.AdminTasks.route) {
                        AdminTasksView(navController = navController, taskDataViewModel = taskViewModel)
                    }
                    composable(
                        route = NavigationState.AdminTaskDetails.route + "/{taskId}",
                        arguments = listOf(navArgument("taskId") { type = NavType.StringType })
                    ) { backStackEntry ->
                        val taskId = backStackEntry.arguments?.getString("taskId")
                        val task = taskViewModel.allTasks.value.find {
                            it.id == (taskId)
                        }
                        task?.let {
                            AdminTaskDetailsView(
                                navController = navController,
                                task = it,
                                onDismiss = { navController.popBackStack() }
                            )
                        }
                    }
                    composable(route = NavigationState.AddTask.route) {
                        AddTaskScreen(
                            navController = navController,
                            onDismiss = { navController.popBackStack() },
                            onSubmit = {
                                navController.popBackStack()
                            }
                        )
                    }

                    composable(route = NavigationState.AvailableTasks.route) {
                        AvailableTasksView(navController = navController, taskViewModel = taskViewModel)
                    }
                    composable(route = NavigationState.PendingHours.route) {
                        PendingHoursView(navController = navController, taskDataViewModel = taskViewModel)
                    }
                    composable(route = NavigationState.HoursHistory.route) {
                        HoursHistoryView(navController = navController, taskDataViewModel = taskViewModel)
                    }
                    composable(route = NavigationState.ProfileProgress.route) {
                        ProfileProgressView(navController = navController, authViewModel = authViewModel)
                    }
                    composable(
                        route = NavigationState.TaskDetails.route + "/{taskId}",
                        arguments = listOf(navArgument("taskId") { type = NavType.StringType })
                    ) { backStackEntry ->
                        val taskId = backStackEntry.arguments?.getString("taskId")
                        val task = taskViewModel.allTasks.value.find {
                            it.id == (taskId)
                        }
                        task?.let {
                            TaskDetailsView(
                                navController = navController,
                                task = it,
                                onDismiss = { navController.popBackStack() },
                                authViewModel = authViewModel,
                                taskDataViewModel = taskViewModel
                            )
                        }
                    }

                    composable(route = NavigationState.LoginScreen.route) {
                        LoginView(navController = navController, authViewModel = authViewModel)
                    }
                }
            }
        )
    }
}

