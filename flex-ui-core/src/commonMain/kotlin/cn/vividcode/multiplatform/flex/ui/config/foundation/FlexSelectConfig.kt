package cn.vividcode.multiplatform.flex.ui.config.foundation

import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cn.vividcode.multiplatform.flex.ui.config.FlexSizeDefaults

/**
 * 选择器配置
 */
@ConsistentCopyVisibility
data class FlexSelectConfig internal constructor(
	var minWidth: Dp,
	var height: Dp,
	var horizontalPadding: Dp,
	var borderWidth: Dp,
	var fontSize: TextUnit,
	var fontWeight: FontWeight,
	var letterSpacing: TextUnit,
	var iconSize: Dp,
	var intervalWithPopup: Dp,
	var heightWithPopup: Dp,
	var paddingWithPopup: Dp,
	var heightWhenOptionEmptyWithPopup: Dp,
	var maxHeightWithPopup: Dp
)

internal object FlexSelectSizeDefaults : FlexSizeDefaults<FlexSelectConfig>() {
	
	override val DefaultMedium: FlexSelectConfig by lazy {
		FlexSelectConfig(
			minWidth = 150.dp,
			height = 36.dp,
			horizontalPadding = 10.dp,
			borderWidth = 1.5.dp,
			fontSize = 14.sp,
			fontWeight = FontWeight.Normal,
			letterSpacing = TextUnit.Unspecified,
			iconSize = 24.dp,
			intervalWithPopup = 4.dp,
			heightWithPopup = 36.dp,
			paddingWithPopup = 6.dp,
			heightWhenOptionEmptyWithPopup = 80.dp,
			maxHeightWithPopup = 192.dp
		)
	}
	
	override fun FlexSelectConfig.scale(scale: Float) = this.copy(
		minWidth = minWidth * scale,
		height = height * scale,
		horizontalPadding = horizontalPadding * scale,
		borderWidth = borderWidth * scale,
		fontSize = fontSize * scale,
		iconSize = iconSize * scale,
		intervalWithPopup = intervalWithPopup * scale,
		heightWithPopup = heightWithPopup * scale,
		paddingWithPopup = paddingWithPopup * scale,
		heightWhenOptionEmptyWithPopup = heightWhenOptionEmptyWithPopup * scale,
		maxHeightWithPopup = maxHeightWithPopup * scale
	)
}