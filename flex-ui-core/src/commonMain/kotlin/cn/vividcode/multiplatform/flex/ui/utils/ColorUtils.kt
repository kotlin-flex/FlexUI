package cn.vividcode.multiplatform.flex.ui.utils

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp

internal fun Color.darken(fraction: Float): Color {
	return lerp(this, Color.Black, fraction)
}

internal fun Color.lighten(fraction: Float): Color {
	return lerp(this, Color.White, fraction)
}

internal val Color.disabledWithColor: Color
	get() = this.copy(alpha = 0.6f)

internal val Color.disabledWithOnColor: Color
	get() = this.copy(alpha = 0.8f)