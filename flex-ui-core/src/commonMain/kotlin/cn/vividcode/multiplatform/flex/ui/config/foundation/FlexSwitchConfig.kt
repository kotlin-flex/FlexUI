package cn.vividcode.multiplatform.flex.ui.config.foundation

import androidx.compose.ui.text.font.FontWeight
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
	var fontSize: TextUnit,
	var fontWeight: FontWeight,
	var letterSpacing: TextUnit,
	var iconSize: Dp,
)

internal object FlexSwitchSizeDefaults : FlexSizeDefaults<FlexSwitchConfig> {
	
	private const val ICON_SIZE_SCALE = 1f / 2f
	private const val PADDING_SCALE = 1f / 12f
	
	/**
	 * 默认超小开关开关配置
	 */
	override val DefaultExtraSmall: FlexSwitchConfig
		get() = FlexSwitchConfig(
			height = 24.dp,
			padding = 24.dp * PADDING_SCALE,
			fontSize = 12.sp,
			fontWeight = FontWeight.Medium,
			letterSpacing = TextUnit.Unspecified,
			iconSize = 24.dp * ICON_SIZE_SCALE,
		)
	
	/**
	 * 默认小尺寸开关配置
	 */
	override val DefaultSmall: FlexSwitchConfig
		get() = FlexSwitchConfig(
			height = 32.dp,
			padding = 32.dp * PADDING_SCALE,
			fontSize = 16.sp,
			fontWeight = FontWeight.Medium,
			letterSpacing = TextUnit.Unspecified,
			iconSize = 32.dp * ICON_SIZE_SCALE
		)
	
	/**
	 * 默认中尺寸开关配置
	 */
	override val DefaultMedium: FlexSwitchConfig
		get() = FlexSwitchConfig(
			height = 40.dp,
			padding = 40.dp * PADDING_SCALE,
			fontSize = 20.sp,
			fontWeight = FontWeight.Medium,
			letterSpacing = TextUnit.Unspecified,
			iconSize = 40.dp * ICON_SIZE_SCALE
		)
	
	/**
	 * 默认大尺寸开关配置
	 */
	override val DefaultLarge: FlexSwitchConfig
		get() = FlexSwitchConfig(
			height = 48.dp,
			padding = 48.dp * PADDING_SCALE,
			fontSize = 24.sp,
			fontWeight = FontWeight.SemiBold,
			letterSpacing = TextUnit.Unspecified,
			iconSize = 48.dp * ICON_SIZE_SCALE
		)
	
	/**
	 * 默认大尺寸开关配置
	 */
	override val DefaultExtraLarge: FlexSwitchConfig
		get() = FlexSwitchConfig(
			height = 56.dp,
			padding = 56.dp * PADDING_SCALE,
			fontSize = 28.sp,
			fontWeight = FontWeight.SemiBold,
			letterSpacing = TextUnit.Unspecified,
			iconSize = 56.dp * ICON_SIZE_SCALE,
		)
}