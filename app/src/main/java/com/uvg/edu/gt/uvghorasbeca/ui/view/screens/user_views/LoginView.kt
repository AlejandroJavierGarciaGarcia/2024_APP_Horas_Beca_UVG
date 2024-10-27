package com.uvg.edu.gt.uvghorasbeca.ui.view.screens.user_views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.app.ui.theme.CustomColors
import com.uvg.edu.gt.uvghorasbeca.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginView(navController: NavController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(CustomColors.PrimaryGreen),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .padding(top = 180.dp)
            .fillMaxWidth()
        ) {
            // Logo UVG
            Image(
                painter = painterResource(id = R.drawable.uvg_logo),
                contentDescription = "Logo UVG",
                modifier = Modifier
                    .height(150.dp)
                    .width(200.dp)
                    .padding(bottom = 32.dp),
                contentScale = ContentScale.Fit,
                colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(CustomColors.White)
            )

            // Campo de texto para el usuario
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Usuario") },
                modifier = Modifier
                    .width(280.dp)
                    .padding(vertical = 8.dp),
                singleLine = true,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = CustomColors.PrimaryGrayDark,
                    unfocusedBorderColor = CustomColors.PrimaryGrayLight,
                    containerColor = Color(0xFFE0E0E0)
                )
            )

            // Campo de texto para la contraseña
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Contraseña") },
                modifier = Modifier
                    .width(280.dp)
                    .padding(vertical = 8.dp),
                singleLine = true,
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                trailingIcon = {
                    val icon = if (passwordVisible) R.drawable.eye_off_icon else R.drawable.eye_icon
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(painter = painterResource(id = icon), contentDescription = "Toggle password visibility")
                    }
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = CustomColors.PrimaryGrayDark,
                    unfocusedBorderColor = CustomColors.PrimaryGrayLight,
                    containerColor = Color(0xFFE0E0E0)
                )
            )

            // Botón de inicio de sesión
            Button(
                onClick = {
                    // Aquí iría la lógica para enviar los datos al backend (autenticación)
                    // Se puede preparar una función para cuando esté listo el backend.
                    // Ejemplo de navegación a otra pantalla:
                    // navController.navigate("HomeView")
                },
                modifier = Modifier
                    .width(200.dp) // Cambiar a un ancho fijo
                    .padding(top = 16.dp)
                    .height(50.dp), // Ajustar la altura del botón
                colors = ButtonDefaults.buttonColors(
                    containerColor = CustomColors.Black, // Fondo negro
                    contentColor = CustomColors.White // Texto blanco
                )
            ) {
                Text(text = "Iniciar sesión", fontSize = 16.sp)
            }
        }
    }
}
