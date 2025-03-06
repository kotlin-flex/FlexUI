package cn.vividcode.multiplatform.flex.ui.sample.page

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cn.vividcode.multiplatform.flex.ui.common.options
import cn.vividcode.multiplatform.flex.ui.foundation.radio.FlexRadio
import cn.vividcode.multiplatform.flex.ui.foundation.radio.FlexRadioSwitchType
import cn.vividcode.multiplatform.flex.ui.foundation.radio.FlexRadioType
import cn.vividcode.multiplatform.flex.ui.foundation.slider.FlexSlider
import cn.vividcode.multiplatform.flex.ui.foundation.slider.FlexSliderDirection
import cn.vividcode.multiplatform.flex.ui.foundation.slider.FlexSliderMark
import cn.vividcode.multiplatform.flex.ui.foundation.slider.FlexSliderMarks
import cn.vividcode.multiplatform.flex.ui.foundation.slider.FlexSliderSteps
import cn.vividcode.multiplatform.flex.ui.foundation.switch.FlexSwitch
import cn.vividcode.multiplatform.flex.ui.sample.brushTypeOptions
import cn.vividcode.multiplatform.flex.ui.sample.components.AdaptiveLayout
import cn.vividcode.multiplatform.flex.ui.sample.components.Code
import cn.vividcode.multiplatform.flex.ui.type.FlexBrushType
import cn.vividcode.multiplatform.flex.ui.type.FlexCornerType
import cn.vividcode.multiplatform.flex.ui.type.FlexSizeType
import kotlin.math.floor

/**
 * 按钮展示页
 */
