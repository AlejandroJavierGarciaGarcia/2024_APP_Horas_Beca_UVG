package com.uvg.edu.gt.uvghorasbeca

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.uvg.edu.gt.uvghorasbeca.navigation.AppNavigation
import com.uvg.edu.gt.uvghorasbeca.ui.theme.UVGHorasBecaTheme
import com.uvg.edu.gt.uvghorasbeca.ui.view.composables.SetupBottomNav
import com.uvg.edu.gt.uvghorasbeca.ui.view.screens.user_views.LoginView

class MainActivity : ComponentActivity() {

    // Variables que controlan el estado de sesión y rol
    private var isLoggedIn: Boolean = false
    private var isAdmin: Boolean = false

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // logica manual de login y admin por ahora, será reemplazada por backend en el futuro
        isLoggedIn = true   // simulando que el usuario está logueado
        isAdmin = false      // simulando que el usuario es admin

        setContent {
            UVGHorasBecaTheme {
                // Configuración del Scaffold para la navegación y el contenido
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    when {
                        isLoggedIn && isAdmin -> {
                            AdminApp()
                        }
                        isLoggedIn && !isAdmin -> {
                            UserApp()
                        }
                        else -> {
                            val navController = rememberNavController()
                            LoginView(navController = navController)
                        }
                    }
                }
            }
        }
    }

    // Navegación y contenido de la app para Administradores
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @Composable
    fun AdminApp(modifier: Modifier = Modifier) {
        val navController = rememberNavController()
        Scaffold(
            modifier = modifier.fillMaxSize(),
            bottomBar = { SetupBottomNav(navController = navController, isAdmin = isAdmin) }
        ) {
            AppNavigation(isAdmin = isAdmin)
        }
    }

    // Navegación y contenido de la app para Usuarios normales
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @Composable
    fun UserApp(modifier: Modifier = Modifier) {
        val navController = rememberNavController()
        Scaffold(
            modifier = modifier.fillMaxSize(),
            bottomBar = { SetupBottomNav(navController = navController, isAdmin = isAdmin) }
        ) {
            AppNavigation(isAdmin = isAdmin)
        }
    }
}


