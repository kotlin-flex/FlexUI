package cn.vividcode.multiplatform.flex.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.*
import cn.vividcode.multiplatform.flex.ui.config.FlexConfig
import cn.vividcode.multiplatform.flex.ui.config.LocalFlexConfig
import cn.vividcode.multiplatform.flex.ui.config.flexConfig

@Composable
fun FlexTheme(
	darkTheme: Boolean = isSystemInDarkTheme(),
	lightColorScheme: ColorScheme = lightColorScheme(),
	darkColorScheme: ColorScheme = darkColorScheme(),
	shapes: Shapes = MaterialTheme.shapes,
	typography: Typography = MaterialTheme.typography,
	flexConfig: FlexConfig = flexConfig(),
	content: @Composable () -> Unit,
) {
	val targetDarkTheme = FlexThemeState.darkTheme ?: darkTheme.also {
		FlexThemeState.darkTheme = it
	}
	CompositionLocalProvider(
		LocalDarkTheme provides targetDarkTheme,
		LocalFlexConfig provides flexConfig
	) {
		MaterialTheme(
			colorScheme = if (targetDarkTheme) darkColorScheme else lightColorScheme,
			shapes = shapes,
			typography = typography,
		) {
			content()
		}
	}
}

object FlexThemeState {
	
	var darkTheme by mutableStateOf<Boolean?>(null)
}