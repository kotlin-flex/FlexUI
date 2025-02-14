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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import cn.vividcode.multiplatform.flex.ui.config.LocalFlexConfig
import cn.vividcode.multiplatform.flex.ui.config.type.*
import cn.vividcode.multiplatform.flex.ui.expends.lightenWithColor

/**
 * FlexSlider 滑动条
 *
 * @param value 当前滑块的值
 * @param onValueChange 当滑块值更改时调用的回调函数
 * @param modifier 组件的修饰符，用于控制布局和样式
 * @param sizeType 滑块的尺寸类型，默认为 [FlexSliderDefaults.DefaultSizeType]
 * @param colorType 滑块的颜色类型，默认为 [FlexSliderDefaults.DefaultColorType]
 * @param cornerType 滑块的圆角类型，默认为 [FlexSliderDefaults.DefaultCornerType]
 * @param direction 滑块的滑动方向，默认为 [FlexSliderDefaults.DefaultSliderDirection]
 * @param valueRange 滑块的取值范围，默认为 [FlexSliderDefaults.DefaultValueRange]
 * @param steps 可选参数，定义滑块的分步设置，如果为 `null`，则为连续滑动
 * @param marks 可选参数，定义滑块上的标记点，如果为 `null`，则不显示
 * @param tooltipPosition 滑块提示框的位置，默认为 [FlexSliderDefaults.DefaultTooltipPosition]
 * @param tooltipFormatter 可选参数，自定义提示框格式化函数，如果为 `null` 则不显示滑块提示框
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
	valueRange: FloatRange = FlexSliderDefaults.DefaultValueRange,
	steps: FlexSliderSteps? = null,
	marks: FlexSliderMarks? = null,
	tooltipFormatter: ((Float) -> Any?)? = null,
) {
	val config = LocalFlexConfig.current.slider.getConfig(sizeType)
	var length by remember { mutableStateOf(Dp.Unspecified) }
	val thickness by animateDpAsState(config.thickness)
	val density = LocalDensity.current
	val isHorizontal by remember(direction) {
		derivedStateOf { direction == FlexSliderDirection.Horizontal }
	}
	
	val stepValues by remember(steps, marks, valueRange) {
		derivedStateOf {
			if (steps == null) return@derivedStateOf null
			steps.calcStepValues(valueRange)
		}
	}
	
	var offsetX by remember { mutableStateOf(Dp.Unspecified) }
	LaunchedEffect(offsetX, length, valueRange, stepValues) {
		if (length == Dp.Hairline || offsetX == Dp.Unspecified) return@LaunchedEffect
		var value = offsetX / length * valueRange.range + valueRange.start
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
		onValueChange(value.coerceIn(valueRange))
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
		val sliderColor by animateColorAsState(
			targetValue = MaterialTheme.colorScheme.surfaceVariant
		)
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
					color = sliderColor,
					shape = sliderCornerShape
				)
		)
		val thumbOffsetStart by remember(value, density, length, thickness, valueRange) {
			derivedStateOf {
				if (length == Dp.Unspecified) return@derivedStateOf Dp.Hairline
				var value = value.coerceIn(valueRange)
				(length - thickness) / valueRange.range * (value - valueRange.start)
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
				.size(
					width = if (isHorizontal) thumbOffsetStart + config.thickness else sliderThickness,
					height = if (!isHorizontal) thumbOffsetStart + config.thickness else sliderThickness
				)
				.padding(
					horizontal = if (isHorizontal) padding else Dp.Hairline,
					vertical = if (!isHorizontal) padding else Dp.Hairline
				)
				.clip(sliderCornerShape)
				.background(
					color = color,
					shape = sliderCornerShape
				)
		)
		
		if (marks != null && length != Dp.Unspecified) {
			FlexSliderMarks(
				value = value,
				colorType = colorType,
				marks = marks,
				valueRange = valueRange,
				config = config,
				length = length,
				thickness = thickness,
				sliderThickness = sliderThickness,
				isHorizontal = isHorizontal,
				contentColor = contentColor
			)
		}
		
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
			targetValue = if (isThumbFocused) 1.2f else 1f
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
					y = if (!isHorizontal) thumbOffsetStart else Dp.Hairline
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
			val tooltipText by remember(value, tooltipFormatter) {
				derivedStateOf {
					tooltipFormatter.invoke(value)?.toString()
				}
			}
			if (tooltipText != null) {
				FlexSliderTooltipPopup(
					tooltipText = tooltipText!!,
					colorType = colorType,
					cornerType = cornerType,
					isHorizontal = isHorizontal,
					isThumbFocused = isThumbFocused,
					thumbOffsetStart = thumbOffsetStart,
					thickness = thickness,
					config = config
				)
			}
		}
	}
}

object FlexSliderDefaults : FlexDefaults(
	defaultCornerType = FlexCornerType.Circle
) {
	override val FlexComposeDefaultConfig.defaultConfig
		get() = this.slider
	
	val DefaultSliderDirection = FlexSliderDirection.Horizontal
	
	val DefaultValueRange = 0f .. 100f
	
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

internal typealias FloatRange = ClosedFloatingPointRange<Float>

internal val FloatRange.range: Float
	get() = this.endInclusive - this.start