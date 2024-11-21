package com.uvg.edu.gt.uvghorasbeca.ui.view.screens.user_views

import android.app.Activity
import android.content.Intent
import android.widget.Toast
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
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.app.ui.theme.CustomColors
import com.uvg.edu.gt.uvghorasbeca.MainActivity
import com.uvg.edu.gt.uvghorasbeca.R
import com.uvg.edu.gt.uvghorasbeca.ui.view.viewmodel.AuthState
import com.uvg.edu.gt.uvghorasbeca.ui.view.viewmodel.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginView(navController: NavController, authViewModel: AuthViewModel) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    val context = LocalContext.current


    val authState by authViewModel.authState.observeAsState()

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

            // Email input
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

            // Password input
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

            // Login button
            Button(
                onClick = {
                    authViewModel.login(email, password)
                },
                modifier = Modifier
                    .width(200.dp)
                    .padding(top = 16.dp)
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = CustomColors.Black,
                    contentColor = CustomColors.White
                )
            ) {
                Text(text = "Iniciar sesión", fontSize = 16.sp)
            }

            // Register button
            Button(
                onClick = {
                    authViewModel.signup(email, password)
                },
                modifier = Modifier
                    .width(200.dp)
                    .padding(top = 16.dp)
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = CustomColors.Black,
                    contentColor = CustomColors.White
                )
            ) {
                Text(text = "Registrarse", fontSize = 16.sp)
            }
        }
    }

    when (authState) {
        is AuthState.Loading -> {
            Toast.makeText(context, "Cargando...", Toast.LENGTH_SHORT).show()
        }
        is AuthState.Authenticated -> {
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
            (context as Activity).finish()
        }
        is AuthState.Error -> {
            Toast.makeText(
                context,
                (authState as AuthState.Error).message,
                Toast.LENGTH_SHORT
            ).show()
        }
        is AuthState.Unauthenticated -> {
            // Quedarse en pantalla, aqui puede salir algun mensaje o algo
        }
        else -> {
            // Etc.
        }
    }
}


