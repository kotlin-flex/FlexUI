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
data class FlexRadioConfig internal constructor(
	var height: Dp,
	var horizontalPadding: Dp,
	var borderWidth: Dp,
	var fontSize: TextUnit,
	var fontWeight: FontWeight,
	var letterSpacing: TextUnit,
)

internal object FlexRadioSizeDefaults : FlexSizeDefaults<FlexRadioConfig>() {
	
	override val DefaultMedium: FlexRadioConfig
		get() = FlexRadioConfig(
			height = 36.dp,
			horizontalPadding = 10.dp,
			borderWidth = 1.5.dp,
			fontSize = 14.sp,
			fontWeight = FontWeight.Normal,
			letterSpacing = TextUnit.Unspecified
		)
	
	override fun FlexRadioConfig.scale(scale: Float): FlexRadioConfig {
		return this.copy(
			height = height * scale,
			horizontalPadding = horizontalPadding * scale,
			borderWidth = borderWidth * scale,
			fontSize = fontSize * scale
		)
	}
}