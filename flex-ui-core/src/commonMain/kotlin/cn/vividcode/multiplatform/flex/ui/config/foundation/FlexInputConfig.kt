package cn.vividcode.multiplatform.flex.ui.config.foundation

import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cn.vividcode.multiplatform.flex.ui.config.FlexSizeDefaults

/**
 * 输入框配置
 */
class FlexInputConfig internal constructor(
	var minWidth: Dp,
	var height: Dp,
	var horizontalPadding: Dp,
	var borderWidth: Dp,
	var fontSize: TextUnit,
	var fontWeight: FontWeight,
	var letterSpacing: TextUnit,
	var iconSize: Dp,
)

internal object FlexInputSizeDefaults : FlexSizeDefaults<FlexInputConfig> {
	
	private const val ICON_SIZE_SCALE = 0.52f
	private const val FONT_SIZE_SCALE = 0.4f
	
	/**
	 * 默认超小尺寸输入框配置
	 */
	override val DefaultExtraSmall: FlexInputConfig
		get() = FlexInputConfig(
			minWidth = 96.dp,
			height = 24.dp,
			horizontalPadding = 8.dp,
			borderWidth = 1.dp,
			fontSize = 24.sp * FONT_SIZE_SCALE,
			fontWeight = FontWeight.Normal,
			letterSpacing = TextUnit.Unspecified,
			iconSize = 24.dp * ICON_SIZE_SCALE
		)
	
	/**
	 * 默认小尺寸输入框配置
	 */
	override val DefaultSmall: FlexInputConfig
		get() = FlexInputConfig(
			minWidth = 128.dp,
			height = 32.dp,
			horizontalPadding = 10.dp,
			borderWidth = 1.5.dp,
			fontSize = 32.sp * FONT_SIZE_SCALE,
			fontWeight = FontWeight.Normal,
			letterSpacing = TextUnit.Unspecified,
			iconSize = 32.dp * ICON_SIZE_SCALE
		)
	
	/**
	 * 默认中尺寸输入框配置
	 */
	override val DefaultMedium: FlexInputConfig
		get() = FlexInputConfig(
			minWidth = 160.dp,
			height = 40.dp,
			horizontalPadding = 12.dp,
			borderWidth = 1.5.dp,
			fontSize = 40.sp * FONT_SIZE_SCALE,
			fontWeight = FontWeight.Normal,
			letterSpacing = TextUnit.Unspecified,
			iconSize = 40.dp * ICON_SIZE_SCALE
		)
	
	/**
	 * 默认大尺寸输入框配置
	 */
	override val DefaultLarge: FlexInputConfig
		get() = FlexInputConfig(
			minWidth = 192.dp,
			height = 48.dp,
			horizontalPadding = 14.dp,
			borderWidth = 1.5.dp,
			fontSize = 48.sp * FONT_SIZE_SCALE,
			fontWeight = FontWeight.Normal,
			letterSpacing = TextUnit.Unspecified,
			iconSize = 48.dp * ICON_SIZE_SCALE
		)
	
	/**
	 * 默认大尺寸输入框配置
	 */
	override val DefaultExtraLarge: FlexInputConfig
		get() = FlexInputConfig(
			minWidth = 224.dp,
			height = 56.dp,
			horizontalPadding = 16.dp,
			borderWidth = 2.dp,
			fontSize = 56.sp * FONT_SIZE_SCALE,
			fontWeight = FontWeight.Normal,
			letterSpacing = TextUnit.Unspecified,
			iconSize = 56.dp * ICON_SIZE_SCALE
		)
}