package cn.vividcode.multiplatform.flex.ui.sample.foundation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowRight
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cn.vividcode.multiplatform.flex.ui.config.type.FlexColorType
import cn.vividcode.multiplatform.flex.ui.config.type.FlexCornerType
import cn.vividcode.multiplatform.flex.ui.config.type.FlexSizeType
import cn.vividcode.multiplatform.flex.ui.foundation.button.FlexButton
import cn.vividcode.multiplatform.flex.ui.foundation.button.FlexButtonIconPosition
import cn.vividcode.multiplatform.flex.ui.foundation.button.FlexButtonType

@Composable
fun FlexButtonPage() {
	LazyColumn(
		modifier = Modifier.fillMaxSize()
	) {
		item {
			Spacer(modifier = Modifier.height(16.dp))
		}
		FlexColorType.entries.forEachIndexed { colorTypeIndex, colorType ->
			FlexSizeType.entries.forEachIndexed { sizeTypeIndex, sizeType ->
				repeat(2) {
					if (colorTypeIndex != 0 || sizeTypeIndex != 0 || it == 1) {
						item {
							Spacer(modifier = Modifier.height(16.dp))
						}
					}
					item {
						LazyRow(
							modifier = Modifier
								.fillMaxWidth()
								.padding(horizontal = 16.dp),
						) {
							val cornerType = FlexCornerType.entries[colorTypeIndex % FlexCornerType.entries.size]
							FlexButtonType.entries.forEachIndexed { index, buttonType ->
								if (index != 0) {
									item {
										Spacer(modifier = Modifier.width(16.dp))
									}
								}
								item {
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
									)
								}
							}
							FlexButtonType.entries.forEach { buttonType ->
								item {
									Spacer(modifier = Modifier.width(16.dp))
								}
								item {
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
									)
								}
							}
						}
					}
				}
			}
		}
		item {
			Spacer(modifier = Modifier.height(16.dp))
		}
	}
}