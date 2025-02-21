package cn.vividcode.multiplatform.flex.ui.config.type

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.util.fastMap
import cn.vividcode.multiplatform.flex.ui.config.LocalFlexConfig
import cn.vividcode.multiplatform.flex.ui.graphics.FlexBrush
import cn.vividcode.multiplatform.flex.ui.theme.LocalDarkTheme

/**
 * 颜色类型
 */
interface FlexColorType {
	
	val onColor: Color
		@Composable
		get
	
	val brush: FlexBrush
		@Composable
		get() = FlexBrush.solidColor(color)
	
	val color: Color
		@Composable
		get
	
	data object Primary : FlexColorType {
		
		override val onColor: Color
			@Composable
			get() = MaterialTheme.colorScheme.onPrimary
		
		override val color: Color
			@Composable
			get() = MaterialTheme.colorScheme.primary
	}
	
	data object Secondary : FlexColorType {
		
		override val onColor: Color
			@Composable
			get() = MaterialTheme.colorScheme.onSecondary
		
		override val color: Color
			@Composable
			get() = MaterialTheme.colorScheme.secondary
	}
	
	data object Tertiary : FlexColorType {
		
		override val onColor: Color
			@Composable
			get() = MaterialTheme.colorScheme.onTertiary
		
		override val color: Color
			@Composable
			get() = MaterialTheme.colorScheme.tertiary
	}
	
	data object Error : FlexColorType {
		
		override val onColor: Color
			@Composable
			get() = MaterialTheme.colorScheme.onError
		
		override val color: Color
			@Composable
			get() = MaterialTheme.colorScheme.error
	}
	
	data object InverseSurface : FlexColorType {
		
		override val onColor: Color
			@Composable
			get() = MaterialTheme.colorScheme.inverseOnSurface
		
		override val color: Color
			@Composable
			get() = MaterialTheme.colorScheme.inverseSurface
	}
	
	data object LinearGradient : FlexColorType {
		
		private val lightBrush = FlexBrush.linearGradient(listOf(Color(0xFF086CD9), Color(0xFF07969C)))
		private val darkBrush = FlexBrush.linearGradient(listOf(Color(0xFF40A9FF), Color(0xFF36CFC8)))
		
		override val onColor: Color
			@Composable
			get() = MaterialTheme.colorScheme.inverseOnSurface
		
		override val color: Color
			@Composable
			get() = MaterialTheme.colorScheme.inverseSurface
		
		override val brush: FlexBrush
			@Composable
			get() = if (LocalDarkTheme.current) darkBrush else lightBrush
	}
	
	data object RadialGradient : FlexColorType {
		
		private val lightBrush = FlexBrush.sweepGradient(listOf(Color(0xFFD3380D), Color(0xFFD38706)))
		private val darkBrush = FlexBrush.sweepGradient(listOf(Color(0xFFFF7945), Color(0xFFFFC53C)))
		
		override val onColor: Color
			@Composable
			get() = MaterialTheme.colorScheme.inverseOnSurface
		
		override val color: Color
			@Composable
			get() = MaterialTheme.colorScheme.inverseSurface
		
		override val brush: FlexBrush
			@Composable
			get() = if (LocalDarkTheme.current) darkBrush else lightBrush
		
	}
	
	companion object {
		
		val entries by lazy {
			listOf(
				Primary,
				Secondary,
				Tertiary,
				Error,
				InverseSurface,
				LinearGradient,
				RadialGradient
			)
		}
	}
}

internal val LocalFlexColorType = compositionLocalOf<FlexColorType?> { null }

@Composable
internal fun getDefaultColorType(
	defaultColorType: FlexColorType = FlexColorType.Primary,
	composeDefaultColorType: FlexComposeDefaultConfig.() -> FlexColorType?,
): FlexColorType = LocalFlexColorType.current
	?: LocalFlexConfig.current.default.let {
		it.composeDefaultColorType()
			?: it.common?.colorType
			?: defaultColorType
	}

@Composable
internal fun FlexColorType.darkenOnColor(fraction: Float = 0.1f): Color {
	return lerp(this.onColor, Color.Black, fraction)
}

@Composable
internal fun FlexColorType.lightenOnColor(fraction: Float = 0.1f): Color {
	return lerp(this.onColor, Color.White, fraction)
}

@Composable
internal fun FlexColorType.disabledOnColor(alpha: Float = 0.8f): Color {
	return this.onColor.copy(alpha = alpha)
}

@Composable
internal fun FlexColorType.darkenBrush(fraction: Float = 0.1f): FlexBrush {
	val colors = this.brush.colors.fastMap { lerp(it, Color.Black, fraction) }
	return this.brush.replace(colors)
}

@Composable
internal fun FlexColorType.lightenBrush(fraction: Float = 0.1f): FlexBrush {
	val colors = this.brush.colors.fastMap { lerp(it, Color.White, fraction) }
	return this.brush.replace(colors)
}

@Composable
internal fun FlexColorType.disabledBrush(alpha: Float = 0.6f): FlexBrush {
	val colors = this.brush.colors.fastMap { it.copy(alpha = alpha) }
	return this.brush.replace(colors)
}

internal val FlexColorType.transparentOnColor: Color
	@Composable
	get() = this.onColor.copy(alpha = 0f)

internal val FlexColorType.transparentBrush: FlexBrush
	@Composable
	get() = this.brush.copy(alpha = 0f)