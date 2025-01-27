package cn.vividcode.multiplatform.flex.ui.foundation.radio

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp
import cn.vividcode.multiplatform.flex.ui.config.LocalFlexConfig
import cn.vividcode.multiplatform.flex.ui.config.foundation.FlexRadioConfig
import cn.vividcode.multiplatform.flex.ui.config.type.FlexColorType
import cn.vividcode.multiplatform.flex.ui.config.type.FlexCornerType
import cn.vividcode.multiplatform.flex.ui.config.type.FlexSizeType
import cn.vividcode.multiplatform.flex.ui.expends.brightness
import cn.vividcode.multiplatform.flex.ui.expends.isDark
import cn.vividcode.multiplatform.flex.ui.theme.LocalDarkTheme
import kotlin.jvm.JvmName

/**
 * FlexRadioGroup 单选框组
 *
 * @param selectedKey 选中的 key
 * @param onSelectedKeyChange 当选中改变事件
 * @param options 选项 [RadioOption] 类型
 * @param sizeType 尺寸类型
 * @param colorType 颜色类型
 * @param cornerType 圆角类型
 * @param radioType 单选框组类型
 * @param switchType 切换类型
 * @param scaleEffect 缩放效果
 */
@Composable
fun <Key> FlexRadioGroup(
	selectedKey: Key,
	onSelectedKeyChange: (Key) -> Unit,
	options: List<RadioOption<Key>>,
	sizeType: FlexSizeType = FlexRadioGroups.DefaultSizeType,
	colorType: FlexColorType = FlexRadioGroups.DefaultColorType,
	cornerType: FlexCornerType = FlexRadioGroups.DefaultCornerType,
	radioType: FlexRadioType = FlexRadioGroups.DefaultRadioType,
	switchType: FlexRadioSwitchType = FlexRadioGroups.DefaultSwitchType,
	scaleEffect: Boolean = FlexRadioGroups.DefaultScaleEffect,
) {
	val targetSelectedKey by remember(options, selectedKey) {
		derivedStateOf {
			val option = options.find { it.key == selectedKey }
			if (option?.enabled == true) option.key else {
				options.find { it.enabled }?.key?.also {
					onSelectedKeyChange(it)
				} ?: selectedKey
			}
		}
	}
	when (switchType) {
		FlexRadioSwitchType.None -> FlexNoneRadioGroup(
			options = options,
			selectedKey = targetSelectedKey,
			onSelectedKeyChange = onSelectedKeyChange,
			sizeType = sizeType,
			colorType = colorType,
			cornerType = cornerType,
			radioType = radioType,
			scaleEffect = scaleEffect
		)
		
		FlexRadioSwitchType.Swipe -> FlexSwipeRadioGroup(
			options = options,
			selectedKey = targetSelectedKey,
			onSelectedKeyChange = onSelectedKeyChange,
			sizeType = sizeType,
			colorType = colorType,
			cornerType = cornerType,
			radioType = radioType,
			scaleEffect = scaleEffect
		)
	}
}

@Suppress("ConstPropertyName")
object FlexRadioGroups {
	
	val DefaultSizeType: FlexSizeType
		@Composable
		get() = LocalFlexConfig.current.default.let {
			it.radio?.sizeType ?: it.common.sizeType ?: FlexSizeType.Medium
		}
	
	val DefaultColorType: FlexColorType
		@Composable
		get() = LocalFlexConfig.current.default.let {
			it.radio?.colorType ?: it.common.colorType ?: FlexColorType.Default
		}
	
	val DefaultCornerType: FlexCornerType
		@Composable
		get() = LocalFlexConfig.current.default.let {
			it.radio?.cornerType ?: it.common.cornerType ?: FlexCornerType.Default
		}
	
	val DefaultRadioType = FlexRadioType.Default
	
	val DefaultSwitchType = FlexRadioSwitchType.None
	
	const val DefaultScaleEffect = true
}

enum class FlexRadioType {
	
	/**
	 * 按钮
	 */
	Button,
	
	/**
	 * 默认（线框）
	 */
	Default,
}

enum class FlexRadioSwitchType {
	
	/**
	 * 默认（无切换）
	 */
	None,
	
	/**
	 * 滑动切换
	 */
	Swipe
}

/**
 * FlexRadioGroup 单选框组
 *
 * @param selectedKey 选中的 key
 * @param onSelectedKeyChange 当选中改变事件
 * @param options 选项 [Pair] 类型
 * @param sizeType 尺寸类型
 * @param colorType 颜色类型
 * @param cornerType 圆角类型
 * @param radioType 单选框组类型
 */
