package com.uvg.edu.gt.uvghorasbeca

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.uvg.edu.gt.uvghorasbeca.navigation.AppNavigation
import com.uvg.edu.gt.uvghorasbeca.ui.theme.UVGHorasBecaTheme
import com.uvg.edu.gt.uvghorasbeca.ui.view.screens.user_views.LoginView


class MainActivity : ComponentActivity() {

    // Variables que controlan el estado de sesión y rol
    private var isLoggedIn: Boolean = false
    private var isAdmin: Boolean = false

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController: NavHostController = rememberNavController()
            AppNavigation(navController = navController, isAdmin = isAdmin)
        }

        // Lógica manual de login y admin por ahora, será reemplazada por backend en el futuro
        isLoggedIn = true   // Simulando que el usuario está logueado
        isAdmin = false      // Simulando que el usuario es admin

        setContent {
            UVGHorasBecaTheme {
                // Crear una instancia única de NavController
                val navController = rememberNavController()

                // Configuración del Scaffold para la navegación y el contenido
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    when {
                        isLoggedIn && isAdmin -> {
                            AdminApp(navController = navController)  // Pasando el NavController
                        }
                        isLoggedIn && !isAdmin -> {
                            UserApp(navController = navController)  // Pasando el NavController
                        }
                        else -> {
                            LoginView(navController = navController) // Vista de Login
                        }
                    }
                }
            }
        }
    }

    // Navegación y contenido de la app para Administradores
    @Composable
    fun AdminApp(navController: NavHostController, modifier: Modifier = Modifier) {
        AppNavigation(navController = navController, isAdmin = true)
    }

    // Navegación y contenido de la app para Usuarios normales
    @Composable
    fun UserApp(navController: NavHostController, modifier: Modifier = Modifier) {
        AppNavigation(navController = navController, isAdmin = false)
    }
}




