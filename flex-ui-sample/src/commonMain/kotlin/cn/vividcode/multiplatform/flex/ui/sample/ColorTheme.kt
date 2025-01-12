package cn.vividcode.multiplatform.flex.ui.sample

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun getColorScheme(): ColorScheme = if (isSystemInDarkTheme()) {
	darkColorScheme(
		background = Color(0xFF121212)
	)
} else {
	lightColorScheme(
		background = Color.White
	)
}