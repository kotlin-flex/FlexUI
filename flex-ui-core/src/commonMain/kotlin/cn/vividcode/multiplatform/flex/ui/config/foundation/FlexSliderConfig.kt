package cn.vividcode.multiplatform.flex.ui.config.foundation

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.spring
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cn.vividcode.multiplatform.flex.ui.config.FlexSizeDefaults

data class FlexSliderConfig internal constructor(
	var thickness: Dp,
	var sliderThickness: Dp,
	var thumbBorderWidth: Dp,
	var toolbarFontSize: TextUnit,
	var toolbarFontWeight: FontWeight,
	var toolbarFontLetterSpacing: TextUnit,
	var toolbarHeight: Dp,
	var toolbarHorizontalPadding: Dp,
	var toolbarAnimationSpec: AnimationSpec<Float>,
	var markBorderWidth: Dp,
	var markFontSize: TextUnit,
	var markFontWeight: FontWeight,
	var markInterval: Dp,
)

internal object FlexSliderSizeDefaults : FlexSizeDefaults<FlexSliderConfig>() {
	
	private val DefaultAnimationSpec = spring<Float>(
		stiffness = 4000f,
	)
	
	/**
	 * 默认中尺寸按钮配置
	 */
	override val DefaultMedium: FlexSliderConfig by lazy {
		FlexSliderConfig(
			thickness = 18.dp,
			sliderThickness = 5.dp,
			thumbBorderWidth = 2.5.dp,
			toolbarFontSize = 15.sp,
			toolbarFontWeight = FontWeight.Normal,
			toolbarFontLetterSpacing = TextUnit.Unspecified,
			toolbarHeight = 36.dp,
			toolbarHorizontalPadding = 8.dp,
			toolbarAnimationSpec = DefaultAnimationSpec,
			markBorderWidth = 2.dp,
			markFontSize = 15.sp,
			markFontWeight = FontWeight.Normal,
			markInterval = 6.dp
		)
	}
	
	override fun FlexSliderConfig.scale(scale: Float): FlexSliderConfig {
		return this.copy(
			thickness = thickness * scale,
			sliderThickness = sliderThickness * scale,
			thumbBorderWidth = thumbBorderWidth * scale,
			toolbarFontSize = toolbarFontSize * scale,
			toolbarHeight = toolbarHeight * scale,
			toolbarHorizontalPadding = toolbarHorizontalPadding * scale,
			markBorderWidth = markBorderWidth * scale,
			markFontSize = markFontSize * scale,
			markInterval = markInterval * scale
		)
	}
}