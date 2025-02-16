package cn.vividcode.multiplatform.flex.ui.config.foundation

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import cn.vividcode.multiplatform.flex.ui.config.FlexSizeDefaults

/**
 * 选择器配置
 */
class FlexSelectConfig internal constructor(
	var height: Dp,
)

internal object FlexSelectSizeDefaults : FlexSizeDefaults<FlexSelectConfig> {
	
	/**
	 * 默认超小尺寸按钮配置
	 */
	override val DefaultExtraSmall: FlexSelectConfig
		get() = FlexSelectConfig(
			height = 20.dp
		)
	
	/**
	 * 默认小尺寸按钮配置
	 */
	override val DefaultSmall: FlexSelectConfig
		get() = FlexSelectConfig(
			height = 28.dp
		)
	
	/**
	 * 默认中尺寸按钮配置
	 */
	override val DefaultMedium: FlexSelectConfig
		get() = FlexSelectConfig(
			height = 36.dp
		)
	
	/**
	 * 默认大尺寸按钮配置
	 */
	override val DefaultLarge: FlexSelectConfig
		get() = FlexSelectConfig(
			height = 44.dp
		)
	
	/**
	 * 默认大尺寸按钮配置
	 */
	override val DefaultExtraLarge: FlexSelectConfig
		get() = FlexSelectConfig(
			height = 52.dp
		)
}