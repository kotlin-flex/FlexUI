package cn.vividcode.multiplatform.flex.ui.sample

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import cn.vividcode.multiplatform.flex.ui.sample.theme.currentColorSchemes
import cn.vividcode.multiplatform.flex.ui.sample.theme.getTypography
import cn.vividcode.multiplatform.flex.ui.theme.FlexTheme
import kotlinx.browser.document
import org.jetbrains.compose.resources.configureWebResources

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
	configureWebResources {
		resourcePathMapping { path -> "./$path" }
	}
	ComposeViewport(document.body!!) {
		FlexTheme(
			colorSchemes = currentColorSchemes,
			typography = getTypography()
		) {
			App()
		}
	}
}