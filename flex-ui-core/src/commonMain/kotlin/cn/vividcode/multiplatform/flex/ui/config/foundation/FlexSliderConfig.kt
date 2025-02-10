package cn.vividcode.multiplatform.flex.ui.config.foundation

import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cn.vividcode.multiplatform.flex.ui.config.FlexSizeDefaults

class FlexSliderConfig internal constructor(
	var thickness: Dp,
	var sliderThickness: Dp,
	var thumbBorderWidth: Dp,
	var toolbarFontSize: TextUnit,
	var toolbarFontWeight: FontWeight,
	var toolbarFontLetterSpacing: TextUnit,
	var toolbarHeight: Dp,
	var toolbarHorizontalPadding: Dp,
)

internal object FlexSliderSizeDefaults : FlexSizeDefaults<FlexSliderConfig> {
	
	private const val SLIDER_THICKNESS_SCALE = 1f / 3f
	private const val THUMB_BORDER_WIDTH_SCALE = 1f / 7f
	
	/**
	 * 默认超小尺寸按钮配置
	 */
	override val DefaultExtraSmall: FlexSliderConfig
		get() = FlexSliderConfig(
			thickness = 12.dp,
			sliderThickness = 12.dp * SLIDER_THICKNESS_SCALE,
			thumbBorderWidth = 12.dp * THUMB_BORDER_WIDTH_SCALE,
			toolbarFontSize = 9.sp,
			toolbarFontWeight = FontWeight.Medium,
			toolbarFontLetterSpacing = TextUnit.Unspecified,
			toolbarHeight = 20.dp,
			toolbarHorizontalPadding = 4.dp
		)
	
	/**
	 * 默认小尺寸按钮配置
	 */
	override val DefaultSmall: FlexSliderConfig
		get() = FlexSliderConfig(
			thickness = 15.dp,
			sliderThickness = 15.dp * SLIDER_THICKNESS_SCALE,
			thumbBorderWidth = 15.dp * THUMB_BORDER_WIDTH_SCALE,
			toolbarFontSize = 12.sp,
			toolbarFontWeight = FontWeight.Medium,
			toolbarFontLetterSpacing = TextUnit.Unspecified,
			toolbarHeight = 28.dp,
			toolbarHorizontalPadding = 6.dp
		)
	
	/**
	 * 默认中尺寸按钮配置
	 */
	override val DefaultMedium: FlexSliderConfig
		get() = FlexSliderConfig(
			thickness = 18.dp,
			sliderThickness = 18.dp * SLIDER_THICKNESS_SCALE,
			thumbBorderWidth = 18.dp * THUMB_BORDER_WIDTH_SCALE,
			toolbarFontSize = 15.sp,
			toolbarFontWeight = FontWeight.Medium,
			toolbarFontLetterSpacing = TextUnit.Unspecified,
			toolbarHeight = 36.dp,
			toolbarHorizontalPadding = 8.dp
		)
	
	/**
	 * 默认大尺寸按钮配置
	 */
	override val DefaultLarge: FlexSliderConfig
		get() = FlexSliderConfig(
			thickness = 21.dp,
			sliderThickness = 21.dp * SLIDER_THICKNESS_SCALE,
			thumbBorderWidth = 21.dp * THUMB_BORDER_WIDTH_SCALE,
			toolbarFontSize = 18.sp,
			toolbarFontWeight = FontWeight.Medium,
			toolbarFontLetterSpacing = TextUnit.Unspecified,
			toolbarHeight = 44.dp,
			toolbarHorizontalPadding = 10.dp
		)
	
	/**
	 * 默认大尺寸按钮配置
	 */
	override val DefaultExtraLarge: FlexSliderConfig
		get() = FlexSliderConfig(
			thickness = 24.dp,
			sliderThickness = 24.dp * SLIDER_THICKNESS_SCALE,
			thumbBorderWidth = 24.dp * THUMB_BORDER_WIDTH_SCALE,
			toolbarFontSize = 21.sp,
			toolbarFontWeight = FontWeight.Medium,
			toolbarFontLetterSpacing = TextUnit.Unspecified,
			toolbarHeight = 52.dp,
			toolbarHorizontalPadding = 12.dp
		)
}