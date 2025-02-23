@file:Suppress("DuplicatedCode")

package cn.vividcode.multiplatform.flex.ui.foundation.radio

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.util.fastForEachIndexed
import cn.vividcode.multiplatform.flex.ui.config.LocalFlexConfig
import cn.vividcode.multiplatform.flex.ui.foundation.radio.FlexRadioType.Button
import cn.vividcode.multiplatform.flex.ui.type.*
import cn.vividcode.multiplatform.flex.ui.utils.animateFlexBrushAsState
import cn.vividcode.multiplatform.flex.ui.utils.background
import cn.vividcode.multiplatform.flex.ui.utils.border

/**
 * FlexRadio 单选框，类型：无效果
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
internal fun <Key : Any> FlexNoneRadio(
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
	val height by animateDpAsState(config.height)
	val corner by animateDpAsState(config.height * cornerType.scale)
	val cornerShape by remember(corner) {
		derivedStateOf { RoundedCornerShape(corner) }
	}
	val horizontalPadding by animateDpAsState(config.horizontalPadding)
	val borderWidth by animateDpAsState(config.borderWidth)
	val borderColor by animateColorAsState(MaterialTheme.colorScheme.outlineVariant)
	Row(
		modifier = Modifier
			.height(height)
			.clip(cornerShape)
			.buttonBorder(
				width = borderWidth,
				color = borderColor,
				corner = corner
			)
	) {
		options.fastForEachIndexed { index, option ->
			val buttonCornerShape by remember(corner, options.lastIndex) {
				derivedStateOf {
					when {
						index in 1 ..< options.lastIndex -> RectangleShape
						else -> RoundedCornerShape(
							topStart = if (index == 0) corner else Dp.Hairline,
							bottomStart = if (index == 0) corner else Dp.Hairline,
							topEnd = if (index == options.lastIndex) corner else Dp.Hairline,
							bottomEnd = if (index == options.lastIndex) corner else Dp.Hairline,
						)
					}
				}
			}
			val interactionSource = remember { MutableInteractionSource() }
			val isHovered by interactionSource.collectIsHoveredAsState()
			val isPressed by interactionSource.collectIsPressedAsState()
			val borderBrush by animateFlexBrushAsState(
				targetValue = when {
					!option.enabled || selectedKey != option.key || radioType == Button -> brushType.transparentBrush
					else -> {
						when {
							isPressed -> brushType.darkenBrush
							isHovered -> brushType.lightenBrush
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
							isPressed -> brushType.darkenBrush
							isHovered -> brushType.lightenBrush
							else -> brushType.brush
						}
					}
				}
			)
			
			if (index != 0) {
				FlexRadioLine(
					index = index,
					options = options,
					selectedKey = selectedKey,
					borderWidth = config.borderWidth,
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
					.background(
						brush = backgroundBrush,
						shape = buttonCornerShape
					)
					.border(
						width = config.borderWidth,
						brush = borderBrush,
						shape = buttonCornerShape
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