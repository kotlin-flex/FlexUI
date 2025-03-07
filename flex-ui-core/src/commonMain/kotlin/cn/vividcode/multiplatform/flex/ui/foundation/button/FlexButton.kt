package cn.vividcode.multiplatform.flex.ui.foundation.button

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import cn.vividcode.multiplatform.flex.ui.config.FlexComposeDefaultConfig
import cn.vividcode.multiplatform.flex.ui.config.FlexDefaults
import cn.vividcode.multiplatform.flex.ui.config.LocalFlexConfig
import cn.vividcode.multiplatform.flex.ui.foundation.icon.FlexIcon
import cn.vividcode.multiplatform.flex.ui.type.*
import cn.vividcode.multiplatform.flex.ui.utils.animateFlexBrushAsState
import cn.vividcode.multiplatform.flex.ui.utils.background
import cn.vividcode.multiplatform.flex.ui.utils.border
import cn.vividcode.multiplatform.flex.ui.utils.dashedBorder

/**
 * FlexButton 按钮
 *
 * @param text 文本
 * @param icon 图标
 * @param modifier [Modifier]
 * @param sizeType 尺寸类型
 * @param brushType 颜色类型
 * @param cornerType 圆角类型
 * @param buttonType 按钮类型
 * @param iconPosition 图标位置
 * @param iconRotation 图标旋转
 * @param scaleEffect 启用悬停按下缩放
 * @param enabled 是否禁用
 * @param onClick 点击事件
 */
