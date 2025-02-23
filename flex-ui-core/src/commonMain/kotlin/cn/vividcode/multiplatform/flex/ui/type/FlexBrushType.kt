package cn.vividcode.multiplatform.flex.ui.type

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color
import cn.vividcode.multiplatform.flex.ui.config.FlexComposeDefaultConfig
import cn.vividcode.multiplatform.flex.ui.config.LocalFlexConfig
import cn.vividcode.multiplatform.flex.ui.graphics.FlexBrush
import cn.vividcode.multiplatform.flex.ui.theme.LocalDarkTheme
import cn.vividcode.multiplatform.flex.ui.utils.*

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
	
	data object Primary : FlexBrushType {
		
		override val onBrush: FlexBrush
			@Composable
			get() = FlexBrush.solidColor(MaterialTheme.colorScheme.onPrimary)
		
		override val brush: FlexBrush
			@Composable
			get() = FlexBrush.solidColor(MaterialTheme.colorScheme.primary)
	}
	
	data object Secondary : FlexBrushType {
		
		override val onBrush: FlexBrush
			@Composable
			get() = FlexBrush.solidColor(MaterialTheme.colorScheme.onSecondary)
		
		override val brush: FlexBrush
			@Composable
			get() = FlexBrush.solidColor(MaterialTheme.colorScheme.secondary)
	}
	
	data object Tertiary : FlexBrushType {
		
		override val onBrush: FlexBrush
			@Composable
			get() = FlexBrush.solidColor(MaterialTheme.colorScheme.onTertiary)
		
		override val brush: FlexBrush
			@Composable
			get() = FlexBrush.solidColor(MaterialTheme.colorScheme.tertiary)
	}
	
	data object Error : FlexBrushType {
		
		override val onBrush: FlexBrush
			@Composable
			get() = FlexBrush.solidColor(MaterialTheme.colorScheme.onError)
		
		override val brush: FlexBrush
			@Composable
			get() = FlexBrush.solidColor(MaterialTheme.colorScheme.error)
	}
	
	data object InverseSurface : FlexBrushType {
		
		override val onBrush: FlexBrush
			@Composable
			get() = FlexBrush.solidColor(MaterialTheme.colorScheme.inverseOnSurface)
		
		override val brush: FlexBrush
			@Composable
			get() = FlexBrush.solidColor(MaterialTheme.colorScheme.inverseSurface)
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
	}
	
	data object RadialGradient : FlexBrushType {
		
		private val lightBrush by lazy { FlexBrush.radialGradient(listOf(Color(0xFF086CD9), Color(0xFF1D39C4))) }
		private val lightOnBrush by lazy { FlexBrush.solidColor(Color.White) }
		private val darkBrush by lazy { FlexBrush.radialGradient(listOf(Color(0xFF40A9FF), Color(0xFF587DF7))) }
		private val darkOnBrush by lazy { FlexBrush.radialGradient(listOf(Color(0xFF002765), Color(0xFF030752))) }
		
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
	}
}

internal val LocalFlexBrushType = compositionLocalOf<FlexBrushType?> { null }

@Composable
internal fun getDefaultBrushType(
	defaultBrushType: FlexBrushType = FlexBrushType.Primary,
	composeDefaultBrushType: FlexComposeDefaultConfig.() -> FlexBrushType?,
): FlexBrushType = LocalFlexBrushType.current
	?: LocalFlexConfig.current.default.let {
		it.composeDefaultBrushType()
			?: it.common?.brushType
			?: defaultBrushType
	}

internal val FlexBrushType.darkenBrush: FlexBrush
	@Composable
	get() = this.brush.darkenWithBrush

internal val FlexBrushType.lightenBrush: FlexBrush
	@Composable
	get() = this.brush.lightenWithBrush

internal val FlexBrushType.disabledBrush: FlexBrush
	@Composable
	get() = this.brush.disabledWithBrush

@Composable
internal fun FlexBrushType.darkenBrush(fraction: Float): FlexBrush =
	this.brush.darken(fraction)

@Composable
internal fun FlexBrushType.lightenBrush(fraction: Float): FlexBrush =
	this.brush.lighten(fraction)

internal val FlexBrushType.transparentBrush: FlexBrush
	@Composable
	get() = this.brush.copy(alpha = 0f)

internal val FlexBrushType.darkenOnBrush: FlexBrush
	@Composable
	get() = this.onBrush.darkenWithOnBrush

internal val FlexBrushType.lightenOnBrush: FlexBrush
	@Composable
	get() = this.onBrush.lightenWithOnBrush

internal val FlexBrushType.disabledOnBrush: FlexBrush
	@Composable
	get() = this.onBrush.disabledWithOnBrush

@Composable
internal fun FlexBrushType.darkenOnBrush(fraction: Float): FlexBrush =
	this.onBrush.darken(fraction)

@Composable
internal fun FlexBrushType.lightenOnBrush(fraction: Float): FlexBrush =
	this.onBrush.lighten(fraction)

internal val FlexBrushType.transparentOnBrush: FlexBrush
	@Composable
	get() = this.onBrush.copy(alpha = 0f)