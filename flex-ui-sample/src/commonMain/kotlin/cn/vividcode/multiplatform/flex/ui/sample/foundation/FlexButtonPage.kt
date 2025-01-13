package cn.vividcode.multiplatform.flex.ui.sample.foundation

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cn.vividcode.multiplatform.flex.ui.config.theme.FlexColorType
import cn.vividcode.multiplatform.flex.ui.config.theme.FlexSizeType
import cn.vividcode.multiplatform.flex.ui.foundation.ButtonIconDirection
import cn.vividcode.multiplatform.flex.ui.foundation.ButtonType
import cn.vividcode.multiplatform.flex.ui.foundation.FlexButton

@Composable
fun FlexButtonPage(
	paddingValues: PaddingValues,
) {
	val verticalScrollState = rememberScrollState()
	Column(
		modifier = Modifier
			.padding(paddingValues)
			.fillMaxSize()
			.verticalScroll(verticalScrollState)
			.padding(vertical = 16.dp)
	) {
		FlexColorType.entries.forEach { colorType ->
			FlexSizeType.entries.forEach { sizeType ->
				Spacer(modifier = Modifier.height(16.dp))
				val scrollState = rememberScrollState()
				Row(
					modifier = Modifier
						.fillMaxWidth()
						.horizontalScroll(scrollState)
						.padding(horizontal = 16.dp)
				) {
					ButtonType.entries.forEachIndexed { index, buttonType ->
						if (index != 0) {
							Spacer(modifier = Modifier.width(16.dp))
						}
						FlexButton(
							text = "$colorType $buttonType",
							sizeType = sizeType,
							colorType = colorType,
							buttonType = buttonType,
							icon = when (buttonType) {
								ButtonType.Primary -> Icons.Outlined.Search
								ButtonType.Dashed -> Icons.Outlined.Add
								ButtonType.Text -> Icons.Outlined.KeyboardArrowDown
								else -> null
							},
							iconDirection = when (buttonType) {
								ButtonType.Dashed -> ButtonIconDirection.Start
								else -> ButtonIconDirection.End
							}
						) {
						
						}
					}
				}
			}
		}
	}
}