@Composable
fun FlexButton(
	text: String = "",
	icon: ImageVector? = null,
	modifier: Modifier = Modifier,
	sizeType: FlexSizeType = FlexButtonDefaults.DefaultSizeType,
	brushType: FlexBrushType = FlexButtonDefaults.DefaultBrushType,
	cornerType: FlexCornerType = FlexButtonDefaults.DefaultCornerType,
	buttonType: FlexButtonType = FlexButtonDefaults.DefaultButtonType,
	iconPosition: FlexButtonIconPosition = FlexButtonDefaults.DefaultIconDirection,
	iconRotation: Float = 0f,
	scaleEffect: Boolean = false,
	enabled: Boolean = true,
	onClick: () -> Unit = {},
) {
	val layoutDirection = LocalLayoutDirection.current
	CompositionLocalProvider(
		LocalLayoutDirection provides when {
			text.isBlank() || iconPosition == FlexButtonIconPosition.Start -> layoutDirection
			else -> if (layoutDirection == LayoutDirection.Ltr) LayoutDirection.Rtl else LayoutDirection.Ltr
		}
	) {
		val config = LocalFlexConfig.current.button.getConfig(sizeType)
		val interactionSource = remember { MutableInteractionSource() }
		val isHovered by interactionSource.collectIsHoveredAsState()
		val isPressed by interactionSource.collectIsPressedAsState()
		val borderBrush by animateFlexBrushAsState(
			targetValue = when (buttonType) {
				FlexButtonType.Default, FlexButtonType.Dashed -> when {
					!enabled -> brushType.disabledBrush
					isPressed -> brushType.darkenBrush
					isHovered -> brushType.lightenBrush
					else -> brushType.brush
				}
				
				else -> brushType.transparentBrush
			}
		)
		val backgroundBrush by animateFlexBrushAsState(
			targetValue = when (buttonType) {
				FlexButtonType.Primary -> {
					when {
						!enabled -> brushType.disabledBrush
						isPressed -> brushType.darkenBrush
						isHovered -> brushType.lightenBrush
						else -> brushType.brush
					}
				}
				
				FlexButtonType.Filled -> {
					when {
						!enabled -> brushType.brush.copy(alpha = 0.08f)
						isPressed -> brushType.brush.copy(alpha = 0.2f)
						isHovered -> brushType.brush.copy(alpha = 0.15f)
						else -> brushType.brush.copy(alpha = 0.1f)
					}
				}
				
				FlexButtonType.Text -> {
					when {
						!enabled -> brushType.transparentBrush
						isPressed -> brushType.brush.copy(alpha = 0.2f)
						isHovered -> brushType.brush.copy(alpha = 0.1f)
						else -> brushType.transparentBrush
					}
				}
				
				else -> brushType.transparentBrush
			},
			label = text
		)
		
		val horizontalPadding by animateDpAsState(
			targetValue = if (text.isNotEmpty()) config.horizontalPadding else Dp.Hairline
		)
		val height by animateDpAsState(config.height)
		val corner by animateDpAsState(height * cornerType.scale)
		val cornerShape by remember(cornerType, corner) {
			derivedStateOf {
				RoundedCornerShape(corner)
			}
		}
		val targetScale by remember(scaleEffect, isPressed, isHovered) {
			derivedStateOf {
				when {
					!scaleEffect -> 1f
					isPressed -> 0.992f
					isHovered -> 1.012f
					else -> 1f
				}
			}
		}
		val scale by animateFloatAsState(targetScale)
		val targetText by remember(text) {
			derivedStateOf { text.trim() }
		}
		val isTextEmpty by remember(text) {
			derivedStateOf { targetText.isEmpty() }
		}
		val borderWidth by animateDpAsState(config.borderWidth)
		Row(
			modifier = modifier
				.scale(scale)
				.then(if (isTextEmpty) Modifier.width(height) else Modifier)
				.height(height)
				.clip(cornerShape)
				.background(
					brush = backgroundBrush,
					shape = cornerShape
				)
				.then(
					when (buttonType) {
						FlexButtonType.Default -> {
							Modifier.border(
								width = borderWidth,
								brush = borderBrush,
								shape = cornerShape
							)
						}
						
						FlexButtonType.Dashed -> {
							Modifier.dashedBorder(
								width = borderWidth,
								brush = borderBrush,
								shape = cornerShape
							)
						}
						
						else -> Modifier
					}
				)
				.clickable(
					interactionSource = interactionSource,
					indication = null,
					enabled = enabled,
					onClick = onClick
				).then(
					if (isTextEmpty) Modifier else Modifier.padding(
						start = horizontalPadding,
						end = horizontalPadding,
					)
				),
			verticalAlignment = Alignment.CenterVertically,
			horizontalArrangement = Arrangement.Center
		) {
			val contentBrush by animateFlexBrushAsState(
				targetValue = when (buttonType) {
					FlexButtonType.Primary -> brushType.onBrush
					FlexButtonType.Filled, FlexButtonType.Text -> {
						if (enabled) brushType.brush else brushType.disabledBrush
					}
					
					FlexButtonType.Link -> {
						when {
							!enabled -> brushType.disabledBrush
							isPressed -> brushType.darkenBrush
							isHovered -> brushType.lightenBrush
							else -> brushType.brush
						}
					}
					
					FlexButtonType.Default, FlexButtonType.Dashed -> {
						when {
							!enabled -> brushType.disabledBrush
							isPressed -> brushType.darkenBrush
							isHovered -> brushType.lightenBrush
							else -> brushType.brush
						}
					}
				}
			)
			if (icon != null) {
				CompositionLocalProvider(
					LocalLayoutDirection provides layoutDirection
				) {
					val iconSize by animateDpAsState(config.iconSize)
					val rotation by animateFloatAsState(iconRotation)
					FlexIcon(
						imageVector = icon,
						modifier = Modifier
							.rotate(rotation)
							.size(iconSize),
						tint = contentBrush
					)
					val internal by remember(text) {
						derivedStateOf {
							if (text.isBlank()) Dp.Hairline else config.iconInterval
						}
					}
					Spacer(modifier = Modifier.width(internal))
				}
			}
			CompositionLocalProvider(
				LocalLayoutDirection provides layoutDirection,
			) {
				val fontSize by animateFloatAsState(config.fontSize.value)
				val letterSpacing by animateFloatAsState(config.letterSpacing.value)
				Text(
					text = targetText,
					fontSize = fontSize.sp,
					fontWeight = config.fontWeight,
					letterSpacing = when (config.letterSpacing) {
						TextUnit.Unspecified -> config.letterSpacing
						else -> letterSpacing.sp
					},
					lineHeight = fontSize.sp,
					overflow = TextOverflow.Ellipsis,
					maxLines = 1,
					style = LocalTextStyle.current.copy(
						brush = contentBrush.original
					)
				)
			}
		}
	}
}

object FlexButtonDefaults : FlexDefaults() {
	
	override val FlexComposeDefaultConfig.defaultConfig
		get() = this.button
	
	val DefaultButtonType: FlexButtonType
		@Composable
		get() = LocalFlexButtonType.current
	
	val DefaultIconDirection: FlexButtonIconPosition
		@Composable
		get() = LocalFlexButtonIconPosition.current
}

enum class FlexButtonIconPosition {
	
	/**
	 * Ltr: 左，Rtl: 右
	 */
	Start,
	
	/**
	 *  Ltr: 右，Rtl: 左
	 */
	End
}

enum class FlexButtonType {
	
	/**
	 * 主要按钮
	 */
	Primary,
	
	/**
	 * 默认（线框）
	 */
	Default,
	
	/**
	 * 虚线框
	 */
	Dashed,
	
	/**
	 * 填充
	 */
	Filled,
	
	/**
	 * 文本
	 */
	Text,
	
	/**
	 * 链接
	 */
	Link,
}

internal val LocalFlexButtonType = compositionLocalOf { FlexButtonType.Default }

internal val LocalFlexButtonIconPosition = compositionLocalOf { FlexButtonIconPosition.End }