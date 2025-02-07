package cn.vividcode.multiplatform.flex.ui.config.foundation

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import cn.vividcode.multiplatform.flex.ui.config.FlexSizeDefaults

class FlexSliderConfig internal constructor(
	var thickness: Dp,
	var sliderThickness: Dp,
	var thumbBorderWidth: Dp,
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
			thumbBorderWidth = 12.dp * THUMB_BORDER_WIDTH_SCALE
		)
	
	/**
	 * 默认小尺寸按钮配置
	 */
	override val DefaultSmall: FlexSliderConfig
		get() = FlexSliderConfig(
			thickness = 16.dp,
			sliderThickness = 16.dp * SLIDER_THICKNESS_SCALE,
			thumbBorderWidth = 16.dp * THUMB_BORDER_WIDTH_SCALE
		)
	
	/**
	 * 默认中尺寸按钮配置
	 */
	override val DefaultMedium: FlexSliderConfig
		get() = FlexSliderConfig(
			thickness = 20.dp,
			sliderThickness = 20.dp * SLIDER_THICKNESS_SCALE,
			thumbBorderWidth = 20.dp * THUMB_BORDER_WIDTH_SCALE
		)
	
	/**
	 * 默认大尺寸按钮配置
	 */
	override val DefaultLarge: FlexSliderConfig
		get() = FlexSliderConfig(
			thickness = 24.dp,
			sliderThickness = 24.dp * SLIDER_THICKNESS_SCALE,
			thumbBorderWidth = 24.dp * THUMB_BORDER_WIDTH_SCALE
		)
	
	/**
	 * 默认大尺寸按钮配置
	 */
	override val DefaultExtraLarge: FlexSliderConfig
		get() = FlexSliderConfig(
			thickness = 28.dp,
			sliderThickness = 28.dp * SLIDER_THICKNESS_SCALE,
			thumbBorderWidth = 28.dp * THUMB_BORDER_WIDTH_SCALE
		)
}