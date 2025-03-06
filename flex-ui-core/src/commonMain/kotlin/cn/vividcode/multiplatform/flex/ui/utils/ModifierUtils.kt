package cn.vividcode.multiplatform.flex.ui.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import cn.vividcode.multiplatform.flex.ui.graphics.FlexBrush
import cn.vividcode.multiplatform.flex.ui.theme.CurrentPlatform
import cn.vividcode.multiplatform.flex.ui.theme.FlexPlatform

/**
 * 绘制虚线
 */
@Stable
internal fun Modifier.dashedBorder(
	width: Dp,
	brush: FlexBrush,
	shape: Shape = RectangleShape,
	dash: Dp = 4.dp,
	gap: Dp = 3.dp,
): Modifier = this.drawBehind {
	val strokeWidthPx = width.toPx()
	val dashPx = dash.toPx()
	val gapPx = gap.toPx()
	
	val pathEffect = PathEffect.dashPathEffect(
		intervals = floatArrayOf(dashPx, gapPx),
		phase = 0f
	)
	
	val inset = strokeWidthPx / 2
	val insetSize = size.copy(
		width = size.width - strokeWidthPx,
		height = size.height - strokeWidthPx
	)
	
	withTransform(
		transformBlock = {
			translate(left = inset, top = inset)
		}
	) {
		drawOutline(
			outline = shape.createOutline(insetSize, layoutDirection, this),
			style = Stroke(width = strokeWidthPx, pathEffect = pathEffect),
			brush = brush.original
		)
	}
}

@Stable
internal fun Modifier.border(
	width: Dp,
	brush: FlexBrush,
	shape: Shape = RectangleShape
) =
	this.border(
		width = width,
		brush = brush.original,
		shape = shape
	)

@Stable
internal fun Modifier.background(
	brush: FlexBrush,
	shape: Shape = RectangleShape,
	alpha: Float = 1f,
) = this.background(
	brush = brush.original,
	shape = shape,
	alpha = alpha
)

fun Modifier.multiplatform(
	vararg platforms: FlexPlatform,
	modifier: Modifier.() -> Modifier,
): Modifier = if (CurrentPlatform in platforms) this.modifier() else this