package cn.vividcode.multiplatform.flex.ui.sample.page

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cn.vividcode.multiplatform.flex.ui.config.type.FlexColorType
import cn.vividcode.multiplatform.flex.ui.config.type.FlexCornerType
import cn.vividcode.multiplatform.flex.ui.config.type.FlexSizeType
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
 * 单选框展示页
 */
@Composable
fun ColumnScope.FlexRadioPage() {
	var radioType by remember { mutableStateOf(FlexRadioType.Default) }
	var sizeType by remember { mutableStateOf(FlexSizeType.Medium) }
	var colorType by remember { mutableStateOf<FlexColorType>(FlexColorType.Primary) }
	var cornerType by remember { mutableStateOf(FlexCornerType.Medium) }
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
			FlexRadio(
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
		val code = """
			var selectedKey by remember { mutableStateOf("option1") }
			FlexRadio(
				selectedKey = selectedKey,
				onSelectedKeyChange = { selectedKey = it },
				options = listOf(
					RadioOption("option1", "Option 1"${if (disabledOption == 1) ", enabled = false" else ""}),
					RadioOption("option2", "Option 2"${if (disabledOption == 2) ", enabled = false" else ""}),
					RadioOption("option3", "Option 3"${if (disabledOption == 3) ", enabled = false" else ""})
				)
				sizeType = FlexSizeType.$sizeType,
				colorType = FlexColorType.$colorType,
				cornerType = FlexCornerType.$cornerType,
				radioType = FlexRadioType.$radioType,
				switchType = FlexRadioSwitchType.$switchType,
				scaleEffect = $scaleEffect
			)
		""".trimIndent()
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
			title = "Radio Type"
		) {
			FlexRadio(
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
			title = "Color Type"
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
			title = "Switch Type"
		) {
			FlexRadio(
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
			title = "Scale Effect"
		) {
			FlexRadio(
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
			title = "Option Disabled"
		) {
			FlexRadio(
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

private val disabledOptions = (1 .. 4).map { RadioOption(it, if (it < 4) "Option $it" else "None") }