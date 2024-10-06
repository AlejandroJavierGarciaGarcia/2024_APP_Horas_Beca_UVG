package com.uvg.edu.gt.uvghorasbeca.ui.view.composables

import android.graphics.fonts.FontStyle
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.uvg.edu.gt.uvghorasbeca.R
import com.uvg.edu.gt.uvghorasbeca.ui.theme.UVGHorasBecaTheme

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
fun BottomNavigationBar(modifier: Modifier = Modifier) {
    Row(
        modifier = Modifier
            .background(color = Color(0xFF27C24C))
            .fillMaxWidth()
            .height(60.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        IconButton(onClick = { /* Acción al hacer clic en el ícono del documento */ }) {
            Icon(
                imageVector = Icons.Filled.Info,
                contentDescription = "Document",
                modifier = Modifier.size(40.dp),
                tint = Color.White
            )
        }
        IconButton(onClick = { /* Acción al hacer clic en el ícono del calendario */ }) {
            Icon(
                imageVector = Icons.Filled.DateRange,
                contentDescription = "Calendar",
                modifier = Modifier.size(40.dp),
                tint = Color.White
            )
        }
        IconButton(onClick = { /* Acción al hacer clic en el ícono de tareas */ }) {
            Icon(
                imageVector = Icons.Filled.CheckCircle,
                contentDescription = "Tasks",
                modifier = Modifier.size(40.dp),
                tint = Color.White
            )
        }
        IconButton(onClick = { /* Acción al hacer clic en el ícono de notificaciones */ }) {
            Icon(
                imageVector = Icons.Filled.Notifications,
                contentDescription = "Notifications",
                modifier = Modifier.size(40.dp),
                tint = Color.White
            )
        }
    }
}