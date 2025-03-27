package cn.vividcode.multiplatform.flex.ui.utils

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.updateTransition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.util.fastMap
import androidx.compose.ui.util.fastMapIndexed
import cn.vividcode.multiplatform.flex.ui.graphics.FlexBrush
import cn.vividcode.multiplatform.flex.ui.theme.LocalDarkTheme
import cn.vividcode.multiplatform.flex.ui.type.FlexBrushType
import kotlin.math.min

/**
 * 支持 FlexBrush 的渐变效果
 */
@Composable
internal fun animateFlexBrushAsState(
	targetValue: FlexBrush,
	transitionSpec: FiniteAnimationSpec<Color> = flexBrushDefaultSpring,
	label: String = "FlexBrushAnimation",
	finishedListener: ((FlexBrush) -> Unit)? = null,
): State<FlexBrush> {
	val transition = updateTransition(
		targetState = targetValue.colors,
		label = label,
	)
	val colors = targetValue.colors.fastMapIndexed { index, _ ->
		transition.animateColor(
			transitionSpec = { transitionSpec },
			label = "$label-$index",
			targetValueByState = { it[min(index, it.lastIndex)] }
		).value
	}
	val flexBrush by remember(targetValue, colors) {
		derivedStateOf { targetValue.replace(colors, false) }
	}
	if (finishedListener != null) {
		LaunchedEffect(flexBrush) {
			if (flexBrush == targetValue) {
				finishedListener(flexBrush)
			}
		}
	}
	return derivedStateOf { flexBrush }
}

private val flexBrushDefaultSpring = spring<Color>()

@Stable
internal fun Color.toSolidColor(): FlexBrush {
	return FlexBrush.solidColor(this)
}

@Stable
internal fun List<Color>.toLinearGradient(): FlexBrush {
	return FlexBrush.linearGradient(this)
}

@Stable
internal fun List<Color>.toRadialGradient(): FlexBrush {
	return FlexBrush.radialGradient(this)
}

@Stable
internal fun List<Color>.toSweepGradient(): FlexBrush {
	return FlexBrush.sweepGradient(this)
}

@Stable
internal fun FlexBrush.darken(fraction: Float): FlexBrush {
	val colors = this.colors.fastMap { it.darken(fraction) }
	return this.replace(colors)
}

@Stable
internal fun FlexBrush.lighten(fraction: Float): FlexBrush {
	val colors = this.colors.fastMap { it.lighten(fraction) }
	return this.replace(colors)
}

internal enum class FractionWeight(
	val weight: Float
) {
	
	Lowest(0.5f),
	
	Low(0.75f),
	
	Medium(1f),
	
	High(1.5f),
	
	Highest(2f)
}

internal enum class BrushType(
	val lightFraction: Float,
	val darkFraction: Float,
	val disabledAlpha: Float,
	val getFlexBrush: @Composable (flexBrushType: FlexBrushType) -> FlexBrush
) {
	
	OnBrush(
		lightFraction = 0.05f,
		darkFraction = 0.15f,
		disabledAlpha = 0.8f,
		getFlexBrush = { it.onBrush }
	),
	
	Brush(
		lightFraction = 0.06f,
		darkFraction = 0.1f,
		disabledAlpha = 0.6f,
		getFlexBrush = { it.brush }
	),
	
	OnBrushContainer(
		lightFraction = 0.05f,
		darkFraction = 0.15f,
		disabledAlpha = 0.8f,
		getFlexBrush = { it.onBrushContainer }
	),
	
	BrushContainer(
		lightFraction = 0.06f,
		darkFraction = 0.1f,
		disabledAlpha = 0.6f,
		getFlexBrush = { it.brushContainer }
	);
}

@Composable
internal fun FlexBrush.darken(
	brushType: BrushType,
	fractionWeight: FractionWeight = FractionWeight.Medium
): FlexBrush {
	var fraction = if (LocalDarkTheme.current) brushType.darkFraction else brushType.lightFraction
	fraction *= fractionWeight.weight
	val colors = this.colors.fastMap { it.darken(fraction) }
	return this.replace(colors)
}

@Composable
internal fun FlexBrush.lighten(
	brushType: BrushType,
	fractionWeight: FractionWeight = FractionWeight.Medium
): FlexBrush {
	var fraction = if (LocalDarkTheme.current) brushType.darkFraction else brushType.lightFraction
	fraction *= fractionWeight.weight
	val colors = this.colors.fastMap { it.lighten(fraction) }
	return this.replace(colors)
}

internal fun FlexBrush.disabled(
	brushType: BrushType,
	fractionWeight: FractionWeight = FractionWeight.Medium
): FlexBrush {
	val alpha = brushType.disabledAlpha * fractionWeight.weight
	return this.copy(alpha)
}