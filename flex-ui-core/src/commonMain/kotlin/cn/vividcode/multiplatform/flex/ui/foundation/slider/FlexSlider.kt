package cn.vividcode.multiplatform.flex.ui.foundation.slider

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import cn.vividcode.multiplatform.flex.ui.config.LocalFlexConfig
import cn.vividcode.multiplatform.flex.ui.config.type.*

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
	minValue: Float = 0f,
	maxValue: Float = 100f,
	steps: Float? = null,
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
		if (steps != null && steps > 0f) {
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
		val thumbOffsetStart by remember(value, length, thickness, maxValue, minValue, steps) {
			derivedStateOf {
				var value = value.coerceIn(minValue, maxValue)
				if (steps != null && steps > 0f) {
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
					color = colorType.backgroundColor,
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
					color = colorType.backgroundColor,
					shape = thumbCornerShape
				)
				.background(
					color = colorType.contentColor,
					shape = thumbCornerShape
				)
		)
	}
}


object FlexSliderDefaults : FlexDefaults(
	defaultCornerType = FlexCornerType.Circle
) {
	override val FlexComposeDefaultConfig.defaultConfig
		get() = this.slider
	
	val DefaultSliderDirection = FlexSliderDirection.Horizontal
}

enum class FlexSliderDirection {
	
	Horizontal,
	
	Vertical
}