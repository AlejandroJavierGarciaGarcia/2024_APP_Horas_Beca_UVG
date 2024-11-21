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
    private val taskViewModel: TaskDataViewModel = TaskDataViewModel()

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        FirebaseFirestore.getInstance().collection("TaskData").get()
            .addOnSuccessListener { snapshot ->
                Log.d("FirestoreTest", "Fetched documents: ${snapshot.documents}")
            }
            .addOnFailureListener { e ->
                Log.e("FirestoreTest", "Error fetching documents", e)
            }
        setContent {
            UVGHorasBecaTheme {
                // Create a single NavController instance
                val navController = rememberNavController()

                // Observe authentication state from AuthViewModel using observeAsState
                val authState by authViewModel.authState.observeAsState(AuthState.Loading)

                // Main scaffold for navigation and content
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    when (authState) {
                        is AuthState.Authenticated -> {
                            UserApp(
                                navController = navController,
                                authViewModel = authViewModel,
                                taskViewModel = taskViewModel
                            )
                        }
                        is AuthState.Unauthenticated -> {
                            LoginView(navController = navController, authViewModel = authViewModel)
                        }
                        is AuthState.Loading -> {
                            // Add a simple loading composable or keep this empty
                        }
                        is AuthState.Error -> {
                            LoginView(navController = navController, authViewModel = authViewModel)
                        }
                    }
                }
            }
        }
    }

    // User-specific navigation and content
    @Composable
    fun UserApp(
        navController: NavHostController,
        authViewModel: AuthViewModel,
        taskViewModel: TaskDataViewModel,
        modifier: Modifier = Modifier
    ) {
        AppNavigation(
            navController = navController,
            isAdmin = true,
            isLoggedIn = true,
            authViewModel = authViewModel,
            taskViewModel = taskViewModel
        )
    }
}
