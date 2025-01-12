package cn.vividcode.multiplatform.flex.ui.expends

import androidx.compose.ui.graphics.Color

private const val RED_WEIGHT = 0.299

private const val GREEN_WEIGHT = 0.587

private const val BLUE_WEIGHT = 0.114

/**
 * 是否是暗色
 */
internal val Color.isDark: Boolean
	get() = this.red * RED_WEIGHT + this.green * GREEN_WEIGHT + blue * BLUE_WEIGHT < 0.5