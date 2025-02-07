package cn.vividcode.multiplatform.flex.ui.foundation.slider

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import cn.vividcode.multiplatform.flex.ui.config.LocalFlexConfig
import cn.vividcode.multiplatform.flex.ui.config.type.*

/**
 * FlexSlider 滑动条
 */
@Composable
fun FlexSlider(
	value: Int,
	onValueChange: (Int) -> Unit,
	modifier: Modifier = Modifier,
	sizeType: FlexSizeType = FlexSliderDefaults.DefaultSizeType,
	colorType: FlexColorType = FlexSliderDefaults.DefaultColorType,
	cornerType: FlexCornerType = FlexSliderDefaults.DefaultCornerType,
	direction: FlexSliderDirection = FlexSliderDefaults.DefaultSliderDirection,
	minValue: Int = 0,
	maxValue: Int = 100,
	steps: Int = 1,
) {
	val config = LocalFlexConfig.current.slider.getConfig(sizeType)
	var length by remember { mutableStateOf(Dp.Hairline) }
	val density = LocalDensity.current
	val thickness by animateDpAsState(config.thickness)
	Box(
		modifier = modifier
			.matchDirection(
				direction = direction,
				horizontal = {
					Modifier
						.fillMaxWidth()
						.height(thickness)
				},
				vertical = {
					Modifier
						.width(thickness)
						.fillMaxHeight()
				}
			)
			.onGloballyPositioned {
				length = with(density) {
					direction.value(
						horizontal = { it.size.width.toDp() },
						vertical = { it.size.height.toDp() }
					)
				}
			},
		contentAlignment = direction.value(
			horizontal = { Alignment.CenterStart },
			vertical = { Alignment.TopCenter }
		)
	) {
		val sliderThickness by animateDpAsState(config.sliderThickness)
		val sliderCorner by animateDpAsState(sliderThickness * cornerType.scale)
		val sliderCornerShape by remember(sliderCorner) {
			derivedStateOf { RoundedCornerShape(sliderCorner) }
		}
		Box(
			modifier = Modifier
				.matchDirection(
					direction = direction,
					horizontal = {
						Modifier
							.fillMaxWidth()
							.height(sliderThickness)
					},
					vertical = {
						Modifier
							.width(sliderThickness)
							.fillMaxHeight()
					}
				)
				.clip(sliderCornerShape)
				.background(
					color = MaterialTheme.colorScheme.surfaceVariant,
					shape = sliderCornerShape
				)
		)
		val thumbOffsetStart by remember(length, config.thickness, maxValue, minValue, value) {
			val value = (length - config.thickness) / (maxValue - minValue) * value.coerceIn(minValue .. maxValue)
			mutableStateOf(value)
		}
		Box(
			modifier = Modifier
				.size(
					width = direction.value(
						horizontal = { thumbOffsetStart + config.thickness },
						vertical = { sliderThickness }
					),
					height = direction.value(
						horizontal = { sliderThickness },
						vertical = { thumbOffsetStart + config.thickness }
					),
				)
				.clip(sliderCornerShape)
				.background(
					color = colorType.backgroundColor,
					shape = sliderCornerShape
				)
		)
		val thumbCorner by animateDpAsState(
			targetValue = config.thickness * cornerType.scale
		)
		val thumbCornerShape by remember(thumbCorner) {
			derivedStateOf { RoundedCornerShape(thumbCorner) }
		}
		val thumbBorderWidth by animateDpAsState(config.thumbBorderWidth)
		Box(
			modifier = Modifier
				.matchDirection(
					direction = direction,
					horizontal = { Modifier.offset(x = thumbOffsetStart) },
					vertical = { Modifier.offset(y = thumbOffsetStart) }
				)
				.matchDirection(
					direction = direction,
					horizontal = { Modifier.offset(x = thumbOffsetStart) },
					vertical = { Modifier.offset(y = thumbOffsetStart) }
				)
				.size(thickness)
				.clip(thumbCornerShape)
				.border(
					width = thumbBorderWidth,
					color = colorType.backgroundColor,
					shape = thumbCornerShape
				)
				.background(
					color = colorType.contentColor,
					shape = thumbCornerShape
				)
		)
	}
}


object FlexSliderDefaults : FlexDefaults(
	defaultCornerType = FlexCornerType.Circle
) {
	override val FlexComposeDefaultConfig.defaultConfig
		get() = this.slider
	
	val DefaultSliderDirection = FlexSliderDirection.Horizontal
}

enum class FlexSliderDirection {
	
	Horizontal,
	
	Vertical
}

private fun <T> FlexSliderDirection.value(
	horizontal: () -> T,
	vertical: () -> T,
): T = when (this) {
	FlexSliderDirection.Horizontal -> horizontal()
	FlexSliderDirection.Vertical -> vertical()
}

private fun Modifier.matchDirection(
	direction: FlexSliderDirection,
	horizontal: Modifier.() -> Modifier,
	vertical: Modifier.() -> Modifier,
): Modifier = when (direction) {
	FlexSliderDirection.Horizontal -> horizontal()
	FlexSliderDirection.Vertical -> vertical()
}