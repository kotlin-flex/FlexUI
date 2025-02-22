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
interface FlexBrushType {
	
	val onBrush: FlexBrush
		@Composable
		get
	
	val brush: FlexBrush
		@Composable
		get
	
	@Deprecated("Please replace onBrush")
	val onColor: Color
		@Composable
		get
	
	@Deprecated("Please replace color")
	val color: Color
		@Composable
		get
	
	data object Primary : FlexBrushType {
		
		override val onBrush: FlexBrush
			@Composable
			get() = FlexBrush.solidColor(MaterialTheme.colorScheme.onPrimary)
		
		override val brush: FlexBrush
			@Composable
			get() = FlexBrush.solidColor(MaterialTheme.colorScheme.primary)
		
		override val onColor: Color
			@Composable
			get() = MaterialTheme.colorScheme.onPrimary
		
		override val color: Color
			@Composable
			get() = MaterialTheme.colorScheme.primary
	}
	
	data object Secondary : FlexBrushType {
		
		override val onBrush: FlexBrush
			@Composable
			get() = FlexBrush.solidColor(MaterialTheme.colorScheme.onSecondary)
		
		override val brush: FlexBrush
			@Composable
			get() = FlexBrush.solidColor(MaterialTheme.colorScheme.secondary)
		
		override val onColor: Color
			@Composable
			get() = MaterialTheme.colorScheme.onSecondary
		
		override val color: Color
			@Composable
			get() = MaterialTheme.colorScheme.secondary
	}
	
	data object Tertiary : FlexBrushType {
		
		override val onBrush: FlexBrush
			@Composable
			get() = FlexBrush.solidColor(MaterialTheme.colorScheme.onTertiary)
		
		override val brush: FlexBrush
			@Composable
			get() = FlexBrush.solidColor(MaterialTheme.colorScheme.tertiary)
		
		override val onColor: Color
			@Composable
			get() = MaterialTheme.colorScheme.onTertiary
		
		override val color: Color
			@Composable
			get() = MaterialTheme.colorScheme.tertiary
	}
	
	data object Error : FlexBrushType {
		
		override val onBrush: FlexBrush
			@Composable
			get() = FlexBrush.solidColor(MaterialTheme.colorScheme.onError)
		
		override val brush: FlexBrush
			@Composable
			get() = FlexBrush.solidColor(MaterialTheme.colorScheme.error)
		
		override val onColor: Color
			@Composable
			get() = MaterialTheme.colorScheme.onError
		
		override val color: Color
			@Composable
			get() = MaterialTheme.colorScheme.error
	}
	
	data object InverseSurface : FlexBrushType {
		
		override val onBrush: FlexBrush
			@Composable
			get() = FlexBrush.solidColor(MaterialTheme.colorScheme.inverseOnSurface)
		
		override val brush: FlexBrush
			@Composable
			get() = FlexBrush.solidColor(MaterialTheme.colorScheme.inverseSurface)
		
		override val onColor: Color
			@Composable
			get() = MaterialTheme.colorScheme.inverseOnSurface
		
		override val color: Color
			@Composable
			get() = MaterialTheme.colorScheme.inverseSurface
	}
	
	data object LinearGradient : FlexBrushType {
		
		private val lightBrush by lazy { FlexBrush.linearGradient(listOf(Color(0xFF086CD9), Color(0xFF07969C))) }
		private val lightOnBrush by lazy { FlexBrush.solidColor(Color.White) }
		private val darkBrush by lazy { FlexBrush.linearGradient(listOf(Color(0xFF40A9FF), Color(0xFF36CFC8))) }
		private val darkOnBrush by lazy { FlexBrush.linearGradient(listOf(Color(0xFF002765), Color(0xFF002229))) }
		
		override val brush: FlexBrush
			@Composable
			get() = if (LocalDarkTheme.current) darkBrush else lightBrush
		
		override val onBrush: FlexBrush
			@Composable
			get() = if (LocalDarkTheme.current) darkOnBrush else lightOnBrush
		
		override val onColor: Color
			@Composable
			get() = MaterialTheme.colorScheme.inverseOnSurface
		
		override val color: Color
			@Composable
			get() = MaterialTheme.colorScheme.inverseSurface
	}
	
	data object RadialGradient : FlexBrushType {
		
