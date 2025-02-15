package cn.vividcode.multiplatform.flex.ui.sample.page

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.VerifiedUser
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import cn.vividcode.multiplatform.flex.ui.foundation.button.FlexButton
import cn.vividcode.multiplatform.flex.ui.foundation.button.FlexButtonType
import cn.vividcode.multiplatform.flex.ui.foundation.input.FlexInput
import cn.vividcode.multiplatform.flex.ui.foundation.input.FlexInputIcons
import cn.vividcode.multiplatform.flex.ui.foundation.input.FlexInputType
import cn.vividcode.multiplatform.flex.ui.foundation.radio.FlexRadio
import cn.vividcode.multiplatform.flex.ui.foundation.radio.FlexRadioType
import cn.vividcode.multiplatform.flex.ui.foundation.slider.FlexSlider
import cn.vividcode.multiplatform.flex.ui.foundation.slider.FlexSliderMarks
import cn.vividcode.multiplatform.flex.ui.foundation.switch.FlexSwitch
import cn.vividcode.multiplatform.flex.ui.foundation.switch.FlexSwitchLabels

/**
 * 组件预览页
 */
@Composable
fun PreviewsPage(
	paddingValues: PaddingValues,
) {
	Column(
		modifier = Modifier
			.padding(paddingValues)
			.fillMaxSize()
			.padding(24.dp)
	) {
		Row {
			FlexButton(
				text = "FlexButton",
				buttonType = FlexButtonType.Primary
			)
			Spacer(modifier = Modifier.width(8.dp))
			FlexButton(
				text = "FlexButton Icon",
				icon = Icons.Outlined.Search,
				buttonType = FlexButtonType.Default
			)
			Spacer(modifier = Modifier.width(8.dp))
			FlexButton(
				icon = Icons.Outlined.Search,
				buttonType = FlexButtonType.Dashed
			)
			Spacer(modifier = Modifier.width(8.dp))
			FlexButton(
				text = "FlexButton Filled",
				buttonType = FlexButtonType.Filled
			)
		}
		Spacer(modifier = Modifier.height(16.dp))
		Row {
			var selectedKey1 by remember { mutableStateOf("Option1") }
			var selectedKey2 by remember { mutableStateOf("Option1") }
			var selectedKey3 by remember { mutableStateOf("Option1") }
			FlexRadio(
				selectedKey = selectedKey1,
				onSelectedKeyChange = { selectedKey1 = it },
				options = { listOf("Option1", "Option2", "Option3").options() }
			)
			Spacer(modifier = Modifier.width(8.dp))
			FlexRadio(
				selectedKey = selectedKey2,
				onSelectedKeyChange = { selectedKey2 = it },
				options = {
					listOf("Option1", "Disabled").options(
						enabledTransform = { it == "Option1" }
					)
				}
			)
			Spacer(modifier = Modifier.width(8.dp))
			FlexRadio(
				selectedKey = selectedKey3,
				onSelectedKeyChange = { selectedKey3 = it },
				options = { listOf("Option1", "Option2", "Option3").options() },
				radioType = FlexRadioType.Button
			)
		}
		Spacer(modifier = Modifier.height(16.dp))
		Row {
			var value1 by remember { mutableStateOf("") }
			FlexInput(
				value = value1,
				onValueChange = { value1 = it },
				modifier = Modifier.width(200.dp),
				placeholder = { Text("Username") },
				leadingIcon = FlexInputIcons.icon(
					icon = Icons.Outlined.VerifiedUser,
				)
			)
			Spacer(modifier = Modifier.width(8.dp))
			var value2 by remember { mutableStateOf("123456") }
			FlexInput(
				value = value2,
				onValueChange = { value2 = it },
				modifier = Modifier.width(200.dp),
				placeholder = { Text("Password") },
				leadingIcon = FlexInputIcons.icon(
					icon = Icons.Outlined.Lock,
				),
				visualTransformation = PasswordVisualTransformation(),
			)
			Spacer(modifier = Modifier.width(8.dp))
			var value3 by remember { mutableStateOf("") }
			FlexInput(
				value = value3,
				onValueChange = { value3 = it },
				modifier = Modifier.width(200.dp),
				placeholder = { Text("Outlined") },
				inputType = FlexInputType.Outlined
			)
		}
		Spacer(modifier = Modifier.height(16.dp))
		Row {
			var checked1 by remember { mutableStateOf(false) }
			FlexSwitch(
				checked = checked1,
				onCheckedChange = { checked1 = it },
			)
			Spacer(modifier = Modifier.width(8.dp))
			var checked2 by remember { mutableStateOf(true) }
			FlexSwitch(
				checked = checked2,
				onCheckedChange = { checked2 = it },
			)
			Spacer(modifier = Modifier.width(8.dp))
			var checked3 by remember { mutableStateOf(false) }
			FlexSwitch(
				checked = checked3,
				onCheckedChange = { checked3 = it },
				label = FlexSwitchLabels.DefaultTextLabel
			)
			Spacer(modifier = Modifier.width(8.dp))
			var checked4 by remember { mutableStateOf(true) }
			FlexSwitch(
				checked = checked4,
				onCheckedChange = { checked4 = it },
				label = FlexSwitchLabels.DefaultIconLabel,
				enabled = false
			)
		}
		Spacer(modifier = Modifier.height(16.dp))
		var value1 by remember { mutableStateOf(20f) }
		FlexSlider(
			value = value1,
			onValueChange = { value1 = it }
		)
		Spacer(modifier = Modifier.height(16.dp))
		var value2 by remember { mutableStateOf(60f) }
		FlexSlider(
			value = value2,
			onValueChange = { value2 = it },
			marks = FlexSliderMarks.rememberTextMarks(
				0f to "0%",
				20f to "20%",
				40f to "40%",
				60f to "60%",
				80f to "80%",
				100f to "100%",
			),
			tooltipFormatter = { "${it.toInt()}%" }
		)
	}
}