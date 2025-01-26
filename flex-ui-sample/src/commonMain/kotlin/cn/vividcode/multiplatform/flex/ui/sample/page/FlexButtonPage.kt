package cn.vividcode.multiplatform.flex.ui.sample.page

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cn.vividcode.multiplatform.flex.ui.config.type.FlexColorType
import cn.vividcode.multiplatform.flex.ui.config.type.FlexCornerType
import cn.vividcode.multiplatform.flex.ui.config.type.FlexSizeType
import cn.vividcode.multiplatform.flex.ui.foundation.button.FlexButton
import cn.vividcode.multiplatform.flex.ui.foundation.button.FlexButtonIconPosition
import cn.vividcode.multiplatform.flex.ui.foundation.button.FlexButtonType
import cn.vividcode.multiplatform.flex.ui.foundation.radio.FlexRadioGroup
import cn.vividcode.multiplatform.flex.ui.foundation.radio.FlexRadioSwitchType
import cn.vividcode.multiplatform.flex.ui.foundation.radio.FlexRadioType
import cn.vividcode.multiplatform.flex.ui.foundation.radio.RadioOption
import cn.vividcode.multiplatform.flex.ui.layout.TitleLayout
import cn.vividcode.multiplatform.flex.ui.sample.components.*
import cn.vividcode.multiplatform.flex.ui.sample.constant.booleanOptions
import cn.vividcode.multiplatform.flex.ui.sample.constant.colorTypeOptions
import cn.vividcode.multiplatform.flex.ui.sample.constant.cornerTypeOptions
import cn.vividcode.multiplatform.flex.ui.sample.constant.sizeTypeOptions

/**
 * FlexButton 展示页
 */
@Composable
fun ColumnScope.FlexButtonPage() {
	var buttonType by remember { mutableStateOf(FlexButtonType.Default) }
	var sizeType by remember { mutableStateOf(FlexSizeType.Medium) }
	var colorType by remember { mutableStateOf<FlexColorType>(FlexColorType.Default) }
	var cornerType by remember { mutableStateOf(FlexCornerType.Default) }
	var showIcon by remember { mutableStateOf(true) }
	var iconPosition by remember { mutableStateOf(FlexButtonIconPosition.End) }
	var scaleEffect by remember { mutableStateOf(true) }
	var enabled by remember { mutableStateOf(true) }
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
			FlexButton(
				text = "Button",
				icon = if (showIcon) Icons.Rounded.Search else null,
				sizeType = sizeType,
				colorType = colorType,
				cornerType = cornerType,
				buttonType = buttonType,
				iconPosition = iconPosition,
				iconRotation = 0f,
				scaleEffect = scaleEffect,
				enabled = enabled
			)
		}
		val isDefaultColorType by remember {
			derivedStateOf { colorType in FlexColorType.defaultColorTypes }
		}
		Code(
			methodName = "FlexButton",
			assigns = listOf(
				"text" assign "Button",
				"icon" assign if (showIcon) ClassT("Icons") + DotT + ClassT("Rounded") + DotT + ParameterT("Search") else KeywordT("null"),
				"sizeType" assign ClassT("FlexSizeType") + DotT + ClassT(sizeType),
				"colorType" assign if (isDefaultColorType) ClassT("FlexColorType") + DotT + ClassT(colorType) else ClassT(colorType),
				"cornerType" assign ClassT("FlexCornerType") + DotT + ClassT(cornerType),
				"buttonType" assign ClassT("FlexButtonType") + DotT + ClassT(buttonType),
				"iconPosition" assign ClassT("FlexButtonIconPosition") + DotT + ClassT(iconPosition),
				"iconRotation" assign 0f,
				"scaleEffect" assign scaleEffect,
				"enabled" assign enabled,
				"onClick" assign LeftBraceT + SpaceT() + RightBraceT
			)
		)
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
			title = { Text("Button Type") }
		) {
			FlexRadioGroup(
				selectedKey = buttonType,
				onSelectedKeyChange = { buttonType = it },
				options = buttonTypeOptions,
				sizeType = FlexSizeType.Small,
				colorType = FlexColorType.Primary,
				radioType = FlexRadioType.Button,
				switchType = FlexRadioSwitchType.Swipe
			)
		}
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
			title = { Text("Icon Visible") }
		) {
			FlexRadioGroup(
				selectedKey = showIcon,
				onSelectedKeyChange = { showIcon = it },
				options = booleanOptions,
				sizeType = FlexSizeType.Small,
				colorType = FlexColorType.Primary,
				radioType = FlexRadioType.Button,
				switchType = FlexRadioSwitchType.Swipe
			)
		}
		Spacer(modifier = Modifier.height(12.dp))
		TitleLayout(
			title = { Text("Icon Position") }
		) {
			FlexRadioGroup(
				selectedKey = iconPosition,
				onSelectedKeyChange = { iconPosition = it },
				options = iconPositionOptions,
				sizeType = FlexSizeType.Small,
				colorType = FlexColorType.Primary,
				radioType = FlexRadioType.Button,
				switchType = FlexRadioSwitchType.Swipe
			)
		}
		Spacer(modifier = Modifier.height(12.dp))
		TitleLayout(
			title = { Text("Scale Effect") }
		) {
			FlexRadioGroup(
				selectedKey = scaleEffect,
				onSelectedKeyChange = { scaleEffect = it },
				options = booleanOptions,
				sizeType = FlexSizeType.Small,
				colorType = FlexColorType.Primary,
				radioType = FlexRadioType.Button,
				switchType = FlexRadioSwitchType.Swipe
			)
		}
		Spacer(modifier = Modifier.height(12.dp))
		TitleLayout(
			title = { Text("Enabled") }
		) {
			FlexRadioGroup(
				selectedKey = enabled,
				onSelectedKeyChange = { enabled = it },
				options = booleanOptions,
				sizeType = FlexSizeType.Small,
				colorType = FlexColorType.Primary,
				radioType = FlexRadioType.Button,
				switchType = FlexRadioSwitchType.Swipe
			)
		}
	}
}

private val iconPositionOptions = FlexButtonIconPosition.entries.map { RadioOption(it, it.toString()) }

private val buttonTypeOptions = FlexButtonType.entries.map { RadioOption(it, it.toString()) }