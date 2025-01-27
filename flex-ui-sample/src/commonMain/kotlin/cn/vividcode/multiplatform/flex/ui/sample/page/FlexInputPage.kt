package cn.vividcode.multiplatform.flex.ui.sample.page

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cn.vividcode.multiplatform.flex.ui.config.type.FlexColorType
import cn.vividcode.multiplatform.flex.ui.config.type.FlexCornerType
import cn.vividcode.multiplatform.flex.ui.config.type.FlexSizeType
import cn.vividcode.multiplatform.flex.ui.foundation.input.FlexInput
import cn.vividcode.multiplatform.flex.ui.foundation.radio.FlexRadioGroup
import cn.vividcode.multiplatform.flex.ui.foundation.radio.FlexRadioSwitchType
import cn.vividcode.multiplatform.flex.ui.foundation.radio.FlexRadioType
import cn.vividcode.multiplatform.flex.ui.layout.TitleLayout
import cn.vividcode.multiplatform.flex.ui.sample.components.*
import cn.vividcode.multiplatform.flex.ui.sample.constant.colorTypeOptions
import cn.vividcode.multiplatform.flex.ui.sample.constant.cornerTypeOptions
import cn.vividcode.multiplatform.flex.ui.sample.constant.sizeTypeOptions

/**
 * 输入框展示页
 */
@Composable
fun ColumnScope.FlexInputPage() {
	var sizeType by remember { mutableStateOf(FlexSizeType.Medium) }
	var colorType by remember { mutableStateOf<FlexColorType>(FlexColorType.Default) }
	var cornerType by remember { mutableStateOf(FlexCornerType.Default) }
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
			FlexInput(
				value = value,
				onValueChange = { value = it },
				sizeType = sizeType,
				colorType = colorType,
				cornerType = cornerType
			)
		}
		Spacer(Modifier.width(12.dp))
		val isDefaultColorType by remember {
			derivedStateOf { colorType in FlexColorType.defaultColorTypes }
		}
		Code(
			methodName = "FlexInput",
			variables = listOf(
				KeywordT("var") + SpaceT() + VariableT("value") + SpaceT() + KeywordT("by") + SpaceT() + MethodT("remember") + SpaceT() + LeftBraceT +
					SpaceT() + MethodT("mutableStateOf") + LeftParenthesesT + DoubleQuotesT + DoubleQuotesT + RightParenthesesT + SpaceT() + RightBraceT
			),
			assigns = listOf(
				"value" assign VariableT("value"),
				"onValueChange" assign LeftBraceT + SpaceT() + VariableT("value") + SpaceT() + EqualsT + SpaceT() + VariableT("it") + SpaceT() + RightBraceT,
				"sizeType" assign ClassT("FlexSizeType") + DotT + ClassT(sizeType),
				"colorType" assign if (isDefaultColorType) ClassT("FlexColorType") + DotT + ClassT(colorType) else ClassT(colorType),
				"cornerType" assign ClassT("FlexCornerType") + DotT + ClassT(cornerType),
				"prefix" assign KeywordT("null"),
				"suffix" assign KeywordT("null"),
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
	}
}