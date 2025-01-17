package cn.vividcode.multiplatform.flex.ui.foundation.button

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import cn.vividcode.multiplatform.flex.ui.config.LocalFlexConfig
import cn.vividcode.multiplatform.flex.ui.config.foundation.FlexButtonConfig
import cn.vividcode.multiplatform.flex.ui.config.type.FlexColorType
import cn.vividcode.multiplatform.flex.ui.config.type.FlexCornerType
import cn.vividcode.multiplatform.flex.ui.config.type.FlexSizeType
import cn.vividcode.multiplatform.flex.ui.expends.brightness
import cn.vividcode.multiplatform.flex.ui.expends.dashedBorder
import cn.vividcode.multiplatform.flex.ui.expends.isDark

/**
 * FlexButton 按钮
 *
 * @param text 文本
 * @param icon 图标
 * @param modifier [Modifier]
 * @param sizeType 尺寸类型
 * @param colorType 颜色类型
 * @param cornerType 圆角类型
 * @param buttonType 按钮类型
 * @param iconPosition 图标位置
 * @param iconRotation 图标旋转
 * @param enabledScale 启用悬停按下缩放
 * @param enabled 是否禁用
 * @param onClick 点击事件
 */
@Composable
fun FlexButton(
	text: String = "",
	icon: ImageVector? = null,
	modifier: Modifier = Modifier,
	sizeType: FlexSizeType = FlexButtons.DefaultSizeType,
	colorType: FlexColorType = FlexButtons.DefaultColorType,
	cornerType: FlexCornerType = FlexButtons.DefaultCornerType,
	buttonType: FlexButtonType = FlexButtons.DefaultButtonType,
	iconPosition: FlexButtonIconPosition = FlexButtons.DefaultIconDirection,
	iconRotation: Float = FlexButtons.DefaultIconRotation,
	enabledScale: Boolean = FlexButtons.DefaultEnabledScale,
	enabled: Boolean = true,
	onClick: () -> Unit = {},
) {
	val current = LocalFlexConfig.current
	val config = current.button.getConfig(sizeType)
	val colorScheme = current.theme.colorScheme.current
	val color = colorScheme.getColor(colorType)
	val interactionSource = remember { MutableInteractionSource() }
	val isHovered by interactionSource.collectIsHoveredAsState()
	val isPressed by interactionSource.collectIsPressedAsState()
	val targetColor by animateColorAsState(
		targetValue = getTargetColor(color, buttonType, enabled, isPressed, isHovered)
	)
	val horizontalPadding = if (text.isNotEmpty()) config.horizontalPadding else Dp.Hairline
	val cornerShape = when (cornerType) {
		FlexCornerType.None -> RectangleShape
		FlexCornerType.Circle -> CircleShape
		else -> RoundedCornerShape(config.height * cornerType.percent)
	}
	val targetScale by animateFloatAsState(
		targetValue = if (!enabledScale) 1f else when {
			isPressed -> 0.99f
			isHovered -> 1.015f
			else -> 1f
		}
	)
	val layoutDirection = LocalLayoutDirection.current
	CompositionLocalProvider(
		LocalLayoutDirection provides when {
			text.isBlank() || iconPosition == FlexButtonIconPosition.Start -> layoutDirection
			else -> if (layoutDirection == LayoutDirection.Ltr) LayoutDirection.Rtl else LayoutDirection.Ltr
		}
	) {
		Row(
			modifier = modifier
				.scale(targetScale)
				.let {
					if (text.isNotEmpty()) it else it.width(
						width = config.height
					)
				}
				.height(config.height)
				.clip(cornerShape)
				.customStyle(buttonType, config, cornerShape, targetColor)
				.clickable(
					interactionSource = interactionSource,
					indication = null,
					enabled = enabled,
					onClick = onClick
				)
				.let {
					if (text.isBlank()) it else it.padding(
						start = horizontalPadding,
						end = horizontalPadding
					)
				},
			verticalAlignment = Alignment.CenterVertically,
			horizontalArrangement = Arrangement.Center
		) {
			val targetFontColor by animateColorAsState(
				targetValue = when (buttonType) {
					FlexButtonType.Primary -> when (color.isDark) {
						true -> if (enabled) Color.White else Color.White.copy(alpha = 0.9f)
						false -> if (enabled) Color.Black else Color.Black.copy(alpha = 0.9f)
					}
					
					FlexButtonType.Filled, FlexButtonType.Text -> {
						if (enabled) color else color.copy(alpha = 0.8f)
					}
					
					FlexButtonType.Link -> {
						when {
							!enabled -> color.copy(alpha = 0.8f)
							isPressed -> color.brightness(0.85f)
							isHovered -> color.brightness(1.2f)
							else -> color
						}
					}
					
					else -> {
						when {
							!enabled -> color.copy(alpha = 0.6f)
							isPressed -> color.brightness(0.9f)
							isHovered -> color.brightness(1.15f)
							else -> color
						}
					}
				}
			)
			if (icon != null) {
				CompositionLocalProvider(
					LocalLayoutDirection provides layoutDirection
				) {
					Icon(
						imageVector = icon,
						tint = targetFontColor,
						contentDescription = null,
						modifier = Modifier
							.padding(
								start = if (text.isBlank()) Dp.Hairline else config.iconInterval
							)
							.rotate(iconRotation)
							.size(config.iconSize)
					)
				}
			}
			CompositionLocalProvider(
				LocalLayoutDirection provides layoutDirection,
			) {
				Text(
					text = text.trim(),
					color = targetFontColor,
					fontSize = config.fontSize,
					fontWeight = config.fontWeight,
					letterSpacing = config.letterSpacing,
					lineHeight = config.fontSize,
					overflow = TextOverflow.Ellipsis,
					maxLines = 1
				)
			}
		}
	}
}

