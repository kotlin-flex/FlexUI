package cn.vividcode.multiplatform.flex.ui.expends

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import cn.vividcode.multiplatform.flex.ui.theme.LocalDarkTheme
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

/**
 * 颜色变亮
 * @param factor 因子, < 1: 变暗, > 1: 变亮
 */
private fun Color.brightness(factor: Float): Color {
	val hsl = this.rgbToHsl()
	val h = hsl[0]
	val s = hsl[1]
	val l = min(1f, max(0f, hsl[2] * factor))
	return hslToRgb(h, s, l)
}

/**
 * 内容变深
 */
internal val Color.darkenWithContent: Color
	@Composable
	get() = this.brightness(
		factor = if (LocalDarkTheme.current) 0.95f else 0.85f
	)

/**
 * 内容变浅
 */
internal val Color.lightenWithContent: Color
	@Composable
	get() = this.brightness(
		factor = if (LocalDarkTheme.current) 1.05f else 1.15f
	)

/**
 * 背景变深
 */
internal val Color.darkenWithBackground: Color
	@Composable
	get() = this.brightness(0.95f)

/**
 * 背景变浅
 */
internal val Color.lightenWithBackground: Color
	@Composable
	get() = this.brightness(1.05f)

/**
 * 禁用内容
 */
internal val Color.disabledWithContent: Color
	get() = this.copy(alpha = 0.8f)

/**
 * 禁用背景
 */
internal val Color.disabledWithBackground: Color
	get() = this.copy(alpha = 0.8f)

/**
 * RGB 转 HSL
 */
private fun Color.rgbToHsl(): FloatArray {
	val max = maxOf(red, green, blue)
	val min = minOf(red, green, blue)
	val delta = max - min
	val l = (max + min) / 2f
	val s = if (delta == 0f) 0f else delta / (1 - abs(2 * l - 1))
	val h = if (delta == 0f) 0f else when (max) {
		red -> ((green - blue) / delta + (if (green < blue) 6 else 0)) / 6f
		green -> ((blue - red) / delta + 2) / 6f
		else -> ((red - green) / delta + 4) / 6f
	}
	return floatArrayOf(h, s, l)
}

/**
 * HSL 转 RGB
 */
private fun hslToRgb(h: Float, s: Float, l: Float): Color {
	val c = (1 - abs(2 * l - 1)) * s
	val x = c * (1 - abs((h * 6) % 2 - 1))
	val m = l - c / 2
	
	val (r, g, b) = when {
		h < 1f / 6f -> Triple(c, x, 0f)
		h < 2f / 6f -> Triple(x, c, 0f)
		h < 3f / 6f -> Triple(0f, c, x)
		h < 4f / 6f -> Triple(0f, x, c)
		h < 5f / 6f -> Triple(x, 0f, c)
		else -> Triple(c, 0f, x)
	}
	
	return Color(r + m, g + m, b + m)
}