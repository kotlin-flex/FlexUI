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
import androidx.compose.runtime.LaunchedEffect
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
import cn.vividcode.multiplatform.flex.ui.theme.LocalDarkTheme
import kotlin.jvm.JvmName

/**
 * 单选框组控件
 */
@Composable
fun FlexRadioGroup(
	options: List<RadioOption>,
	selectedKey: String,
	onSelectedKeyChange: (String) -> Unit,
	sizeType: FlexSizeType = FlexRadioGroups.DefaultSizeType,
	colorType: FlexColorType = FlexRadioGroups.DefaultColorType,
	cornerType: FlexCornerType = FlexRadioGroups.DefaultCornerType,
	radioType: FlexRadioType = FlexRadioGroups.DefaultRadioType,
) {
	LaunchedEffect(options) {
		val defaultOption = options.find { it.key == selectedKey }
		if (defaultOption == null || !defaultOption.enabled) {
			val resetSelectedKey = options.find { it.enabled }?.key ?: return@LaunchedEffect
			onSelectedKeyChange(resetSelectedKey)
		}
	}
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

private val BorderColor = Color.Gray.copy(alpha = 0.3f)

private val UnselectedFontColor
	@Composable
	get() = if (LocalDarkTheme.current) Color.LightGray else Color.DarkGray

private val DisabledBackgroundColor = Color.Gray.copy(alpha = 0.15f)

object FlexRadioGroups {
	
	val DefaultSizeType = FlexSizeType.Medium
	
	val DefaultColorType = FlexColorType.Default
	
	val DefaultCornerType = FlexCornerType.Default
	
	val DefaultRadioType = FlexRadioType.Default
}

enum class FlexRadioType {
	
	Button,
	
	Default
}

/**
 * 适配 key to value 形式的 options
 */
@JvmName("FlexRadioGroupWithPairOptions")
@Composable
inline fun FlexRadioGroup(
	options: List<Pair<String, String>>,
	selectedKey: String,
	noinline onSelectedKeyChange: (String) -> Unit,
	sizeType: FlexSizeType = FlexRadioGroups.DefaultSizeType,
	colorType: FlexColorType = FlexRadioGroups.DefaultColorType,
	cornerType: FlexCornerType = FlexRadioGroups.DefaultCornerType,
	radioType: FlexRadioType = FlexRadioGroups.DefaultRadioType,
) {
	FlexRadioGroup(
		options = options.map { RadioOption(it.first, it.second) },
		selectedKey = selectedKey,
		onSelectedKeyChange = onSelectedKeyChange,
		sizeType = sizeType,
		colorType = colorType,
		cornerType = cornerType,
		radioType = radioType
	)
}

/**
 * 适配 String 形式的 options
 */
@JvmName("FlexRadioGroupWithStringOptions")
@Composable
inline fun FlexRadioGroup(
	options: List<String>,
	selectedKey: String,
	noinline onSelectedKeyChange: (String) -> Unit,
	sizeType: FlexSizeType = FlexRadioGroups.DefaultSizeType,
	colorType: FlexColorType = FlexRadioGroups.DefaultColorType,
	cornerType: FlexCornerType = FlexRadioGroups.DefaultCornerType,
	radioType: FlexRadioType = FlexRadioGroups.DefaultRadioType,
) {
	FlexRadioGroup(
		options = options.map { RadioOption(it) },
		selectedKey = selectedKey,
		onSelectedKeyChange = onSelectedKeyChange,
		sizeType = sizeType,
		colorType = colorType,
		cornerType = cornerType,
		radioType = radioType
	)
}

data class RadioOption(
	val key: String,
	val value: String = key,
	val enabled: Boolean = true,
)