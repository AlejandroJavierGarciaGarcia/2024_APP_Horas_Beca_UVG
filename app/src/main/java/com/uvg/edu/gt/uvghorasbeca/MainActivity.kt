package com.uvg.edu.gt.uvghorasbeca

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.uvg.edu.gt.uvghorasbeca.navigation.Navigation
import com.uvg.edu.gt.uvghorasbeca.ui.theme.UVGHorasBecaTheme
import com.uvg.edu.gt.uvghorasbeca.ui.view.composables.TopAppBar
import com.uvg.edu.gt.uvghorasbeca.ui.view.composables.BottomNavigationBar
import com.uvg.edu.gt.uvghorasbeca.ui.view.screens.HistoryView
import com.uvg.edu.gt.uvghorasbeca.ui.view.screens.AdminController
import com.uvg.edu.gt.uvghorasbeca.ui.view.screens.LoginScreen
import com.uvg.edu.gt.uvghorasbeca.ui.view.screens.WelcomeScreen
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UVGHorasBecaTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Navigation()
                }
            }
        }
    }
}

@Composable
fun MyApp() {
    val navController = rememberNavController()

    Scaffold(
        topBar = { TopAppBar(modifier = Modifier.statusBarsPadding()) },
        bottomBar = { BottomNavigationBar(navController) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("home") { HomeScreen() }
            composable("search") { AdminController(            modifier = Modifier.padding(innerPadding),navController) }
            composable("profile") { HistoryView(navController) }
            composable("notifications") { NotificationsScreen() }  // Nueva pantalla
        }
    }
}

@Composable
fun HomeScreen() {
    Text("Home Screen")
}

@Composable
fun SearchScreen() {
    Text("Search Screen")
}

@Composable
fun ProfileScreen() {
    Text("Profile Screen")
}

@Composable
fun NotificationsScreen() {
    Text("Notifications Screen")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    UVGHorasBecaTheme {
        MyApp()
    }
}