package com.lowjunee.healthsafe.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// Define your custom colors
val PrimaryColor = Color(0xFFC6494B) // #C6494B
val WhiteColor = Color(0xFFFFFFFF)   // White
private val BlackColor = Color(0xFF000000)   // Black

// Define Light and Dark Color Schemes
private val LightColorScheme = lightColorScheme(
    primary = PrimaryColor,         // Used for primary elements like AppBar, Buttons, etc.
    onPrimary = WhiteColor,         // Text and icons on primary elements
    background = WhiteColor,        // Main background color
    onBackground = BlackColor,      // Text on main background
    surface = PrimaryColor,         // Surfaces like cards, dialogs, etc.
    onSurface = WhiteColor          // Text on surfaces
    // You can customize other colors if needed
)

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryColor,         // Maintains primary color in dark theme
    onPrimary = WhiteColor,         // Text and icons on primary elements
    background = PrimaryColor,      // Main background color in dark theme
    onBackground = WhiteColor,      // Text on main background in dark theme
    surface = WhiteColor,           // Surfaces like cards, dialogs, etc.
    onSurface = BlackColor          // Text on surfaces in dark theme
    // You can customize other colors if needed
)

@Composable
fun HealthSafeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false, // Disabled to use our custom color scheme
    content: @Composable () -> Unit
) {
    // Choose the appropriate color scheme based on the theme and dynamicColor flag
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    // Obtain the current view
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            // Update the status bar color to match the primary color
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            // Adjust the status bar icons to be light or dark based on the theme
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    // Apply the MaterialTheme with the defined color scheme and default typography
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography, // Uses default Typography; can be customized if needed
        content = content
    )
}
