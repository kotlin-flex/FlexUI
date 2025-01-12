package cn.vividcode.multiplatform.flex.ui.config.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

class FlexThemeColorSchemeConfig internal constructor() {
	
	internal val lightScheme = FlexColorScheme.lightScheme
	
	internal val darkScheme = FlexColorScheme.darkScheme
	
	/**
	 * 浅色主题
	 */
	fun lightScheme(config: FlexColorScheme.() -> Unit) {
		this.lightScheme.apply(config)
	}
	
	/**
	 * 深色主题
	 */
	fun darkScheme(config: FlexColorScheme.() -> Unit) {
		this.darkScheme.apply(config)
	}
	
	val current: FlexColorScheme
		@Composable
		get() = if (isSystemInDarkTheme()) this.darkScheme else this.lightScheme
}

class FlexColorScheme internal constructor(
	var default: Color,
	var primary: Color,
	var secondary: Color,
	var success: Color,
	var warning: Color,
	var error: Color,
	var customs: Map<FlexColorType, Color> = emptyMap(),
) {
	
	companion object Defaults {
		
		val lightScheme: FlexColorScheme
			get() = FlexColorScheme(
				default = Color(0xFFF5F5F5),
				primary = Color(0xFF2E53EB),
				secondary = Color(0xFF07969C),
				success = Color(0xFF52C41A),
				warning = Color(0xFFF9AC13),
				error = Color(0xfff1211c),
				customs = emptyMap()
			)
		
		val darkScheme: FlexColorScheme
			get() = FlexColorScheme(
				default = Color(0xFF434343),  // 更深的灰蓝色背景
				primary = Color(0xFF0050B3),  // 更深的蓝色
				secondary = Color(0xFF006C75), // 更深的青色
				success = Color(0xFF227804),   // 更深的绿色
				warning = Color(0xFFAC4E00),   // 更深的橙色
				error = Color(0xFFA8061A),     // 更深的红色
				customs = emptyMap()
			)
	}
	
	/**
	 * 获取颜色s
	 */
	fun getColor(type: FlexColorType) = when (type) {
		FlexColorType.Default -> default
		FlexColorType.Primary -> primary
		FlexColorType.Secondary -> secondary
		FlexColorType.Success -> success
		FlexColorType.Warning -> warning
		FlexColorType.Error -> error
		else -> customs[type] ?: default
	}
}