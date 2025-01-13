package cn.vividcode.multiplatform.flex.ui.foundation

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import cn.vividcode.multiplatform.flex.ui.config.LocalFlex
import cn.vividcode.multiplatform.flex.ui.config.button.FlexButtonConfig
import cn.vividcode.multiplatform.flex.ui.config.theme.FlexColorType
import cn.vividcode.multiplatform.flex.ui.config.theme.FlexSizeType
import cn.vividcode.multiplatform.flex.ui.expends.brightness
import cn.vividcode.multiplatform.flex.ui.expends.dashedBorder
import cn.vividcode.multiplatform.flex.ui.expends.isDark
import cn.vividcode.multiplatform.flex.ui.foundation.ButtonType.*

@Composable
fun FlexButton(
	text: String = "",
	modifier: Modifier = Modifier,
	sizeType: FlexSizeType = FlexButtons.DefaultSize,
	colorType: FlexColorType = FlexButtons.DefaultColor,
	buttonType: ButtonType = FlexButtons.DefaultButtonType,
	icon: ImageVector? = null,
	iconDirection: ButtonIconDirection = FlexButtons.DefaultIconDirection,
	enabled: Boolean = true,
	onClick: () -> Unit,
) {
	val current = LocalFlex.current
	val config = current.button.getButton(sizeType)
	val colorScheme = current.theme.colorScheme.current
	val color = colorScheme.getColor(colorType)
	val interactionSource = remember { MutableInteractionSource() }
	val isHovered by interactionSource.collectIsHoveredAsState()
	val isPressed by interactionSource.collectIsPressedAsState()
	val backgroundColor by animateColorAsState(
		targetValue = when (buttonType) {
			Primary -> {
				when {
					!enabled -> color.brightness(1.2f)
					isPressed -> color.brightness(0.95f)
					isHovered -> color.brightness(1.1f)
					else -> color
				}
			}
			
			Default, Dashed -> {
				when {
					!enabled -> color.brightness(1.25f)
					isPressed -> color.brightness(0.9f)
					isHovered -> color.brightness(1.15f)
					else -> color
				}
			}
			
			Filled -> {
				when {
					!enabled -> color.copy(alpha = 0.2f)
					isPressed -> color.copy(alpha = 0.25f)
					isHovered -> color.copy(alpha = 0.4f)
					else -> color.copy(alpha = 0.3f)
				}
			}
			
			Text -> {
				when {
					isPressed -> color.copy(alpha = 0.25f)
					isHovered -> color.copy(alpha = 0.3f)
					else -> Color.Transparent
				}
			}
			
			else -> Color.Transparent
		}
	)
	val startPadding = config.padding / when {
		icon == null || iconDirection == ButtonIconDirection.End || text.isEmpty() -> 1
		else -> 2
	}
	val endPadding = config.padding / when {
		icon == null || iconDirection == ButtonIconDirection.Start || text.isEmpty() -> 1
		else -> 2
	}
	Row(
		modifier = modifier
			.let {
				if (text.isNotEmpty()) it else it.width(
					width = config.height
				)
			}
			.height(config.height)
			.clip(config.cornerShape)
			.customStyle(buttonType, config, backgroundColor)
			.clickable(
				interactionSource = interactionSource,
				indication = null,
				enabled = enabled,
				onClick = onClick
			)
			.let {
				if (text.isEmpty()) it else it.padding(
					start = startPadding,
					end = endPadding
				)
			},
		verticalAlignment = Alignment.CenterVertically,
		horizontalArrangement = Arrangement.Center
	) {
		val fontColor = when (buttonType) {
			Primary -> if (color.isDark) Color.White else colorScheme.default
			Filled, Text -> color
			else -> {
				when {
					!enabled -> color.brightness(1.2f)
					isPressed -> color.brightness(0.9f)
					isHovered -> color.brightness(1.15f)
					else -> color
				}
			}
		}
		if (icon != null && iconDirection == ButtonIconDirection.Start) {
			Icon(
				imageVector = icon,
				tint = fontColor,
				contentDescription = null,
				modifier = Modifier
					.padding(end = if (text.isEmpty()) Dp.Hairline else startPadding)
					.size(config.iconSize)
			)
		}
		Text(
			text = text,
			color = fontColor,
			fontSize = config.fontSize,
			fontWeight = config.fontWeight,
			lineHeight = config.fontSize
		)
		if (icon != null && iconDirection == ButtonIconDirection.End) {
			Icon(
				imageVector = icon,
				tint = fontColor,
				contentDescription = null,
				modifier = Modifier
					.padding(start = if (text.isEmpty()) Dp.Hairline else endPadding)
					.size(config.iconSize)
			)
		}
	}
}

/**
 * 设置按钮样式
 */
@Composable
private fun Modifier.customStyle(
	buttonType: ButtonType,
	config: FlexButtonConfig,
	color: Color,
): Modifier {
	return when (buttonType) {
		Default -> this.border(
			width = config.borderWidth,
			color = color,
			shape = config.cornerShape
		)
		
		Dashed -> this.dashedBorder(
			width = config.borderWidth,
			color = color,
			shape = config.cornerShape
		)
		
		Link -> this
		
		else -> this.background(
			color = color,
			shape = config.cornerShape,
		)
	}
}

object FlexButtons {
	
	val DefaultSize = FlexSizeType.Medium
	
	val DefaultColor = FlexColorType.Default
	
	val DefaultIconDirection = ButtonIconDirection.End
	
	val DefaultButtonType = Default
}

enum class ButtonIconDirection {
	
	Start,
	
	End
}

enum class ButtonType {
	
	Primary,
	
	Default,
	
	Dashed,
	
	Filled,
	
	Text,
	
	Link,
}