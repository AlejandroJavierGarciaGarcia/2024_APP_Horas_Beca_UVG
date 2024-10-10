package com.uvg.edu.gt.uvghorasbeca.ui.view.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.uvg.edu.gt.uvghorasbeca.R

@Composable
fun TopAppBar(modifier: Modifier = Modifier, onMenuClick: () -> Unit) {
    Row(
        modifier = modifier
            .background(color = Color(0xFF27C24C)) // Verde UVG
            .fillMaxWidth()
            .height(56.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onMenuClick) {
            Icon(
                imageVector = Icons.Filled.Menu,
                contentDescription = "Menu",
                modifier = Modifier.size(40.dp),
                tint = Color.White
            )
        }

        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = "UVG",
            color = Color.White,
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}

@Composable
fun BottomNavigationBar(modifier: Modifier = Modifier, navController: NavController, isAdmin: Boolean) {
    val currentRoute = navController.currentBackStackEntryAsState()?.value?.destination?.route

    Row(
        modifier = Modifier
            .background(color = Color(0xFF27C24C)) // Verde UVG
            .fillMaxWidth()
            .height(60.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        // Primer botón: Tareas (disponibles o administrativas)
        IconButton(
            onClick = {
                if (currentRoute != if (isAdmin) "AdminTasksView" else "AvailableTasksView") {
                    navController.navigate(if (isAdmin) "AdminTasksView" else "AvailableTasksView")
                }
            }
        ) {
            Icon(
                painterResource(id = R.drawable.available_tasks_view_icon),
                contentDescription = "Tasks",
                modifier = Modifier.size(40.dp),
                tint = Color.White
            )
        }

        // Segundo botón: Horas Pendientes
        IconButton(
            onClick = {
                if (currentRoute != "PendingHoursView") {
                    navController.navigate("PendingHoursView")
                }
            }
        ) {
            Icon(
                painterResource(id = R.drawable.pending_hours_view_icon),
                contentDescription = "Pending Hours",
                modifier = Modifier.size(40.dp),
                tint = Color.White
            )
        }

        // Tercer botón: Historial de Horas
        IconButton(
            onClick = {
                if (currentRoute != "HoursHistoryView") {
                    navController.navigate("HoursHistoryView")
                }
            }
        ) {
            Icon(
                painterResource(id = R.drawable.hours_history_view_icon),
                contentDescription = "Hours History",
                modifier = Modifier.size(40.dp),
                tint = Color.White
            )
        }
    }
}

//@Composable
//fun DrawerButton(icon: ImageVector, label: String, onClick: () -> Unit) {
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .clickable { onClick() }
//            .padding(16.dp)
//            .background(Color.White, shape = MaterialTheme.shapes.small)
//            .padding(8.dp),
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//        Icon(imageVector = icon, contentDescription = label)
//        Spacer(modifier = Modifier.width(16.dp))
//        Text(text = label, fontSize = 16.sp)
//    }
//}
