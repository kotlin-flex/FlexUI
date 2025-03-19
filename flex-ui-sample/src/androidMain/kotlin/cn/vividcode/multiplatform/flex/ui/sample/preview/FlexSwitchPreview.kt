package cn.vividcode.multiplatform.flex.ui.sample.preview

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import cn.vividcode.multiplatform.flex.ui.common.options
import cn.vividcode.multiplatform.flex.ui.foundation.select.FlexSelect
import cn.vividcode.multiplatform.flex.ui.theme.FlexTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun FlexSwitchPreview() {
	FlexTheme {
		var selectedKey by remember { mutableStateOf<Int?>(0) }
		FlexSelect(
			selectedKey = selectedKey,
			onSelectedKeyChanged = { selectedKey = it },
			options = remember { (0 ..< 10).options() }
		)
	}
}