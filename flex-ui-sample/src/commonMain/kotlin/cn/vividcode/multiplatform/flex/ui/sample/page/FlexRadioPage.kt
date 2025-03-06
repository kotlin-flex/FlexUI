package cn.vividcode.multiplatform.flex.ui.sample.page

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import cn.vividcode.multiplatform.flex.ui.common.FlexOption
import cn.vividcode.multiplatform.flex.ui.common.options
import cn.vividcode.multiplatform.flex.ui.foundation.radio.FlexRadio
import cn.vividcode.multiplatform.flex.ui.foundation.radio.FlexRadioSwitchType
import cn.vividcode.multiplatform.flex.ui.foundation.radio.FlexRadioType
import cn.vividcode.multiplatform.flex.ui.foundation.switch.FlexSwitch
import cn.vividcode.multiplatform.flex.ui.sample.brushTypeOptions
import cn.vividcode.multiplatform.flex.ui.sample.components.AdaptiveLayout
import cn.vividcode.multiplatform.flex.ui.sample.components.Code
import cn.vividcode.multiplatform.flex.ui.type.FlexBrushType
import cn.vividcode.multiplatform.flex.ui.type.FlexCornerType
import cn.vividcode.multiplatform.flex.ui.type.FlexSizeType

/**
 * 单选框展示页
 */
@Composable
fun ColumnScope.FlexRadioPage() {
	var radioType by remember { mutableStateOf(FlexRadioType.Default) }
	var sizeType by remember { mutableStateOf(FlexSizeType.Medium) }
	var brushType by remember { mutableStateOf<FlexBrushType>(FlexBrushType.Primary) }
	var cornerType by remember { mutableStateOf(FlexCornerType.Medium) }
	var switchType by remember { mutableStateOf(FlexRadioSwitchType.None) }
	var scaleEffect by remember { mutableStateOf(true) }
	var disabledOption by remember { mutableStateOf(4) }
	
	AdaptiveLayout(
		code = {
			val code by remember(sizeType, brushType, cornerType, radioType, switchType, scaleEffect, disabledOption) {
				derivedStateOf {
					"""
						var selectedKey by remember { mutableStateOf(1) }
						FlexRadio(
							selectedKey = selectedKey,
							onSelectedKeyChange = { selectedKey = it },
							options = remember {
								(1 .. 3).options {
									FlexOption(it, "Option ${'$'}it", it != $disabledOption)
								},
							},
							sizeType = FlexSizeType.$sizeType,
							brushType = FlexBrushType.$brushType,
							cornerType = FlexCornerType.$cornerType,
							radioType = FlexRadioType.$radioType,
							switchType = FlexRadioSwitchType.$switchType,
							scaleEffect = $scaleEffect
						)
					""".trimIndent()
				}
			}
			Code(code)
		},
		preview = {
			var selectedKey by remember { mutableStateOf(1) }
			FlexRadio(
				selectedKey = selectedKey,
				onSelectedKeyChange = { selectedKey = it },
				options = remember(disabledOption) {
					(1 .. 3).options {
						FlexOption(it, "Option $it", it != disabledOption)
					}
				},
				sizeType = sizeType,
				brushType = brushType,
				cornerType = cornerType,
				radioType = radioType,
				switchType = switchType,
				scaleEffect = scaleEffect,
			)
		},
		options = {
			item("Radio Type") {
				FlexRadio(
					selectedKey = radioType,
					onSelectedKeyChange = { radioType = it },
					options = remember { FlexRadioType.entries.options() },
					sizeType = FlexSizeType.Small,
					radioType = FlexRadioType.Button,
					switchType = FlexRadioSwitchType.Swipe
				)
			}
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
			item("Switch Type") {
				FlexRadio(
					selectedKey = switchType,
					onSelectedKeyChange = { switchType = it },
					options = remember { FlexRadioSwitchType.entries.options() },
					sizeType = FlexSizeType.Small,
					radioType = FlexRadioType.Button,
					switchType = FlexRadioSwitchType.Swipe
				)
			}
			item("Scale Effect") {
				FlexSwitch(
					checked = scaleEffect,
					onCheckedChange = { scaleEffect = it },
					sizeType = FlexSizeType.Small,
				)
			}
			item("Option Disabled") {
				FlexRadio(
					selectedKey = disabledOption,
					onSelectedKeyChange = { disabledOption = it },
					options = remember {
						(1 .. 4).options {
							FlexOption(it, if (it < 4) "Option $it" else "None")
						}
					},
					sizeType = FlexSizeType.Small,
					radioType = FlexRadioType.Button,
					switchType = FlexRadioSwitchType.Swipe
				)
			}
		}
	)
}