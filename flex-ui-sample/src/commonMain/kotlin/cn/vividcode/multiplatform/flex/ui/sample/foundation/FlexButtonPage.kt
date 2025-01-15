package cn.vividcode.multiplatform.flex.ui.sample.foundation

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowRight
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cn.vividcode.multiplatform.flex.ui.config.theme.FlexColorType
import cn.vividcode.multiplatform.flex.ui.config.theme.FlexCornerType
import cn.vividcode.multiplatform.flex.ui.config.theme.FlexSizeType
import cn.vividcode.multiplatform.flex.ui.foundation.FlexButton
import cn.vividcode.multiplatform.flex.ui.foundation.FlexButtonIconPosition
import cn.vividcode.multiplatform.flex.ui.foundation.FlexButtonType

@Composable
fun FlexButtonPage() {
	val verticalScrollState = rememberScrollState()
	val horizontalScrollState = rememberScrollState()
	Column(
		modifier = Modifier
			.fillMaxSize()
			.verticalScroll(verticalScrollState)
			.horizontalScroll(horizontalScrollState)
			.padding(vertical = 16.dp)
	) {
		FlexColorType.entries.forEachIndexed { colorTypeIndex, colorType ->
			FlexSizeType.entries.forEachIndexed { sizeTypeIndex, sizeType ->
				repeat(2) {
					if (colorTypeIndex != 0 || sizeTypeIndex != 0 || it == 1) {
						Spacer(modifier = Modifier.height(16.dp))
					}
					Row(
						modifier = Modifier
							.fillMaxWidth()
							.padding(horizontal = 16.dp)
					) {
						val cornerType = FlexCornerType.entries[colorTypeIndex % FlexCornerType.entries.size]
						FlexButtonType.entries.forEachIndexed { index, buttonType ->
							if (index != 0) {
								Spacer(modifier = Modifier.width(16.dp))
							}
							FlexButton(
								text = "$colorType $buttonType",
								sizeType = sizeType,
								colorType = colorType,
								cornerType = cornerType,
								buttonType = buttonType,
								icon = when (buttonType) {
									FlexButtonType.Primary -> Icons.Rounded.Search
									FlexButtonType.Dashed -> Icons.Rounded.Add
									FlexButtonType.Text -> Icons.Outlined.KeyboardArrowDown
									FlexButtonType.Link -> Icons.AutoMirrored.Outlined.KeyboardArrowRight
									else -> null
								},
								iconPosition = when (buttonType) {
									FlexButtonType.Dashed -> FlexButtonIconPosition.Start
									else -> FlexButtonIconPosition.End
								},
								enabled = it % 2 == 0
							) {
							
							}
						}
						FlexButtonType.entries.forEach { buttonType ->
							Spacer(modifier = Modifier.width(16.dp))
							FlexButton(
								sizeType = sizeType,
								colorType = colorType,
								cornerType = cornerType,
								buttonType = buttonType,
								icon = Icons.Rounded.Search,
								iconPosition = when (buttonType) {
									FlexButtonType.Dashed -> FlexButtonIconPosition.Start
									else -> FlexButtonIconPosition.End
								},
								enabled = it % 2 == 0
							) {
							
							}
						}
					}
				}
			}
		}
	}
}