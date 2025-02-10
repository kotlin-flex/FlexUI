package cn.vividcode.multiplatform.flex.ui.foundation.slider

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
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
import cn.vividcode.multiplatform.flex.ui.expends.lightenWithColor
import kotlin.math.min
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
	steps: FlexSliderSteps? = null,
	tooltipPosition: FlexSliderTooltipPosition = FlexSliderDefaults.DefaultTooltipPosition,
	tooltipFormatter: ((Float) -> String)? = { FlexSliderDefaults.defaultTooltipFormatter(it, minValue, maxValue) },
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
		
		when (steps) {
			is FlexSliderAverageSteps -> {
				val count = steps.count
				if (count >= 1f) {
					val steps = (maxValue - minValue) / count
					val diff = newValue % steps
					if (diff >= count / 2) {
						newValue += steps - diff
					} else {
						newValue -= diff
					}
				}
			}
			
			else -> {}
		}
		onValueChange(newValue)
	}
	
	val interactionSource = remember { MutableInteractionSource() }
	var isDragging by remember { mutableStateOf(false) }
	var isTap by remember { mutableStateOf(false) }
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
					onDrag = { _, dragAmount ->
						updateOffset(
							offset = if (isHorizontal) dragAmount.x else dragAmount.y,
							isRefresh = false
						)
					},
					onDragEnd = {
						isDragging = false
					}
				)
			}
			.pointerInput(isHorizontal) {
				detectTapGestures(
					onPress = {
						isDragging = true
						updateOffset(
							offset = if (isHorizontal) it.x else it.y,
							isRefresh = true
						)
					},
					onTap = {
						isTap = true
					}
				)
			}
			.hoverable(
				interactionSource = interactionSource
			),
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
		val thumbOffsetStart by remember(value, length, thickness, maxValue, minValue, steps) {
			derivedStateOf {
				var value = value.coerceIn(minValue, maxValue)
				val offsetStart = (length - thicknessPx) / (maxValue - minValue) * (value - minValue)
				with(density) { offsetStart.toDp() }
			}
		}
		val isHovered by interactionSource.collectIsHoveredAsState()
		val isFocused by remember(isHovered, isDragging) {
			derivedStateOf { isHovered || isDragging }
		}
		val color by animateColorAsState(
			targetValue = run {
				val color = colorType.color
				when {
					isFocused -> color
					else -> color.copy(alpha = 0.75f)
				}
			}
		)
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
		val thumbInteractionSource = remember { MutableInteractionSource() }
		val isThumbHovered by thumbInteractionSource.collectIsHoveredAsState()
		LaunchedEffect(isTap, isThumbHovered) {
			if (isTap && isThumbHovered) {
				isTap = false
				isDragging = false
			}
		}
		val isThumbFocused by remember(isThumbHovered, isDragging) {
			derivedStateOf { isThumbHovered || isDragging }
		}
		val scale by animateFloatAsState(
			targetValue = if (isThumbFocused) 1.25f else 1f
		)
		val thumbBorderWidth by animateDpAsState(
			targetValue = if (isThumbFocused) config.thumbBorderWidth * 1.1f else config.thumbBorderWidth
		)
		val borderColor by animateColorAsState(
			targetValue = run {
				val color = colorType.color
				when {
					isThumbFocused -> color.lightenWithColor
					isFocused -> color
					else -> color.copy(alpha = 0.8f)
				}
			}
		)
		Box(
			modifier = Modifier
				.offset(
					x = if (isHorizontal) thumbOffsetStart else Dp.Hairline,
					y = if (isHorizontal) Dp.Hairline else thumbOffsetStart
				)
				.scale(scale)
				.size(thickness)
				.clip(thumbCornerShape)
				.border(
					width = thumbBorderWidth,
					color = borderColor,
					shape = thumbCornerShape
				)
				.background(
					color = contentColor,
					shape = thumbCornerShape
				)
				.hoverable(
					interactionSource = thumbInteractionSource
				)
		)
		if (tooltipFormatter != null) {
			TooltipPopup(
				value = value,
				colorType = colorType,
				cornerType = cornerType,
				isHorizontal = isHorizontal,
				isThumbFocused = isThumbFocused,
				thumbOffsetStart = thumbOffsetStart,
				thickness = thickness,
				config = config,
				tooltipPosition = tooltipPosition,
				tooltipFormatter = tooltipFormatter
			)
		}
	}
}

@Composable
private fun TooltipPopup(
	value: Float,
	colorType: FlexColorType,
	cornerType: FlexCornerType,
	isHorizontal: Boolean,
	isThumbFocused: Boolean,
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
	
	fun defaultTooltipFormatter(value: Float, minValue: Float, maxValue: Float): String {
		val percent = (value / (maxValue - minValue) * 1000).toInt() / 10f
		return "$percent%"
	}
}

enum class FlexSliderDirection {
	
	Horizontal,
	
	Vertical
}

enum class FlexSliderTooltipPosition {
	
	TopSide,
	
	BottomSide
}

sealed interface FlexSliderSteps {
	
	companion object {
		
		/**
		 * 按照数量平均分配
		 */
		fun averageSteps(count: Int) = FlexSliderAverageSteps(count)
	}
}

class FlexSliderAverageSteps internal constructor(
	val count: Int,
) : FlexSliderSteps