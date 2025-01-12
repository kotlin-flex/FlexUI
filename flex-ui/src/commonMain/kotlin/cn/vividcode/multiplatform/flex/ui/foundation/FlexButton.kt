package cn.vividcode.multiplatform.flex.ui.foundation

import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import cn.vividcode.multiplatform.flex.ui.config.LocalFlex
import cn.vividcode.multiplatform.flex.ui.config.button.FlexButtonConfig
import cn.vividcode.multiplatform.flex.ui.config.theme.FlexColorType
import cn.vividcode.multiplatform.flex.ui.config.theme.FlexSizeType
import cn.vividcode.multiplatform.flex.ui.expends.dashedBorder
import cn.vividcode.multiplatform.flex.ui.expends.isDark
import cn.vividcode.multiplatform.flex.ui.foundation.ButtonType.*

@Composable
fun FlexButton(
	text: String,
	modifier: Modifier = Modifier,
	sizeType: FlexSizeType = FlexButtons.DefaultSize,
	colorType: FlexColorType = FlexButtons.DefaultColor,
	buttonType: ButtonType = FlexButtons.DefaultButtonType,
	icon: ImageVector? = null,
	iconDirection: ButtonIconDirection = FlexButtons.DefaultIconDirection,
	onClick: () -> Unit,
) {
	val current = LocalFlex.current
	val config = current.button.getButton(sizeType)
	val color = current.theme.colorScheme.current.getColor(colorType)
	val interactionSource = remember { MutableInteractionSource() }
	
	Row(
		modifier = modifier
			.height(config.height)
			.clip(config.cornerShape)
			.buttonStyle(buttonType, config, color)
			.clickable(
				interactionSource = interactionSource,
				indication = LocalIndication.current,
				onClick = onClick
			)
			.padding(
				horizontal = config.padding
			),
		verticalAlignment = Alignment.CenterVertically,
		horizontalArrangement = Arrangement.Center
	) {
		if (icon != null && iconDirection == ButtonIconDirection.Left) {
			val size = config.fontSize.value.dp
			Icon(
				imageVector = icon,
				tint = color,
				contentDescription = null,
				modifier = Modifier
					.padding(end = size / 4)
					.size(config.fontSize.value.dp)
			)
		}
		val textColor = when (buttonType) {
			Primary -> if (color.isDark) Color.White else Color.Black
			Default -> Color.Black
			Dashed -> Color.Black
			Link -> color
		}
		Text(
			text = text,
			modifier = Modifier
				.focusable(),
			color = textColor,
			fontSize = config.fontSize,
			fontWeight = config.fontWeight
		)
		if (icon != null && iconDirection == ButtonIconDirection.Right) {
			val size = config.fontSize.value.dp
			Icon(
				imageVector = icon,
				tint = color,
				contentDescription = null,
				modifier = Modifier
					.padding(start = size / 4)
					.size(config.fontSize.value.dp)
			)
		}
	}
}

/**
 * 设置按钮样式
 */
private fun Modifier.buttonStyle(
	buttonType: ButtonType,
	config: FlexButtonConfig,
	color: Color,
): Modifier {
	return when (buttonType) {
		Primary -> this.background(
			color = color,
			shape = config.cornerShape
		)
		
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
	}
}

object FlexButtons {
	
	val DefaultSize = FlexSizeType.Medium
	
	val DefaultColor = FlexColorType.Default
	
	val DefaultIconDirection = ButtonIconDirection.Right
	
	val DefaultButtonType = Default
}

enum class ButtonIconDirection {
	
	Left,
	
	Right
}

enum class ButtonType {
	
	Primary,
	
	Default,
	
	Dashed,
	
	Link
}