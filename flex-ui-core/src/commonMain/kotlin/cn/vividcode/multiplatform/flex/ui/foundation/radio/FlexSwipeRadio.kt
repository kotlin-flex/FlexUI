package cn.vividcode.multiplatform.flex.ui.foundation.radio

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.util.fastAll
import androidx.compose.ui.util.fastForEachIndexed
import androidx.compose.ui.util.fastMap
import cn.vividcode.multiplatform.flex.ui.config.LocalFlexConfig
import cn.vividcode.multiplatform.flex.ui.foundation.radio.FlexRadioType.Button
import cn.vividcode.multiplatform.flex.ui.type.*
import cn.vividcode.multiplatform.flex.ui.utils.animateFlexBrushAsState
import cn.vividcode.multiplatform.flex.ui.utils.background
import cn.vividcode.multiplatform.flex.ui.utils.border
import cn.vividcode.multiplatform.flex.ui.utils.sum

/**
 * FlexRadio 单选框，类型：滑块
 *
 * @param options 选项 [RadioOption] 类型
 * @param selectedKey 选中的 key
 * @param onSelectedKeyChange 当选中改变事件
 * @param sizeType 尺寸类型
 * @param brushType 颜色类型
 * @param cornerType 圆角类型
 * @param radioType 单选框组类型
 * @param scaleEffect 开启缩放效果
 */
@Composable
internal fun <Key : Any> FlexSwipeRadio(
	options: List<RadioOption<Key>>,
	selectedKey: Key,
	onSelectedKeyChange: (Key) -> Unit,
	sizeType: FlexSizeType,
	brushType: FlexBrushType,
	cornerType: FlexCornerType,
	radioType: FlexRadioType,
	scaleEffect: Boolean,
) {
	val config = LocalFlexConfig.current.radio.getConfig(sizeType)
	val corner by animateDpAsState(config.height * cornerType.scale)
	val cornerShape by remember(cornerType, corner) {
		derivedStateOf { RoundedCornerShape(corner) }
	}
	val horizontalPadding by animateDpAsState(config.horizontalPadding)
	val height by animateDpAsState(config.height)
	val borderWidth by animateDpAsState(config.borderWidth)
	val borderColor by animateColorAsState(MaterialTheme.colorScheme.outlineVariant)
	Box(
		modifier = Modifier
			.height(height)
			.buttonBorder(
				width = borderWidth,
				color = borderColor,
				corner = corner
			)
	) {
		val buttonWidths = remember { mutableStateListOf(*options.fastMap { Dp.Hairline }.toTypedArray()) }
		val currentIndex = options.indexOfFirst { it.key == selectedKey }
		var isButtonPressed by remember { mutableStateOf(false) }
		var isButtonHovered by remember { mutableStateOf(false) }
		val isCalcWidths by remember(currentIndex, buttonWidths) {
			derivedStateOf { currentIndex != -1 && buttonWidths.fastAll { it != Dp.Hairline } }
		}
		if (isCalcWidths) {
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
			val borderBrush by animateFlexBrushAsState(
				targetValue = when {
					!option.enabled || selectedKey != option.key || radioType == Button -> brushType.transparentBrush
					else -> {
						when {
							isButtonPressed -> brushType.darkenBrush
							isButtonHovered -> brushType.lightenBrush
							else -> brushType.brush
						}
					}
				}
			)
			val backgroundBrush by animateFlexBrushAsState(
				targetValue = when {
					!option.enabled -> DisabledBackgroundBrush
					selectedKey != option.key || radioType == FlexRadioType.Default -> brushType.transparentBrush
					else -> {
						when {
							isButtonPressed -> brushType.darkenBrush
							isButtonHovered -> brushType.lightenBrush
							else -> brushType.brush
						}
					}
				}
			)
			Box(
				modifier = Modifier
					.width(targetWidth)
					.fillMaxHeight()
					.offset(x = offsetX)
					.background(
						brush = backgroundBrush,
						shape = buttonCornerShape
					)
					.border(
						width = borderWidth,
						brush = borderBrush,
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
			options.fastForEachIndexed { index, option ->
				val interactionSource = remember { MutableInteractionSource() }
				val isPressed by interactionSource.collectIsPressedAsState()
				val isHovered by interactionSource.collectIsHoveredAsState()
				LaunchedEffect(isHovered) {
					if (option.key == selectedKey) {
						isButtonHovered = isHovered
					}
				}
				LaunchedEffect(isPressed) {
					if (option.key == selectedKey) {
						isButtonPressed = isPressed
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
								color = DisabledColor
							)
						)
						.padding(horizontal = horizontalPadding),
					horizontalArrangement = Arrangement.Center,
					verticalAlignment = Alignment.CenterVertically,
				) {
					FlexRadioText(
						value = option.value,
						enabled = option.enabled,
						brushType = brushType,
						radioType = radioType,
						config = config,
						selected = selectedKey == option.key,
						isPressed = isPressed,
						isHovered = isHovered,
						scaleEffect = scaleEffect
					)
				}
			}
		}
	}
}