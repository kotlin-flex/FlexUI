package cn.vividcode.multiplatform.flex.ui.config.foundation

import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cn.vividcode.multiplatform.flex.ui.config.FlexSizeDefaults

/**
 * 按钮配置
 */
class FlexButtonConfig internal constructor(
	var height: Dp,
	var horizontalPadding: Dp,
	var borderWidth: Dp,
	var fontSize: TextUnit,
	var fontWeight: FontWeight,
	var letterSpacing: TextUnit,
	var iconSize: Dp,
	var iconInterval: Dp,
)

internal object FlexButtonSizeDefaults : FlexSizeDefaults<FlexButtonConfig> {
	
	private const val ICON_SIZE_SCALE = 2 / 3f
	private const val FONT_SIZE_SCALE = 2 / 5f
	
	/**
	 * 默认超小尺寸按钮配置
	 */
	override val DefaultExtraSmall: FlexButtonConfig
		get() = FlexButtonConfig(
			height = 20.dp,
			horizontalPadding = 6.dp,
			borderWidth = 1.dp,
			fontSize = 20.sp * FONT_SIZE_SCALE,
			fontWeight = FontWeight.Normal,
			letterSpacing = TextUnit.Unspecified,
			iconSize = 20.dp * ICON_SIZE_SCALE,
			iconInterval = 2.dp
		)
	
	/**
	 * 默认小尺寸按钮配置
	 */
	override val DefaultSmall: FlexButtonConfig
		get() = FlexButtonConfig(
			height = 28.dp,
			horizontalPadding = 8.dp,
			borderWidth = 1.5.dp,
			fontSize = 28.sp * FONT_SIZE_SCALE,
			fontWeight = FontWeight.Normal,
			letterSpacing = TextUnit.Unspecified,
			iconSize = 28.dp * ICON_SIZE_SCALE,
			iconInterval = 4.dp
		)
	
	/**
	 * 默认中尺寸按钮配置
	 */
	override val DefaultMedium: FlexButtonConfig
		get() = FlexButtonConfig(
			height = 36.dp,
			horizontalPadding = 10.dp,
			borderWidth = 1.5.dp,
			fontSize = 36.sp * FONT_SIZE_SCALE,
			fontWeight = FontWeight.Normal,
			letterSpacing = TextUnit.Unspecified,
			iconSize = 36.dp * ICON_SIZE_SCALE,
			iconInterval = 6.dp
		)
	
	/**
	 * 默认大尺寸按钮配置
	 */
	override val DefaultLarge: FlexButtonConfig
		get() = FlexButtonConfig(
			height = 44.dp,
			horizontalPadding = 12.dp,
			borderWidth = 1.5.dp,
			fontSize = 44.sp * FONT_SIZE_SCALE,
			fontWeight = FontWeight.Normal,
			letterSpacing = TextUnit.Unspecified,
			iconSize = 44.dp * ICON_SIZE_SCALE,
			iconInterval = 8.dp
		)
	
	/**
	 * 默认大尺寸按钮配置
	 */
	override val DefaultExtraLarge: FlexButtonConfig
		get() = FlexButtonConfig(
			height = 52.dp,
			horizontalPadding = 14.dp,
			borderWidth = 2.dp,
			fontSize = 52.sp * FONT_SIZE_SCALE,
			fontWeight = FontWeight.Normal,
			letterSpacing = TextUnit.Unspecified,
			iconSize = 52.dp * ICON_SIZE_SCALE,
			iconInterval = 10.dp
		)
}