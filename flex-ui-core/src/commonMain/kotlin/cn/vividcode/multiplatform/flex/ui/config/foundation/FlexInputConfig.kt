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
	
	private const val ICON_SIZE_SCALE = 1 / 2f
	private const val FONT_SIZE_SCALE = 2 / 5f
	
	/**
	 * 默认超小尺寸输入框配置
	 */
	override val DefaultExtraSmall: FlexInputConfig
		get() = FlexInputConfig(
			minWidth = 80.dp,
			height = 20.dp,
			horizontalPadding = 6.dp,
			borderWidth = 1.dp,
			fontSize = 20.sp * FONT_SIZE_SCALE,
			fontWeight = FontWeight.Normal,
			letterSpacing = TextUnit.Unspecified,
			iconSize = 20.dp * ICON_SIZE_SCALE
		)
	
	/**
	 * 默认小尺寸输入框配置
	 */
	override val DefaultSmall: FlexInputConfig
		get() = FlexInputConfig(
			minWidth = 112.dp,
			height = 28.dp,
			horizontalPadding = 8.dp,
			borderWidth = 1.5.dp,
			fontSize = 28.sp * FONT_SIZE_SCALE,
			fontWeight = FontWeight.Normal,
			letterSpacing = TextUnit.Unspecified,
			iconSize = 28.dp * ICON_SIZE_SCALE
		)
	
	/**
	 * 默认中尺寸输入框配置
	 */
	override val DefaultMedium: FlexInputConfig
		get() = FlexInputConfig(
			minWidth = 144.dp,
			height = 36.dp,
			horizontalPadding = 10.dp,
			borderWidth = 1.5.dp,
			fontSize = 36.sp * FONT_SIZE_SCALE,
			fontWeight = FontWeight.Normal,
			letterSpacing = TextUnit.Unspecified,
			iconSize = 36.dp * ICON_SIZE_SCALE
		)
	
	/**
	 * 默认大尺寸输入框配置
	 */
	override val DefaultLarge: FlexInputConfig
		get() = FlexInputConfig(
			minWidth = 176.dp,
			height = 44.dp,
			horizontalPadding = 12.dp,
			borderWidth = 1.5.dp,
			fontSize = 44.sp * FONT_SIZE_SCALE,
			fontWeight = FontWeight.Normal,
			letterSpacing = TextUnit.Unspecified,
			iconSize = 44.dp * ICON_SIZE_SCALE
		)
	
	/**
	 * 默认大尺寸输入框配置
	 */
	override val DefaultExtraLarge: FlexInputConfig
		get() = FlexInputConfig(
			minWidth = 208.dp,
			height = 52.dp,
			horizontalPadding = 14.dp,
			borderWidth = 2.dp,
			fontSize = 52.sp * FONT_SIZE_SCALE,
			fontWeight = FontWeight.Normal,
			letterSpacing = TextUnit.Unspecified,
			iconSize = 52.dp * ICON_SIZE_SCALE
		)
}