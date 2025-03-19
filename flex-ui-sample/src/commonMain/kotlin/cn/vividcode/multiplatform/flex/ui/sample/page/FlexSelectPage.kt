package cn.vividcode.multiplatform.flex.ui.sample.page

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cn.vividcode.multiplatform.flex.ui.common.FlexOption
import cn.vividcode.multiplatform.flex.ui.common.options
import cn.vividcode.multiplatform.flex.ui.foundation.radio.FlexRadio
import cn.vividcode.multiplatform.flex.ui.foundation.radio.FlexRadioSwitchType
import cn.vividcode.multiplatform.flex.ui.foundation.radio.FlexRadioType
import cn.vividcode.multiplatform.flex.ui.foundation.select.FlexSelect
import cn.vividcode.multiplatform.flex.ui.foundation.select.FlexSelectType
import cn.vividcode.multiplatform.flex.ui.foundation.slider.FlexSlider
import cn.vividcode.multiplatform.flex.ui.foundation.slider.FlexSliderMarks
import cn.vividcode.multiplatform.flex.ui.foundation.switch.FlexSwitch
import cn.vividcode.multiplatform.flex.ui.sample.brushTypeOptions
import cn.vividcode.multiplatform.flex.ui.sample.components.AdaptiveLayout
import cn.vividcode.multiplatform.flex.ui.sample.components.Code
import cn.vividcode.multiplatform.flex.ui.type.FlexBrushType
import cn.vividcode.multiplatform.flex.ui.type.FlexCornerType
import cn.vividcode.multiplatform.flex.ui.type.FlexSizeType

@Composable
fun ColumnScope.FlexSelectPage() {
	var sizeType by remember { mutableStateOf(FlexSizeType.Medium) }
	var cornerType by remember { mutableStateOf(FlexCornerType.Medium) }
	var brushType by remember { mutableStateOf<FlexBrushType>(FlexBrushType.Primary) }
	var selectType by remember { mutableStateOf(FlexSelectType.Default) }
	var quantityType by remember { mutableStateOf(FlexSelectQuantityType.Single) }
	var selectWidth by remember { mutableStateOf(180.dp) }
	var enabled by remember { mutableStateOf(true) }
	AdaptiveLayout(
		code = {
			val code by remember(sizeType, brushType, cornerType) {
				derivedStateOf {
					"""
						var selectedKey by remember { mutableStateOf("Option 1") }
						FlexSelect(
							selectedKey = selectedKey,
							onSelectedKeyChanged = { selectedKey = it },
							options = remember { listOf("Option 1", "Option 2", "Option 3").options() },
							sizeType = FlexSizeType.$sizeType,
							brushType = FlexBrushType.$brushType,
							cornerType = FlexCornerType.$cornerType,
							selectType = FlexSelectType.$selectType,
							enabled = $enabled
						)
					""".trimIndent()
				}
			}
			Code(code)
		},
		preview = {
			if (quantityType == FlexSelectQuantityType.Single) {
				var selectedKey by remember { mutableStateOf<Int?>(null) }
				FlexSelect(
					selectedKey = selectedKey,
					onSelectedKeyChanged = { selectedKey = it },
					options = remember {
						(1 .. 10).options { FlexOption(it, "Option $it") }
					},
					modifier = Modifier.width(selectWidth),
					sizeType = sizeType,
					brushType = brushType,
					cornerType = cornerType,
					selectType = selectType,
					enabled = enabled
				)
			} else {
				var selectedKeys by remember { mutableStateOf(listOf<Int>()) }
				FlexSelect(
					selectedKeys = selectedKeys,
					onSelectedKeysChanged = { selectedKeys = it },
					options = remember {
						(1 .. 10).options { FlexOption(it, "Option $it") }
					},
					modifier = Modifier.width(selectWidth),
					sizeType = sizeType,
					brushType = brushType,
					cornerType = cornerType,
					selectType = selectType,
					enabled = enabled
				)
			}
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
			item("Corner Type") {
				FlexRadio(
					selectedKey = cornerType,
					onSelectedKeyChange = { cornerType = it },
					options = remember { FlexCornerType.entries.options() },
					sizeType = FlexSizeType.Small,
					radioType = FlexRadioType.Button,
					cornerType = cornerType,
					switchType = FlexRadioSwitchType.Swipe
				)
			}
			item("Brush Type") {
				FlexRadio(
					selectedKey = brushType,
					onSelectedKeyChange = { brushType = it },
					options = brushTypeOptions,
					sizeType = FlexSizeType.Small,
					radioType = FlexRadioType.Button,
					switchType = FlexRadioSwitchType.Swipe
				)
			}
			item("Select Type") {
				FlexRadio(
					selectedKey = selectType,
					onSelectedKeyChange = { selectType = it },
					options = remember { FlexSelectType.entries.options() },
					sizeType = FlexSizeType.Small,
					radioType = FlexRadioType.Button,
					switchType = FlexRadioSwitchType.Swipe
				)
			}
			item("Single Or Multiple") {
				FlexRadio(
					selectedKey = quantityType,
					onSelectedKeyChange = { quantityType = it },
					options = remember { FlexSelectQuantityType.entries.options() },
					sizeType = FlexSizeType.Small,
					radioType = FlexRadioType.Button,
					switchType = FlexRadioSwitchType.Swipe
				)
			}
			item("Select Width") {
				FlexSlider(
					value = selectWidth.value,
					onValueChange = { selectWidth = it.dp },
					modifier = Modifier.width(300.dp),
					sizeType = FlexSizeType.Small,
					valueRange = 180f .. 400f,
					marks = FlexSliderMarks.rememberTextMarks(
						180f to "min",
						400f to "max"
					)
				)
			}
			item("Enabled") {
				FlexSwitch(
					checked = enabled,
					onCheckedChange = { enabled = it },
					sizeType = FlexSizeType.Small
				)
			}
		}
	)
}

private enum class FlexSelectQuantityType {
	
	Single,
	
	Multiple
}