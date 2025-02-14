package cn.vividcode.multiplatform.flex.ui.foundation.slider

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight

/**
 *
 */
data class FlexSliderMarks(
	val marks: List<FlexSliderMark>,
) {
	
	companion object {
		
		@Composable
		fun rememberMarks(vararg marks: FlexSliderMark): FlexSliderMarks {
			return remember(marks) {
				FlexSliderMarks(marks.distinctBy { it.value })
			}
		}
		
		@Composable
		fun rememberMarks(marks: List<FlexSliderMark>): FlexSliderMarks {
			return remember(marks) {
				FlexSliderMarks(marks.distinctBy { it.value })
			}
		}
		
		@Composable
		fun rememberTextMarks(vararg marks: Pair<Float, String>): FlexSliderMarks {
			return remember(marks) {
				FlexSliderMarks(marks.distinctBy { it.first }.map { FlexSliderMark(it.first, it.second) })
			}
		}
		
		@Composable
		fun rememberTextMarks(marks: Map<Float, String>): FlexSliderMarks {
			return remember(marks) {
				FlexSliderMarks(marks.map { FlexSliderMark(it.key, it.value) })
			}
		}
	}
}

data class FlexSliderMark(
	val value: Float,
	val text: String,
	val color: Color? = null,
	val weight: FontWeight? = null,
)