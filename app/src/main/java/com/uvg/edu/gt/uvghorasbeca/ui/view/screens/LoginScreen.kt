package com.uvg.edu.gt.uvghorasbeca.ui.view.screens

import android.graphics.drawable.Icon
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.uvg.edu.gt.uvghorasbeca.R

/**
 * Login screen composable, displays screen and validates that there is SOME input
 * before being able to navigate to next screen via login button.
 */
@Composable
fun LoginScreen(navController : NavController) {
    // Text field values
    var userText by remember { mutableStateOf(TextFieldValue("")) }
    var passwordText by remember { mutableStateOf(TextFieldValue("")) }

    // Determine if both fields are not empty
    val isLoginEnabled = userText.text.isNotBlank() && passwordText.text.isNotBlank()

    // Main column with padding logic
    Box (
        modifier = Modifier.fillMaxSize()
    ){
        Image(
            painter = painterResource(id = R.drawable.uvg),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            colorFilter = ColorFilter.tint(color = Color(0xFF008000), blendMode = BlendMode.Modulate),
            modifier = Modifier
                .matchParentSize() // Match the Box size
        )
        Column(
            modifier = Modifier
                .matchParentSize()
                .background(Color(0x80008000)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Spacer pads out 1/5th of screen from the top
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f) // Takes 1/5th of screen height
            )
            // Column for content, content starts at top of column (1/5th of the screen down)
            Column(
                modifier = Modifier
                    .width(350.dp)
                    .weight(4f), // Takes the remaining space
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Logo Image
                Image(
                    painter = painterResource(id = R.drawable.logouvg),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth(0.75f) // Take 75% of available width
                        .padding(bottom = 24.dp) // Add bottom padding
                )

                // Username label and TextField
                Text(
                    text = stringResource(R.string.username),
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                        .align(Alignment.Start),
                    fontSize = 18.sp,
                    color = Color.White
                )
                TextField(
                    value = userText,
                    singleLine = true,
                    onValueChange = { newText -> userText = newText },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Email // Set keyboard type to email
                    ),
                    // Add user leading icon
                    leadingIcon = {
                        Icon(
                            Icons.Default.Person,
                            contentDescription = null,
                        )
                    }
                )

                // Password label and TextField
                Text(
                    text = stringResource(R.string.password),
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                        .align(Alignment.Start),
                    fontSize = 18.sp,
                    color = Color.White
                )
                TextField(
                    value = passwordText,
                    singleLine = true,
                    onValueChange = { newText -> passwordText = newText },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp),
                    visualTransformation = PasswordVisualTransformation(), // Hide password characters
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Password // Set keyboard type to password
                    ),
                    // Add password leading icon
                    leadingIcon = {
                        Icon(
                            Icons.Default.Lock,
                            contentDescription = null,
                        )
                    }
                )

                // Login Button
                Button(
                    onClick = { navController.navigate("UserController") },
                    modifier = Modifier
                        .fillMaxWidth(0.6f)
                        .padding(top = 22.dp)
                        .height(58.dp),
                    shape = RoundedCornerShape(8.dp),
                    enabled = isLoginEnabled,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = Color.Black,
                        disabledContainerColor = Color.Gray
                    )
                ) {
                    Text(
                        text = stringResource(R.string.login),
                        fontSize = 22.sp
                    )
                }
            }

            // Spacer to take up the remaining space
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )
        }
    }
}
