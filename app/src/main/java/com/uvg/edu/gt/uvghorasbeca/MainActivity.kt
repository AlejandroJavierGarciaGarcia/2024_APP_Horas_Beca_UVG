package com.uvg.edu.gt.uvghorasbeca

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.uvg.edu.gt.uvghorasbeca.ui.theme.UVGHorasBecaTheme
import kotlinx.coroutines.launch
import androidx.compose.ui.graphics.Color.Companion.Black

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UVGHorasBecaTheme {
                MainScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            SideBarContent(
                onCloseDrawer = {
                    scope.launch { drawerState.close() }
                }
            )
        },
        modifier = Modifier.fillMaxSize(),
        scrimColor = Color.Transparent
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            IconButton(onClick = {
                scope.launch { drawerState.open() }
            }) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = "Abrir menú"
                )
            }
            UserProfileScreen()
        }
    }
}

@Composable
fun SideBarContent(onCloseDrawer: () -> Unit) {
    var username by remember { mutableStateOf("Juan Perez") }
    var hoursCompleted by remember { mutableStateOf(50) }
    var hoursRemaining by remember { mutableStateOf(10) }
    var showDialog by remember { mutableStateOf(false) }
    var dialogMessage by remember { mutableStateOf("") }
    val totalHours = hoursCompleted + hoursRemaining

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .background(Color.DarkGray)
            .padding(16.dp),
        verticalArrangement = Arrangement.Top, // Los elementos van hacia arriba
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Imagen de usuario y datos
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Image(
                painter = painterResource(id = R.drawable.user),
                contentDescription = "Imagen de usuario",
                modifier = Modifier.size(50.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = username, fontSize = 20.sp, color = Black) // Cambiado a negro
        }

        // Añadido un pequeño espaciador para separar el username de la barra de progreso
        Spacer(modifier = Modifier.height(8.dp)) // Ajusta el tamaño para bajar la barra ligeramente

        // Barra de progreso
        LinearProgressIndicator(
            progress = hoursCompleted / totalHours.toFloat(),
            color = Color.Green,
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
        )

        // Texto de horas acumuladas y restantes, pegados a la barra de progreso
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "${hoursCompleted} h acumuladas", fontSize = 12.sp, color = Black) // Cambiado a negro
            Text(text = "${hoursRemaining} h restantes", fontSize = 12.sp, color = Black) // Cambiado a negro
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
                icon = painterResource(id = R.drawable.admiracion),
                onClick = {
                    dialogMessage = "Ayuda"
                    showDialog = true
                }
            )
            IconButtonWithText(
                text = "Cambiar usuario",
                icon = painterResource(id = R.drawable.users),
                onClick = {
                    dialogMessage = "Cambiar usuario"
                    showDialog = true
                }
            )
            IconButtonWithText(
                text = "Cerrar sesión",
                icon = painterResource(id = R.drawable.outsesion),
                onClick = {
                    dialogMessage = "Cerrar sesión"
                    showDialog = true
                }
            )
        }

        Button(
            onClick = { onCloseDrawer() },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Cerrar menú")
        }

        if (showDialog) {
            showMessage(message = dialogMessage, onDismiss = { showDialog = false })
        }
    }
}

@Composable
fun IconButtonWithText(text: String, icon: Painter, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(Color.DarkGray)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = icon,
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        ClickableText(
            text = AnnotatedString(text),
            style = MaterialTheme.typography.bodyLarge.copy(color = Black), // Cambiado a negro
            onClick = { onClick() }
        )
    }
}

@Composable
fun UserProfileScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Pantalla Principal", fontSize = 24.sp)
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
