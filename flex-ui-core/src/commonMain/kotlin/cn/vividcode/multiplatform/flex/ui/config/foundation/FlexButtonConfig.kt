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
data class FlexButtonConfig internal constructor(
	var height: Dp,
	var horizontalPadding: Dp,
	var borderWidth: Dp,
	var fontSize: TextUnit,
	var fontWeight: FontWeight,
	var letterSpacing: TextUnit,
	var iconSize: Dp,
	var iconInterval: Dp,
)

internal object FlexButtonSizeDefaults : FlexSizeDefaults<FlexButtonConfig>() {
	
	override val DefaultMedium: FlexButtonConfig by lazy {
		FlexButtonConfig(
			height = 36.dp,
			horizontalPadding = 10.dp,
			borderWidth = 1.5.dp,
			fontSize = 14.sp,
			fontWeight = FontWeight.Normal,
			letterSpacing = TextUnit.Unspecified,
			iconSize = 24.dp,
			iconInterval = 6.dp
		)
	}
	
	override fun FlexButtonConfig.scale(scale: Float): FlexButtonConfig {
		return this.copy(
			height = height * scale,
			horizontalPadding = horizontalPadding * scale,
			borderWidth = borderWidth * scale,
			fontSize = fontSize * scale,
			iconSize = iconSize * scale,
			iconInterval = iconInterval * scale
		)
	}
}