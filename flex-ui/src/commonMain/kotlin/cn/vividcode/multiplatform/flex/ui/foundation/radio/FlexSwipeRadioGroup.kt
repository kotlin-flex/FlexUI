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
import androidx.compose.ui.unit.dp
import cn.vividcode.multiplatform.flex.ui.config.LocalFlexConfig
import cn.vividcode.multiplatform.flex.ui.config.type.FlexColorType
import cn.vividcode.multiplatform.flex.ui.config.type.FlexCornerType
import cn.vividcode.multiplatform.flex.ui.config.type.FlexSizeType
import cn.vividcode.multiplatform.flex.ui.expends.brightness
import cn.vividcode.multiplatform.flex.ui.expends.sum
import cn.vividcode.multiplatform.flex.ui.foundation.radio.FlexRadioType.Button
import cn.vividcode.multiplatform.flex.ui.foundation.radio.FlexRadioType.Default

/**
 * FlexRadioGroup 单选框组，类型：滑块
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
internal fun <Key> FlexSwipeRadioGroup(
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
	val corner = config.height * cornerType.percent
	val cornerShape = getCornerShape(cornerType, corner)
	Box(
		modifier = Modifier
			.height(config.height)
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
			val targetWidth by animateDpAsState(
				targetValue = buttonWidths[currentIndex]
			)
			val targetOffsetX by remember(buttonWidths, currentIndex, config.borderWidth) {
				derivedStateOf { buttonWidths.take(currentIndex).sum() + config.borderWidth * currentIndex }
			}
			val offsetX by animateDpAsState(
				targetValue = targetOffsetX
			)
			val startCorner by animateDpAsState(
				targetValue = if (currentIndex == 0) corner else Dp.Hairline,
			)
			val endCorner by animateDpAsState(
				targetValue = if (currentIndex == options.lastIndex) corner else Dp.Hairline,
			)
			val buttonCornerShape by remember(startCorner, endCorner) {
				derivedStateOf {
					when {
						startCorner == Dp.Hairline && endCorner == Dp.Hairline -> RectangleShape
						else -> RoundedCornerShape(
							topStart = startCorner,
							topEnd = endCorner,
							bottomEnd = endCorner,
							bottomStart = startCorner,
						)
					}
				}
			}
			val option = options[currentIndex]
			val targetButtonColor by remember(color, option.enabled, radioType, buttonIsPressed, buttonIsHovered) {
				derivedStateOf {
					when {
						!option.enabled -> DisabledBackgroundColor
						
						radioType == Button -> {
							when {
								buttonIsPressed -> color.brightness(0.95f)
								buttonIsHovered -> color.brightness(1.1f)
								else -> color
							}
						}
						
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
			val buttonColor by animateColorAsState(
				targetValue = targetButtonColor
			)
			Box(
				modifier = Modifier
					.width(targetWidth)
					.fillMaxHeight()
					.offset(x = offsetX)
					.then(
						when (radioType) {
							Button -> {
								Modifier.background(
									color = buttonColor,
									shape = buttonCornerShape
								)
							}
							
							Default -> {
								Modifier.border(
									width = config.borderWidth,
									color = buttonColor,
									shape = buttonCornerShape
								)
							}
						}
					)
			)
		}
		Row(
			modifier = Modifier
				.fillMaxHeight()
				.clip(cornerShape)
		) {
			val density = LocalDensity.current.density
			options.forEachIndexed { index, option ->
				val interactionSource = remember { MutableInteractionSource() }
				val isPressed by interactionSource.collectIsPressedAsState()
				val isHovered by interactionSource.collectIsHoveredAsState()
				LaunchedEffect(isHovered, isPressed) {
					if (option.key == selectedKey) {
						buttonIsPressed = isPressed
						buttonIsHovered = isHovered
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
							buttonWidths[index] = (it.size.width / density).dp
						}
						.clip(cornerShape)
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
						.padding(
							horizontal = config.horizontalPadding,
						),
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