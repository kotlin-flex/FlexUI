package cn.vividcode.multiplatform.flex.ui.foundation.slider

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import cn.vividcode.multiplatform.flex.ui.config.foundation.FlexSliderConfig
import cn.vividcode.multiplatform.flex.ui.config.type.FlexColorType
import cn.vividcode.multiplatform.flex.ui.expends.darkenWithColor

/**
 * 滑动条的标记点
 */
@ConsistentCopyVisibility
data class FlexSliderMarks private constructor(
	val marks: List<FlexSliderMark>,
) {
	
	companion object {
		
		@Composable
		fun rememberMarks(marks: List<FlexSliderMark>): FlexSliderMarks =
			remember(marks) {
				val filterMarks = marks.distinctBy { it.value }
					.sortedBy { it.value }
				FlexSliderMarks(filterMarks)
			}
		
		@Composable
		fun rememberMarks(vararg marks: FlexSliderMark): FlexSliderMarks =
			remember(marks) {
				val filterMarks = marks.distinctBy { it.value }
					.sortedBy { it.value }
				FlexSliderMarks(filterMarks)
			}
		
		@Composable
		fun rememberValuesMarks(values: Iterable<Float>): FlexSliderMarks =
			remember(values) {
				val filterMarks = values.distinct()
					.sorted()
					.map { FlexSliderMark(it) }
				FlexSliderMarks(filterMarks)
			}
		
		@Composable
		fun rememberValuesMarks(vararg values: Float): FlexSliderMarks =
			remember(values) {
				val filterMarks = values.distinct()
					.sorted()
					.map { FlexSliderMark(it) }
				FlexSliderMarks(filterMarks)
			}
		
		@Composable
		fun rememberTextMarks(vararg marks: Pair<Float, String>): FlexSliderMarks =
			remember(marks) {
				val filterMarks = marks
					.distinctBy { it.first }
					.sortedBy { it.first }
					.map { FlexSliderMark(it.first, it.second) }
				FlexSliderMarks(filterMarks)
			}
		
		@Composable
		fun rememberTextMarks(marks: Map<Float, String>): FlexSliderMarks =
			remember(marks) {
				val filterMarks = marks.entries
					.sortedBy { it.key }
					.map { FlexSliderMark(it.key, it.value) }
				FlexSliderMarks(filterMarks)
			}
	}
}

data class FlexSliderMark(
	val value: Float,
	val text: String? = null,
	val color: Color? = null,
	val weight: FontWeight? = null,
)

/**
 * 滑动条的标记点
 */
@Composable
internal fun FlexSliderMarks(
	value: Float,
	colorType: FlexColorType,
	marks: FlexSliderMarks,
	valueRange: FloatRange,
	config: FlexSliderConfig,
	length: Dp,
	thickness: Dp,
	sliderThickness: Dp,
	isHorizontal: Boolean,
	isFocused: Boolean,
	shape: RoundedCornerShape,
) {
	val filterMarks by remember(marks, valueRange) {
		derivedStateOf {
			marks.marks.filter { it.value in valueRange }
		}
	}
	val markBorderWidth by animateDpAsState(
		targetValue = config.markBorderWidth
	)
	val density = LocalDensity.current
	filterMarks.forEach {
		val offsetStart by remember(it.value, length, thickness, sliderThickness, markBorderWidth, valueRange) {
			derivedStateOf {
				(length - thickness) / valueRange.range * (it.value - valueRange.start) + thickness / 2 - markBorderWidth - sliderThickness / 2
			}
		}
		val color = colorType.color
		val surfaceVariant = MaterialTheme.colorScheme.surfaceVariant
		val borderColor by remember(it.value, value, color, surfaceVariant, isFocused) {
			derivedStateOf {
				val color = if (it.value <= value) color else surfaceVariant.darkenWithColor
				if (isFocused) color else color.copy(alpha = 0.75f)
			}
		}
		Box(
			modifier = Modifier
				.offset(
					x = if (isHorizontal) offsetStart else Dp.Hairline,
					y = if (!isHorizontal) offsetStart else Dp.Hairline
				)
				.size(sliderThickness + markBorderWidth * 2)
				.border(
					width = markBorderWidth,
					color = borderColor,
					shape = shape
				)
				.background(
					color = MaterialTheme.colorScheme.surface,
					shape = shape
				)
		)
		if (it.text != null) {
			var size by remember { mutableStateOf(IntSize.Zero) }
			val textOffsetStartPx by remember(offsetStart, length, sliderThickness, markBorderWidth, config.thickness) {
				derivedStateOf {
					with(density) {
						if (isHorizontal) {
							IntOffset(
								x = (offsetStart + sliderThickness / 2 + markBorderWidth).roundToPx() - size.width / 2,
								y = (config.thickness + config.markInterval).roundToPx()
							)
						} else {
							IntOffset(
								x = -size.width - config.markInterval.roundToPx(),
								y = (offsetStart + sliderThickness / 2 + markBorderWidth).roundToPx() - size.height / 2,
							)
						}
					}
				}
			}
			val color by animateColorAsState(
				targetValue = it.color ?: colorType.color
			)
			val markFontSize by animateFloatAsState(
				targetValue = config.markFontSize.value
			)
			Popup(
				alignment = Alignment.TopStart,
				offset = textOffsetStartPx
			) {
				Text(
					text = it.text,
					modifier = Modifier
						.onGloballyPositioned {
							size = it.size
						}
						.alpha(if (size == IntSize.Zero) 0f else 1f),
					color = color,
					fontWeight = it.weight ?: config.markFontWeight,
					fontSize = markFontSize.sp,
					lineHeight = markFontSize.sp
				)
			}
		}
	}
}