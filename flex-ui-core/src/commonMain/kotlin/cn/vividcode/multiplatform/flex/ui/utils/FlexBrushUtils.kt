package cn.vividcode.multiplatform.flex.ui.utils

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.updateTransition
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.util.fastMap
import androidx.compose.ui.util.fastMapIndexed
import cn.vividcode.multiplatform.flex.ui.graphics.FlexBrush
import cn.vividcode.multiplatform.flex.ui.theme.LocalDarkTheme
import kotlin.math.min

/**
 * 支持 FlexBrush 的渐变效果
 */
@Composable
internal fun animateFlexBrushAsState(
	targetValue: FlexBrush,
	transitionSpec: @Composable FiniteAnimationSpec<Color> = flexBrushDefaultSpring,
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
	return derivedStateOf {
		flexBrush
	}
}

private val flexBrushDefaultSpring = spring<Color>()

internal fun Color.toSolidColor(): FlexBrush {
	return FlexBrush.solidColor(this)
}

internal fun List<Color>.toLinearGradient(): FlexBrush {
	return FlexBrush.linearGradient(this)
}

internal fun List<Color>.toRadialGradient(): FlexBrush {
	return FlexBrush.radialGradient(this)
}

internal fun List<Color>.toSweepGradient(): FlexBrush {
	return FlexBrush.sweepGradient(this)
}

internal fun FlexBrush.darken(fraction: Float): FlexBrush {
	val colors = this.colors.fastMap { it.darken(fraction) }
	return this.replace(colors)
}

internal fun FlexBrush.lighten(fraction: Float): FlexBrush {
	val colors = this.colors.fastMap { it.lighten(fraction) }
	return this.replace(colors)
}

internal val FlexBrush.darkenWithBrush: FlexBrush
	@Composable
	get() = this.darken(
		fraction = if (LocalDarkTheme.current) 0.06f else 0.1f
	)

internal val FlexBrush.lightenWithBrush: FlexBrush
	@Composable
	get() = this.lighten(
		fraction = if (LocalDarkTheme.current) 0.06f else 0.1f
	)

internal val FlexBrush.disabledWithBrush: FlexBrush
	get() = this.copy(alpha = 0.6f)

internal val FlexBrush.darkenWithOnBrush: FlexBrush
	@Composable
	get() = this.darken(
		fraction = if (LocalDarkTheme.current) 0.05f else 0.15f
	)

internal val FlexBrush.lightenWithOnBrush: FlexBrush
	@Composable
	get() = this.lighten(
		fraction = if (LocalDarkTheme.current) 0.05f else 0.15f
	)

internal val FlexBrush.disabledWithOnBrush: FlexBrush
	get() = this.copy(alpha = 0.8f)