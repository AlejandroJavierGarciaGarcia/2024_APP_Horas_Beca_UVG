package com.uvg.edu.gt.uvghorasbeca

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import com.uvg.edu.gt.uvghorasbeca.navigation.AppNavigation
import com.uvg.edu.gt.uvghorasbeca.ui.theme.UVGHorasBecaTheme
import com.uvg.edu.gt.uvghorasbeca.ui.view.screens.user_views.LoginView
import com.uvg.edu.gt.uvghorasbeca.ui.view.viewmodel.AuthState
import com.uvg.edu.gt.uvghorasbeca.ui.view.viewmodel.AuthViewModel
import com.uvg.edu.gt.uvghorasbeca.data.repository.FirebaseTaskDataRepository
import com.uvg.edu.gt.uvghorasbeca.ui.view.viewmodels.TaskDataViewModel

class MainActivity : ComponentActivity() {

    // Initialize ViewModels
    private val authViewModel: AuthViewModel = AuthViewModel()
    private val taskViewModel: TaskDataViewModel = TaskDataViewModel(authViewModel)

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        setContent {
            UVGHorasBecaTheme {
                // NavController instance
                val navController = rememberNavController()

                // authState as state
                val authState by authViewModel.authState.observeAsState(AuthState.Loading)

                // Main scaffold for navigation and content
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    when (authState) {
                        // Autenticado -> UserApp -> Navigation
                        is AuthState.Authenticated -> {
                            UserApp(
                                navController = navController,
                                authViewModel = authViewModel,
                                taskViewModel = taskViewModel
                            )
                        }
                        // Sin autenticar -> Login
                        is AuthState.Unauthenticated -> {
                            LoginView(navController = navController, authViewModel = authViewModel)
                        }
                        // Cargando -> Pantalla de Carga
                        is AuthState.Loading -> {
                            // Agregar pantalla de carga aqui
                        }
                        // Error / Sin autenticar -> Login
                        is AuthState.Error -> {
                            LoginView(navController = navController, authViewModel = authViewModel)
                        }
                    }
                }
            }
        }
    }

    // Navegacion dependiente del usuario
    @Composable
    fun UserApp(
        navController: NavHostController,
        authViewModel: AuthViewModel,
        taskViewModel: TaskDataViewModel,
        modifier: Modifier = Modifier
    ) {
        AppNavigation(
            navController = navController,
            isAdmin = authViewModel.isAdmin(),
            isLoggedIn = true,
            authViewModel = authViewModel,
            taskViewModel = taskViewModel
        )
    }
}
