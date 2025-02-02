package cn.vividcode.multiplatform.flex.ui.sample.page

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cn.vividcode.multiplatform.flex.ui.config.type.FlexColorType
import cn.vividcode.multiplatform.flex.ui.config.type.FlexSizeType
import cn.vividcode.multiplatform.flex.ui.foundation.radio.FlexRadioGroup
import cn.vividcode.multiplatform.flex.ui.foundation.radio.FlexRadioSwitchType
import cn.vividcode.multiplatform.flex.ui.foundation.radio.FlexRadioType
import cn.vividcode.multiplatform.flex.ui.foundation.radio.RadioOption
import cn.vividcode.multiplatform.flex.ui.sample.components.TitleLayout
import cn.vividcode.multiplatform.flex.ui.sample.theme.HighContrastColorSchemes
import cn.vividcode.multiplatform.flex.ui.sample.theme.MediumContrastColorSchemes
import cn.vividcode.multiplatform.flex.ui.sample.theme.StandardContrastColorSchemes
import cn.vividcode.multiplatform.flex.ui.sample.theme.currentColorSchemes
import cn.vividcode.multiplatform.flex.ui.theme.ColorSchemes

/**
 * 设置页
 */
@Composable
fun SettingsPage(
	paddingValues: PaddingValues,
) {
	Column(
		modifier = Modifier
			.padding(paddingValues)
			.fillMaxSize()
			.padding(12.dp)
	) {
		GroupLayout(
			title = "Theme"
		) {
			TitleLayout(
				title = "Contrast"
			) {
				FlexRadioGroup(
					selectedKey = contrastType,
					onSelectedKeyChange = {
						contrastType = it
						currentColorSchemes = it.colorSchemes
					},
					options = contrastTypeOptions,
					sizeType = FlexSizeType.Small,
					colorType = FlexColorType.Primary,
					radioType = FlexRadioType.Button,
					switchType = FlexRadioSwitchType.Swipe
				)
			}
		}
	}
	
}

private var contrastType by mutableStateOf(ContrastType.Standard)

private enum class ContrastType(
	val colorSchemes: ColorSchemes,
) {
	
	Standard(StandardContrastColorSchemes),
	
	Medium(MediumContrastColorSchemes),
	
	High(HighContrastColorSchemes)
}

private val contrastTypeOptions = ContrastType.entries.map { RadioOption(it, it.toString()) }

@Composable
fun GroupLayout(
	title: String,
	content: @Composable ColumnScope.() -> Unit,
) {
	Column(
		modifier = Modifier.fillMaxWidth()
	) {
		Text(
			text = title,
			style = MaterialTheme.typography.titleLarge,
		)
		Spacer(Modifier.height(12.dp))
		content()
	}
}