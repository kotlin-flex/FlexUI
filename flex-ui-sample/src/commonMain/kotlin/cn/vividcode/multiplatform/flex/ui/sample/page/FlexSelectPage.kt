package cn.vividcode.multiplatform.flex.ui.sample.page

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import cn.vividcode.multiplatform.flex.ui.common.options
import cn.vividcode.multiplatform.flex.ui.foundation.radio.FlexRadio
import cn.vividcode.multiplatform.flex.ui.foundation.radio.FlexRadioSwitchType
import cn.vividcode.multiplatform.flex.ui.foundation.radio.FlexRadioType
import cn.vividcode.multiplatform.flex.ui.foundation.select.FlexSelect
import cn.vividcode.multiplatform.flex.ui.sample.brushTypeOptions
import cn.vividcode.multiplatform.flex.ui.sample.components.AdaptiveLayout
import cn.vividcode.multiplatform.flex.ui.sample.components.Code
import cn.vividcode.multiplatform.flex.ui.type.FlexBrushType
import cn.vividcode.multiplatform.flex.ui.type.FlexCornerType
import cn.vividcode.multiplatform.flex.ui.type.FlexSizeType

@Composable
fun ColumnScope.FlexSelectPage() {
	var sizeType by remember { mutableStateOf(FlexSizeType.Medium) }
	var cornerType by remember { mutableStateOf(FlexCornerType.Medium) }
	var brushType by remember { mutableStateOf<FlexBrushType>(FlexBrushType.Primary) }
	AdaptiveLayout(
		code = {
			val code by remember(sizeType, brushType, cornerType) {
				derivedStateOf {
					"""
						var selectedKey by remember { mutableStateOf("Option 1") }
						FlexSelect(
							selectedKey = selectedKey,
							onSelectedKeyChanged = { selectedKey = it },
							options = remember { listOf("Option 1", "Option 2", "Option 3").options() },
							sizeType = $sizeType,
							brushType = $brushType,
							cornerType = $cornerType
						)
					""".trimIndent()
				}
			}
			Code(code)
		},
		preview = {
			var selectedKey by remember { mutableStateOf("Option 1") }
			FlexSelect(
				selectedKey = selectedKey,
				onSelectedKeyChanged = { selectedKey = it },
				options = remember { listOf("Option 1", "Option 2", "Option 3").options() },
				sizeType = sizeType,
				brushType = brushType,
				cornerType = cornerType
			)
		},
		options = {
			item("Size Type") {
				FlexRadio(
					selectedKey = sizeType,
					onSelectedKeyChange = { sizeType = it },
					options = remember { FlexSizeType.entries.options() },
					sizeType = FlexSizeType.Small,
					radioType = FlexRadioType.Button,
					switchType = FlexRadioSwitchType.Swipe
				)
			}
			item("Corner Type") {
				FlexRadio(
					selectedKey = cornerType,
					onSelectedKeyChange = { cornerType = it },
					options = remember { FlexCornerType.entries.options() },
					sizeType = FlexSizeType.Small,
					radioType = FlexRadioType.Button,
					switchType = FlexRadioSwitchType.Swipe
				)
			}
			item("Brush Type") {
				FlexRadio(
					selectedKey = brushType,
					onSelectedKeyChange = { brushType = it },
					options = brushTypeOptions,
					sizeType = FlexSizeType.Small,
					radioType = FlexRadioType.Button,
					switchType = FlexRadioSwitchType.Swipe
				)
			}
		}
	)
}