		private val lightBrush by lazy { FlexBrush.radialGradient(listOf(Color(0xFF086CD9), Color(0xFF1D39C4))) }
		private val lightOnBrush by lazy { FlexBrush.solidColor(Color.White) }
		private val darkBrush by lazy { FlexBrush.radialGradient(listOf(Color(0xFF40A9FF), Color(0xFF587DF7))) }
		private val darkOnBrush by lazy { FlexBrush.radialGradient(listOf(Color(0xFF002765), Color(0xFF030752))) }
		
		override val onColor: Color
			@Composable
			get() = MaterialTheme.colorScheme.inverseOnSurface
		
		override val color: Color
			@Composable
			get() = MaterialTheme.colorScheme.inverseSurface
		
		override val brush: FlexBrush
			@Composable
			get() = if (LocalDarkTheme.current) darkBrush else lightBrush
		
		override val onBrush: FlexBrush
			@Composable
			get() = if (LocalDarkTheme.current) darkOnBrush else lightOnBrush
	}
	
	data object SweepGradient : FlexBrushType {
		
		private val lightBrush by lazy { FlexBrush.sweepGradient(listOf(Color(0xFFD3380D), Color(0xFFD38706))) }
		private val lightOnBrush by lazy { FlexBrush.solidColor(Color.White) }
		private val darkBrush by lazy { FlexBrush.sweepGradient(listOf(Color(0xFFFF7945), Color(0xFFFFC53C))) }
		private val darkOnBrush by lazy { FlexBrush.sweepGradient(listOf(Color(0xFF600A00), Color(0xFF603400))) }
		
		override val brush: FlexBrush
			@Composable
			get() = if (LocalDarkTheme.current) darkBrush else lightBrush
		
		override val onBrush: FlexBrush
			@Composable
			get() = if (LocalDarkTheme.current) darkOnBrush else lightOnBrush
		
		override val onColor: Color
			@Composable
			get() = MaterialTheme.colorScheme.inverseOnSurface
		
		override val color: Color
			@Composable
			get() = MaterialTheme.colorScheme.inverseSurface
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
				RadialGradient,
				SweepGradient
			)
		}
	}
}

internal val LocalFlexBrushType = compositionLocalOf<FlexBrushType?> { null }

@Composable
internal fun getDefaultbrushType(
	defaultbrushType: FlexBrushType = FlexBrushType.Primary,
	composeDefaultbrushType: FlexComposeDefaultConfig.() -> FlexBrushType?,
): FlexBrushType = LocalFlexBrushType.current
	?: LocalFlexConfig.current.default.let {
		it.composeDefaultbrushType()
			?: it.common?.brushType
			?: defaultbrushType
	}

@Composable
internal fun FlexBrushType.darkenBrush(
	fraction: Float = if (LocalDarkTheme.current) 0.06f else 0.1f,
): FlexBrush {
	val colors = this.brush.colors.fastMap { lerp(it, Color.Black, fraction) }
	return this.brush.replace(colors)
}

@Composable
internal fun FlexBrushType.lightenBrush(
	fraction: Float = if (LocalDarkTheme.current) 0.06f else 0.1f,
): FlexBrush {
	val colors = this.brush.colors.fastMap { lerp(it, Color.White, fraction) }
	return this.brush.replace(colors)
}

@Composable
internal fun FlexBrushType.disabledBrush(
	alpha: Float = 0.6f,
): FlexBrush {
	val colors = this.brush.colors.fastMap { it.copy(alpha = alpha) }
	return this.brush.replace(colors)
}

@Composable
internal fun FlexBrushType.darkenOnBrush(
	fraction: Float = if (LocalDarkTheme.current) 0.05f else 0.15f,
): FlexBrush {
	val colors = this.brush.colors.fastMap { lerp(it, Color.Black, fraction) }
	return this.brush.replace(colors)
}

@Composable
internal fun FlexBrushType.lightenOnBrush(
	fraction: Float = if (LocalDarkTheme.current) 0.05f else 0.15f,
): FlexBrush {
	val colors = this.brush.colors.fastMap { lerp(it, Color.White, fraction) }
	return this.brush.replace(colors)
}

@Composable
internal fun FlexBrushType.disabledOnBrush(
	alpha: Float = 0.8f,
): FlexBrush {
	val colors = this.brush.colors.fastMap { it.copy(alpha = alpha) }
	return this.brush.replace(colors)
}

internal val FlexBrushType.TransparentBrush: FlexBrush
	@Composable
	get() = this.brush.copy(alpha = 0f)

internal val FlexBrushType.OnTransparentBrush: FlexBrush
	@Composable
	get() = this.onBrush.copy(alpha = 0f)