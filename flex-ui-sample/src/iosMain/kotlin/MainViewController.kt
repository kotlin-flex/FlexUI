import androidx.compose.ui.window.ComposeUIViewController
import cn.vividcode.multiplatform.flex.ui.sample.App
import cn.vividcode.multiplatform.flex.ui.theme.FlexTheme

@Suppress("unused", "FunctionName")
fun MainViewController() = ComposeUIViewController {
	FlexTheme {
		App()
	}
}