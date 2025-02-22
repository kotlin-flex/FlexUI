package cn.vividcode.multiplatform.flex.ui.sample.page

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import cn.vividcode.multiplatform.flex.ui.config.type.FlexBrushType
import cn.vividcode.multiplatform.flex.ui.config.type.FlexCornerType
import cn.vividcode.multiplatform.flex.ui.config.type.FlexSizeType
import cn.vividcode.multiplatform.flex.ui.foundation.button.FlexButton
import cn.vividcode.multiplatform.flex.ui.foundation.button.FlexButtonIconPosition
import cn.vividcode.multiplatform.flex.ui.foundation.button.FlexButtonType
import cn.vividcode.multiplatform.flex.ui.foundation.input.FlexInput
import cn.vividcode.multiplatform.flex.ui.foundation.radio.FlexRadio
import cn.vividcode.multiplatform.flex.ui.foundation.radio.FlexRadioSwitchType
import cn.vividcode.multiplatform.flex.ui.foundation.radio.FlexRadioType
import cn.vividcode.multiplatform.flex.ui.foundation.switch.FlexSwitch
import cn.vividcode.multiplatform.flex.ui.sample.components.AdaptiveLayout
import cn.vividcode.multiplatform.flex.ui.sample.components.Code

/**
 * 按钮展示页
 */
@Composable
fun ColumnScope.FlexButtonPage() {
	var buttonText by remember { mutableStateOf("FlexButton") }
	var buttonType by remember { mutableStateOf(FlexButtonType.Default) }
	var sizeType by remember { mutableStateOf(FlexSizeType.Medium) }
	var brushType by remember { mutableStateOf<FlexBrushType>(FlexBrushType.Primary) }
	var cornerType by remember { mutableStateOf(FlexCornerType.Medium) }
	var iconType by remember { mutableStateOf(IconType.None) }
	var iconPosition by remember { mutableStateOf(FlexButtonIconPosition.End) }
	var scaleEffect by remember { mutableStateOf(false) }
	var enabled by remember { mutableStateOf(true) }
	
	AdaptiveLayout(
		code = {
			val code by remember(buttonText, iconType, sizeType, brushType, cornerType, buttonType, iconPosition, scaleEffect, enabled) {
				derivedStateOf {
					"""
						FlexButton(
							text = "$buttonText",
							icon = ${iconType.code},
							sizeType = FlexSizeType.$sizeType,
							brushType = FlexbrushType.$brushType,
							cornerType = FlexCornerType.$cornerType,
							buttonType = FlexButtonType.$buttonType,
							iconPosition = FlexButtonIconPosition.$iconPosition,
							iconRotation = 0f,
							scaleEffect = $scaleEffect,
							enabled = $enabled
						)
					""".trimIndent()
				}
			}
			Code(code)
		},
		preview = {
			FlexButton(
				text = buttonText,
				icon = iconType.icon,
				sizeType = sizeType,
				brushType = brushType,
				cornerType = cornerType,
				buttonType = buttonType,
				iconPosition = iconPosition,
				iconRotation = 0f,
				scaleEffect = scaleEffect,
				enabled = enabled
			)
		},
		options = {
			item("Button Text") {
				FlexInput(
					value = buttonText,
					onValueChange = { buttonText = it },
					modifier = Modifier.width(220.dp),
					sizeType = FlexSizeType.Small,
					brushType = brushType,
					placeholder = { Text("Button Text") }
				)
			}
			item("Button Type") {
				FlexRadio(
					selectedKey = buttonType,
					onSelectedKeyChange = { buttonType = it },
					options = { FlexButtonType.entries.options() },
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
			item("Icon") {
				FlexRadio(
					selectedKey = iconType,
					onSelectedKeyChange = { iconType = it },
					options = { IconType.entries.options() },
					sizeType = FlexSizeType.Small,
					radioType = FlexRadioType.Button,
					switchType = FlexRadioSwitchType.Swipe
				)
			}
			item("Icon Position") {
				FlexRadio(
					selectedKey = iconPosition,
					onSelectedKeyChange = { iconPosition = it },
					options = { FlexButtonIconPosition.entries.options() },
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
					brushType = FlexBrushType.Primary
				)
			}
			item("Enabled") {
				FlexSwitch(
					checked = enabled,
					onCheckedChange = { enabled = it },
					sizeType = FlexSizeType.Small,
					brushType = FlexBrushType.Primary
				)
			}
		}
	)
}

private enum class IconType(
	val icon: ImageVector?,
	val code: String,
) {
	
	Search(
		icon = Icons.Rounded.Search,
		code = "Icons.Rounded.Search"
	),
	
	Add(
		icon = Icons.Rounded.Add,
		code = "Icons.Rounded.Add"
	),
	
	KeyboardArrowRight(
		icon = Icons.AutoMirrored.Rounded.KeyboardArrowRight,
		code = "Icons.AutoMirrored.Rounded.KeyboardArrowRight"
	),
	
	None(
		icon = null,
		code = "null"
	)
}