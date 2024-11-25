import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.app.ui.theme.CustomColors
import com.uvg.edu.gt.uvghorasbeca.R

@Composable
fun CustomCard(
    id: String, // Updated to String
    title: String,
    location: String,
    date: String,
    startTime: String?,
    endTime: String?,
    totalHoursCompleted: Float?,
    isRecurring: Boolean = false,
    recurrencePattern: String? = null,
    showSemaphore: Boolean = false,
    currentParticipants: Int = 0,
    maxParticipants: Int = 0,
    showStars: Boolean = false,
    rating: Int = 0,
    showRemainingInfo: Boolean = false,
    remainingHours: Long? = null,
    onClick: (String) -> Unit // Updated to pass String ID
) {
    val dynamicBackgroundColor = if (showRemainingInfo) {
        RemainingTimeColor(remainingHours = remainingHours)
    } else {
        CustomColors.PrimaryGrayLight
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick(id) } // Pass String ID to the callback
            .shadow(4.dp, RoundedCornerShape(8.dp)),
        colors = CardDefaults.cardColors(containerColor = dynamicBackgroundColor),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
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

                if (showSemaphore) {
                    SemaphoreIndicator(currentParticipants, maxParticipants)
                }

                if (showStars) {
                    RatingStars(rating)
                }

                if (showRemainingInfo) {
                    RemainingTimeInfo(remainingHours = remainingHours)
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
            Divider(color = CustomColors.SeparatorOpacity70, thickness = 1.dp)
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = location,
                        fontSize = 16.sp
                    )

                    if (isRecurring && recurrencePattern != null) {
                        Text(
                            text = "Recurrente: $recurrencePattern",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Blue
                        )
                    }
                }

                Column(
                    horizontalAlignment = Alignment.End
                ) {
                    Text(text = date, fontSize = 14.sp)
                    startTime?.let {
                        Text(text = "$startTime - $endTime", fontSize = 14.sp)
                    }
                    if (showStars) {
                        totalHoursCompleted?.let {
                            Text(
                                text = "Horas completadas: $it",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}


// Semáforo para mostrar el cupo de la actividad
@Composable
fun SemaphoreIndicator(currentParticipants: Int, maxParticipants: Int) {
    val fillRatio = currentParticipants.toFloat() / maxParticipants.toFloat()

    // Definir los colores según el porcentaje de cupo
    val colors = when {
        fillRatio <= 0.33f -> listOf(CustomColors.GreenLight, CustomColors.GrayLight, CustomColors.GrayLight) // Verde
        fillRatio <= 0.66f -> listOf(CustomColors.YellowLight, CustomColors.YellowLight, CustomColors.GrayLight)  // Amarillo
        else -> listOf(CustomColors.RedLight, CustomColors.RedLight, CustomColors.RedLight)  // Rojo
    }

    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = "$currentParticipants/$maxParticipants",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.width(6.dp))
        // Dibujar los tres rectángulos
        colors.forEach { color ->
            Box(
                modifier = Modifier
                    .size(width = 20.dp, height = 13.dp)  // Tamaño de los rectángulos
                    .clip(RoundedCornerShape(4.dp))  // Bordes redondeados
                    .background(color)
                    .padding(4.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))  // Espaciado entre los rectángulos
        }
    }
}


// Componente para mostrar las estrellas de rating
@Composable
fun RatingStars(rating: Int, modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End,
        modifier = modifier.fillMaxWidth()
    ) {
        // Lista de estrellas (llenas y vacías) ordenadas de derecha a izquierda
        for (i in 1..5) {
            val starColor = if (i <= rating) CustomColors.YellowForStars else CustomColors.GrayLight  // Amarillo o gris
            Icon(
                painterResource(id = R.drawable.grade_star_icon_2),
                contentDescription = null,
                tint = starColor,
                modifier = Modifier.size(25.dp)
            )
        }
    }
}

@Composable
fun RemainingTimeColor(remainingHours: Long?): Color {
    // Determinar el color de fondo según las horas restantes
    return when {
        remainingHours != null && remainingHours <= 6 -> CustomColors.RedCard  // Rojo claro
        remainingHours != null && remainingHours <= 24 -> CustomColors.YellowCard  // Amarillo claro
        remainingHours != null && remainingHours > 24 -> CustomColors.GreenCard  // Verde claro
        else -> CustomColors.PrimaryGrayLight // Color por defecto si no hay tiempo restante
    }
}

@Composable
fun RemainingTimeInfo(
    remainingHours: Long?,
    modifier: Modifier = Modifier
){
    // Calcular si el tiempo se debe mostrar en días o en horas
    val remainingTimeText = if (remainingHours != null && remainingHours > 24) {
        val days = remainingHours / 24
        "Quedan $days días"
    } else if (remainingHours != null) {
        "Quedan $remainingHours h"
    } else {
        ""
    }

    // Mostrar el texto de horas o días restantes
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End
    ) {
        if (remainingTimeText.isNotEmpty()) {
            Text(
                text = remainingTimeText,
                fontWeight = FontWeight.Bold,
                color = CustomColors.Black,
                fontSize = 14.sp
            )
        }
    }
}