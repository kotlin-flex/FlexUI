package cn.vividcode.multiplatform.flex.ui.config.button

import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class FlexButtonConfig internal constructor(
	var height: Dp,
	var padding: Dp,
	var cornerShape: CornerBasedShape,
	var borderWidth: Dp,
	var fontSize: TextUnit,
	var fontWeight: FontWeight,
	var iconSize: Dp,
) {
	
	internal companion object {
		
		/**
		 * 默认迷你尺寸按钮配置
		 */
		val DefaultMiniButton: FlexButtonConfig
			get() = FlexButtonConfig(
				height = 32.dp,
				padding = 11.dp,
				cornerShape = RoundedCornerShape(5.dp),
				borderWidth = 1.5.dp,
				fontSize = 12.sp,
				fontWeight = FontWeight.Medium,
				iconSize = 18.dp
			)
		
		// 默认小尺寸按钮配置
		val DefaultSmallButton: FlexButtonConfig
			get() = FlexButtonConfig(
				height = 36.dp,
				padding = 12.dp,
				cornerShape = RoundedCornerShape(6.dp),
				borderWidth = 1.5.dp,
				fontSize = 14.sp,
				fontWeight = FontWeight.Medium,
				iconSize = 21.dp,
			)
		
		// 默认中尺寸按钮配置
		val DefaultMediumButton: FlexButtonConfig
			get() = FlexButtonConfig(
				height = 40.dp,
				padding = 16.dp,
				cornerShape = RoundedCornerShape(8.dp),
				borderWidth = 1.5.dp,
				fontSize = 16.sp,
				fontWeight = FontWeight.Medium,
				iconSize = 24.dp
			)
		
		// 默认大尺寸按钮配置
		val DefaultLargeButton: FlexButtonConfig
			get() = FlexButtonConfig(
				height = 44.dp,
				padding = 20.dp,
				cornerShape = RoundedCornerShape(10.dp),
				borderWidth = 1.5.dp,
				fontSize = 18.sp,
				fontWeight = FontWeight.Medium,
				iconSize = 27.dp
			)
	}
}