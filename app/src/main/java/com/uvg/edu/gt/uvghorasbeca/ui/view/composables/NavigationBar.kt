package com.uvg.edu.gt.uvghorasbeca.ui.view.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.uvg.edu.gt.uvghorasbeca.R
import com.uvg.edu.gt.uvghorasbeca.navigation.NavigationState

@Composable
fun TopAppBar(modifier: Modifier = Modifier, onMenuClick: () -> Unit) {
    Row(
        modifier = modifier
            .background(color = Color(0xFF27C24C))
            .fillMaxWidth()
            .padding(top = 10.dp)
            .height(56.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onMenuClick) {
            Icon(
                painterResource(id = R.drawable.hamburguer_top_menu_icon),
                contentDescription = "Menu",
                modifier = Modifier.size(50.dp),
                tint = Color.White
            )
        }

        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = "UVG",
            color = Color.White,
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(end = 16.dp)
        )
    }
}

data class BottomNavItem(
    val icon: Int,
    val route: String,
    val contentDescription: String
)

@Composable
fun BottomNavigationBar(
    navController: NavController,
    isAdmin: Boolean,
    items: List<BottomNavItem>
) {
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    Box(
        modifier = Modifier
            .background(color = Color(0xFFEFEFEF))
            .fillMaxWidth()
//            .padding(bottom = 10.dp)

    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
//                .padding(bottom = 0.dp)
                .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)), // Esquinas redondeadas
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly // Distribución equitativa entre los íconos
        ) {
            items.forEach { item ->
                IconButton(
                    onClick = {
                        if (currentRoute != item.route && item.route != null) {
                            navController.navigate(item.route) {
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    },
                    modifier = Modifier
                        .size(56.dp) // Tamaño del botón para mejorar la zona táctil
                        .align(Alignment.CenterVertically)
                ) {
                    Icon(
                        painter = painterResource(id = item.icon),
                        contentDescription = item.contentDescription,
                        modifier = Modifier.size(46.dp),
                        tint = Color.Black
                    )
                }
            }
        }
    }
}

@Composable
fun SetupBottomNav(navController: NavController, isAdmin: Boolean) {
    // Generar la lista de ítems según el rol del usuario
    val items = if (isAdmin) {
        listOf(
            BottomNavItem(R.drawable.available_tasks_view_icon, NavigationState.AdminTasks.route, "Admin Tasks"),
            BottomNavItem(R.drawable.pending_hours_view_icon, NavigationState.PendingHours.route, "Pending Hours"),
            BottomNavItem(R.drawable.hours_history_view_icon, NavigationState.HoursHistory.route, "Hours History")
        )
    } else {
        listOf(
            BottomNavItem(R.drawable.available_tasks_view_icon, NavigationState.AvailableTasks.route, "Available Tasks"),
            BottomNavItem(R.drawable.pending_hours_view_icon, NavigationState.PendingHours.route, "Pending Hours"),
            BottomNavItem(R.drawable.hours_history_view_icon, NavigationState.HoursHistory.route, "Hours History")
        )
    }

    // Pasamos la lista de ítems a la barra de navegación
    BottomNavigationBar(navController = navController, isAdmin = isAdmin, items = items)
}