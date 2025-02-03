package cn.vividcode.multiplatform.flex.ui.config.type

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.compositionLocalOf
import cn.vividcode.multiplatform.flex.ui.config.LocalFlexConfig

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
	 * 中圆角，1/4
	 */
	Medium(0.25f),
	
	/**
	 * 大圆角，1/3
	 */
	Large(0.333f),
	
	/**
	 * 完整的圆角，1/2
	 */
	Circle(0.5f)
}

internal val LocalFlexCornerType = compositionLocalOf<FlexCornerType?> { null }

@Composable
internal fun getDefaultCornerType(
	defaultCornerType: FlexComposeDefaultConfig.() -> FlexCornerType?,
): FlexCornerType = LocalFlexCornerType.current
	?: LocalFlexConfig.current.default.let {
		it.defaultCornerType()
			?: it.common.cornerType
			?: FlexCornerType.Circle
	}