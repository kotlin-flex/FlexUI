package cn.vividcode.multiplatform.flex.ui.sample

import android.content.ClipData
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.platform.LocalClipboard
import cn.vividcode.multiplatform.flex.ui.sample.theme.currentColorSchemes
import cn.vividcode.multiplatform.flex.ui.sample.theme.getTypography
import cn.vividcode.multiplatform.flex.ui.theme.FlexTheme

class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		enableEdgeToEdge()
		setContent {
			FlexTheme(
				colorSchemes = currentColorSchemes,
				typography = getTypography()
			) {
				App()
			}
			val clipboard = LocalClipboard.current
			clipboard.nativeClipboard.setPrimaryClip(ClipData.newPlainText(null, "AA"))
		}
	}
}