/**
 * 设置按钮样式
 */
@Composable
private fun Modifier.customStyle(
	buttonType: FlexButtonType,
	config: FlexButtonConfig,
	cornerShape: Shape,
	color: Color,
): Modifier {
	return when (buttonType) {
		FlexButtonType.Default -> this.border(
			width = config.borderWidth,
			color = color,
			shape = cornerShape
		)
		
		FlexButtonType.Dashed -> this.dashedBorder(
			width = config.borderWidth,
			color = color,
			shape = cornerShape
		)
		
		FlexButtonType.Link -> this
		
		else -> this.background(
			color = color,
			shape = cornerShape
		)
	}
}

private fun getTargetColor(
	color: Color,
	buttonType: FlexButtonType,
	enabled: Boolean,
	isPressed: Boolean,
	isHovered: Boolean,
): Color = when (buttonType) {
	FlexButtonType.Primary -> {
		when {
			!enabled -> color.copy(alpha = 0.6f)
			isPressed -> color.brightness(0.95f)
			isHovered -> color.brightness(1.1f)
			else -> color
		}
	}
	
	FlexButtonType.Default, FlexButtonType.Dashed -> {
		when {
			!enabled -> color.copy(alpha = 0.6f)
			isPressed -> color.brightness(0.9f)
			isHovered -> color.brightness(1.15f)
			else -> color
		}
	}
	
	FlexButtonType.Filled -> {
		when {
			!enabled -> color.copy(alpha = 0.08f)
			isPressed -> color.copy(alpha = 0.2f)
			isHovered -> color.copy(alpha = 0.15f)
			else -> color.copy(alpha = 0.1f)
		}
	}
	
	FlexButtonType.Text -> {
		when {
			isPressed -> color.copy(alpha = 0.2f)
			isHovered -> color.copy(alpha = 0.1f)
			else -> Color.Transparent
		}
	}
	
	else -> Color.Transparent
}

@Suppress("ConstPropertyName")
object FlexButtons {
	
	val DefaultSizeType = FlexSizeType.Medium
	
	val DefaultColorType = FlexColorType.Default
	
	val DefaultCornerType = FlexCornerType.Default
	
	val DefaultIconDirection = FlexButtonIconPosition.End
	
	val DefaultButtonType = FlexButtonType.Default
	
	const val DefaultIconRotation = 0f
	
	const val DefaultEnabledScale = true
}

enum class FlexButtonIconPosition {
	
	Start,
	
	End
}

enum class FlexButtonType {
	
	Primary,
	
	Default,
	
	Dashed,
	
	Filled,
	
	Text,
	
	Link,
}