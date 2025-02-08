package cn.vividcode.multiplatform.flex.ui.foundation.slider

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import cn.vividcode.multiplatform.flex.ui.config.LocalFlexConfig
import cn.vividcode.multiplatform.flex.ui.config.foundation.FlexSliderConfig
import cn.vividcode.multiplatform.flex.ui.config.type.*
import kotlin.math.sqrt

/**
 * FlexSlider 滑动条
 */
@Composable
fun FlexSlider(
	value: Float,
	onValueChange: (Float) -> Unit,
	modifier: Modifier = Modifier,
	sizeType: FlexSizeType = FlexSliderDefaults.DefaultSizeType,
	colorType: FlexColorType = FlexSliderDefaults.DefaultColorType,
	cornerType: FlexCornerType = FlexSliderDefaults.DefaultCornerType,
	direction: FlexSliderDirection = FlexSliderDefaults.DefaultSliderDirection,
	minValue: Float = FlexSliderDefaults.MIN_VALUE,
	maxValue: Float = FlexSliderDefaults.MAX_VALUE,
	stepCount: Int? = null,
	tooltipPosition: FlexSliderTooltipPosition = FlexSliderDefaults.DefaultTooltipPosition,
	tooltipFormatter: ((Float) -> String)? = {
		val percent = (it / (maxValue - minValue) * 1000).toInt() / 10f
		"$percent%"
	},
) {
	val config = LocalFlexConfig.current.slider.getConfig(sizeType)
	var length by remember { mutableStateOf(1) }
	val thickness by animateDpAsState(config.thickness)
	val density = LocalDensity.current
	val thicknessPx by remember(thickness) {
		derivedStateOf { with(density) { thickness.toPx() } }
	}
	val isHorizontal by remember(direction) {
		derivedStateOf { direction == FlexSliderDirection.Horizontal }
	}
	var offsetTotal = 0f
	fun updateOffset(offset: Float, isRefresh: Boolean) {
		if (isRefresh) {
			offsetTotal = offset
		} else {
			offsetTotal += offset
		}
		var newValue = ((maxValue - minValue) * ((offsetTotal - thicknessPx / 2) / (length - thicknessPx)) + minValue)
			.coerceIn(minValue, maxValue)
		if (stepCount != null && stepCount >= 1f) {
			val steps = (maxValue - minValue) / stepCount
			val diff = newValue % steps
			if (diff >= steps / 2) {
				newValue += steps - diff
			} else {
				newValue -= diff
			}
		}
		onValueChange(newValue)
	}
	Box(
		modifier = modifier
			.then(
				if (isHorizontal) {
					Modifier
						.fillMaxWidth()
						.height(thickness)
				} else {
					Modifier
						.width(thickness)
						.fillMaxHeight()
				}
			)
			.onGloballyPositioned {
				length = if (isHorizontal) it.size.width else it.size.height
			}
			.pointerInput(isHorizontal) {
				detectDragGestures(
					onDragStart = {
						updateOffset(
							offset = if (isHorizontal) it.x else it.y,
							isRefresh = true
						)
					},
					onDrag = { _, dragAmount ->
						updateOffset(
							offset = if (isHorizontal) dragAmount.x else dragAmount.y,
							isRefresh = false
						)
					}
				)
			}
			.pointerInput(isHorizontal) {
				detectTapGestures(
					onPress = {
						updateOffset(
							offset = if (isHorizontal) it.x else it.y,
							isRefresh = true
						)
					}
				)
			},
		contentAlignment = if (isHorizontal) Alignment.CenterStart else Alignment.TopCenter
	) {
		val thickness by animateDpAsState(config.thickness)
		val sliderThickness by animateDpAsState(config.sliderThickness)
		val sliderCorner by animateDpAsState(sliderThickness * cornerType.scale)
		val sliderCornerShape by remember(sliderCorner) {
			derivedStateOf { RoundedCornerShape(sliderCorner) }
		}
		val padding by remember(thickness, sliderThickness) {
			derivedStateOf { thickness / 2 - sliderThickness / 2 }
		}
		Box(
			modifier = Modifier
				.then(
					if (isHorizontal) {
						Modifier
							.fillMaxWidth()
							.height(sliderThickness)
							.padding(horizontal = padding)
					} else {
						Modifier
							.width(sliderThickness)
							.fillMaxHeight()
							.padding(vertical = padding)
					}
				)
				.clip(sliderCornerShape)
				.background(
					color = MaterialTheme.colorScheme.surfaceVariant,
					shape = sliderCornerShape
				)
		)
		val thumbOffsetStart by remember(value, length, thickness, maxValue, minValue, stepCount) {
			derivedStateOf {
				var value = value.coerceIn(minValue, maxValue)
				if (stepCount != null && stepCount >= 1f) {
					val steps = (maxValue - minValue) / stepCount
					val diff = value % steps
					if (value - diff >= steps / 2) {
						value += diff
					} else {
						value -= diff
					}
				}
				val offsetStart = (length - thicknessPx) / (maxValue - minValue) * (value - minValue)
				with(density) { offsetStart.toDp() }
			}
		}
		val color by animateColorAsState(colorType.color)
		val contentColor by animateColorAsState(colorType.contentColor)
		Box(
			modifier = Modifier
				.width(if (isHorizontal) thumbOffsetStart + config.thickness else sliderThickness)
				.height(if (isHorizontal) sliderThickness else thumbOffsetStart + config.thickness)
				.padding(
					horizontal = if (isHorizontal) padding else Dp.Hairline,
					vertical = if (isHorizontal) Dp.Hairline else padding
				)
				.clip(sliderCornerShape)
				.background(
					color = color,
					shape = sliderCornerShape
				)
		)
		val thumbCorner by animateDpAsState(
			targetValue = config.thickness * cornerType.scale
		)
		val thumbCornerShape by remember(thumbCorner) {
			derivedStateOf { RoundedCornerShape(thumbCorner) }
		}
		val thumbBorderWidth by animateDpAsState(config.thumbBorderWidth)
		Box(
			modifier = Modifier
				.then(
					if (isHorizontal) {
						Modifier.offset(x = thumbOffsetStart)
					} else {
						Modifier.offset(y = thumbOffsetStart)
					}
				)
				.size(thickness)
				.clip(thumbCornerShape)
				.border(
					width = thumbBorderWidth,
					color = color,
					shape = thumbCornerShape
				)
				.background(
					color = contentColor,
					shape = thumbCornerShape
				)
		)
		if (tooltipFormatter != null) {
			TooltipPopup(
				value = value,
				color = color,
				contentColor = contentColor,
				isHorizontal = isHorizontal,
				thumbOffsetStart = thumbOffsetStart,
				thickness = thickness,
				config = config,
				tooltipPosition = tooltipPosition,
				tooltipFormatter = tooltipFormatter,
			)
		}
	}
}

