package cn.vividcode.multiplatform.flex.ui.sample.page

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.runtime.*
import cn.vividcode.multiplatform.flex.ui.type.FlexBrushType
import cn.vividcode.multiplatform.flex.ui.type.FlexCornerType
import cn.vividcode.multiplatform.flex.ui.type.FlexSizeType
import cn.vividcode.multiplatform.flex.ui.foundation.radio.FlexRadio
import cn.vividcode.multiplatform.flex.ui.foundation.radio.FlexRadioSwitchType
import cn.vividcode.multiplatform.flex.ui.foundation.radio.FlexRadioType
import cn.vividcode.multiplatform.flex.ui.foundation.switch.FlexSwitch
import cn.vividcode.multiplatform.flex.ui.foundation.switch.FlexSwitchLabels
import cn.vividcode.multiplatform.flex.ui.sample.components.AdaptiveLayout
import cn.vividcode.multiplatform.flex.ui.sample.components.Code

/**
 * 按钮展示页
 */
@Composable
fun ColumnScope.FlexSwitchPage() {
	var sizeType by remember { mutableStateOf(FlexSizeType.Medium) }
	var brushType by remember { mutableStateOf<FlexBrushType>(FlexBrushType.Primary) }
	var cornerType by remember { mutableStateOf(FlexCornerType.Circle) }
	var labelType by remember { mutableStateOf(FlexSwitchLabelType.None) }
	var enabled by remember { mutableStateOf(true) }
	
	AdaptiveLayout(
		code = {
			val label = when (labelType) {
				FlexSwitchLabelType.None -> "null"
				FlexSwitchLabelType.OnOff -> "FlexSwitchLabels.DefaultTextLabel"
				FlexSwitchLabelType.OpenClose -> "FlexSwitchLabels.textLabel(\"开\", \"关\")"
				FlexSwitchLabelType.Icon -> "FlexSwitchLabels.DefaultIconLabel"
			}
			val code by remember(sizeType, brushType, cornerType, label, enabled) {
				derivedStateOf {
					"""
						var checked by remember { mutableStateOf(false) }
						FlexSwitch(
							checked = checked,
							onCheckedChange = { checked = it },
							sizeType = FlexSizeType.$sizeType,
							brushType = FlexbrushType.$brushType,
							cornerType = FlexCornerType.$cornerType,
							label = $label,
							enabled = $enabled
						)
					""".trimIndent()
				}
			}
			Code(code)
		},
		preview = {
			var checked by remember { mutableStateOf(false) }
			FlexSwitch(
				checked = checked,
				onCheckedChange = { checked = it },
				sizeType = sizeType,
				brushType = brushType,
				cornerType = cornerType,
				label = when (labelType) {
					FlexSwitchLabelType.None -> null
					FlexSwitchLabelType.OnOff -> FlexSwitchLabels.DefaultTextLabel
					FlexSwitchLabelType.OpenClose -> FlexSwitchLabels.textLabel("开", "关")
					FlexSwitchLabelType.Icon -> FlexSwitchLabels.DefaultIconLabel
				},
				enabled = enabled
			)
		},
		options = {
			item("Size Type") {
				FlexRadio(
					selectedKey = sizeType,
					onSelectedKeyChange = { sizeType = it },
					options = { FlexSizeType.entries.options() },
					sizeType = FlexSizeType.Small,
					radioType = FlexRadioType.Button,
					switchType = FlexRadioSwitchType.Swipe
				)
			}
			item("Color Type") {
				FlexRadio(
					selectedKey = brushType,
					onSelectedKeyChange = { brushType = it },
					options = { FlexBrushType.entries.options() },
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
					options = { FlexCornerType.entries.options() },
					sizeType = FlexSizeType.Small,
					cornerType = cornerType,
					radioType = FlexRadioType.Button,
					switchType = FlexRadioSwitchType.Swipe
				)
			}
			item("Label Type") {
				FlexRadio(
					selectedKey = labelType,
					onSelectedKeyChange = { labelType = it },
					options = { FlexSwitchLabelType.entries.options() },
					sizeType = FlexSizeType.Small,
					radioType = FlexRadioType.Button,
					switchType = FlexRadioSwitchType.Swipe
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

private enum class FlexSwitchLabelType {
	
	None,
	
	OnOff,
	
	OpenClose,
	
	Icon
}