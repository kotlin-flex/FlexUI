package cn.vividcode.multiplatform.flex.ui.sample.page

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import cn.vividcode.multiplatform.flex.ui.foundation.select.FlexSelect
import cn.vividcode.multiplatform.flex.ui.sample.components.AdaptiveLayout
import cn.vividcode.multiplatform.flex.ui.sample.components.Code

@Composable
fun ColumnScope.FlexSelectPage() {
	AdaptiveLayout(
		code = {
			val code by remember {
				derivedStateOf {
					"""
						FlexSelect()
					""".trimIndent()
				}
			}
			Code(code)
		},
		preview = {
			var selectedKey by remember { mutableStateOf("") }
			FlexSelect(
				selectedKey = selectedKey,
				onSelectedKeyChanged = { selectedKey = it }
			)
		},
		options = {
		
		}
	)
}