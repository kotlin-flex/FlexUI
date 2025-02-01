package cn.vividcode.multiplatform.flex.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.runtime.*
import cn.vividcode.multiplatform.flex.ui.config.FlexConfig
import cn.vividcode.multiplatform.flex.ui.config.LocalFlexConfig
import cn.vividcode.multiplatform.flex.ui.config.flexConfig
import androidx.compose.material3.darkColorScheme as defaultDarkColorScheme
import androidx.compose.material3.lightColorScheme as defaultLightColorScheme

@Composable
fun FlexTheme(
	darkTheme: Boolean = isSystemInDarkTheme(),
	lightColorScheme: () -> ColorScheme = { defaultLightColorScheme() },
	darkColorScheme: () -> ColorScheme = { defaultDarkColorScheme() },
	shapes: Shapes = MaterialTheme.shapes,
	typography: Typography = MaterialTheme.typography,
	flexConfig: FlexConfig = flexConfig(),
	content: @Composable () -> Unit,
) {
	LaunchedEffect(darkTheme) {
		FlexThemeState.darkTheme = darkTheme
	}
	CompositionLocalProvider(
		LocalDarkTheme provides FlexThemeState.darkTheme,
		LocalFlexConfig provides flexConfig
	) {
		MaterialTheme(
			colorScheme = if (LocalDarkTheme.current) darkColorScheme() else lightColorScheme(),
			shapes = shapes,
			typography = typography,
			content = content
		)
	}
}

object FlexThemeState {
	
	var darkTheme by mutableStateOf(false)
}