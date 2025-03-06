package cn.vividcode.multiplatform.flex.ui.foundation.slider

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.fastForEach
import cn.vividcode.multiplatform.flex.ui.config.FlexComposeDefaultConfig
import cn.vividcode.multiplatform.flex.ui.config.FlexDefaults
import cn.vividcode.multiplatform.flex.ui.config.LocalFlexConfig
import cn.vividcode.multiplatform.flex.ui.config.foundation.FlexSliderConfig
import cn.vividcode.multiplatform.flex.ui.type.*
import cn.vividcode.multiplatform.flex.ui.utils.*

/**
 * FlexSlider 滑动条
 *
 * @param value 当前滑块的值
 * @param onValueChange 当滑块值更改时调用的回调函数
 * @param modifier 组件的修饰符，用于控制布局和样式
 * @param sizeType 滑块的尺寸类型，默认为 [FlexSliderDefaults.DefaultSizeType]
 * @param brushType 滑块的颜色类型，默认为 [FlexSliderDefaults.DefaultBrushType]
 * @param cornerType 滑块的圆角类型，默认为 [FlexSliderDefaults.DefaultCornerType]
 * @param direction 滑块的滑动方向，默认为 [FlexSliderDefaults.DefaultSliderDirection]
 * @param enabled 是否启用，默认为 `true`
 * @param valueRange 滑块的取值范围，默认为 [FlexSliderDefaults.DefaultValueRange]
 * @param steps 可选参数，定义滑块的分步设置，如果为 `null`，则为连续滑动
 * @param marks 可选参数，定义滑块上的标记点，如果为 `null`，则不显示
 * @param tooltipFormatter 可选参数，自定义提示框格式化函数，如果为 `null` 则不显示滑块提示框
 */
