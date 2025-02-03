package cn.vividcode.multiplatform.flex.ui.foundation.radio

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import cn.vividcode.multiplatform.flex.ui.config.LocalFlexConfig
import cn.vividcode.multiplatform.flex.ui.config.type.FlexColorType
import cn.vividcode.multiplatform.flex.ui.config.type.FlexCornerType
import cn.vividcode.multiplatform.flex.ui.config.type.FlexSizeType
import cn.vividcode.multiplatform.flex.ui.expends.brightness
import cn.vividcode.multiplatform.flex.ui.expends.sum

/**
 * FlexRadio 单选框，类型：滑块
 *
 * @param options 选项 [RadioOption] 类型
 * @param selectedKey 选中的 key
 * @param onSelectedKeyChange 当选中改变事件
 * @param sizeType 尺寸类型
 * @param colorType 颜色类型
 * @param cornerType 圆角类型
 * @param radioType 单选框组类型
 * @param scaleEffect 开启缩放效果
 */
@Composable
internal fun <Key> FlexSwipeRadio(
	options: List<RadioOption<Key>>,
	selectedKey: Key,
	onSelectedKeyChange: (Key) -> Unit,
	sizeType: FlexSizeType,
	colorType: FlexColorType,
	cornerType: FlexCornerType,
	radioType: FlexRadioType,
	scaleEffect: Boolean,
) {
	val current = LocalFlexConfig.current
	val config = current.radio.getConfig(sizeType)
	val color = current.theme.colorScheme.current.getColor(colorType)
	val corner by animateDpAsState(config.height * cornerType.percent)
	val cornerShape by remember(cornerType, corner) {
		derivedStateOf {
			RoundedCornerShape(corner)
		}
	}
	val horizontalPadding by animateDpAsState(config.horizontalPadding)
	val height by animateDpAsState(config.height)
	Box(
		modifier = Modifier
			.height(height)
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
		val buttonWidths = remember { mutableStateListOf(*options.map { Dp.Hairline }.toTypedArray()) }
		val currentIndex = options.indexOfFirst { it.key == selectedKey }
		var buttonIsPressed by remember { mutableStateOf(false) }
		var buttonIsHovered by remember { mutableStateOf(false) }
		if (currentIndex != -1 && buttonWidths.all { it != Dp.Hairline }) {
			val targetWidth by animateDpAsState(buttonWidths[currentIndex])
			val targetOffsetX by remember(buttonWidths, currentIndex, config.borderWidth) {
				derivedStateOf { buttonWidths.take(currentIndex).sum() + config.borderWidth * currentIndex }
			}
			val offsetX by animateDpAsState(targetOffsetX)
			val startCorner by animateDpAsState(
				targetValue = if (currentIndex == 0) corner else Dp.Hairline,
			)
			val endCorner by animateDpAsState(
				targetValue = if (currentIndex == options.lastIndex) corner else Dp.Hairline,
			)
			val buttonCornerShape by remember(startCorner, endCorner) {
				derivedStateOf {
					RoundedCornerShape(
						topStart = startCorner,
						topEnd = endCorner,
						bottomEnd = endCorner,
						bottomStart = startCorner,
					)
				}
			}
			val option = options[currentIndex]
			val targetBorderColor by remember(color, option.enabled, selectedKey, radioType) {
				derivedStateOf {
					when {
						!option.enabled -> color.copy(alpha = 0f)
						radioType == FlexRadioType.Button -> color.copy(alpha = 0f)
						else -> {
							when {
								buttonIsPressed -> color.brightness(1.1f)
								buttonIsHovered -> color.brightness(1.15f)
								else -> color
							}
						}
					}
				}
			}
			val targetBackgroundColor by remember(color, option.enabled, selectedKey, radioType) {
				derivedStateOf {
					when {
						!option.enabled -> DisabledBackgroundColor
						radioType == FlexRadioType.Button -> {
							when {
								buttonIsPressed -> color.brightness(0.95f)
								buttonIsHovered -> color.brightness(1.1f)
								else -> color
							}
						}
						
						else -> color.copy(alpha = 0f)
					}
				}
			}
			val borderColor by animateColorAsState(targetBorderColor)
			val backgroundColor by animateColorAsState(targetBackgroundColor)
			Box(
				modifier = Modifier
					.width(targetWidth)
					.fillMaxHeight()
					.offset(x = offsetX)
					.background(
						color = backgroundColor,
						shape = buttonCornerShape
					)
					.border(
						width = config.borderWidth,
						color = borderColor,
						shape = buttonCornerShape
					)
			)
		}
		Row(
			modifier = Modifier
				.fillMaxHeight()
				.clip(cornerShape)
		) {
			val density = LocalDensity.current
			options.forEachIndexed { index, option ->
				val interactionSource = remember { MutableInteractionSource() }
				val isPressed by interactionSource.collectIsPressedAsState()
				val isHovered by interactionSource.collectIsHoveredAsState()
				LaunchedEffect(isHovered) {
					if (option.key == selectedKey) {
						buttonIsHovered = isHovered
					}
				}
				LaunchedEffect(isPressed) {
					if (option.key == selectedKey) {
						buttonIsPressed = isPressed
					}
				}
				if (index != 0) {
					FlexRadioLine(
						index = index,
						options = options,
						selectedKey = selectedKey,
						borderWidth = config.borderWidth
					)
				}
				Row(
					modifier = Modifier
						.fillMaxHeight()
						.onGloballyPositioned {
							buttonWidths[index] = with(density) { it.size.width.toDp() }
						}
						.clip(RectangleShape)
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
							if (option.enabled) Modifier else Modifier.background(
								color = DisabledBackgroundColor
							)
						)
						.padding(horizontal = horizontalPadding),
					horizontalArrangement = Arrangement.Center,
					verticalAlignment = Alignment.CenterVertically,
				) {
					FlexRadioText(
						value = option.value,
						enabled = option.enabled,
						selected = option.key == selectedKey,
						color = color,
						config = config,
						radioType = radioType,
						isPressed = isPressed,
						isHovered = isHovered,
						scaleEffect = scaleEffect
					)
				}
			}
		}
	}
}