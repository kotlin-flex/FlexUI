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
import cn.vividcode.multiplatform.flex.ui.foundation.switch.FlexSwitch
import cn.vividcode.multiplatform.flex.ui.foundation.switch.FlexSwitchLabels
import cn.vividcode.multiplatform.flex.ui.sample.components.Code
import cn.vividcode.multiplatform.flex.ui.sample.components.TitleLayout
import cn.vividcode.multiplatform.flex.ui.sample.constant.colorTypeOptions
import cn.vividcode.multiplatform.flex.ui.sample.constant.cornerTypeOptions
import cn.vividcode.multiplatform.flex.ui.sample.constant.sizeTypeOptions

/**
 * 按钮展示页
 */
@Composable
fun ColumnScope.FlexSwitchPage() {
	var sizeType by remember { mutableStateOf(FlexSizeType.Medium) }
	var colorType by remember { mutableStateOf<FlexColorType>(FlexColorType.Primary) }
	var cornerType by remember { mutableStateOf(FlexCornerType.Circle) }
	var labelType by remember { mutableStateOf(FlexSwitchLabelType.None) }
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
			var checked by remember { mutableStateOf(false) }
			FlexSwitch(
				checked = checked,
				onCheckedChange = { checked = it },
				sizeType = sizeType,
				colorType = colorType,
				cornerType = cornerType,
				label = when (labelType) {
					FlexSwitchLabelType.None -> null
					FlexSwitchLabelType.OnOff -> FlexSwitchLabels.DefaultTextLabel
					FlexSwitchLabelType.OpenClose -> FlexSwitchLabels.textLabel(
						checked = "开",
						unchecked = "关"
					)
					
					FlexSwitchLabelType.Icon -> FlexSwitchLabels.DefaultIconLabel
				},
				enabled = enabled
			)
		}
		Spacer(Modifier.width(12.dp))
		val code = """
			var checked by remember { mutableStateOf(false) }
			FlexSwitch(
				checked = checked,
				onCheckedChange = { checked = it },
				sizeType = FlexSizeType.$sizeType,
				colorType = FlexColorType.$colorType,
				cornerType = FlexCornerType.$cornerType
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
			title = "Size Type"
		) {
			FlexRadio(
				selectedKey = sizeType,
				onSelectedKeyChange = { sizeType = it },
				options = sizeTypeOptions,
				sizeType = FlexSizeType.Small,
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
				cornerType = cornerType,
				radioType = FlexRadioType.Button,
				switchType = FlexRadioSwitchType.Swipe
			)
		}
		Spacer(modifier = Modifier.height(12.dp))
		TitleLayout(
			title = "Label Type"
		) {
			FlexRadio(
				selectedKey = labelType,
				onSelectedKeyChange = { labelType = it },
				options = switchLabelTypeOptions,
				sizeType = FlexSizeType.Small,
				radioType = FlexRadioType.Button,
				switchType = FlexRadioSwitchType.Swipe
			)
		}
		Spacer(modifier = Modifier.height(12.dp))
		TitleLayout(
			title = "Enabled"
		) {
			FlexSwitch(
				checked = enabled,
				onCheckedChange = { enabled = it },
				sizeType = FlexSizeType.Small
			)
		}
	}
}

private enum class FlexSwitchLabelType {
	
	None,
	
	OnOff,
	
	OpenClose,
	
	Icon
}

private val switchLabelTypeOptions = FlexSwitchLabelType.entries.map { RadioOption(it, it.toString()) }