@Composable
fun FlexSlider(
	value: Float,
	onValueChange: (Float) -> Unit,
	modifier: Modifier = Modifier,
	sizeType: FlexSizeType = FlexSliderDefaults.DefaultSizeType,
	brushType: FlexBrushType = FlexSliderDefaults.DefaultBrushType,
	cornerType: FlexCornerType = FlexSliderDefaults.DefaultCornerType,
	direction: FlexSliderDirection = FlexSliderDefaults.DefaultSliderDirection,
	enabled: Boolean = true,
	valueRange: FloatRange = FlexSliderDefaults.DefaultValueRange,
	steps: FlexSliderSteps? = null,
	marks: FlexSliderMarks? = null,
	tooltipFormatter: ((Float) -> Any?)? = null,
) {
	val config = LocalFlexConfig.current.slider.getConfig(sizeType)
	val isHorizontal by remember(direction) {
		derivedStateOf { direction == FlexSliderDirection.Horizontal }
	}
	FlexSliderLayout(
		modifier = modifier,
		isHorizontal = isHorizontal
	) {
		var length by remember { mutableStateOf(Dp.Unspecified) }
		val thickness by animateDpAsState(config.thickness)
		val density = LocalDensity.current
		val stepValues by remember(steps, valueRange) {
			derivedStateOf { steps?.calcStepValues(valueRange) }
		}
		
		var offsetX by remember { mutableStateOf(Dp.Unspecified) }
		LaunchedEffect(offsetX, value, length, valueRange, stepValues, thickness) {
			if (length == Dp.Unspecified || offsetX == Dp.Unspecified) return@LaunchedEffect
			var newValue = (offsetX - thickness / 2) / (length - thickness) * valueRange.range + valueRange.start
			newValue = newValue.coerceIn(valueRange)
			stepValues?.also { values ->
				for (i in 1 ..< values.size) {
					if (newValue <= values[i]) {
						val leftDistance = newValue - values[i - 1]
						val rightDistance = values[i] - newValue
						newValue += if (leftDistance > rightDistance) rightDistance else -leftDistance
						break
					}
				}
			}
			onValueChange(newValue)
		}
		
		val interactionSource = remember { MutableInteractionSource() }
		var isDragging by remember { mutableStateOf(false) }
		var isTap by remember { mutableStateOf(false) }
		
		val showVerticalTextMarks by remember(marks, length, isHorizontal) {
			derivedStateOf { marks != null && length != Dp.Unspecified && !isHorizontal }
		}
		if (showVerticalTextMarks) {
			FlexSliderTextMarks(
				config = config,
				isHorizontal = isHorizontal,
				valueRange = valueRange,
				marks = marks!!,
				brushType = brushType,
				length = length,
			)
			Spacer(modifier = Modifier.width(config.markInterval))
		}
		
		Box(
			modifier = Modifier
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
				.pointerInput(isHorizontal, enabled) {
					if (!enabled) return@pointerInput
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
				.pointerInput(isHorizontal, enabled) {
					if (!enabled) return@pointerInput
					detectTapGestures(
						onPress = {
							isDragging = true
							offsetX = with(density) {
								if (isHorizontal) it.x.toDp() else it.y.toDp()
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
			
			val percent by animateIntAsState((cornerType.scale * 100).toInt())
			val shape by remember(percent) {
				derivedStateOf { RoundedCornerShape(percent) }
			}
			val padding by remember(thickness, sliderThickness) {
				derivedStateOf { thickness / 2 - sliderThickness / 2 }
			}
			val sliderBrush by animateFlexBrushAsState(
				targetValue = MaterialTheme.colorScheme.surfaceVariant.toSolidColor().let {
					if (enabled) it else it.disabledWithBrush
				}
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
					.clip(shape)
					.background(
						brush = sliderBrush,
						shape = shape
					)
			)
			val thumbOffsetStart by remember(value, length, config.thickness, valueRange) {
				derivedStateOf {
					if (length == Dp.Unspecified) return@derivedStateOf Dp.Hairline
					var value = value.coerceIn(valueRange)
					(length - config.thickness) / valueRange.range * (value - valueRange.start)
				}
			}
			val isHovered by interactionSource.collectIsHoveredAsState()
			val isFocused by remember(isHovered, isDragging) {
				derivedStateOf { isHovered || isDragging }
			}
			val thumbBrush by animateFlexBrushAsState(
				targetValue = when {
					!enabled -> brushType.disabledBrush
					isFocused -> brushType.brush
					else -> brushType.brush.copy(alpha = 0.75f)
				}
			)
			val contentBrush by animateFlexBrushAsState(
				targetValue = if (enabled) brushType.onBrush else brushType.lightenOnBrush
			)
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
					.clip(shape)
					.background(
						brush = thumbBrush,
						shape = shape
					)
			)
			
			if (marks != null && length != Dp.Unspecified) {
				FlexSliderMarks(
					value = value,
					brushType = brushType,
					marks = marks,
					valueRange = valueRange,
					config = config,
					length = length,
					thickness = thickness,
					sliderThickness = sliderThickness,
					isHorizontal = isHorizontal,
					isFocused = isFocused,
					shape = shape
				)
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
				targetValue = if (isThumbFocused && enabled) 1.2f else 1f
			)
			val thumbBorderWidth by animateDpAsState(
				targetValue = if (isThumbFocused) config.thumbBorderWidth * 1.1f else config.thumbBorderWidth
			)
			val borderBrush by animateFlexBrushAsState(
				targetValue = when {
					!enabled -> brushType.disabledBrush
					isThumbFocused -> brushType.lightenBrush
					isFocused -> brushType.brush
					else -> brushType.brush.copy(alpha = 0.8f)
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
					.clip(shape)
					.border(
						width = thumbBorderWidth,
						brush = borderBrush,
						shape = shape
					)
					.background(
						brush = contentBrush,
						shape = shape
					)
					.hoverable(
						interactionSource = thumbInteractionSource
					)
			)
			
			if (tooltipFormatter != null && enabled) {
				val tooltipText by remember(value, tooltipFormatter) {
					derivedStateOf {
						tooltipFormatter.invoke(value)?.toString()
					}
				}
				if (tooltipText != null) {
					FlexSliderTooltipPopup(
						tooltipText = tooltipText!!,
						brushType = brushType,
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
		
		val showHorizontalTextMarks by remember(marks, length, isHorizontal) {
			derivedStateOf { marks != null && length != Dp.Unspecified && isHorizontal }
		}
		if (showHorizontalTextMarks) {
			Spacer(modifier = Modifier.height(config.markInterval))
			FlexSliderTextMarks(
				config = config,
				isHorizontal = isHorizontal,
				valueRange = valueRange,
				marks = marks!!,
				brushType = brushType,
				length = length
			)
		}
	}
}

@Composable
private fun FlexSliderTextMarks(
	config: FlexSliderConfig,
	isHorizontal: Boolean,
	valueRange: FloatRange,
	marks: FlexSliderMarks,
	brushType: FlexBrushType,
	length: Dp,
) {
	Box(
		modifier = when (isHorizontal) {
			true -> Modifier.fillMaxWidth()
			false -> Modifier.fillMaxHeight()
		},
		contentAlignment = if (isHorizontal) Alignment.TopStart else Alignment.TopEnd
	) {
		val filterMarks by remember(marks, valueRange) {
			derivedStateOf {
				marks.marks.filter {
					it.text != null && it.value in valueRange
				}
			}
		}
		val density = LocalDensity.current
		val thickness by animateDpAsState(config.thickness)
		filterMarks.fastForEach {
			var markTextLength by remember { mutableStateOf(Dp.Hairline) }
			val textOffsetStart by remember(it.value, length, markTextLength, thickness, valueRange) {
				derivedStateOf {
					(length - thickness) / valueRange.range * (it.value - valueRange.start) + thickness / 2 - markTextLength / 2
				}
			}
			val brush by animateFlexBrushAsState(
				targetValue = it.brush ?: it.color?.toSolidColor() ?: brushType.brush
			)
			val markFontSize by animateFloatAsState(
				targetValue = config.markFontSize.value
			)
			Text(
				text = it.text!!,
				modifier = Modifier
					.offset(
						x = if (isHorizontal) textOffsetStart else Dp.Hairline,
						y = if (!isHorizontal) textOffsetStart else Dp.Hairline
					)
					.onGloballyPositioned {
						markTextLength = with(density) {
							if (isHorizontal) it.size.width.toDp() else it.size.height.toDp()
						}
					}
					.alpha(
						alpha = if (markTextLength == Dp.Hairline) 0f else 1f
					),
				fontWeight = it.weight ?: config.markFontWeight,
				fontSize = markFontSize.sp,
				lineHeight = markFontSize.sp,
				style = LocalTextStyle.current.copy(
					brush = brush.original
				)
			)
		}
	}
}

@Composable
private fun FlexSliderLayout(
	modifier: Modifier,
	isHorizontal: Boolean,
	content: @Composable () -> Unit,
) {
	if (isHorizontal) {
		Column(
			modifier = modifier
		) {
			content()
		}
	} else {
		Row(
			modifier = modifier
		) {
			content()
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
}

enum class FlexSliderDirection {
	
	Horizontal,
	
	Vertical
}

internal typealias FloatRange = ClosedFloatingPointRange<Float>

internal val FloatRange.range: Float
	get() = this.endInclusive - this.start