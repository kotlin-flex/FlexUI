package cn.vividcode.multiplatform.flex.ui.foundation.slider

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

/**
 * 滑动条步骤
 */
sealed interface FlexSliderSteps {
	
	fun calcStepValues(valueRange: FloatRange): List<Float>
	
	companion object {
		
		/**
		 * 根据步幅数量平均分配
		 */
		@Composable
		fun rememberAverageSteps(count: Int): FlexSliderSteps =
			remember(count) { FlexSliderAverageSteps(count) }
		
		/**
		 * 根据值分配步幅
		 */
		@Composable
		fun rememberValuesSteps(vararg values: Float): FlexSliderSteps =
			remember(values) { FlexSliderValuesSteps(values.toSet()) }
		
		/**
		 * 根据值分配步幅
		 */
		@Composable
		fun rememberValuesSteps(values: Set<Float>): FlexSliderSteps =
			remember(values) { FlexSliderValuesSteps(values) }
		
		/**
		 * 根据百分比分配步幅
		 */
		@Composable
		fun rememberPercentSteps(vararg percents: Float): FlexSliderSteps =
			remember(percents) { FlexSliderPercentSteps(percents.toSet()) }
		
		/**
		 * 根据百分比分配步幅
		 */
		@Composable
		fun rememberPercentSteps(percents: Set<Float>): FlexSliderSteps =
			remember(percents) { FlexSliderPercentSteps(percents) }
	}
}

private data class FlexSliderAverageSteps(
	private val count: Int,
) : FlexSliderSteps {
	
	override fun calcStepValues(valueRange: FloatRange): List<Float> {
		val steps = valueRange.range / count
		return (0 ..< count).map { valueRange.start + steps * it } + valueRange.endInclusive
	}
}

private data class FlexSliderValuesSteps(
	private val values: Set<Float>,
) : FlexSliderSteps {
	
	override fun calcStepValues(valueRange: FloatRange): List<Float> {
		return (values + valueRange.start + valueRange.endInclusive)
			.distinct()
			.filter { it in valueRange }
			.sorted()
			.toList()
	}
}

private data class FlexSliderPercentSteps(
	private val percents: Set<Float>,
) : FlexSliderSteps {
	
	override fun calcStepValues(valueRange: FloatRange): List<Float> {
		return (percents + 0f + 1f)
			.distinct()
			.filter { it in 0f .. 1f }
			.sorted()
			.map { valueRange.range * it + valueRange.start }
	}
}