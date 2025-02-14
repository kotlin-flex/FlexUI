package cn.vividcode.multiplatform.flex.ui.config.foundation

import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cn.vividcode.multiplatform.flex.ui.config.FlexSizeDefaults

/**
 * 单选框组配置
 */
class FlexRadioConfig internal constructor(
	var height: Dp,
	var horizontalPadding: Dp,
	var borderWidth: Dp,
	var fontSize: TextUnit,
	var fontWeight: FontWeight,
	var letterSpacing: TextUnit,
)

internal object FlexRadioSizeDefaults : FlexSizeDefaults<FlexRadioConfig> {
	
	private const val FONT_SIZE_SCALE = 2 / 5f
	
	/**
	 * 默认超小尺寸单选框配置
	 */
	override val DefaultExtraSmall: FlexRadioConfig
		get() = FlexRadioConfig(
			height = 20.dp,
			horizontalPadding = 6.dp,
			borderWidth = 1.dp,
			fontSize = 20.sp * FONT_SIZE_SCALE,
			fontWeight = FontWeight.Medium,
			letterSpacing = TextUnit.Unspecified
		)
	
	/**
	 * 默认小尺寸单选框配置
	 */
	override val DefaultSmall: FlexRadioConfig
		get() = FlexRadioConfig(
			height = 28.dp,
			horizontalPadding = 8.dp,
			borderWidth = 1.5.dp,
			fontSize = 28.sp * FONT_SIZE_SCALE,
			fontWeight = FontWeight.Medium,
			letterSpacing = TextUnit.Unspecified
		)
	
	/**
	 * 默认中尺寸单选框配置
	 */
	override val DefaultMedium: FlexRadioConfig
		get() = FlexRadioConfig(
			height = 36.dp,
			horizontalPadding = 10.dp,
			borderWidth = 1.5.dp,
			fontSize = 36.sp * FONT_SIZE_SCALE,
			fontWeight = FontWeight.Medium,
			letterSpacing = TextUnit.Unspecified
		)
	
	/**
	 * 默认大尺寸单选框配置
	 */
	override val DefaultLarge: FlexRadioConfig
		get() = FlexRadioConfig(
			height = 44.dp,
			horizontalPadding = 12.dp,
			borderWidth = 1.5.dp,
			fontSize = 44.sp * FONT_SIZE_SCALE,
			fontWeight = FontWeight.SemiBold,
			letterSpacing = TextUnit.Unspecified
		)
	
	/**
	 * 默认大尺寸单选框配置
	 */
	override val DefaultExtraLarge: FlexRadioConfig
		get() = FlexRadioConfig(
			height = 52.dp,
			horizontalPadding = 14.dp,
			borderWidth = 2.dp,
			fontSize = 52.sp * FONT_SIZE_SCALE,
			fontWeight = FontWeight.SemiBold,
			letterSpacing = TextUnit.Unspecified
		)
}