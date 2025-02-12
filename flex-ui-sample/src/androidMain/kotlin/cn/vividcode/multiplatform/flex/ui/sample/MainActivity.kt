package cn.vividcode.multiplatform.flex.ui.sample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
		}
	}
}