package cn.vividcode.multiplatform.flex.ui.config.foundation

import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cn.vividcode.multiplatform.flex.ui.config.FlexDefaultSizeConfig

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
) {
	
	internal companion object : FlexDefaultSizeConfig<FlexRadioConfig> {
		
		/**
		 * 默认超小尺寸单选框配置
		 */
		override val DefaultExtraSmall: FlexRadioConfig
			get() = FlexRadioConfig(
				height = 24.dp,
				horizontalPadding = 8.dp,
				borderWidth = 1.dp,
				fontSize = 10.sp,
				fontWeight = FontWeight.Medium,
				letterSpacing = TextUnit.Unspecified
			)
		
		/**
		 * 默认小尺寸单选框配置
		 */
		override val DefaultSmall: FlexRadioConfig
			get() = FlexRadioConfig(
				height = 32.dp,
				horizontalPadding = 10.dp,
				borderWidth = 1.5.dp,
				fontSize = 13.sp,
				fontWeight = FontWeight.Medium,
				letterSpacing = TextUnit.Unspecified
			)
		
		/**
		 * 默认中尺寸单选框配置
		 */
		override val DefaultMedium: FlexRadioConfig
			get() = FlexRadioConfig(
				height = 40.dp,
				horizontalPadding = 12.dp,
				borderWidth = 1.5.dp,
				fontSize = 16.sp,
				fontWeight = FontWeight.Medium,
				letterSpacing = TextUnit.Unspecified
			)
		
		/**
		 * 默认大尺寸单选框配置
		 */
		override val DefaultLarge: FlexRadioConfig
			get() = FlexRadioConfig(
				height = 48.dp,
				horizontalPadding = 14.dp,
				borderWidth = 1.5.dp,
				fontSize = 19.sp,
				fontWeight = FontWeight.SemiBold,
				letterSpacing = TextUnit.Unspecified
			)
		
		/**
		 * 默认大尺寸单选框配置
		 */
		override val DefaultExtraLarge: FlexRadioConfig
			get() = FlexRadioConfig(
				height = 56.dp,
				horizontalPadding = 16.dp,
				borderWidth = 2.dp,
				fontSize = 22.5.sp,
				fontWeight = FontWeight.SemiBold,
				letterSpacing = TextUnit.Unspecified
			)
	}
}