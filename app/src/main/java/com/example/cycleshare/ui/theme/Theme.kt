package com.example.cycleshare.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

val PrimaryColor = primary1neon  // Neon Cyan (bright, futuristic)
val SecondaryColor = Color(0xFF32CD32)  // Neon Green (bold contrast)
val TertiaryColor = Color(0xFF8B00FF)  // Electric Violet (for subtle accents)
val AccentColor = Color(0xFFFF1493)
private val DarkColorScheme = darkColorScheme(
    primary = PrimaryColor,
    secondary = SecondaryColor,
    tertiary = TertiaryColor,
    background = Color.Black,    // Dark background
    surface = Color(0xFF1E1E1E), // Dark surface color
    onPrimary = Color.Black,     // Text color on primary buttons
    onSecondary = Color.White,   // Text color on secondary buttons
    onTertiary = Color.White,    // Text color on tertiary
    onBackground = Color.White,  // General text color on dark background
    onSurface = Color.White      // Text color on surface
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF00BFFF),  // Softer Cyan for light mode
    secondary = Color(0xFF32CD32), // Neon Green
    tertiary = Color(0xFFDDA0DD),  // Soft Lavender (Tertiary)
    background = Color(0xFFFFFBFE), // Light background
    surface = Color.LightGray,     // Light surface color
    onPrimary = Color.White,       // Text color on primary buttons
    onSecondary = Color.White,     // Text color on secondary buttons
    onTertiary = Color.Black,      // Text color on tertiary elements
    onBackground = Color.Black,    // General text color on light background
    onSurface = Color.Black        // Text color on surface
    //primary = Purple40,
    //secondary = PurpleGrey40,
    //tertiary = Pink40

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun CycleShareTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}