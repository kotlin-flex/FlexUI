package cn.vividcode.multiplatform.flex.ui.utils

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.updateTransition
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.util.fastMapIndexed
import cn.vividcode.multiplatform.flex.ui.graphics.FlexBrush
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
		derivedStateOf { targetValue.replace(colors) }
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