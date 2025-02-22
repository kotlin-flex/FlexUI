package cn.vividcode.multiplatform.flex.ui.sample.page

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.runtime.*
import cn.vividcode.multiplatform.flex.ui.type.FlexBrushType
import cn.vividcode.multiplatform.flex.ui.type.FlexCornerType
import cn.vividcode.multiplatform.flex.ui.type.FlexSizeType
import cn.vividcode.multiplatform.flex.ui.foundation.radio.FlexRadio
import cn.vividcode.multiplatform.flex.ui.foundation.radio.FlexRadioSwitchType
import cn.vividcode.multiplatform.flex.ui.foundation.radio.FlexRadioType
import cn.vividcode.multiplatform.flex.ui.foundation.radio.RadioOption
import cn.vividcode.multiplatform.flex.ui.foundation.switch.FlexSwitch
import cn.vividcode.multiplatform.flex.ui.sample.components.AdaptiveLayout
import cn.vividcode.multiplatform.flex.ui.sample.components.Code

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
						var selectedKey by remember { mutableStateOf("option1") }
						FlexRadio(
							selectedKey = selectedKey,
							onSelectedKeyChange = { selectedKey = it },
							options = {
								(1 .. 3).map {
									RadioOption("option${'$'}it", "Option ${'$'}it", it != $disabledOption)
								}
							}
							sizeType = FlexSizeType.$sizeType,
							brushType = FlexbrushType.$brushType,
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
			var selectedKey by remember { mutableStateOf("option1") }
			FlexRadio(
				selectedKey = selectedKey,
				onSelectedKeyChange = { selectedKey = it },
				options = {
					(1 .. 3).map {
						RadioOption("option$it", "Option $it", it != disabledOption)
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
					options = { FlexRadioType.entries.options() },
					sizeType = FlexSizeType.Small,
					radioType = FlexRadioType.Button,
					switchType = FlexRadioSwitchType.Swipe
				)
			}
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
			item("Switch Type") {
				FlexRadio(
					selectedKey = switchType,
					onSelectedKeyChange = { switchType = it },
					options = { FlexRadioSwitchType.entries.options() },
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
					options = {
						(1 .. 4).options {
							RadioOption(it, if (it < 4) "Option $it" else "None")
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