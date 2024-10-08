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

@Composable
fun showMessage(message: String, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("OK")
            }
        },
        title = {
            Text(text = "Advertencia")
        },
        text = {
            Text("Presionaste $message")
        }
    )
}

@Composable
fun IconButtonWithText(text: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 20.dp)
            .background(Color.Gray)
            .padding(horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.width(16.dp))
        ClickableText(
            text = AnnotatedString(text),
            style = MaterialTheme.typography.bodyLarge.copy(color = Black), // Cambiado a negro
            onClick = { onClick() }
        )
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

    var username by remember { mutableStateOf("Juan Perez") }
    var hoursCompleted by remember { mutableStateOf(50) }
    var hoursRemaining by remember { mutableStateOf(10) }
    var showDialog by remember { mutableStateOf(false) }
    var dialogMessage by remember { mutableStateOf("") }
    val totalHours = hoursCompleted + hoursRemaining
    
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .background(Color.Gray)
            .padding(16.dp),
        verticalArrangement = Arrangement.Top, // Los elementos van hacia arriba
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Añadido un pequeño espaciador para separar el username de la barra de progreso
        Spacer(modifier = Modifier.height(100.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ){

            Image(
                painter = painterResource(id = R.drawable.user),
                contentDescription = "Imagen de usuario",
                modifier = Modifier.size(100.dp)
            )
            Spacer(modifier = Modifier.width(50.dp))
            Text(text = username, fontSize = 50.sp, color = Black) // Cambiado a negro
        }

        // Añadido un pequeño espaciador para separar el username de la barra de progreso
        Spacer(modifier = Modifier.height(100.dp)) // Ajusta el tamaño para bajar la barra ligeramente

        // Barra de progreso
        LinearProgressIndicator(
            progress = hoursCompleted / totalHours.toFloat(),
            color = Color.Green,
            modifier = Modifier
                .fillMaxWidth()
                .height(20.dp)
        )

        // Texto de horas acumuladas y restantes, pegados a la barra de progreso
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "${hoursCompleted} h acumuladas", fontSize = 15.sp, color = Black) // Cambiado a negro
            Text(text = "${hoursRemaining} h restantes", fontSize = 15.sp, color = Black) // Cambiado a negro
        }

        Spacer(modifier = Modifier.weight(1f)) // Empuja los botones hacia la parte inferior de la pantalla

        // Botones de ayuda, cambiar usuario y cerrar sesión
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.Start
        ) {
            IconButtonWithText(
                text = "Ayuda",
                onClick = {
                    dialogMessage = "Ayuda"
                    showDialog = true
                }
            )
            IconButtonWithText(
                text = "Cambiar usuario",
                onClick = {
                    dialogMessage = "Cambiar usuario"
                    showDialog = true
                }
            )
            IconButtonWithText(
                text = "Cerrar sesión",
                onClick = {
                    dialogMessage = "Cerrar sesión"
                    showDialog = true
                }
            )
        }

        if (showDialog) {
            showMessage(message = dialogMessage, onDismiss = { showDialog = false })
        }
    }
}