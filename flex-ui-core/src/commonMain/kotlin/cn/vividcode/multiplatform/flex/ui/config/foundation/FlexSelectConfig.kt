package cn.vividcode.multiplatform.flex.ui.config.foundation

import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.isUnspecified
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
	var tagHeight: Dp,
	var tagFontSize: TextUnit,
	var tagFontWeight: FontWeight,
	var tagLetterSpacing: TextUnit,
	var tagIconSize: Dp,
	var tagHorizontalPadding: Dp,
	var popupHeight: Dp,
	var popupMaxHeight: Dp,
	var popupHeightWithEmpty: Dp,
	var popupPadding: Dp,
	var popupInterval: Dp,
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
			tagHeight = 24.dp,
			tagFontSize = 12.sp,
			tagFontWeight = FontWeight.Normal,
			tagLetterSpacing = TextUnit.Unspecified,
			tagIconSize = 20.dp,
			tagHorizontalPadding = 4.dp,
			popupHeight = 36.dp,
			popupMaxHeight = 192.dp,
			popupHeightWithEmpty = 80.dp,
			popupPadding = 6.dp,
			popupInterval = 4.dp,
		)
	}
	
	override fun FlexSelectConfig.scale(scale: Float) = this.copy(
		minWidth = minWidth * scale,
		height = height * scale,
		horizontalPadding = horizontalPadding * scale,
		borderWidth = borderWidth * scale,
		fontSize = fontSize * scale,
		letterSpacing = letterSpacing.scale(scale),
		iconSize = iconSize * scale,
		tagHeight = tagHeight * scale,
		tagFontSize = tagFontSize * scale,
		tagLetterSpacing = tagLetterSpacing.scale(scale),
		tagIconSize = tagIconSize,
		popupHeight = popupHeight * scale,
		popupMaxHeight = popupMaxHeight * scale,
		popupHeightWithEmpty = popupHeightWithEmpty * scale,
		popupPadding = popupPadding * scale,
		popupInterval = popupInterval * scale,
	)
}