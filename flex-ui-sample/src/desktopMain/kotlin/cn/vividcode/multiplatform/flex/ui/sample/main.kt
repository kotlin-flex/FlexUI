package cn.vividcode.multiplatform.flex.ui.sample

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import cn.vividcode.multiplatform.flex.ui.theme.FlexTheme
import cn.vividcode.multiplatform.flex.ui.theme.LocalDarkTheme
import java.awt.Color
import java.awt.Dimension

fun main() = application {
	Window(
		onCloseRequest = ::exitApplication,
		title = "",
		state = rememberWindowState(
			position = WindowPosition.Aligned(Alignment.Center),
			size = DpSize(1000.dp, 750.dp)
		),
		resizable = true
	) {
		LaunchedEffect(Unit) {
			window.rootPane.putClientProperty("apple.awt.fullWindowContent", true)
			window.rootPane.putClientProperty("apple.awt.transparentTitleBar", true)
			window.minimumSize = Dimension(800, 600)
		}
		FlexTheme {
			val backgroundColor = MaterialTheme.colorScheme.background
			LaunchedEffect(LocalDarkTheme.current) {
				window.background = backgroundColor.let { Color(it.red, it.green, it.blue) }
			}
			App()
		}
	}
}