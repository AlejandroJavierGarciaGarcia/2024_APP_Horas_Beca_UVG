package com.uvg.edu.gt.uvghorasbeca.ui.view.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import com.uvg.edu.gt.uvghorasbeca.ui.theme.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight

@Composable
fun CustomCard(
    title: String,
    location: String,
    date: String,
    timeRange: String?,
    totalHours: String?,
    backgroundColor: Color,
    progress: Float = -1f, // Barra de progreso opcional (0.0 - 1.0). Si es -1f, no se muestra
    contentIcon: @Composable (() -> Unit)? = null, // Icono o cualquier contenido adicional
    extraContent: @Composable (() -> Unit)? = null // Para agregar o quitar contenido flexible
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { /* Acción del clic */ }
            .shadow(4.dp, RoundedCornerShape(8.dp)),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Título y contenido superior (íconos o rating)
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                // Si hay contenido adicional (íconos o rating), mostrarlo
                contentIcon?.let {
                    it()
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
            Divider(color = Color.Gray, thickness = 1.dp)
            Spacer(modifier = Modifier.height(8.dp))

            // Mostrar barra de progreso si es relevante
            if (progress >= 0) {
                LinearProgressIndicator(
                    progress = progress,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(6.dp)
                        .clip(RoundedCornerShape(4.dp)),
                    color = if (progress == 1f) Color.Red else Color.Green
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            // Información principal (ubicación, fecha, hora)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = location,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )

                Column(
                    horizontalAlignment = Alignment.End,
                    modifier = Modifier.align(Alignment.CenterVertically)
                ) {
                    Text(text = date, fontSize = 14.sp)
                    timeRange?.let {
                        Text(text = it, fontSize = 14.sp)
                    }
                    totalHours?.let {
                        Text(text = "$it h", fontSize = 16.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Contenido flexible adicional
            extraContent?.let {
                it()
            }
        }
    }
}
