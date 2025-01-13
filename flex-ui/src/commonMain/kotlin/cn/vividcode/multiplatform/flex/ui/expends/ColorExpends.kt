package cn.vividcode.multiplatform.flex.ui.expends

import androidx.compose.ui.graphics.Color
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

private const val RED_WEIGHT = 0.299

private const val GREEN_WEIGHT = 0.587

private const val BLUE_WEIGHT = 0.114

/**
 * 是否是暗色
 */
internal val Color.isDark: Boolean
	get() = (this.red * RED_WEIGHT + this.green * GREEN_WEIGHT + this.blue * BLUE_WEIGHT) < 0.5

/**
 * 颜色变量
 * @param factor 因子, < 1: 变暗, > 1: 变亮
 */
internal fun Color.brightness(factor: Float): Color {
	val hsl = this.rgbToHsl()
	val h = hsl[0]
	val s = hsl[1]
	val l = min(1f, max(0f, hsl[2] * factor))
	return hslToRgb(h, s, l)
}

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
	val c = (1 - kotlin.math.abs(2 * l - 1)) * s
	val x = c * (1 - kotlin.math.abs((h * 6) % 2 - 1))
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