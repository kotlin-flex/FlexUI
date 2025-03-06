package cn.vividcode.multiplatform.flex.ui.sample.page

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cn.vividcode.multiplatform.flex.ui.common.options
import cn.vividcode.multiplatform.flex.ui.foundation.radio.FlexRadio
import cn.vividcode.multiplatform.flex.ui.foundation.radio.FlexRadioSwitchType
import cn.vividcode.multiplatform.flex.ui.foundation.radio.FlexRadioType
import cn.vividcode.multiplatform.flex.ui.sample.theme.HighContrastColorSchemes
import cn.vividcode.multiplatform.flex.ui.sample.theme.MediumContrastColorSchemes
import cn.vividcode.multiplatform.flex.ui.sample.theme.StandardContrastColorSchemes
import cn.vividcode.multiplatform.flex.ui.sample.theme.currentColorSchemes
import cn.vividcode.multiplatform.flex.ui.theme.ColorSchemes
import cn.vividcode.multiplatform.flex.ui.type.FlexSizeType

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
			item("Contrast") {
				FlexRadio(
					selectedKey = contrastType,
					onSelectedKeyChange = {
						contrastType = it
						currentColorSchemes = it.colorSchemes
					},
					options = remember { ContrastType.entries.options() },
					sizeType = FlexSizeType.Small,
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

@Composable
fun GroupLayout(
	title: String,
	content: @Composable GroupLayoutScope.() -> Unit,
) {
	Column(
		modifier = Modifier.fillMaxWidth()
	) {
		Text(
			text = title,
			style = MaterialTheme.typography.titleMedium,
		)
		Spacer(Modifier.height(16.dp))
		with(GroupLayoutScopeImpl) {
			content()
		}
	}
}

interface GroupLayoutScope {
	
	@Composable
	fun item(
		title: String,
		content: @Composable RowScope.() -> Unit,
	)
}

private object GroupLayoutScopeImpl : GroupLayoutScope {
	
	@Composable
	override fun item(
		title: String,
		content: @Composable RowScope.() -> Unit,
	) {
		Row(
			modifier = Modifier
				.fillMaxWidth()
				.padding(vertical = 6.dp),
			verticalAlignment = Alignment.CenterVertically,
		) {
			Text(
				text = title,
				style = MaterialTheme.typography.labelMedium,
			)
			Spacer(modifier = Modifier.width(12.dp))
			Spacer(modifier = Modifier.weight(1f))
			content()
		}
	}
}