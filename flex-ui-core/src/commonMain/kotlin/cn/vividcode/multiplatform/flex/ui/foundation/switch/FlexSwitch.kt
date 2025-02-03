package cn.vividcode.multiplatform.flex.ui.foundation.switch

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import cn.vividcode.multiplatform.flex.ui.config.LocalFlexConfig
import cn.vividcode.multiplatform.flex.ui.config.type.*
import cn.vividcode.multiplatform.flex.ui.theme.LocalDarkTheme

@Composable
fun FlexSwitch(
	checked: Boolean,
	onCheckedChange: (Boolean) -> Unit,
	modifier: Modifier = Modifier,
	sizeType: FlexSizeType = FlexSwitchDefaults.DefaultSizeType,
	colorType: FlexColorType = FlexSwitchDefaults.DefaultColorType,
	cornerType: FlexCornerType = FlexSwitchDefaults.DefaultCornerType,
) {
	val current = LocalFlexConfig.current
	val config = current.switch.getConfig(sizeType)
	val targetColor = current.theme.colorScheme.current.getColor(colorType)
	val height by animateDpAsState(config.height)
	val padding by animateDpAsState(config.padding)
	val corner by animateDpAsState(config.height * cornerType.percent)
	val cornerShape by remember(corner) {
		derivedStateOf { RoundedCornerShape(corner) }
	}
	val interactionSource = remember { MutableInteractionSource() }
	val isHovered by interactionSource.collectIsHoveredAsState()
	val backgroundColor by animateColorAsState(
		targetValue = run {
			val color = when {
				checked -> targetColor
				LocalDarkTheme.current -> Color.DarkGray
				else -> Color.LightGray
			}
			if (isHovered) color.copy(alpha = 0.85f) else color
		}
	)
	Box(
		modifier = modifier
			.width(height * 2)
			.height(height)
			.clip(cornerShape)
			.background(
				color = backgroundColor,
				shape = cornerShape
			)
			.clickable(
				interactionSource = interactionSource,
				indication = null,
				onClick = { onCheckedChange(!checked) }
			)
			.padding(padding),
		contentAlignment = Alignment.CenterStart,
	) {
		val isPressed by interactionSource.collectIsPressedAsState()
		val offsetX by animateDpAsState(
			targetValue = when {
				!checked -> Dp.Hairline
				isPressed -> height - config.padding * 2
				else -> height
			}
		)
		val checkBoxCorner by animateDpAsState(
			targetValue = (config.height - config.padding * 2) * cornerType.percent
		)
		val checkBoxCornerShape by remember(checkBoxCorner) {
			derivedStateOf { RoundedCornerShape(checkBoxCorner) }
		}
		val width by animateDpAsState(
			targetValue = if (isPressed) config.height else config.height - config.padding * 2
		)
		Box(
			modifier = Modifier
				.offset(x = offsetX)
				.width(width)
				.height(height - padding * 2)
				.clip(checkBoxCornerShape)
				.background(
					color = Color.White,
					shape = checkBoxCornerShape
				)
		)
	}
}

object FlexSwitchDefaults : FlexDefaults() {
	
	override val FlexComposeDefaultConfig.defaultConfig: FlexDefaultConfig
		get() = this.switch
}