package cn.vividcode.multiplatform.flex.ui.sample.page

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cn.vividcode.multiplatform.flex.ui.config.type.FlexColorType
import cn.vividcode.multiplatform.flex.ui.config.type.FlexCornerType
import cn.vividcode.multiplatform.flex.ui.config.type.FlexSizeType
import cn.vividcode.multiplatform.flex.ui.foundation.radio.FlexRadio
import cn.vividcode.multiplatform.flex.ui.foundation.radio.FlexRadioSwitchType
import cn.vividcode.multiplatform.flex.ui.foundation.radio.FlexRadioType
import cn.vividcode.multiplatform.flex.ui.foundation.radio.toRadioOptions
import cn.vividcode.multiplatform.flex.ui.foundation.slider.*
import cn.vividcode.multiplatform.flex.ui.foundation.switch.FlexSwitch
import cn.vividcode.multiplatform.flex.ui.sample.components.Code
import cn.vividcode.multiplatform.flex.ui.sample.components.TitleLayout
import kotlin.math.floor

/**
 * 按钮展示页
 */
@Composable
fun ColumnScope.FlexSliderPage() {
	var sizeType by remember { mutableStateOf(FlexSizeType.Medium) }
	var colorType by remember { mutableStateOf<FlexColorType>(FlexColorType.Primary) }
	var cornerType by remember { mutableStateOf(FlexCornerType.Circle) }
	var direction by remember { mutableStateOf(FlexSliderDirection.Horizontal) }
	var stepsType by remember { mutableStateOf(FlexSliderStepsType.None) }
	var marksType by remember { mutableStateOf(FlexSliderMarksType.None) }
	var showTooltip by remember { mutableStateOf(false) }
	var tooltipPosition by remember { mutableStateOf(FlexSliderTooltipPosition.TopSide) }
	Row(
		modifier = Modifier
			.fillMaxWidth()
			.weight(1f)
			.padding(12.dp),
	) {
		Box(
			modifier = Modifier
				.weight(1f)
				.fillMaxHeight(),
			contentAlignment = Alignment.Center
		) {
			var value by remember { mutableFloatStateOf(20f) }
			FlexSlider(
				value = value,
				onValueChange = { value = it },
				sizeType = sizeType,
				colorType = colorType,
				cornerType = cornerType,
				direction = direction,
				valueRange = 0f .. 100f,
				steps = when (stepsType) {
					FlexSliderStepsType.None -> null
					FlexSliderStepsType.AverageStep -> FlexSliderSteps.rememberAverageSteps(100)
					FlexSliderStepsType.Values -> FlexSliderSteps.rememberValuesSteps(
						values = (0 .. 100 step 20).map { it.toFloat() }.toSet()
					)
					
					FlexSliderStepsType.Percents -> FlexSliderSteps.rememberPercentSteps(
						percents = (0 .. 100 step 20).map { it / 100f }.toSet()
					)
				},
				marks = when (marksType) {
					FlexSliderMarksType.None -> null
					FlexSliderMarksType.Text -> FlexSliderMarks.rememberTextMarks(
						0f to "0%",
						50f to "50%",
						100f to "100%",
					)
					
					FlexSliderMarksType.Mark -> FlexSliderMarks.rememberMarks(
						FlexSliderMark(
							value = 0f,
							text = "0%",
							color = MaterialTheme.colorScheme.error,
							weight = FontWeight.Bold
						),
						FlexSliderMark(
							value = 50f,
							text = "50%"
						),
						FlexSliderMark(
							value = 100f,
							text = "100%",
							color = MaterialTheme.colorScheme.error,
							weight = FontWeight.Bold
						)
					)
				},
				tooltipPosition = tooltipPosition,
				tooltipFormatter = if (showTooltip) {
					{ floor(it * 10) / 10 }
				} else null
			)
		}
		Spacer(Modifier.width(12.dp))
		val code by remember(sizeType, colorType, cornerType, direction, stepsType, tooltipPosition, showTooltip) {
			derivedStateOf {
				val stepsCode = when (stepsType) {
					FlexSliderStepsType.None -> "null"
					FlexSliderStepsType.AverageStep -> "FlexSliderSteps.rememberAverageSteps(100)"
					FlexSliderStepsType.Values -> "FlexSliderSteps.rememberValuesSteps(0f, 20f, 40f, 60f, 80f, 100f)"
					FlexSliderStepsType.Percents -> "FlexSliderSteps.rememberPercentSteps(0f, 0.2f, 0.4f, 0.6f, 0.8f 1f)"
				}
				val marksCode = when (marksType) {
					FlexSliderMarksType.None -> "null"
					FlexSliderMarksType.Text -> """
						FlexSliderMarks.rememberTextMarks(
							0f to "0%",
							50f to "50%",
							100f to "100%"
						)
					""".trim()
					
					FlexSliderMarksType.Mark -> """
						FlexSliderMarks.rememberMarks(
							FlexSliderMark(
								value = 0f,
								text = "0%",
								color = MaterialTheme.colorScheme.error,
								weight = FontWeight.Bold
							),
							FlexSliderMark(
								value = 50f,
								text = "50%"
							),
							FlexSliderMark(
								value = 100f,
								text = "100%",
								color = MaterialTheme.colorScheme.error,
								weight = FontWeight.Bold
							)
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
						colorType = FlexColorType.$colorType,
						cornerType = FlexCornerType.$cornerType,
						direction = FlexSliderDirection.$direction,
						valueRange = 0f .. 100f,
						steps = $stepsCode,
						marks = $marksCode,
						tooltipPosition = FlexSliderTooltipPosition.$tooltipPosition,
						tooltipFormatter = $tooltipFormatterCode
					)
				""".trimIndent()
			}
		}
		Code(code)
	}
	HorizontalDivider()
	val verticalScrollState = rememberScrollState()
	Column(
		modifier = Modifier
			.height(252.dp)
			.padding(4.dp)
			.verticalScroll(verticalScrollState)
			.padding(8.dp)
	) {
		TitleLayout(
			title = "Size Type"
		) {
			FlexRadio(
				selectedKey = sizeType,
				onSelectedKeyChange = { sizeType = it },
				options = FlexSizeType.entries.toRadioOptions(),
				sizeType = FlexSizeType.Small,
				radioType = FlexRadioType.Button,
				switchType = FlexRadioSwitchType.Swipe
			)
		}
		Spacer(modifier = Modifier.height(12.dp))
		TitleLayout(
			title = "Color Type"
		) {
			FlexRadio(
				selectedKey = colorType,
				onSelectedKeyChange = { colorType = it },
				options = FlexColorType.entries.toRadioOptions(),
				sizeType = FlexSizeType.Small,
				colorType = colorType,
				radioType = FlexRadioType.Button,
				switchType = FlexRadioSwitchType.Swipe
			)
		}
		Spacer(modifier = Modifier.height(12.dp))
		TitleLayout(
			title = "Corner Type"
		) {
			FlexRadio(
				selectedKey = cornerType,
				onSelectedKeyChange = { cornerType = it },
				options = FlexCornerType.entries.toRadioOptions(),
				sizeType = FlexSizeType.Small,
				cornerType = cornerType,
				radioType = FlexRadioType.Button,
				switchType = FlexRadioSwitchType.Swipe
			)
		}
		Spacer(modifier = Modifier.height(12.dp))
		TitleLayout(
			title = "Slider Direction"
		) {
			FlexRadio(
				selectedKey = direction,
				onSelectedKeyChange = { direction = it },
				options = FlexSliderDirection.entries.toRadioOptions(),
				sizeType = FlexSizeType.Small,
				radioType = FlexRadioType.Button,
				switchType = FlexRadioSwitchType.Swipe
			)
		}
		Spacer(modifier = Modifier.height(12.dp))
		TitleLayout(
			title = "Steps Type"
		) {
			FlexRadio(
				selectedKey = stepsType,
				onSelectedKeyChange = { stepsType = it },
				options = FlexSliderStepsType.entries.toRadioOptions(),
				sizeType = FlexSizeType.Small,
				radioType = FlexRadioType.Button,
				switchType = FlexRadioSwitchType.Swipe
			)
		}
		Spacer(modifier = Modifier.height(12.dp))
		TitleLayout(
			title = "Marks Type"
		) {
			FlexRadio(
				selectedKey = marksType,
				onSelectedKeyChange = { marksType = it },
				options = FlexSliderMarksType.entries.toRadioOptions(),
				sizeType = FlexSizeType.Small,
				radioType = FlexRadioType.Button,
				switchType = FlexRadioSwitchType.Swipe
			)
		}
		Spacer(modifier = Modifier.height(12.dp))
		TitleLayout(
			title = "Show Tooltip"
		) {
			FlexSwitch(
				checked = showTooltip,
				onCheckedChange = { showTooltip = it },
				sizeType = FlexSizeType.Small,
			)
		}
		Spacer(modifier = Modifier.height(12.dp))
		TitleLayout(
			title = "Tooltip Position"
		) {
			FlexRadio(
				selectedKey = tooltipPosition,
				onSelectedKeyChange = { tooltipPosition = it },
				options = FlexSliderTooltipPosition.entries.toRadioOptions(),
				sizeType = FlexSizeType.Small,
				radioType = FlexRadioType.Button,
				switchType = FlexRadioSwitchType.Swipe
			)
		}
	}
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