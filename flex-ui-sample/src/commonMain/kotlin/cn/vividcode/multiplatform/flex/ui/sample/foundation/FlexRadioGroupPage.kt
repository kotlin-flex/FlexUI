package cn.vividcode.multiplatform.flex.ui.sample.foundation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cn.vividcode.multiplatform.flex.ui.config.type.FlexColorType
import cn.vividcode.multiplatform.flex.ui.config.type.FlexCornerType
import cn.vividcode.multiplatform.flex.ui.config.type.FlexSizeType
import cn.vividcode.multiplatform.flex.ui.foundation.radio.FlexRadioGroup
import cn.vividcode.multiplatform.flex.ui.foundation.radio.FlexRadioType
import cn.vividcode.multiplatform.flex.ui.foundation.radio.FlexSwitchType
import cn.vividcode.multiplatform.flex.ui.foundation.radio.RadioOption

@Composable
fun FlexRadioPage() {
	LazyColumn(
		modifier = Modifier.fillMaxSize(),
	) {
		item {
			Spacer(modifier = Modifier.height(16.dp))
		}
		FlexColorType.entries.forEachIndexed { colorTypeIndex, colorType ->
			FlexSizeType.entries.forEach { sizeType ->
				FlexSwitchType.entries.forEach { switchType ->
					item {
						LazyRow(
							modifier = Modifier.fillMaxWidth()
						) {
							repeat(2) {
								FlexRadioType.entries.forEachIndexed { index, radioType ->
									if (index == 0 && it == 0) {
										item {
											Spacer(modifier = Modifier.width(16.dp))
										}
									}
									item {
										val options = listOf(
											RadioOption("option1", if (it % 2 == 0) "Option 1" else "Disabled", enabled = it % 2 == 0),
											RadioOption("option2", "Option 2"),
											RadioOption("option3", "Option 3"),
										)
										var selectedKey by remember { mutableStateOf(options.first().key) }
										FlexRadioGroup(
											options = options,
											selectedKey = selectedKey,
											onSelectedKeyChange = { selectedKey = it },
											sizeType = sizeType,
											colorType = colorType,
											cornerType = FlexCornerType.entries[colorTypeIndex % FlexCornerType.entries.size],
											radioType = radioType,
											switchType = switchType,
										)
									}
									item {
										Spacer(modifier = Modifier.width(16.dp))
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
		}
	}
}