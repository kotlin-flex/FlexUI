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
	tooltipFormatter: ((Float) -> Any?)? = null,
) {
	val config = LocalFlexConfig.current.slider.getConfig(sizeType)
	var length by remember { mutableStateOf(Dp.Unspecified) }
	val thickness by animateDpAsState(config.thickness)
	val density = LocalDensity.current
	val isHorizontal by remember(direction) {
		derivedStateOf { direction == FlexSliderDirection.Horizontal }
	}
	
	val stepValues by remember(steps, minValue, maxValue) {
		derivedStateOf {
			if (steps == null) return@derivedStateOf null
			steps.calcStepValues(minValue, maxValue)
		}
	}
	
	var offsetX by remember { mutableStateOf(Dp.Unspecified) }
	LaunchedEffect(offsetX) {
		if (length == Dp.Hairline || offsetX == Dp.Unspecified) return@LaunchedEffect
		var value = offsetX / length * (maxValue - minValue) + minValue
		stepValues?.also { values ->
			for (i in 1 ..< values.size) {
				if (value <= values[i]) {
					val leftDistance = value - values[i - 1]
					val rightDistance = values[i] - value
					if (leftDistance > rightDistance) {
						value += rightDistance
					} else {
						value -= leftDistance
					}
					break
				}
			}
		}
		onValueChange(value.coerceIn(minValue, maxValue))
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
				length = with(density) {
					if (isHorizontal) it.size.width.toDp() else it.size.height.toDp()
				}
			}
			.pointerInput(isHorizontal) {
				detectDragGestures(
					onDrag = { change, dragAmount ->
						change.consume()
						offsetX += with(density) {
							if (isHorizontal) dragAmount.x.toDp() else dragAmount.y.toDp()
						}
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
						offsetX = with(density) {
							(if (isHorizontal) it.x else it.y).toDp()
						}
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
		val thumbOffsetStart by remember(value, density, length, thickness, maxValue, minValue) {
			derivedStateOf {
				if (length == Dp.Unspecified) return@derivedStateOf Dp.Hairline
				var value = value.coerceIn(minValue, maxValue)
				(length - thickness) / (maxValue - minValue) * (value - minValue)
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
		val tooltipText by remember(value, tooltipFormatter) {
			derivedStateOf {
				tooltipFormatter?.invoke(value)?.toString()
			}
		}
		tooltipText?.let {
			TooltipPopup(
				tooltipText = it,
				colorType = colorType,
				cornerType = cornerType,
				isHorizontal = isHorizontal,
				isThumbFocused = isThumbFocused,
				thumbOffsetStart = thumbOffsetStart,
				thickness = thickness,
				config = config,
				tooltipPosition = tooltipPosition
			)
		}
	}
}

@Composable
private fun TooltipPopup(
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

/**
 * 滑动条步骤
 */
sealed interface FlexSliderSteps {
	
	fun calcStepValues(minValue: Float, maxValue: Float): List<Float>
	
	companion object {
		
		/**
		 * 根据步幅数量平均分配
		 */
		fun averageSteps(count: Int): FlexSliderSteps = FlexSliderAverageSteps(count)
		
		/**
		 * 根据值分配步幅
		 */
		fun valuesSteps(vararg values: Float): FlexSliderSteps = FlexSliderValuesSteps(values.toList())
		
		/**
		 * 根据百分比分配步幅
		 */
		fun percentSteps(vararg percents: Float): FlexSliderSteps = FlexSliderPercentSteps(percents.toList())
	}
}

internal class FlexSliderAverageSteps(
	private val count: Int,
) : FlexSliderSteps {
	
	override fun calcStepValues(minValue: Float, maxValue: Float): List<Float> {
		val steps = (maxValue - minValue) / count
		return (0 ..< count).map { minValue + steps * it } + maxValue
	}
}

internal class FlexSliderValuesSteps(
	private val values: List<Float>,
) : FlexSliderSteps {
	
	override fun calcStepValues(minValue: Float, maxValue: Float): List<Float> {
		return (values + minValue + maxValue).asSequence()
			.distinct()
			.filter { it in minValue .. maxValue }
			.sorted()
			.toList()
	}
}

internal class FlexSliderPercentSteps(
	private val percents: List<Float>,
) : FlexSliderSteps {
	
	override fun calcStepValues(minValue: Float, maxValue: Float): List<Float> {
		return (percents + 0f + 1f).asSequence()
			.distinct()
			.filter { it in 0f .. 1f }
			.sorted()
			.map { (maxValue - minValue) * it + minValue }
			.toList()
	}
}