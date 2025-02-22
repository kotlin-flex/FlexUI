package cn.vividcode.multiplatform.flex.ui.foundation.slider

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import cn.vividcode.multiplatform.flex.ui.config.foundation.FlexSliderConfig
import cn.vividcode.multiplatform.flex.ui.type.FlexBrushType
import cn.vividcode.multiplatform.flex.ui.type.FlexCornerType
import kotlin.math.sqrt

/**
 * 拖动条值显示
 */
@Composable
internal fun FlexSliderTooltipPopup(
	tooltipText: String,
	brushType: FlexBrushType,
	cornerType: FlexCornerType,
	isHorizontal: Boolean,
	isThumbFocused: Boolean,
	thumbOffsetStart: Dp,
	thickness: Dp,
	config: FlexSliderConfig,
) {
	val density = LocalDensity.current
	var size by remember { mutableStateOf(IntSize.Zero) }
	val offset by remember(thumbOffsetStart, thickness, size, isHorizontal) {
		derivedStateOf {
			val thumbOffsetStartPx = with(density) { thumbOffsetStart.roundToPx() }
			val thicknessPx = with(density) { thickness.roundToPx() }
			if (isHorizontal) {
				val x = thumbOffsetStartPx - size.width / 2 + thicknessPx / 2
				val y = -size.height
				IntOffset(x, y)
			} else {
				val x = thicknessPx
				val y = (thumbOffsetStartPx - size.height / 2 + thicknessPx / 2).toInt()
				IntOffset(x, y)
			}
		}
	}
	Popup(
		alignment = Alignment.TopStart,
		offset = offset,
	) {
		val tooltipHeight by animateDpAsState(config.toolbarHeight)
		val arrowSize by remember {
			derivedStateOf { tooltipHeight / 2 * sqrt(2.0).toFloat() / 2 }
		}
		val tooltipCornerPercent by animateIntAsState((cornerType.scale * 50).toInt())
		val tooltipCornerShape by remember(tooltipCornerPercent) {
			derivedStateOf { RoundedCornerShape(tooltipCornerPercent) }
		}
		val arrowCornerPercent by animateIntAsState((cornerType.scale * 50).toInt())
		val arrowCornerShape by remember(arrowCornerPercent) {
			derivedStateOf { RoundedCornerShape(bottomEndPercent = arrowCornerPercent) }
		}
		val color by animateColorAsState(brushType.color)
		val contentColor by animateColorAsState(brushType.onColor)
		var targetAlpha by remember { mutableStateOf(0f) }
		var targetScale by remember { mutableStateOf(1f) }
		val alpha by animateFloatAsState(
			targetValue = targetAlpha,
			animationSpec = config.toolbarAnimationSpec,
			finishedListener = {
				if (it == 0f) {
					targetScale = 0f
				}
			}
		)
		val scale by animateFloatAsState(
			targetValue = targetScale,
			animationSpec = config.toolbarAnimationSpec
		)
		LaunchedEffect(isThumbFocused) {
			targetAlpha = if (isThumbFocused) 1f else 0f
			targetScale = if (isThumbFocused) 1f else 0.9f
		}
		if (isHorizontal) {
			Column(
				modifier = Modifier
					.alpha(alpha)
					.graphicsLayer {
						scaleX = scale
						scaleY = scale
						transformOrigin = TransformOrigin(0.5f, 1f)
					}
					.onGloballyPositioned {
						size = it.size
					},
				horizontalAlignment = Alignment.CenterHorizontally,
			) {
				TooltipText(
					tooltipText = tooltipText,
					config = config,
					color = color,
					contentColor = contentColor,
					tooltipCornerShape = tooltipCornerShape
				)
				Box(
					modifier = Modifier
						.offset(
							y = -arrowSize / 2
						)
						.rotate(45f)
						.size(arrowSize)
						.background(
							color = color,
							shape = arrowCornerShape
						)
				)
			}
		} else {
			Row(
				modifier = Modifier
					.alpha(alpha)
					.onGloballyPositioned {
						size = it.size
					},
				verticalAlignment = Alignment.CenterVertically,
			) {
				Box(
					modifier = Modifier
						.offset(
							x = arrowSize / 2
						)
						.rotate(135f)
						.size(arrowSize)
						.background(
							color = color,
							shape = arrowCornerShape
						)
				)
				TooltipText(
					tooltipText = tooltipText,
					config = config,
					color = color,
					contentColor = contentColor,
					tooltipCornerShape = tooltipCornerShape,
				)
			}
		}
	}
}

@Composable
private fun TooltipText(
	tooltipText: String,
	config: FlexSliderConfig,
	color: Color,
	contentColor: Color,
	tooltipCornerShape: RoundedCornerShape,
) {
	val tooltipHeight by animateDpAsState(config.toolbarHeight)
	val tooltipHorizontalPadding by animateDpAsState(config.toolbarHorizontalPadding)
	Box(
		modifier = Modifier
			.height(tooltipHeight)
			.clip(tooltipCornerShape)
			.background(
				color = color,
				shape = tooltipCornerShape
			)
			.padding(
				horizontal = tooltipHorizontalPadding
			),
		contentAlignment = Alignment.Center
	) {
		val fontSize by animateFloatAsState(config.toolbarFontSize.value)
		Text(
			text = tooltipText,
			color = contentColor,
			fontSize = fontSize.sp,
			fontWeight = config.toolbarFontWeight,
			lineHeight = fontSize.sp,
			letterSpacing = config.toolbarFontLetterSpacing
		)
	}
}