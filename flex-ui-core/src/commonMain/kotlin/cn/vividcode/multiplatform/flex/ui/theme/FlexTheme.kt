package cn.vividcode.multiplatform.flex.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cn.vividcode.multiplatform.flex.ui.config.FlexConfig
import cn.vividcode.multiplatform.flex.ui.config.LocalFlexConfig
import cn.vividcode.multiplatform.flex.ui.config.rememberFlexConfig

@Composable
fun FlexTheme(
	darkTheme: Boolean = isSystemInDarkTheme(),
	colorSchemes: ColorSchemes = DefaultColorSchemes,
	shapes: Shapes = MaterialTheme.shapes,
	typography: Typography = MaterialTheme.typography,
	flexConfig: FlexConfig = rememberFlexConfig(),
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
			colorScheme = if (LocalDarkTheme.current) colorSchemes.darkScheme else colorSchemes.lightScheme,
			shapes = shapes,
			typography = typography,
			content = content
		)
	}
}

interface ColorSchemes {
	
	val lightScheme: ColorScheme
	
	val darkScheme: ColorScheme
}

object DefaultColorSchemes : ColorSchemes {
	
	override val lightScheme = lightColorScheme()
	
	override val darkScheme = darkColorScheme()
}

object FlexThemeState {
	
	var darkTheme by mutableStateOf(false)
}