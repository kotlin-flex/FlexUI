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
import cn.vividcode.multiplatform.flex.ui.foundation.input.FlexInputDefaults
import cn.vividcode.multiplatform.flex.ui.foundation.radio.FlexRadio
import cn.vividcode.multiplatform.flex.ui.foundation.radio.FlexRadioSwitchType
import cn.vividcode.multiplatform.flex.ui.foundation.radio.FlexRadioType
import cn.vividcode.multiplatform.flex.ui.foundation.radio.RadioOption
import cn.vividcode.multiplatform.flex.ui.sample.components.Code
import cn.vividcode.multiplatform.flex.ui.sample.components.TitleLayout
import cn.vividcode.multiplatform.flex.ui.sample.constant.booleanOptions
import cn.vividcode.multiplatform.flex.ui.sample.constant.colorTypeOptions
import cn.vividcode.multiplatform.flex.ui.sample.constant.cornerTypeOptions
import cn.vividcode.multiplatform.flex.ui.sample.constant.sizeTypeOptions

/**
 * 输入框展示页
 */
@Composable
fun ColumnScope.FlexInputPage() {
	var exampleType by remember { mutableStateOf(ExampleType.Default) }
	var prefixSuffixType by remember { mutableStateOf(PrefixSuffixType.None) }
	var sizeType by remember { mutableStateOf(FlexSizeType.Medium) }
	var colorType by remember { mutableStateOf<FlexColorType>(FlexColorType.OnSurface) }
	var cornerType by remember { mutableStateOf(FlexCornerType.Medium) }
	var enabled by remember { mutableStateOf(true) }
	var readOnly by remember { mutableStateOf(false) }
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
			when (exampleType) {
				ExampleType.Default -> {
					FlexInput(
						value = value,
						onValueChange = { value = it },
						sizeType = sizeType,
						colorType = colorType,
						cornerType = cornerType,
						enabled = enabled,
						readOnly = readOnly,
						placeholder = { Text("Input Text") },
						prefix = if (prefixSuffixType.prefix) ({
							Text("Prefix")
						}) else null,
						suffix = if (prefixSuffixType.suffix) ({
							Text("Suffix")
						}) else null,
					)
				}
				
				ExampleType.Password -> {
					var visualTransformation by remember {
						mutableStateOf<VisualTransformation>(PasswordVisualTransformation())
					}
					FlexInput(
						value = value,
						onValueChange = { value = it },
						sizeType = sizeType,
						colorType = colorType,
						cornerType = cornerType,
						enabled = enabled,
						readOnly = readOnly,
						placeholder = { Text("Input Password") },
						leadingIcon = FlexInputDefaults.icon(
							icon = Icons.Rounded.Lock
						),
						trailingIcon = FlexInputDefaults.icon(
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
						),
						visualTransformation = visualTransformation,
					)
				}
				
				ExampleType.Search -> {
					val isEmpty by remember(value) {
						derivedStateOf { value.isEmpty() }
					}
					FlexInput(
						value = value,
						onValueChange = { value = it },
						sizeType = sizeType,
						colorType = colorType,
						cornerType = cornerType,
						enabled = enabled,
						readOnly = readOnly,
						placeholder = { Text("Search Text") },
						leadingIcon = FlexInputDefaults.icon(
							icon = Icons.Rounded.Search
						),
						trailingIcon = if (isEmpty) null else FlexInputDefaults.icon(
							icon = Icons.Rounded.Cancel,
							tint = Color.Gray,
							onClick = {
								value = ""
							},
						)
					)
				}
			}
		}
		Spacer(Modifier.width(12.dp))
		val code = when (exampleType) {
			ExampleType.Default -> {
				val code = """
					var value by remember { mutableStateOf("") }
					FlexInput(
						value = value,
						onValueChange = { value = it },
						sizeType = FlexSizeType.$sizeType,
						colorType = FlexColorType.$colorType,
						cornerType = FlexCornerType.$cornerType,
						enabled = $enabled,
						readOnly = $readOnly,
						placeholder = { Text("Input Text") },
						prefix = ${if (prefixSuffixType.prefix) "{ Text(\"Prefix\") }" else "null"},
						suffix = ${if (prefixSuffixType.suffix) "{ Text(\"Suffix\") }" else "null"}
					)
				""".trimIndent()
				code
			}
			
			ExampleType.Password ->
				"""
					var value by remember { mutableStateOf("") }
					var visualTransformation by remember {
						mutableStateOf<VisualTransformation>(PasswordVisualTransformation())
					}
					FlexInput(
						value = value,
						onValueChange = { value = it },
						sizeType = FlexSizeType.$sizeType,
						colorType = FlexColorType.$colorType,
						cornerType = FlexCornerType.$cornerType,
						enabled = $enabled,
						readOnly = $readOnly,
						placeholder = { Text("Input Password") },
						leadingIcon = FlexInputs.icon(
							icon = Icons.Rounded.Lock
						),
						trailingIcon = FlexInputs.icon(
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
						),
						visualTransformation = visualTransformation,
					)
				""".trimIndent()
			
			ExampleType.Search ->
				"""
					var value by remember { mutableStateOf("") }
					val isEmpty by remember(value) {
						derivedStateOf { value.isEmpty() }
					}
					FlexInput(
						value = value,
						onValueChange = { value = it },
						sizeType = FlexSizeType.$sizeType,
						colorType = FlexColorType.$colorType,
						cornerType = FlexCornerType.$cornerType,
						enabled = $enabled,
						readOnly = $readOnly,
						placeholder = { Text("Search Text") },
						leadingIcon = FlexInputs.icon(
							icon = Icons.Rounded.Search
						),
						trailingIcon = if (isEmpty) null else FlexInputs.icon(
							icon = Icons.Rounded.Cancel,
							tint = Color.Gray,
							onClick = { value = "" }
						)
					)
				""".trimIndent()
		}
		Code(code)
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
		TitleLayout(
			title = "Example Type"
		) {
			FlexRadio(
				selectedKey = exampleType,
				onSelectedKeyChange = { exampleType = it },
				options = exampleTypeOptions,
				sizeType = FlexSizeType.Small,
				colorType = FlexColorType.Primary,
				radioType = FlexRadioType.Button,
				switchType = FlexRadioSwitchType.Swipe
			)
		}
		Spacer(modifier = Modifier.height(12.dp))
		TitleLayout(
			title = "Size Type"
		) {
			FlexRadio(
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
			title = "Color type"
		) {
			FlexRadio(
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
			title = "Corner Type"
		) {
			FlexRadio(
				selectedKey = cornerType,
				onSelectedKeyChange = { cornerType = it },
				options = cornerTypeOptions,
				sizeType = FlexSizeType.Small,
				colorType = FlexColorType.Primary,
				cornerType = cornerType,
				radioType = FlexRadioType.Button,
				switchType = FlexRadioSwitchType.Swipe
			)
		}
		Spacer(modifier = Modifier.height(12.dp))
		TitleLayout(
			title = "Show Prefix And Suffix"
		) {
			FlexRadio(
				selectedKey = prefixSuffixType,
				onSelectedKeyChange = { prefixSuffixType = it },
				options = prefixSuffixTypeOptions,
				sizeType = FlexSizeType.Small,
				colorType = FlexColorType.Primary,
				radioType = FlexRadioType.Button,
				switchType = FlexRadioSwitchType.Swipe
			)
		}
		Spacer(modifier = Modifier.height(12.dp))
		TitleLayout(
			title = "Enabled"
		) {
			FlexRadio(
				selectedKey = enabled,
				onSelectedKeyChange = { enabled = it },
				options = booleanOptions,
				sizeType = FlexSizeType.Small,
				colorType = FlexColorType.Primary,
				radioType = FlexRadioType.Button,
				switchType = FlexRadioSwitchType.Swipe
			)
		}
		Spacer(modifier = Modifier.height(12.dp))
		TitleLayout(
			title = "Read Only"
		) {
			FlexRadio(
				selectedKey = readOnly,
				onSelectedKeyChange = { readOnly = it },
				options = booleanOptions,
				sizeType = FlexSizeType.Small,
				colorType = FlexColorType.Primary,
				radioType = FlexRadioType.Button,
				switchType = FlexRadioSwitchType.Swipe
			)
		}
	}
}

private enum class ExampleType {
	
	Default,
	
	Password,
	
	Search
}

private enum class PrefixSuffixType(
	val prefix: Boolean,
	val suffix: Boolean,
) {
	
	None(prefix = false, suffix = false),
	
	Prefix(prefix = true, suffix = false),
	
	Suffix(prefix = false, suffix = true),
	
	Both(prefix = true, suffix = true),
}

private val exampleTypeOptions = ExampleType.entries.map { RadioOption(it, it.toString()) }

private val prefixSuffixTypeOptions = PrefixSuffixType.entries.map { RadioOption(it, it.toString()) }