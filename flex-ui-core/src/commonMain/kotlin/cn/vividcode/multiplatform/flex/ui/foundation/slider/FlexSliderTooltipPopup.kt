package cn.vividcode.multiplatform.flex.ui.foundation.slider

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import cn.vividcode.multiplatform.flex.ui.config.foundation.FlexSliderConfig
import cn.vividcode.multiplatform.flex.ui.config.type.FlexColorType
import cn.vividcode.multiplatform.flex.ui.config.type.FlexCornerType
import kotlin.math.min
import kotlin.math.sqrt

/**
 * 拖动条值显示
 */
@Composable
internal fun FlexSliderTooltipPopup(
	tooltipText: String,
	colorType: FlexColorType,
	cornerType: FlexCornerType,
	isHorizontal: Boolean,
	isThumbFocused: Boolean,
	thumbOffsetStart: Dp,
	thickness: Dp,
	config: FlexSliderConfig,
	tooltipPosition: FlexSliderTooltipPosition,
) {
	val density = LocalDensity.current
	var size by remember { mutableStateOf(IntSize.Zero) }
	val isTopSide by remember(tooltipPosition) {
		derivedStateOf { tooltipPosition == FlexSliderTooltipPosition.TopSide }
	}
	val offset by remember(thumbOffsetStart, thickness, size, isTopSide, isHorizontal) {
		derivedStateOf {
			val thumbOffsetStartPx = with(density) { thumbOffsetStart.roundToPx() }
			val thicknessPx = with(density) { thickness.roundToPx() }
			if (isHorizontal) {
				val x = thumbOffsetStartPx - size.width / 2 + thicknessPx / 2
				val y = if (isTopSide) -size.height else thicknessPx
				IntOffset(x, y)
			} else {
				val x = if (isTopSide) thicknessPx else -size.width
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
		val tooltipCorner by animateDpAsState(config.toolbarHeight * min(cornerType.scale, 1 / 8f))
		val tooltipCornerShape by remember(tooltipCorner) {
			derivedStateOf { RoundedCornerShape(tooltipCorner) }
		}
		val arrowCorner by animateDpAsState(config.toolbarHeight * min(cornerType.scale / 2, 1 / 8f))
		val arrowCornerShape by remember(arrowCorner) {
			derivedStateOf {
				RoundedCornerShape(
					bottomEnd = arrowCorner,
				)
			}
		}
		val color by animateColorAsState(colorType.color)
		val contentColor by animateColorAsState(colorType.contentColor)
		var targetAlpha by remember { mutableStateOf(0f) }
		var targetScale by remember { mutableStateOf(1f) }
		val alpha by animateFloatAsState(
			targetValue = targetAlpha,
			finishedListener = {
				if (it == 0f) {
					targetScale = 0f
				}
			}
		)
		val scale by animateFloatAsState(
			targetValue = targetScale
		)
		LaunchedEffect(isThumbFocused) {
			targetAlpha = if (isThumbFocused) 1f else 0f
			targetScale = if (isThumbFocused) 1f else 0.9f
		}
		if (isHorizontal) {
			Column(
				modifier = Modifier
					.alpha(alpha)
					.scale(scale)
					.onGloballyPositioned {
						size = it.size
					},
				horizontalAlignment = Alignment.CenterHorizontally,
			) {
				if (!isTopSide) {
					Box(
						modifier = Modifier
							.offset(
								y = arrowSize / 2
							)
							.rotate(-135f)
							.size(arrowSize)
							.background(
								color = color,
								shape = arrowCornerShape
							)
					)
				}
				TooltipText(
					tooltipText = tooltipText,
					config = config,
					color = color,
					contentColor = contentColor,
					tooltipCornerShape = tooltipCornerShape
				)
				if (isTopSide) {
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
				if (isTopSide) {
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
				}
				TooltipText(
					tooltipText = tooltipText,
					config = config,
					color = color,
					contentColor = contentColor,
					tooltipCornerShape = tooltipCornerShape,
				)
				if (!isTopSide) {
					Box(
						modifier = Modifier
							.offset(
								x = -arrowSize / 2
							)
							.rotate(-45f)
							.size(arrowSize)
							.background(
								color = color,
								shape = arrowCornerShape
							)
					)
				}
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