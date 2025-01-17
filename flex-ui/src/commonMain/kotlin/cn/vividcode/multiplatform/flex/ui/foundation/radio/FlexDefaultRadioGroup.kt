package cn.vividcode.multiplatform.flex.ui.foundation.radio

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import cn.vividcode.multiplatform.flex.ui.config.LocalFlexConfig
import cn.vividcode.multiplatform.flex.ui.config.type.FlexColorType
import cn.vividcode.multiplatform.flex.ui.config.type.FlexCornerType
import cn.vividcode.multiplatform.flex.ui.config.type.FlexSizeType
import cn.vividcode.multiplatform.flex.ui.expends.brightness
import cn.vividcode.multiplatform.flex.ui.expends.isDark

/**
 * FlexRadioGroup 单选框组，类型：默认
 *
 * @param options 选项 [RadioOption] 类型
 * @param selectedKey 选中的 key
 * @param onSelectedKeyChange 当选中改变事件
 * @param sizeType 尺寸类型
 * @param colorType 颜色类型
 * @param cornerType 圆角类型
 * @param radioType 单选框组类型
 */
@Composable
internal fun FlexDefaultRadioGroup(
	options: List<RadioOption>,
	selectedKey: String,
	onSelectedKeyChange: (String) -> Unit,
	sizeType: FlexSizeType = FlexRadioGroups.DefaultSizeType,
	colorType: FlexColorType = FlexRadioGroups.DefaultColorType,
	cornerType: FlexCornerType = FlexRadioGroups.DefaultCornerType,
	radioType: FlexRadioType = FlexRadioGroups.DefaultRadioType,
) {
	val current = LocalFlexConfig.current
	val config = current.radio.getConfig(sizeType)
	val color = current.theme.colorScheme.current.getColor(colorType)
	val corner = config.height * cornerType.percent
	val cornerShape = when (cornerType) {
		FlexCornerType.None -> RectangleShape
		FlexCornerType.Circle -> CircleShape
		else -> RoundedCornerShape(corner)
	}
	Row(
		modifier = Modifier
			.height(config.height)
			.clip(cornerShape)
			.drawBehind {
				val borderWidth = config.borderWidth.toPx()
				drawRoundRect(
					color = BorderColor,
					size = size.copy(
						width = size.width - borderWidth,
						height = size.height - borderWidth
					),
					topLeft = Offset(borderWidth / 2f, borderWidth / 2f),
					cornerRadius = CornerRadius(corner.toPx()),
					style = Stroke(width = borderWidth)
				)
			}
	) {
		options.forEachIndexed { index, option ->
			val buttonCornerShape = when {
				cornerType == FlexCornerType.None || index in 1 ..< options.lastIndex -> RectangleShape
				else -> RoundedCornerShape(
					topStart = if (index == 0) corner else Dp.Hairline,
					bottomStart = if (index == 0) corner else Dp.Hairline,
					topEnd = if (index == options.lastIndex) corner else Dp.Hairline,
					bottomEnd = if (index == options.lastIndex) corner else Dp.Hairline,
				)
			}
			val interactionSource = remember { MutableInteractionSource() }
			val isHovered by interactionSource.collectIsHoveredAsState()
			val isPressed by interactionSource.collectIsPressedAsState()
			val targetColor by animateColorAsState(
				targetValue = when {
					!option.enabled -> DisabledBackgroundColor
					selectedKey != option.key -> Color.Transparent
					radioType == FlexRadioType.Button -> {
						when {
							isPressed -> color.brightness(0.95f)
							isHovered -> color.brightness(1.1f)
							else -> color
						}
					}
					
					else -> {
						when {
							isPressed -> color.brightness(0.9f)
							isHovered -> color.brightness(1.15f)
							else -> color
						}
					}
				}
			)
			if (index != 0) {
				val targetLineColor by animateColorAsState(
					targetValue = when {
						option.key != selectedKey && options[index - 1].key != selectedKey -> BorderColor
						!options[index - 1].enabled || !option.enabled -> DisabledBackgroundColor
						else -> Color.Transparent
					}
				)
				Spacer(
					modifier = Modifier
						.width(config.borderWidth)
						.fillMaxHeight()
						.padding(
							vertical = config.borderWidth
						)
						.background(targetLineColor)
				)
			}
			Row(
				modifier = Modifier
					.fillMaxHeight()
					.clip(buttonCornerShape)
					.clickable(
						interactionSource = interactionSource,
						indication = null,
						onClick = { onSelectedKeyChange(option.key) },
						enabled = option.enabled
					)
					.padding(
						vertical = if (!option.enabled) config.borderWidth else Dp.Hairline
					)
					.then(
						when {
							!option.enabled || radioType == FlexRadioType.Button -> {
								Modifier.background(
									color = targetColor,
									shape = buttonCornerShape
								)
							}
							
							radioType == FlexRadioType.Default -> {
								Modifier.border(
									width = config.borderWidth,
									color = targetColor,
									shape = buttonCornerShape
								)
							}
							
							else -> Modifier.background(
								color = targetColor,
								shape = buttonCornerShape
							)
						}
					)
					.padding(
						horizontal = config.horizontalPadding,
					),
				horizontalArrangement = Arrangement.Center,
				verticalAlignment = Alignment.CenterVertically,
			) {
				val targetFontColor by animateColorAsState(
					targetValue = when {
						!option.enabled -> UnselectedFontColor.copy(alpha = 0.8f)
						selectedKey == option.key -> {
							when (radioType) {
								FlexRadioType.Button -> if (color.isDark) Color.White else Color.Black
								FlexRadioType.Default -> targetColor
							}
						}
						
						isPressed -> color.brightness(0.85f)
						isHovered -> color
						else -> UnselectedFontColor
					}
				)
				Text(
					text = option.value,
					color = targetFontColor,
					fontSize = config.fontSize,
					fontWeight = config.fontWeight,
					lineHeight = config.fontSize,
					letterSpacing = config.letterSpacing,
				)
			}
		}
	}
}