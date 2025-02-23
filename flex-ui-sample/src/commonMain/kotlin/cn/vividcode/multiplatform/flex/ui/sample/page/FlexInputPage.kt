package cn.vividcode.multiplatform.flex.ui.sample.page

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import cn.vividcode.multiplatform.flex.ui.foundation.input.FlexInput
import cn.vividcode.multiplatform.flex.ui.foundation.input.FlexInputIcons
import cn.vividcode.multiplatform.flex.ui.foundation.input.FlexInputType
import cn.vividcode.multiplatform.flex.ui.foundation.radio.FlexRadio
import cn.vividcode.multiplatform.flex.ui.foundation.radio.FlexRadioSwitchType
import cn.vividcode.multiplatform.flex.ui.foundation.radio.FlexRadioType
import cn.vividcode.multiplatform.flex.ui.foundation.switch.FlexSwitch
import cn.vividcode.multiplatform.flex.ui.graphics.FlexBrush
import cn.vividcode.multiplatform.flex.ui.sample.brushTypeOptions
import cn.vividcode.multiplatform.flex.ui.sample.components.AdaptiveLayout
import cn.vividcode.multiplatform.flex.ui.sample.components.Code
import cn.vividcode.multiplatform.flex.ui.type.FlexBrushType
import cn.vividcode.multiplatform.flex.ui.type.FlexCornerType
import cn.vividcode.multiplatform.flex.ui.type.FlexSizeType

/**
 * 输入框展示页
 */
@Composable
fun ColumnScope.FlexInputPage() {
	var exampleType by remember { mutableStateOf(ExampleType.Default) }
	var prefixSuffixType by remember { mutableStateOf(PrefixSuffixType.None) }
	var sizeType by remember { mutableStateOf(FlexSizeType.Medium) }
	var brushType by remember { mutableStateOf<FlexBrushType>(FlexBrushType.Primary) }
	var cornerType by remember { mutableStateOf(FlexCornerType.Medium) }
	var inputType by remember { mutableStateOf(FlexInputType.Default) }
	var enabled by remember { mutableStateOf(true) }
	var readOnly by remember { mutableStateOf(false) }
	
	AdaptiveLayout(
		code = {
			val code by remember(exampleType, sizeType, brushType, cornerType, enabled, readOnly, prefixSuffixType) {
				derivedStateOf {
					when (exampleType) {
						ExampleType.Default -> {
							"""
								var value by remember { mutableStateOf("") }
								FlexInput(
									value = value,
									onValueChange = { value = it },
									sizeType = FlexSizeType.$sizeType,
									brushType = FlexBrushType.$brushType,
									cornerType = FlexCornerType.$cornerType,
									inputType = FlexInputType.$inputType,
									enabled = $enabled,
									readOnly = $readOnly,
									placeholder = { Text("Input Text") },
									prefix = ${if (prefixSuffixType.prefix) "{ Text(\"Prefix\") }" else "null"},
									suffix = ${if (prefixSuffixType.suffix) "{ Text(\"Suffix\") }" else "null"}
								)
							""".trimIndent()
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
									brushType = FlexBrushType.$brushType,
									cornerType = FlexCornerType.$cornerType,
									inputType = FlexInputType.$inputType,
									enabled = $enabled,
									readOnly = $readOnly,
									placeholder = { Text("Input Password") },
									leadingIcon = FlexInputIcons.icon(
										icon = Icons.Rounded.Lock
									),
									trailingIcon = FlexInputIcons.icon(
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
									brushType = FlexBrushType.$brushType,
									cornerType = FlexCornerType.$cornerType,
									inputType = FlexInputType.$inputType,
									enabled = $enabled,
									readOnly = $readOnly,
									placeholder = { Text("Search Text") },
									leadingIcon = FlexInputIcons.icon(
										icon = Icons.Rounded.Search
									),
									trailingIcon = if (isEmpty) null else FlexInputIcons.icon(
										icon = Icons.Rounded.Cancel,
										tint = Color.Gray,
										onClick = { value = "" }
									)
								)
							""".trimIndent()
					}
				}
			}
			Code(code)
		},
		preview = {
			var value by remember { mutableStateOf("") }
			when (exampleType) {
				ExampleType.Default -> {
					FlexInput(
						value = value,
						onValueChange = { value = it },
						sizeType = sizeType,
						brushType = brushType,
						cornerType = cornerType,
						inputType = inputType,
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
						brushType = brushType,
						cornerType = cornerType,
						inputType = inputType,
						enabled = enabled,
						readOnly = readOnly,
						placeholder = { Text("Input Password") },
						leadingIcon = FlexInputIcons.icon(
							icon = Icons.Rounded.Lock
						),
						trailingIcon = FlexInputIcons.icon(
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
						brushType = brushType,
						cornerType = cornerType,
						inputType = inputType,
						enabled = enabled,
						readOnly = readOnly,
						placeholder = { Text("Search Text") },
						leadingIcon = FlexInputIcons.icon(
							icon = Icons.Rounded.Search
						),
						trailingIcon = if (isEmpty) null else FlexInputIcons.icon(
							icon = Icons.Rounded.Cancel,
							tint = FlexBrush.Gray,
							onClick = {
								value = ""
							},
						)
					)
				}
			}
		},
		options = {
			item("Example Type") {
				FlexRadio(
					selectedKey = exampleType,
					onSelectedKeyChange = { exampleType = it },
					options = { ExampleType.entries.options() },
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
					options = { FlexCornerType.entries.options() },
					sizeType = FlexSizeType.Small,
					cornerType = cornerType,
					radioType = FlexRadioType.Button,
					switchType = FlexRadioSwitchType.Swipe
				)
			}
			item("Input Type") {
				FlexRadio(
					selectedKey = inputType,
					onSelectedKeyChange = { inputType = it },
					options = { FlexInputType.entries.options() },
					sizeType = FlexSizeType.Small,
					radioType = FlexRadioType.Button,
					switchType = FlexRadioSwitchType.Swipe
				)
			}
			item("Prefix & Suffix") {
				FlexRadio(
					selectedKey = prefixSuffixType,
					onSelectedKeyChange = { prefixSuffixType = it },
					options = { PrefixSuffixType.entries.options() },
					sizeType = FlexSizeType.Small,
					radioType = FlexRadioType.Button,
					switchType = FlexRadioSwitchType.Swipe
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
			item("Read Only") {
				FlexSwitch(
					checked = readOnly,
					onCheckedChange = { readOnly = it },
					sizeType = FlexSizeType.Small,
					brushType = FlexBrushType.Primary
				)
			}
		}
	)
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