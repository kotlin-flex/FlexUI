package cn.vividcode.multiplatform.flex.ui.config.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import cn.vividcode.multiplatform.flex.ui.config.type.FlexColorType
import cn.vividcode.multiplatform.flex.ui.theme.LocalDarkTheme

class FlexThemeColorSchemeConfig internal constructor() {
	
	private val lightScheme = FlexExpendedColorScheme.lightScheme
	
	private val darkScheme = FlexExpendedColorScheme.darkScheme
	
	/**
	 * 浅色主题
	 */
	fun lightScheme(config: FlexExpendedColorScheme.() -> Unit) {
		this.lightScheme.apply(config)
	}
	
	/**
	 * 深色主题
	 */
	fun darkScheme(config: FlexExpendedColorScheme.() -> Unit) {
		this.darkScheme.apply(config)
	}
	
	val current: FlexExpendedColorScheme
		@Composable
		get() = if (LocalDarkTheme.current) this.darkScheme else this.lightScheme
}

class FlexExpendedColorScheme internal constructor(
	var success: Color,
	var warning: Color,
	var customs: Map<FlexColorType, Color> = emptyMap(),
) {
	
	companion object Defaults {
		
		val lightScheme: FlexExpendedColorScheme
			get() = FlexExpendedColorScheme(
				success = Color(0xFF52C41A),
				warning = Color(0xFFF9AC13),
				customs = emptyMap()
			)
		
		val darkScheme: FlexExpendedColorScheme
			get() = FlexExpendedColorScheme(
				success = Color(0xff7cd351),
				warning = Color(0xffffc34e),
				customs = emptyMap()
			)
	}
	
	/**
	 * 获取颜色
	 */
	@Composable
	fun getColor(colorType: FlexColorType) = when (colorType) {
		FlexColorType.Primary -> MaterialTheme.colorScheme.primary
		FlexColorType.Secondary -> MaterialTheme.colorScheme.secondary
		FlexColorType.Tertiary -> MaterialTheme.colorScheme.tertiary
		FlexColorType.OnSurface -> MaterialTheme.colorScheme.onSurface
		FlexColorType.Success -> success
		FlexColorType.Warning -> warning
		FlexColorType.Error -> MaterialTheme.colorScheme.error
		else -> customs[colorType] ?: MaterialTheme.colorScheme.error
	}
}