package cn.vividcode.multiplatform.flex.ui.config.type

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.compositionLocalOf
import cn.vividcode.multiplatform.flex.ui.config.LocalFlexConfig

/**
 * 尺寸类型
 */
@Stable
enum class FlexSizeType {
	
	/**
	 * 超小尺寸
	 */
	ExtraSmall,
	
	/**
	 * 小尺寸
	 */
	Small,
	
	/**
	 * 中尺寸（默认）
	 */
	Medium,
	
	/**
	 * 大尺寸
	 */
	Large,
	
	/**
	 * 超大尺寸
	 */
	ExtraLarge
}

internal val LocalFlexSizeType = compositionLocalOf<FlexSizeType?> { null }

@Composable
internal fun getDefaultSizeType(
	defaultSizeType: FlexComposeDefaultConfig.() -> FlexSizeType?,
): FlexSizeType = LocalFlexSizeType.current
	?: LocalFlexConfig.current.default.let {
		it.defaultSizeType()
			?: it.common.sizeType
			?: FlexSizeType.Medium
	}