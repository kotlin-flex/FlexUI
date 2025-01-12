package cn.vividcode.multiplatform.flex.ui.sample

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import cn.vividcode.multiplatform.flex.ui.config.LocalFlex
import cn.vividcode.multiplatform.flex.ui.config.flex
import java.awt.Color
import java.awt.Dimension

fun main() = application {
	Window(
		onCloseRequest = ::exitApplication,
		title = "",
		state = rememberWindowState(
			position = WindowPosition.Aligned(Alignment.Center),
			size = DpSize(800.dp, 600.dp)
		),
		resizable = true
	) {
		LaunchedEffect(Unit) {
			window.rootPane.putClientProperty("apple.awt.fullWindowContent", true)
			window.rootPane.putClientProperty("apple.awt.transparentTitleBar", true)
			window.minimumSize = Dimension(800, 600)
		}
		MaterialTheme(
			colorScheme = getColorScheme()
		) {
			CompositionLocalProvider(
				LocalFlex provides flex(),
				LocalSystemInDarkTheme provides isSystemInDarkTheme()
			) {
				val surface = MaterialTheme.colorScheme.surface
				LaunchedEffect(LocalSystemInDarkTheme.current) {
					window.background = surface.let { Color(it.red, it.green, it.blue) }
				}
				App()
			}
		}
	}
}

private val LocalSystemInDarkTheme = staticCompositionLocalOf { false }