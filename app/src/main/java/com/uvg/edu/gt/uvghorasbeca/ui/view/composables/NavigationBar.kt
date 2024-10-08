package com.uvg.edu.gt.uvghorasbeca.ui.view.composables

import androidx.compose.material3.*
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.uvg.edu.gt.uvghorasbeca.R


import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import com.uvg.edu.gt.uvghorasbeca.navigation.NavigationState
import com.uvg.edu.gt.uvghorasbeca.ui.theme.Black
import kotlinx.coroutines.launch


@Composable
fun TopAppBar(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .background(color = Color(0xFF27C24C))
            .fillMaxWidth()
            .height(56.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { /* Acción al hacer clic en el ícono */ }) {
            Icon(
                imageVector = Icons.Filled.Menu,
                contentDescription = "Menú",
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
        /*Image(
            painter = painterResource(id = R.drawable.logouvg),
            contentDescription = "Logo UVG",
            modifier = Modifier
                .size(80.dp)
        )*/
    }
    Row (
        modifier = modifier
            .background(color = Color(0xFF7DFF9C))
            .fillMaxWidth()
            .height(5.dp),
        verticalAlignment = Alignment.CenterVertically)
    {
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
                contentDescription = "Home",
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
                contentDescription = "Search",
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
                contentDescription = "Profile",
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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    // Estado del panel lateral
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Text("Drawer Title", modifier = Modifier.padding(16.dp))
                Divider()
                NavigationDrawerItem(
                    label = { Text(text = "Opción 1") },
                    selected = false,
                    onClick = { /* Navegar a Opción 1 */ }
                )
                NavigationDrawerItem(
                    label = { Text(text = "Opción 2") },
                    selected = false,
                    onClick = { /* Navegar a Opción 2 */ }
                )
                // Agrega más elementos según sea necesario
            }
        }
    ) {
        // Contenido principal de la pantalla
        Column {
            TopAppBar(
                onMenuClick = {
                    scope.launch {
                        if (drawerState.isClosed) {
                            drawerState.open()
                        } else {
                            drawerState.close()
                        }
                    }
                }
            )
            // Resto del contenido de la pantalla
            Text("Contenido Principal", modifier = Modifier.padding(16.dp))
        }
    }
}

@Composable
fun TopAppBar(onMenuClick: () -> Unit, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .background(color = Color(0xFF27C24C))
            .fillMaxWidth()
            .height(56.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { onMenuClick() }) {
            Icon(
                imageVector = Icons.Filled.Menu,
                contentDescription = "Menú",
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
fun DrawerContent(navController: NavController, onClose: () -> Unit) {
    // Define your drawer items here
    Column {
        Text("Home", modifier = Modifier.clickable {
            navController.navigate(NavigationState.WelcomeScreen.route)
            onClose()
        })
        Text("History", modifier = Modifier.clickable {
            navController.navigate(NavigationState.HistoryScreen.route)
            onClose()
        })
        Text("Admin", modifier = Modifier.clickable {
            navController.navigate(NavigationState.AdminController.route)
            onClose()
        })
        // Add other menu items as needed
    }
}