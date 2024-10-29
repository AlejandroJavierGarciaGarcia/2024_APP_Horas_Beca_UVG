package com.uvg.edu.gt.uvghorasbeca.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.example.app.ui.theme.CustomColors

val CustomShapes = Shapes(
    small = RoundedCornerShape(4.dp),
    medium = RoundedCornerShape(8.dp),
    large = RoundedCornerShape(16.dp)
)

val LightColorPalette = lightColorScheme(
    primary = CustomColors.PrimaryGreen,        // No cambia entre temas
    background = CustomColors.White,       // Fondo para tema claro
    surface = CustomColors.PrimaryGrayLight,    // Gris para componentes en tema claro
    onBackground = CustomColors.Black  // Texto en tema claro
)

val DarkColorPalette = darkColorScheme(
    primary = CustomColors.PrimaryGreen,        // No cambia entre temas
    background = CustomColors.Black,        // Fondo para tema oscuro
    surface = CustomColors.PrimaryGrayDark,     // Gris para componentes en tema oscuro
    onBackground = CustomColors.Black  // Texto en tema oscuro
)

@Composable
fun UVGHorasBecaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(), // Detecta el tema del sistema automáticamente
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        shapes = CustomShapes,
        content = content
    )
}