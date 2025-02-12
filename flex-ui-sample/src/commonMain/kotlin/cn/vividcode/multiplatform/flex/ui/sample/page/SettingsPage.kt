package cn.vividcode.multiplatform.flex.ui.sample.page

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontVariation
import androidx.compose.ui.unit.dp
import cn.vividcode.multiplatform.flex.ui.config.type.FlexSizeType
import cn.vividcode.multiplatform.flex.ui.foundation.radio.FlexRadio
import cn.vividcode.multiplatform.flex.ui.foundation.radio.FlexRadioSwitchType
import cn.vividcode.multiplatform.flex.ui.foundation.radio.FlexRadioType
import cn.vividcode.multiplatform.flex.ui.foundation.radio.toRadioOptions
import cn.vividcode.multiplatform.flex.ui.foundation.slider.FlexSlider
import cn.vividcode.multiplatform.flex.ui.foundation.slider.FlexSliderSteps
import cn.vividcode.multiplatform.flex.ui.sample.components.TitleLayout
import cn.vividcode.multiplatform.flex.ui.sample.generated.resources.MiSans_VF
import cn.vividcode.multiplatform.flex.ui.sample.generated.resources.Res
import cn.vividcode.multiplatform.flex.ui.sample.theme.HighContrastColorSchemes
import cn.vividcode.multiplatform.flex.ui.sample.theme.MediumContrastColorSchemes
import cn.vividcode.multiplatform.flex.ui.sample.theme.StandardContrastColorSchemes
import cn.vividcode.multiplatform.flex.ui.sample.theme.currentColorSchemes
import cn.vividcode.multiplatform.flex.ui.theme.ColorSchemes
import org.jetbrains.compose.resources.Font

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
			.padding(16.dp)
	) {
		GroupLayout(
			title = "Theme"
		) {
			TitleLayout(
				title = "Contrast"
			) {
				FlexRadio(
					selectedKey = contrastType,
					onSelectedKeyChange = {
						contrastType = it
						currentColorSchemes = it.colorSchemes
					},
					options = ContrastType.entries.toRadioOptions(),
					sizeType = FlexSizeType.Small,
					radioType = FlexRadioType.Button,
					switchType = FlexRadioSwitchType.Swipe
				)
			}
		}
		Spacer(modifier = Modifier.height(24.dp))
		GroupLayout(
			title = "Variable Font"
		) {
			var value by remember { mutableStateOf(100f) }
			TitleLayout(
				title = "Weight"
			) {
				FlexSlider(
					value = value,
					onValueChange = { value = it },
					sizeType = FlexSizeType.Small,
					minValue = 100f,
					maxValue = 900f,
					steps = FlexSliderSteps.averageSteps(8)
				)
			}
			Spacer(modifier = Modifier.height(12.dp))
			TitleLayout(
				title = "Text"
			) {
				Text(
					text = "这是一段测试可变字体的内容",
					fontFamily = FontFamily(
						Font(
							resource = Res.font.MiSans_VF,
							variationSettings = FontVariation.Settings(FontVariation.weight(value.toInt()))
						)
					)
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
			style = MaterialTheme.typography.titleMedium,
		)
		Spacer(Modifier.height(16.dp))
		content()
	}
}