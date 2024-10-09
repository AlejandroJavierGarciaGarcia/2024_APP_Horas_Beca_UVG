package com.uvg.edu.gt.uvghorasbeca.ui.view.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.uvg.edu.gt.uvghorasbeca.R

import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.vector.ImageVector
import com.uvg.edu.gt.uvghorasbeca.navigation.NavigationState
import kotlinx.coroutines.launch

@Composable
fun TopAppBar(modifier: Modifier = Modifier, onMenuClick: () -> Unit) {
    Row(
        modifier = modifier
            .background(color = Color(0xFF27C24C))
            .fillMaxWidth()
            .height(56.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onMenuClick) {
            Icon(
                imageVector = Icons.Filled.Menu,
                contentDescription = stringResource(id = R.string.menu),
                modifier = Modifier.size(40.dp),
                tint = Color.White
            )
        }

        Spacer(modifier = Modifier.weight(2f))
        Text(
            text = stringResource(id = R.string.logoUVG),
            color = Color.White,
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}

@Composable
fun BottomNavigationBar(modifier: Modifier = Modifier,navController: NavController) {
    val currentRoute = navController.currentBackStackEntryAsState()?.value?.destination?.route

    Row(
        modifier = Modifier
            .background(color = Color(0xFF27C24C))
            .fillMaxWidth()
            .height(60.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        IconButton(
            onClick = {
                if (currentRoute != "UserController") {
                    navController.navigate("UserController")
                }
            }
        ) {
            Icon(
                imageVector = Icons.Filled.Info,
                contentDescription = stringResource(id = R.string.menu),
                modifier = Modifier.size(40.dp),
                tint = Color.White
            )
        }

        IconButton(
            onClick = {
                if (currentRoute != "AdminController") {
                    navController.navigate("AdminController")
                }
            }
        ) {
            Icon(
                imageVector = Icons.Filled.DateRange,
                contentDescription = stringResource(id = R.string.menu),
                modifier = Modifier.size(40.dp),
                tint = Color.White
            )
        }

        IconButton(
            onClick = {
                if (currentRoute != "HistoryScreen") {
                    navController.navigate("HistoryScreen")
                }
            }
        ) {
            Icon(
                imageVector = Icons.Filled.CheckCircle,
                contentDescription = stringResource(id = R.string.menu),
                modifier = Modifier.size(40.dp),
                tint = Color.White
            )
        }

        IconButton(
            onClick = {
                if (currentRoute != "AssignedActivitiesScreen") {
                    navController.navigate("AssignedActivitiesScreen")
                }
            }
        ) {
            Icon(
                imageVector = Icons.Filled.Notifications,
                contentDescription = "Notifications",
                modifier = Modifier.size(40.dp),
                tint = Color.White
            )
        }
    }
}

@Composable
fun DrawerContent(navController: NavController, onClose: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth(0.85f)
            .fillMaxHeight()
            .background(Color(0xFF27C24C))
            .padding(16.dp)
    ) {
        // Ícono de usuario grande en el centro
        Box(
            modifier = Modifier
                .size(180.dp)
                .padding(30.dp)
                .background(Color.White, shape = CircleShape)
                .align(Alignment.CenterHorizontally)
        ) {
            Image(
                imageVector = Icons.Default.Person,
                contentDescription = "Perfil",
                modifier = Modifier.fillMaxSize()
            )
        }

        Text(
            text = stringResource(id = R.string.usernameExmple),
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.padding(vertical = 4.dp).align(Alignment.CenterHorizontally)
        )
        Text(
            text = stringResource(id = R.string.email),
            fontSize = 16.sp,
            color = Color.Black,
            modifier = Modifier.padding(vertical = 2.dp).align(Alignment.CenterHorizontally)
        )
        Text(
            text = stringResource(id = R.string.student_id),
            fontSize = 16.sp,
            color = Color.Black,
            modifier = Modifier.padding(vertical = 2.dp).align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(16.dp))
        LinearProgressIndicator(
            progress = 0.5f,
            modifier = Modifier.fillMaxWidth(),
            color = Color(0xFF006400) // Verde oscuro (Dark Green)
        )


        Spacer(modifier = Modifier.height(16.dp))

        Divider(color = Color.Black, thickness = 1.dp)

        Spacer(modifier = Modifier.height(16.dp))

        DrawerButton(icon = Icons.Default.CheckCircle, label = stringResource(id = R.string.history)) {
            navController.navigate(NavigationState.HistoryScreen.route)
            onClose()
        }
        DrawerButton(icon = Icons.Default.Settings, label = stringResource(id = R.string.admin)) {
            navController.navigate(NavigationState.AdminController.route)
            onClose()
        }
        DrawerButton(icon = Icons.Default.Home, label = stringResource(id = R.string.home)) {
            navController.navigate(NavigationState.WelcomeScreen.route)
            onClose()
        }
    }
}



@Composable
fun DrawerButton(icon: ImageVector, label: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(16.dp)
            .background(Color.White, shape = MaterialTheme.shapes.small)
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = icon, contentDescription = label)
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = label, fontSize = 16.sp)
    }
}
