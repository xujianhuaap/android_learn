package com.skullmind.io.theme

import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import com.skullmind.io.theme.AppTheme.localColorFilter

@Composable
fun AppTheme(theme: Theme, content: @Composable () -> Unit) {
    CompositionLocalProvider(
        localColorFilter provides remember {
            getColorFilter(theme = theme)
        }
    ) {
        MaterialTheme(colors = getColors(theme = theme), content = content)
    }


}

object AppTheme {

    val colorFilter: ColorFilter?
        @Composable
        @ReadOnlyComposable
        get() = localColorFilter.current

    internal val localColorFilter = staticCompositionLocalOf {
        ColorMatrix().run {
            ColorFilter.colorMatrix(this)
        }

    }

}

private fun getColorFilter(theme: Theme): ColorFilter {


    return ColorFilter.colorMatrix(ColorMatrix().apply {
        if (theme == Theme.Sad) setToSaturation(0f)
    })
}

private fun getColors(theme: Theme) = when (theme) {
    Theme.Happy -> happyColors()
    Theme.Sad -> sadColors()
    Theme.Normal -> darkColors()
}

enum class Theme {
    Happy,
    Sad,
    Normal
}

private fun sadColors(
    primary: Color = Color(0xFF251E1E),
    primaryVariant: Color = Color(0xFF5A5655),
    secondary: Color = Color(0xFF6F6568),
    secondaryVariant: Color = Color(0xFFA89A9E),
    background: Color = Color(0xFF9C9999),
    surface: Color = Color.White,
    error: Color = Color(0xFF160C0E),
    onPrimary: Color = Color.White,
    onSecondary: Color = Color.Black,
    onBackground: Color = Color.Black,
    onSurface: Color = Color.Black,
    onError: Color = Color.White
): Colors = Colors(
    primary,
    primaryVariant,
    secondary,
    secondaryVariant,
    background,
    surface,
    error,
    onPrimary,
    onSecondary,
    onBackground,
    onSurface,
    onError,
    true
)

private fun happyColors(
    primary: Color = Color(0xFFF44336),
    primaryVariant: Color = Color(0xFFB96660),
    secondary: Color = Color(0xFFAD486B),
    secondaryVariant: Color = Color(0xFFC591A3),
    background: Color = Color(0xFFB1ABAD),
    surface: Color = Color.White,
    error: Color = Color(0xFFB00020),
    onPrimary: Color = Color(0xFF3F51B5),
    onSecondary: Color = Color.Black,
    onBackground: Color = Color.Black,
    onSurface: Color = Color.Black,
    onError: Color = Color.White
): Colors = Colors(
    primary,
    primaryVariant,
    secondary,
    secondaryVariant,
    background,
    surface,
    error,
    onPrimary,
    onSecondary,
    onBackground,
    onSurface,
    onError,
    true
)

private fun darkColors(
    primary: Color = Color(0xFF226CC9),
    primaryVariant: Color = Color(0xFFB96660),
    secondary: Color = Color(0xFFAD486B),
    secondaryVariant: Color = Color(0xFFC591A3),
    background: Color = Color.White,
    surface: Color = Color.White,
    error: Color = Color(0xFFB00020),
    onPrimary: Color = Color.White,
    onSecondary: Color = Color.Black,
    onBackground: Color = Color.Black,
    onSurface: Color = Color.Black,
    onError: Color = Color.White
): Colors = Colors(
    primary,
    primaryVariant,
    secondary,
    secondaryVariant,
    background,
    surface,
    error,
    onPrimary,
    onSecondary,
    onBackground,
    onSurface,
    onError,
    true
)