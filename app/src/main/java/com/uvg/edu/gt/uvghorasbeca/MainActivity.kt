package com.uvg.edu.gt.uvghorasbeca

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.uvg.edu.gt.uvghorasbeca.navigation.Navigation
import com.uvg.edu.gt.uvghorasbeca.ui.theme.UVGHorasBecaTheme

// MainActivity class inheriting from ComponentActivity
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        // Sets the Compose content for this activity
        setContent {
            UVGHorasBecaTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    // Navigation composable is called here to handle the navigation between screens
                    Navigation(
                        modifier = Modifier.padding(innerPadding),

                        )
                }
            }
        }
    }
}
