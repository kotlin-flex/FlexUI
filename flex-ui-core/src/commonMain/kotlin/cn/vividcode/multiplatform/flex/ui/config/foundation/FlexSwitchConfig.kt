package cn.vividcode.multiplatform.flex.ui.config.foundation

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cn.vividcode.multiplatform.flex.ui.config.FlexSizeDefaults

/**
 * 开关配置
 */
class FlexSwitchConfig internal constructor(
	var height: Dp,
	var padding: Dp,
	var textLabelSize: TextUnit,
	var iconLabelSize: Dp,
)

internal object FlexSwitchSizeDefaults : FlexSizeDefaults<FlexSwitchConfig> {
	
	private const val PADDING_SCALE = 1f / 12f
	private const val TEXT_LABEL_SIZE_SCALE = 4f / 9f
	private const val ICON_LABEL_SIZE_SCALE = 3f / 4f
	
	/**
	 * 默认超小开关开关配置
	 */
	override val DefaultExtraSmall: FlexSwitchConfig
		get() = FlexSwitchConfig(
			height = 20.dp,
			padding = 20.dp * PADDING_SCALE,
			textLabelSize = 20.sp * TEXT_LABEL_SIZE_SCALE,
			iconLabelSize = 20.dp * ICON_LABEL_SIZE_SCALE,
		)
	
	/**
	 * 默认小尺寸开关配置
	 */
	override val DefaultSmall: FlexSwitchConfig
		get() = FlexSwitchConfig(
			height = 28.dp,
			padding = 28.dp * PADDING_SCALE,
			textLabelSize = 28.sp * TEXT_LABEL_SIZE_SCALE,
			iconLabelSize = 28.dp * ICON_LABEL_SIZE_SCALE,
		)
	
	/**
	 * 默认中尺寸开关配置
	 */
	override val DefaultMedium: FlexSwitchConfig
		get() = FlexSwitchConfig(
			height = 36.dp,
			padding = 36.dp * PADDING_SCALE,
			textLabelSize = 36.sp * TEXT_LABEL_SIZE_SCALE,
			iconLabelSize = 36.dp * ICON_LABEL_SIZE_SCALE,
		)
	
	/**
	 * 默认大尺寸开关配置
	 */
	override val DefaultLarge: FlexSwitchConfig
		get() = FlexSwitchConfig(
			height = 44.dp,
			padding = 44.dp * PADDING_SCALE,
			textLabelSize = 44.sp * TEXT_LABEL_SIZE_SCALE,
			iconLabelSize = 44.dp * ICON_LABEL_SIZE_SCALE,
		)
	
	/**
	 * 默认大尺寸开关配置
	 */
	override val DefaultExtraLarge: FlexSwitchConfig
		get() = FlexSwitchConfig(
			height = 52.dp,
			padding = 52.dp * PADDING_SCALE,
			textLabelSize = 52.sp * TEXT_LABEL_SIZE_SCALE,
			iconLabelSize = 52.dp * ICON_LABEL_SIZE_SCALE,
		)
}