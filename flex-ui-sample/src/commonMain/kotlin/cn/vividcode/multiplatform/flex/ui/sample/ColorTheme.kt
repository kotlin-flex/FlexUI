package cn.vividcode.multiplatform.flex.ui.sample

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color

@Composable
fun getColorScheme(): ColorScheme {
	val darkTheme = InDarkTheme ?: isSystemInDarkTheme().also { InDarkTheme = it }
	return when (darkTheme) {
		true -> darkColorScheme(
			background = Color(0xFF121212)
		)
		
		false -> lightColorScheme(
			background = Color.White
		)
	}
}

var InDarkTheme by mutableStateOf<Boolean?>(null)

val LocalSystemInDarkTheme = staticCompositionLocalOf { false }