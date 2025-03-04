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
data class FlexInputConfig internal constructor(
	var minWidth: Dp,
	var height: Dp,
	var horizontalPadding: Dp,
	var borderWidth: Dp,
	var fontSize: TextUnit,
	var fontWeight: FontWeight,
	var letterSpacing: TextUnit,
	var iconSize: Dp,
)

internal object FlexInputSizeDefaults : FlexSizeDefaults<FlexInputConfig>() {
	
	override val DefaultMedium: FlexInputConfig
		get() = FlexInputConfig(
			minWidth = 150.dp,
			height = 36.dp,
			horizontalPadding = 10.dp,
			borderWidth = 1.5.dp,
			fontSize = 14.sp,
			fontWeight = FontWeight.Normal,
			letterSpacing = TextUnit.Unspecified,
			iconSize = 24.dp
		)
	
	override fun FlexInputConfig.scale(scale: Float): FlexInputConfig {
		return this.copy(
			minWidth = minWidth * scale,
			height = height * scale,
			horizontalPadding = horizontalPadding * scale,
			borderWidth = borderWidth * scale,
			fontSize = fontSize * scale,
			iconSize = iconSize * scale
		)
	}
}