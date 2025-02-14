package cn.vividcode.multiplatform.flex.ui.foundation.slider

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import cn.vividcode.multiplatform.flex.ui.config.foundation.FlexSliderConfig
import cn.vividcode.multiplatform.flex.ui.config.type.FlexColorType
import cn.vividcode.multiplatform.flex.ui.expends.darkenWithColor

/**
 * 滑动条的标记点
 */
@ConsistentCopyVisibility
data class FlexSliderMarks private constructor(
	val marks: List<FlexSliderTextMark>,
) {
	
	companion object {
		
		@Composable
		fun rememberMarks(vararg marks: FlexSliderTextMark): FlexSliderMarks {
			return remember(marks) {
				FlexSliderMarks(marks.distinctBy { it.value })
			}
		}
		
		@Composable
		fun rememberMarks(marks: List<FlexSliderTextMark>): FlexSliderMarks {
			return remember(marks) {
				FlexSliderMarks(marks.distinctBy { it.value })
			}
		}
		
		@Composable
		fun rememberValuesMarks(vararg values: Float): FlexSliderMarks {
			return remember(values) {
				FlexSliderMarks(values.distinct().map { FlexSliderTextMark(it) })
			}
		}
		
		@Composable
		fun rememberValuesMarks(values: List<Float>): FlexSliderMarks {
			return remember(values) {
				FlexSliderMarks(values.distinct().map { FlexSliderTextMark(it) })
			}
		}
		
		@Composable
		fun rememberTextMarks(vararg marks: Pair<Float, String>): FlexSliderMarks {
			return remember(marks) {
				FlexSliderMarks(marks.toMap().map { FlexSliderTextMark(it.key, it.value) })
			}
		}
		
		@Composable
		fun rememberTextMarks(marks: Map<Float, String>): FlexSliderMarks {
			return remember(marks) {
				FlexSliderMarks(marks.map { FlexSliderTextMark(it.key, it.value) })
			}
		}
	}
}

data class FlexSliderTextMark(
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
	contentColor: Color,
) {
	val realMarks by remember(marks, valueRange) {
		derivedStateOf {
			marks.marks.filter { it.value in valueRange }
		}
	}
	val markBorderWidth by animateDpAsState(
		targetValue = config.markBorderWidth
	)
	realMarks.forEach {
		val offsetStart by remember(it.value, length, thickness, sliderThickness, markBorderWidth, valueRange) {
			derivedStateOf {
				(length - thickness) / valueRange.range * (it.value - valueRange.start) + thickness / 2 - markBorderWidth - sliderThickness / 2
			}
		}
		val borderColor by animateColorAsState(
			targetValue = if (it.value <= value) colorType.color else MaterialTheme.colorScheme.surfaceVariant.darkenWithColor
		)
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
					shape = CircleShape
				)
				.background(
					color = contentColor,
					shape = CircleShape
				)
		)
		if (it.text != null) {
			// xxx
		}
	}
}