@JvmName("FlexRadioGroupWithPairOption")
@Composable
fun <Key> FlexRadioGroup(
	selectedKey: Key,
	onSelectedKeyChange: (Key) -> Unit,
	options: List<Pair<Key, String>>,
	sizeType: FlexSizeType = FlexRadioGroups.DefaultSizeType,
	colorType: FlexColorType = FlexRadioGroups.DefaultColorType,
	cornerType: FlexCornerType = FlexRadioGroups.DefaultCornerType,
	radioType: FlexRadioType = FlexRadioGroups.DefaultRadioType,
	switchType: FlexRadioSwitchType = FlexRadioGroups.DefaultSwitchType,
	scaleEffect: Boolean = FlexRadioGroups.DefaultScaleEffect,
) {
	FlexRadioGroup(
		selectedKey = selectedKey,
		onSelectedKeyChange = onSelectedKeyChange,
		options = options.map { RadioOption(it.first, it.second) },
		sizeType = sizeType,
		colorType = colorType,
		cornerType = cornerType,
		radioType = radioType,
		switchType = switchType,
		scaleEffect = scaleEffect,
	)
}

/**
 * FlexRadioGroup 单选框组
 *
 * @param selectedKey 选中的 key
 * @param onSelectedKeyChange 当选中改变事件
 * @param options 选项 [Pair] 类型
 * @param sizeType 尺寸类型
 * @param colorType 颜色类型
 * @param cornerType 圆角类型
 * @param radioType 单选框组类型
 */
@JvmName("FlexRadioGroupWithStringOption")
@Composable
fun FlexRadioGroup(
	selectedKey: String,
	onSelectedKeyChange: (String) -> Unit,
	options: List<String>,
	sizeType: FlexSizeType = FlexRadioGroups.DefaultSizeType,
	colorType: FlexColorType = FlexRadioGroups.DefaultColorType,
	cornerType: FlexCornerType = FlexRadioGroups.DefaultCornerType,
	radioType: FlexRadioType = FlexRadioGroups.DefaultRadioType,
	switchType: FlexRadioSwitchType = FlexRadioGroups.DefaultSwitchType,
	scaleEffect: Boolean = FlexRadioGroups.DefaultScaleEffect,
) {
	FlexRadioGroup(
		selectedKey = selectedKey,
		onSelectedKeyChange = onSelectedKeyChange,
		options = options.map { RadioOption(it, it) },
		sizeType = sizeType,
		colorType = colorType,
		cornerType = cornerType,
		radioType = radioType,
		switchType = switchType,
		scaleEffect = scaleEffect,
	)
}

/**
 * 选项
 */
class RadioOption<Key>(
	val key: Key,
	val value: String,
	val enabled: Boolean = true,
)

internal val BorderColor = Color.Gray.copy(alpha = 0.3f)

internal val UnselectedFontColor
	@Composable
	get() = when (LocalDarkTheme.current) {
		true -> Color.LightGray
		false -> Color.DarkGray
	}

internal val DisabledBackgroundColor = Color.Gray.copy(alpha = 0.15f)

/**
 * 单选框文本
 */
@Composable
internal fun FlexRadioText(
	value: String,
	enabled: Boolean,
	selected: Boolean,
	color: Color,
	config: FlexRadioConfig,
	radioType: FlexRadioType,
	isPressed: Boolean,
	isHovered: Boolean,
	scaleEffect: Boolean,
) {
	val targetFontColor by animateColorAsState(
		targetValue = when {
			!enabled -> UnselectedFontColor.copy(alpha = 0.8f)
			selected -> {
				when (radioType) {
					FlexRadioType.Button -> if (color.isDark) Color.White else Color.Black
					FlexRadioType.Default -> {
						when {
							isPressed -> color.brightness(0.9f)
							isHovered -> color.brightness(1.15f)
							else -> color
						}
					}
				}
			}
			
			isPressed -> color.brightness(0.9f)
			isHovered -> color
			else -> UnselectedFontColor
		}
	)
	val targetScale by remember(enabled, scaleEffect, isPressed, isHovered) {
		derivedStateOf {
			when {
				!enabled || !scaleEffect -> 1f
				isPressed -> 0.98f
				isHovered -> 1.02f
				else -> 1f
			}
		}
	}
	val scale by animateFloatAsState(targetScale)
	val fontSize by animateFloatAsState(config.fontSize.value)
	Text(
		text = value,
		modifier = Modifier.scale(scale),
		color = targetFontColor,
		fontSize = fontSize.sp,
		fontWeight = config.fontWeight,
		lineHeight = fontSize.sp,
		letterSpacing = config.letterSpacing,
	)
}

/**
 * 单选框竖线
 */
@Composable
internal fun <Key> FlexRadioLine(
	index: Int,
	options: List<RadioOption<Key>>,
	selectedKey: Key,
	borderWidth: Dp,
) {
	val lineColor by animateColorAsState(
		targetValue = when {
			options[index].key != selectedKey && options[index - 1].key != selectedKey -> BorderColor
			!options[index].enabled || !options[index - 1].enabled -> DisabledBackgroundColor
			else -> Color.Transparent
		}
	)
	val width by animateDpAsState(borderWidth)
	Spacer(
		modifier = Modifier
			.width(width)
			.fillMaxHeight()
			.padding(
				vertical = width
			)
			.background(lineColor)
	)
}