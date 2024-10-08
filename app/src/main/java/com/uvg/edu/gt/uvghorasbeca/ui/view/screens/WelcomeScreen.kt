package com.uvg.edu.gt.uvghorasbeca.ui.view.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
            painter = painterResource(id = R.drawable.uvg),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            colorFilter = ColorFilter.tint(color = Color(0xFF008000), blendMode = BlendMode.Modulate),
            modifier = Modifier
                .matchParentSize()
        )

        // Centered content column
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .width(350.dp)
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.logouvg),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth(0.75f)
                    .padding(bottom = 16.dp)
            )

            Text(
                text = stringResource(R.string.horas_beca),
                fontWeight = FontWeight.Bold,
                fontSize = 32.sp,
                color = Color.White,
                modifier = Modifier.padding(bottom = 36.dp)
            )


            Button(
                onClick = {
                    navController.navigate("LoginScreen")
                },
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .height(58.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = Color.Black,
                )
            ) {
                Text(text = stringResource(R.string.login),
                    fontSize = 22.sp)
            }
        }
    }
}
