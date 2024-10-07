package com.uvg.edu.gt.uvghorasbeca.ui.view.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.uvg.edu.gt.uvghorasbeca.R

/**
 * Welcome screen composable, transitions via the button
 * to the login screen
 */
@Composable
fun WelcomeScreen(navController : NavController) {
    // Box to contain the image background and the column with other elements
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Image as background with tint
        Image(
            painter = painterResource(id = R.drawable.uvg), // Replace with your image drawable
            contentDescription = null,
            contentScale = ContentScale.Crop, // Scale the image to fill the Box
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary, blendMode = BlendMode.Modulate),
            modifier = Modifier
                .matchParentSize() // Match the Box size
        )

        // Centered content column
        Column(
            modifier = Modifier
                .fillMaxSize(0.67f) // Take 2/3rds of screen width
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally, // Center horizontally in Box
            verticalArrangement = Arrangement.Center // Center content vertically in Column
        ) {
            // Drawable at the top
            Image(
                painter = painterResource(id = R.drawable.logouvg), // Replace with drawable
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth(0.75f) // Adjust size as needed
                    .padding(bottom = 16.dp) // Padding below the image
            )

            // Text in the middle
            Text(
                text = stringResource(R.string.horas_beca),
                fontWeight = FontWeight.Bold,
                fontSize = 32.sp,
                color = Color.White, // Text color
                modifier = Modifier.padding(bottom = 32.dp)
            )

            // Login Button
            Button(
                onClick = {
                    navController.navigate("LoginScreen")
                },
                modifier = Modifier
                    .fillMaxWidth(0.5f),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color.White // Text color
                )
            ) {
                Text(text = stringResource(R.string.login))
            }
        }
    }
}
