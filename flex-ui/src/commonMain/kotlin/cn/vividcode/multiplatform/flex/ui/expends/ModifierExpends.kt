package cn.vividcode.multiplatform.flex.ui.expends

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * 绘制虚线
 */
internal fun Modifier.dashedBorder(
	width: Dp,
	color: Color,
	dashLength: Dp = 4.dp,
	gapLength: Dp = 3.dp,
	shape: Shape = RectangleShape,
): Modifier = this.drawBehind {
	val strokeWidthPx = width.toPx()
	val dashPx = dashLength.toPx()
	val gapPx = gapLength.toPx()
	
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
			color = color
		)
	}
}