@Composable
private fun TooltipPopup(
	value: Float,
	color: Color,
	contentColor: Color,
	isHorizontal: Boolean,
	thumbOffsetStart: Dp,
	thickness: Dp,
	config: FlexSliderConfig,
	tooltipPosition: FlexSliderTooltipPosition,
	tooltipFormatter: (Float) -> String,
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
		val tooltipCorner by animateDpAsState(config.toolbarHeight / 8)
		val tooltipCornerShape by remember(tooltipCorner) {
			derivedStateOf { RoundedCornerShape(tooltipCorner) }
		}
		val arrowCorner by animateDpAsState(config.toolbarHeight / 16)
		val arrowCornerShape by remember(arrowCorner) {
			derivedStateOf {
				RoundedCornerShape(
					bottomEnd = arrowCorner,
				)
			}
		}
		if (isHorizontal) {
			Column(
				modifier = Modifier
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
					value = value,
					config = config,
					color = color,
					contentColor = contentColor,
					tooltipCornerShape = tooltipCornerShape,
					tooltipFormatter = tooltipFormatter,
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
					value = value,
					config = config,
					color = color,
					contentColor = contentColor,
					tooltipCornerShape = tooltipCornerShape,
					tooltipFormatter = tooltipFormatter,
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
	value: Float,
	config: FlexSliderConfig,
	color: Color,
	contentColor: Color,
	tooltipCornerShape: RoundedCornerShape,
	tooltipFormatter: (Float) -> String,
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
		val text by remember(value) {
			derivedStateOf { tooltipFormatter(value) }
		}
		val fontSize by animateFloatAsState(config.toolbarFontSize.value)
		Text(
			text = text,
			color = contentColor,
			fontSize = fontSize.sp,
			fontWeight = config.toolbarFontWeight,
			lineHeight = fontSize.sp,
			letterSpacing = config.toolbarFontLetterSpacing
		)
	}
}

object FlexSliderDefaults : FlexDefaults(
	defaultCornerType = FlexCornerType.Circle
) {
	override val FlexComposeDefaultConfig.defaultConfig
		get() = this.slider
	
	val DefaultSliderDirection = FlexSliderDirection.Horizontal
	
	const val MIN_VALUE = 0f
	
	const val MAX_VALUE = 100f
	
	val DefaultTooltipPosition = FlexSliderTooltipPosition.TopSide
}

enum class FlexSliderDirection {
	
	Horizontal,
	
	Vertical
}

enum class FlexSliderTooltipPosition {
	
	TopSide,
	
	BottomSide
}