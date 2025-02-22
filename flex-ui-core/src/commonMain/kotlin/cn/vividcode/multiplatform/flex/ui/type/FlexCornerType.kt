package cn.vividcode.multiplatform.flex.ui.type

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.compositionLocalOf
import cn.vividcode.multiplatform.flex.ui.config.FlexComposeDefaultConfig
import cn.vividcode.multiplatform.flex.ui.config.LocalFlexConfig

/**
 * 圆角类型
 */
@Stable
enum class FlexCornerType(
	internal val scale: Float,
) {
	
	/**
	 * 无圆角 0/16
	 */
	None(NONE_SCALE),
	
	/**
	 * 超小圆角 1/16
	 */
	ExtraSmall(EXTRA_SMALL_SCALE),
	
	/**
	 * 小圆角 2/16
	 */
	Small(SMALL_SCALE),
	
	/**
	 * 中圆角 4/16
	 */
	Medium(MEDIUM_SCALE),
	
	/**
	 * 大圆角 6/16
	 */
	Large(LARGE_SCALE),
	
	/**
	 * 超大圆角 7/16
	 */
	ExtraLarge(EXTRA_LARGE_SCALE),
	
	/**
	 * 最大圆角 8/16
	 */
	Circle(CIRCLE_SCALE)
}

private const val NONE_SCALE = 0f / 12f

private const val EXTRA_SMALL_SCALE = 1f / 12f

private const val SMALL_SCALE = 2f / 12f

private const val MEDIUM_SCALE = 3f / 12f

private const val LARGE_SCALE = 4f / 12f

private const val EXTRA_LARGE_SCALE = 5f / 12f

private const val CIRCLE_SCALE = 6f / 12f

internal val LocalFlexCornerType = compositionLocalOf<FlexCornerType?> { null }

@Composable
internal fun getDefaultCornerType(
	defaultCornerType: FlexCornerType = FlexCornerType.Medium,
	composeDefaultCornerType: FlexComposeDefaultConfig.() -> FlexCornerType?,
): FlexCornerType = LocalFlexCornerType.current
	?: LocalFlexConfig.current.default.let {
		it.composeDefaultCornerType()
			?: it.common?.cornerType
			?: defaultCornerType
	}