package cn.vividcode.multiplatform.flex.ui.sample.page

import androidx.compose.runtime.*
import cn.vividcode.multiplatform.flex.ui.foundation.input.FlexInput

@Composable
fun FlexInputPage() {
	var value by remember { mutableStateOf("") }
	FlexInput(
		value = value,
		onValueChange = { value = it },
	)
}