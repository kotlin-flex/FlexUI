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
 * 单选框展示页
 */
@Composable
fun ColumnScope.FlexRadioPage() {
	var radioType by remember { mutableStateOf(FlexRadioType.Default) }
	var sizeType by remember { mutableStateOf(FlexSizeType.Medium) }
	var colorType by remember { mutableStateOf<FlexColorType>(FlexColorType.Default) }
	var cornerType by remember { mutableStateOf(FlexCornerType.Default) }
	var switchType by remember { mutableStateOf(FlexRadioSwitchType.None) }
	var scaleEffect by remember { mutableStateOf(true) }
	var disabledOption by remember { mutableStateOf(4) }
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
			var selectedKey by remember { mutableStateOf("option1") }
			FlexRadioGroup(
				selectedKey = selectedKey,
				onSelectedKeyChange = { selectedKey = it },
				options = (1 .. 3).map {
					RadioOption("option$it", "Option $it", it != disabledOption)
				},
				sizeType = sizeType,
				colorType = colorType,
				cornerType = cornerType,
				radioType = radioType,
				switchType = switchType,
				scaleEffect = scaleEffect,
			)
		}
		Spacer(modifier = Modifier.width(12.dp))
		val isDefaultColorType by remember {
			derivedStateOf { colorType in FlexColorType.defaultColorTypes }
		}
		Code(
			methodName = "FlexRadioGroup",
			variables = listOf(
				KeywordT("var") + SpaceT() + VariableT("selectedKey") + SpaceT() + KeywordT("by") + SpaceT() + MethodT("remember") + SpaceT() + LeftBraceT,
				SpaceT(4) + MethodT("mutableStateOf") + LeftParenthesesT + DoubleQuotesT + StringT("option1") + DoubleQuotesT + RightParenthesesT,
				RightBraceT
			),
			assigns = listOf(
				"selectedKey" assign VariableT("selectedKey"),
				"onSelectedKeyChange" assign LeftBraceT + SpaceT() + VariableT("selectedKey") + SpaceT() + EqualsT + SpaceT() + VariableT("it") + SpaceT() + RightBraceT,
				"options" assign (1 .. 3).map {
					val codes =
						ClassT("RadioOption") + LeftParenthesesT + DoubleQuotesT + StringT("option$it") + DoubleQuotesT + CommaT + SpaceT() + DoubleQuotesT + StringT("Option $it") + DoubleQuotesT
					if (it == disabledOption) {
						codes + CommaT + SpaceT() + KeywordT("false") + RightParenthesesT
					} else {
						codes + RightParenthesesT
					}
				},
				"sizeType" assign ClassT("FlexSizeType") + DotT + ClassT(sizeType),
				"colorType" assign if (isDefaultColorType) ClassT("FlexColorType") + DotT + ClassT(colorType) else ClassT(colorType),
				"cornerType" assign ClassT("FlexCornerType") + DotT + ClassT(cornerType),
				"radioType" assign ClassT("FlexRadioType") + DotT + ClassT(radioType),
				"switchType" assign ClassT("FlexRadioSwitchType") + DotT + ClassT(switchType),
				"scaleEffect" assign scaleEffect
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
			title = { Text("Radio Type") }
		) {
			FlexRadioGroup(
				selectedKey = radioType,
				onSelectedKeyChange = { radioType = it },
				options = radioTypeOptions,
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
			title = { Text("Switch Type") }
		) {
			FlexRadioGroup(
				selectedKey = switchType,
				onSelectedKeyChange = { switchType = it },
				options = switchTypeOptions,
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
			title = { Text("Option Disabled") }
		) {
			FlexRadioGroup(
				selectedKey = disabledOption,
				onSelectedKeyChange = { disabledOption = it },
				options = disabledOptions,
				sizeType = FlexSizeType.Small,
				colorType = FlexColorType.Primary,
				radioType = FlexRadioType.Button,
				switchType = FlexRadioSwitchType.Swipe
			)
		}
	}
}

private val radioTypeOptions = FlexRadioType.entries.map { RadioOption(it, it.toString()) }

private val switchTypeOptions = FlexRadioSwitchType.entries.map { RadioOption(it, it.toString()) }

private val disabledOptions = (1 .. 4).map { RadioOption(it, if (it < 4) "Option $it" else "Cancel") }