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
) {
	
	companion object Defaults {
		
		// 默认小尺寸按钮配置
		val smallButton: FlexButtonConfig
			get() = FlexButtonConfig(
				height = 36.dp,
				padding = 12.dp,
				cornerShape = RoundedCornerShape(6.dp),
				borderWidth = 2.dp,
				fontSize = 14.sp,
				fontWeight = FontWeight.Normal
			)
		
		// 默认中尺寸按钮配置
		val mediumButton: FlexButtonConfig
			get() = FlexButtonConfig(
				height = 40.dp,
				padding = 16.dp,
				cornerShape = RoundedCornerShape(8.dp),
				borderWidth = 2.dp,
				fontSize = 16.sp,
				fontWeight = FontWeight.Normal
			)
		
		// 默认大尺寸按钮配置
		val largeButton: FlexButtonConfig
			get() = FlexButtonConfig(
				height = 44.dp,
				padding = 20.dp,
				cornerShape = RoundedCornerShape(10.dp),
				borderWidth = 2.dp,
				fontSize = 18.sp,
				fontWeight = FontWeight.Normal
			)
	}
}