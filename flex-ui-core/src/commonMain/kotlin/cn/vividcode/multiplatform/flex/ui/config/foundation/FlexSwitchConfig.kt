package cn.vividcode.multiplatform.flex.ui.config.foundation

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cn.vividcode.multiplatform.flex.ui.config.FlexSizeDefaults

/**
 * 开关配置
 */
data class FlexSwitchConfig internal constructor(
	var height: Dp,
	var padding: Dp,
	var textLabelSize: TextUnit,
	var iconLabelSize: Dp,
)

internal object FlexSwitchSizeDefaults : FlexSizeDefaults<FlexSwitchConfig>() {
	
	override val DefaultMedium: FlexSwitchConfig by lazy {
		FlexSwitchConfig(
			height = 36.dp,
			padding = 3.dp,
			textLabelSize = 14.sp,
			iconLabelSize = 24.dp
		)
	}
	
	override fun FlexSwitchConfig.scale(scale: Float): FlexSwitchConfig {
		return this.copy(
			height = height * scale,
			padding = padding * scale,
			textLabelSize = textLabelSize * scale,
			iconLabelSize = iconLabelSize * scale
		)
	}
}