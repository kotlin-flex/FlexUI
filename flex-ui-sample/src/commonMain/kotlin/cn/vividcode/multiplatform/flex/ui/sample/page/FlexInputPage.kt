package cn.vividcode.multiplatform.flex.ui.sample.page

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import cn.vividcode.multiplatform.flex.ui.config.type.FlexColorType
import cn.vividcode.multiplatform.flex.ui.config.type.FlexCornerType
import cn.vividcode.multiplatform.flex.ui.config.type.FlexSizeType
import cn.vividcode.multiplatform.flex.ui.foundation.input.FlexInput
import cn.vividcode.multiplatform.flex.ui.foundation.input.FlexInputs
import cn.vividcode.multiplatform.flex.ui.foundation.radio.FlexRadioGroup
import cn.vividcode.multiplatform.flex.ui.foundation.radio.FlexRadioSwitchType
import cn.vividcode.multiplatform.flex.ui.foundation.radio.FlexRadioType
import cn.vividcode.multiplatform.flex.ui.foundation.radio.RadioOption
import cn.vividcode.multiplatform.flex.ui.sample.components.Code2
import cn.vividcode.multiplatform.flex.ui.sample.components.TitleLayout
import cn.vividcode.multiplatform.flex.ui.sample.constant.colorTypeOptions
import cn.vividcode.multiplatform.flex.ui.sample.constant.cornerTypeOptions
import cn.vividcode.multiplatform.flex.ui.sample.constant.sizeTypeOptions
import cn.vividcode.multiplatform.flex.ui.sample.page.FlexInputExample.*
import cn.vividcode.multiplatform.flex.ui.theme.LocalDarkTheme

/**
 * 输入框展示页
 */
@Composable
fun ColumnScope.FlexInputPage() {
	var sizeType by remember { mutableStateOf(FlexSizeType.Medium) }
	var colorType by remember { mutableStateOf<FlexColorType>(FlexColorType.Default) }
	var cornerType by remember { mutableStateOf(FlexCornerType.Default) }
	var inputType by remember { mutableStateOf(Default) }
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
			var value by remember { mutableStateOf("") }
			val isEmpty by remember(value) {
				derivedStateOf { value.isEmpty() }
			}
			var visualTransformation by remember { mutableStateOf(VisualTransformation.None) }
			FlexInput(
				value = value,
				onValueChange = { value = it },
				sizeType = sizeType,
				colorType = colorType,
				cornerType = cornerType,
				placeholder = {
					when (inputType) {
						Default -> {
							Text("Text")
						}
						
						Password -> {
							Text("Password")
						}
						
						Search -> {
							Text("Search Text")
						}
					}
				},
				leadingIcon = when (inputType) {
					Default -> null
					Password -> FlexInputs.icon(
						icon = Icons.Rounded.Lock
					)
					
					Search -> FlexInputs.icon(
						icon = Icons.Rounded.Search
					)
				},
				trailingIcon = when (inputType) {
					Default -> null
					Password -> FlexInputs.icon(
						icon = when (visualTransformation == VisualTransformation.None) {
							true -> Icons.Rounded.Visibility
							false -> Icons.Rounded.VisibilityOff
						},
						onClick = {
							visualTransformation = if (visualTransformation == VisualTransformation.None) {
								PasswordVisualTransformation()
							} else {
								VisualTransformation.None
							}
						}
					)
					
					Search -> if (isEmpty) null else FlexInputs.icon(
						icon = Icons.Rounded.Cancel,
						tint = if (LocalDarkTheme.current) Color.DarkGray else Color.LightGray,
						onClick = {
							value = ""
						},
					)
				},
				visualTransformation = visualTransformation
			)
		}
		Spacer(Modifier.width(12.dp))
		val code = """
			var value by remember { mutableStateOf("") }
			FlexInput(
				value = value,
				onValueChange = { value = it },
				sizeType = sizeType,
				colorType = colorType,
				cornerType = cornerType,
				prefix = {
					FlexIcon(
						icon = Icons.Rounded.Lock
					)
				},
				suffix = {
					FlexIcon(
						icon = Icons.Rounded.Visibility,
					)
				}
			)
			""".trimIndent()
		Code2(code)
	}
	HorizontalDivider()
	val verticalScrollState = rememberScrollState()
	Column(
		modifier = Modifier
			.height(276.dp)
			.padding(4.dp)
			.verticalScroll(verticalScrollState)
			.padding(8.dp)
	) {
		Spacer(modifier = Modifier.height(12.dp))
		TitleLayout(
			title = { Text("Size Type") }
		) {
			FlexRadioGroup(
				selectedKey = sizeType,
				onSelectedKeyChange = { sizeType = it },
				options = sizeTypeOptions,
				sizeType = FlexSizeType.Small,
				colorType = FlexColorType.Primary,
				radioType = FlexRadioType.Button,
				switchType = FlexRadioSwitchType.Swipe
			)
		}
		Spacer(modifier = Modifier.height(12.dp))
		TitleLayout(
			title = { Text("Color Type") }
		) {
			FlexRadioGroup(
				selectedKey = colorType,
				onSelectedKeyChange = { colorType = it },
				options = colorTypeOptions,
				sizeType = FlexSizeType.Small,
				colorType = colorType,
				radioType = FlexRadioType.Button,
				switchType = FlexRadioSwitchType.Swipe
			)
		}
		Spacer(modifier = Modifier.height(12.dp))
		TitleLayout(
			title = { Text("Corner Type") }
		) {
			FlexRadioGroup(
				selectedKey = cornerType,
				onSelectedKeyChange = { cornerType = it },
				options = cornerTypeOptions,
				sizeType = FlexSizeType.Small,
				colorType = FlexColorType.Primary,
				radioType = FlexRadioType.Button,
				switchType = FlexRadioSwitchType.Swipe
			)
		}
		Spacer(modifier = Modifier.height(12.dp))
		TitleLayout(
			title = { Text("Input Example") }
		) {
			FlexRadioGroup(
				selectedKey = inputType,
				onSelectedKeyChange = { inputType = it },
				options = inputExampleOptions,
				sizeType = FlexSizeType.Small,
				colorType = FlexColorType.Primary,
				radioType = FlexRadioType.Button,
				switchType = FlexRadioSwitchType.Swipe
			)
		}
	}
}

private enum class FlexInputExample {
	
	Default,
	
	Password,
	
	Search
}

private val inputExampleOptions = FlexInputExample.entries.map { RadioOption(it, it.toString()) }