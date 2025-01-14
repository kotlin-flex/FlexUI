package cn.vividcode.multiplatform.flex.ui.config.theme

import androidx.compose.runtime.Stable

/**
 * 圆角类型
 */
@Stable
enum class FlexCornerType(
	internal val percent: Float,
) {
	
	/**
	 * 无圆角，0/8
	 */
	None(0f),
	
	/**
	 * 小圆角，1/8
	 */
	Small(0.125f),
	
	/**
	 * 默认圆角，1/4
	 */
	Default(0.25f),
	
	/**
	 * 大圆角，1/3
	 */
	Large(0.333f),
	
	/**
	 * 完整的圆角，1/2
	 */
	Circle(0.5f)
}