@Composable
fun ColumnScope.FlexSliderPage() {
	var sizeType by remember { mutableStateOf(FlexSizeType.Medium) }
	var brushType by remember { mutableStateOf<FlexBrushType>(FlexBrushType.Primary) }
	var cornerType by remember { mutableStateOf(FlexCornerType.Circle) }
	var enabled by remember { mutableStateOf(true) }
	var showTooltip by remember { mutableStateOf(true) }
	var direction by remember { mutableStateOf(FlexSliderDirection.Horizontal) }
	var stepsType by remember { mutableStateOf(FlexSliderStepsType.None) }
	var marksType by remember { mutableStateOf(FlexSliderMarksType.None) }
	
	AdaptiveLayout(
		code = {
			val code by remember(sizeType, brushType, cornerType, direction, stepsType, showTooltip) {
				derivedStateOf {
					val stepsCode = when (stepsType) {
						FlexSliderStepsType.None -> "null"
						FlexSliderStepsType.AverageStep -> "FlexSliderSteps.rememberAverageSteps(4)"
						FlexSliderStepsType.Values -> """
							FlexSliderSteps.rememberValuesSteps(
								values = (0 .. 100 step 25).map { it.toFloat() }.toSet()
							)
						""".trim()
						
						FlexSliderStepsType.Percents -> """
							FlexSliderSteps.rememberPercentSteps(
								percents = (0 .. 100 step 25).map { it / 100f }.toSet()
							)
						""".trim()
					}
					val marksCode = when (marksType) {
						FlexSliderMarksType.None -> "null"
						FlexSliderMarksType.Text -> """
							FlexSliderMarks.rememberTextMarks(
								(0 .. 100 step 25).associate { it.toFloat() to "${'$'}it%" }
							)
						""".trim()
						
						FlexSliderMarksType.Mark -> """
							FlexSliderMarks.rememberMarks(
								(0 .. 100 step 25).map {
									FlexSliderMark(
										value = it.toFloat(),
										text = "${'$'}it%",
										color = if (it == 0 || it == 100) MaterialTheme.colorScheme.error else null,
										weight = if (it == 0 || it == 100) FontWeight.Medium else null,
									)
								}
							)
						""".trim()
					}
					
					val tooltipFormatterCode = when (showTooltip) {
						true -> "{ floor(it * 10) / 10 }"
						false -> "null"
					}
					
					"""
						var value by remember { mutableIntStateOf(0) }
						FlexSlider(
							value = value,
							onValueChange = { value = it },
							sizeType = FlexSizeType.$sizeType,
							brushType = FlexBrushType.$brushType,
							cornerType = FlexCornerType.$cornerType,
							direction = FlexSliderDirection.$direction,
							valueRange = 0f .. 100f,
							steps = $stepsCode,
							marks = $marksCode,
							tooltipFormatter = $tooltipFormatterCode
						)
					""".trimIndent()
				}
			}
			Code(code)
		},
		preview = {
			var value by remember { mutableFloatStateOf(25f) }
			FlexSlider(
				value = value,
				onValueChange = { value = it },
				sizeType = sizeType,
				brushType = brushType,
				cornerType = cornerType,
				direction = direction,
				enabled = enabled,
				valueRange = 0f .. 100f,
				steps = when (stepsType) {
					FlexSliderStepsType.None -> null
					FlexSliderStepsType.AverageStep -> FlexSliderSteps.rememberAverageSteps(4)
					FlexSliderStepsType.Values -> FlexSliderSteps.rememberValuesSteps(
						values = (0 .. 100 step 25).map { it.toFloat() }.toSet()
					)
					
					FlexSliderStepsType.Percents -> FlexSliderSteps.rememberPercentSteps(
						percents = (0 .. 100 step 25).map { it / 100f }.toSet()
					)
				},
				marks = when (marksType) {
					FlexSliderMarksType.None -> null
					FlexSliderMarksType.Text -> FlexSliderMarks.rememberTextMarks(
						(0 .. 100 step 25).associate { it.toFloat() to "$it%" }
					)
					
					FlexSliderMarksType.Mark -> FlexSliderMarks.rememberMarks(
						(0 .. 100 step 25).map {
							FlexSliderMark(
								value = it.toFloat(),
								text = "$it%",
								color = if (it == 0 || it == 100) MaterialTheme.colorScheme.error else null,
								weight = if (it == 0 || it == 100) FontWeight.Medium else null,
							)
						}
					)
				},
				tooltipFormatter = if (showTooltip) {
					{ floor(it * 10) / 10 }
				} else null
			)
		},
		options = {
			item("Size Type") {
				FlexRadio(
					selectedKey = sizeType,
					onSelectedKeyChange = { sizeType = it },
					options = remember { FlexSizeType.entries.options() },
					sizeType = FlexSizeType.Small,
					radioType = FlexRadioType.Button,
					switchType = FlexRadioSwitchType.Swipe
				)
			}
			item("Color Type") {
				FlexRadio(
					selectedKey = brushType,
					onSelectedKeyChange = { brushType = it },
					options = brushTypeOptions,
					sizeType = FlexSizeType.Small,
					brushType = brushType,
					radioType = FlexRadioType.Button,
					switchType = FlexRadioSwitchType.Swipe
				)
			}
			item("Corner Type") {
				FlexRadio(
					selectedKey = cornerType,
					onSelectedKeyChange = { cornerType = it },
					options = remember { FlexCornerType.entries.options() },
					sizeType = FlexSizeType.Small,
					cornerType = cornerType,
					radioType = FlexRadioType.Button,
					switchType = FlexRadioSwitchType.Swipe
				)
			}
			item("Enabled") {
				FlexSwitch(
					checked = enabled,
					onCheckedChange = { enabled = it },
					sizeType = FlexSizeType.Small,
				)
			}
			item("Slider Direction") {
				FlexRadio(
					selectedKey = direction,
					onSelectedKeyChange = { direction = it },
					options = remember { FlexSliderDirection.entries.options() },
					sizeType = FlexSizeType.Small,
					radioType = FlexRadioType.Button,
					switchType = FlexRadioSwitchType.Swipe
				)
			}
			item("Steps Type") {
				FlexRadio(
					selectedKey = stepsType,
					onSelectedKeyChange = { stepsType = it },
					options = remember { FlexSliderStepsType.entries.options() },
					sizeType = FlexSizeType.Small,
					radioType = FlexRadioType.Button,
					switchType = FlexRadioSwitchType.Swipe
				)
			}
			item("Marks Type") {
				FlexRadio(
					selectedKey = marksType,
					onSelectedKeyChange = { marksType = it },
					options = remember { FlexSliderMarksType.entries.options() },
					sizeType = FlexSizeType.Small,
					radioType = FlexRadioType.Button,
					switchType = FlexRadioSwitchType.Swipe
				)
			}
			Spacer(modifier = Modifier.height(12.dp))
			item("Show Tooltip") {
				FlexSwitch(
					checked = showTooltip,
					onCheckedChange = { showTooltip = it },
					sizeType = FlexSizeType.Small,
				)
			}
		}
	)
}

private enum class FlexSliderStepsType {
	
	None,
	
	AverageStep,
	
	Values,
	
	Percents
}

private enum class FlexSliderMarksType {
	
	None,
	
	Text,
	
	Mark
}