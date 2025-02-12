package cn.vividcode.multiplatform.flex.ui.sample

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.CanvasBasedWindow
import cn.vividcode.multiplatform.flex.ui.sample.theme.currentColorSchemes
import cn.vividcode.multiplatform.flex.ui.sample.theme.getTypography
import cn.vividcode.multiplatform.flex.ui.theme.FlexTheme
import org.jetbrains.skiko.wasm.onWasmReady

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
	onWasmReady {
		CanvasBasedWindow("flexUISample") {
			FlexTheme(
				colorSchemes = currentColorSchemes,
				typography = getTypography()
			) {
				App()
			}
		}
	}
}