package cn.vividcode.multiplatform.flex.ui.type

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color
import cn.vividcode.multiplatform.flex.ui.config.FlexComposeDefaultConfig
import cn.vividcode.multiplatform.flex.ui.config.LocalFlexConfig
import cn.vividcode.multiplatform.flex.ui.graphics.FlexBrush
import cn.vividcode.multiplatform.flex.ui.theme.LocalDarkTheme
import cn.vividcode.multiplatform.flex.ui.utils.BrushType
import cn.vividcode.multiplatform.flex.ui.utils.FractionWeight
import cn.vividcode.multiplatform.flex.ui.utils.darken
import cn.vividcode.multiplatform.flex.ui.utils.disabled
import cn.vividcode.multiplatform.flex.ui.utils.lighten
import cn.vividcode.multiplatform.flex.ui.utils.toSolidColor

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
	
	val onBrushContainer: FlexBrush
		@Composable
		get() = onBrush
	
	val brushContainer: FlexBrush
		@Composable
		get() = brush
	
	data object Primary : FlexBrushType {
		
		override val onBrush: FlexBrush
			@Composable
			get() = MaterialTheme.colorScheme.onPrimary.toSolidColor()
		
		override val brush: FlexBrush
			@Composable
			get() = MaterialTheme.colorScheme.primary.toSolidColor()
		
		override val onBrushContainer: FlexBrush
			@Composable
			get() = MaterialTheme.colorScheme.onPrimaryContainer.toSolidColor()
		
		override val brushContainer: FlexBrush
			@Composable
			get() = MaterialTheme.colorScheme.primaryContainer.toSolidColor()
	}
	
	data object Secondary : FlexBrushType {
		
		override val onBrush: FlexBrush
			@Composable
			get() = MaterialTheme.colorScheme.onSecondary.toSolidColor()
		
		override val brush: FlexBrush
			@Composable
			get() = MaterialTheme.colorScheme.secondary.toSolidColor()
		
		override val onBrushContainer: FlexBrush
			@Composable
			get() = MaterialTheme.colorScheme.onSecondaryContainer.toSolidColor()
		
		override val brushContainer: FlexBrush
			@Composable
			get() = MaterialTheme.colorScheme.secondaryContainer.toSolidColor()
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

@Composable
internal fun FlexBrushType.darken(
	brushType: BrushType,
	fractionWeight: FractionWeight = FractionWeight.Medium
): FlexBrush {
	val flexBrush = brushType.getFlexBrush(this)
	return flexBrush.darken(brushType, fractionWeight)
}

@Composable
internal fun FlexBrushType.lighten(
	brushType: BrushType,
	fractionWeight: FractionWeight = FractionWeight.Medium
): FlexBrush {
	val flexBrush = brushType.getFlexBrush(this)
	return flexBrush.lighten(brushType, fractionWeight)
}

@Composable
internal fun FlexBrushType.disabled(
	brushType: BrushType,
	fractionWeight: FractionWeight = FractionWeight.Medium
): FlexBrush {
	val flexBrush = brushType.getFlexBrush(this)
	return flexBrush.disabled(brushType, fractionWeight)
}

@Composable
internal fun FlexBrushType.transparent(
	brushType: BrushType
): FlexBrush {
	val flexBrush = brushType.getFlexBrush(this)
	return flexBrush.copy(alpha = 0f)
}