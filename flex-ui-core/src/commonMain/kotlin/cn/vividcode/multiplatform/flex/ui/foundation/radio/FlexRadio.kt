package cn.vividcode.multiplatform.flex.ui.foundation.radio

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp
import cn.vividcode.multiplatform.flex.ui.config.foundation.FlexRadioConfig
import cn.vividcode.multiplatform.flex.ui.config.type.*
import cn.vividcode.multiplatform.flex.ui.expends.darkenWithContent
import cn.vividcode.multiplatform.flex.ui.expends.disabledWithBackground
import cn.vividcode.multiplatform.flex.ui.expends.lightenWithContent
import cn.vividcode.multiplatform.flex.ui.foundation.radio.FlexRadioType.Button
import cn.vividcode.multiplatform.flex.ui.theme.LocalDarkTheme
import kotlin.jvm.JvmName

/**
 * FlexRadio 单选框
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
@JvmName("FlexRadio")
@Composable
fun <Key> FlexRadio(
	selectedKey: Key,
	onSelectedKeyChange: (Key) -> Unit,
	options: List<RadioOption<Key>>,
	sizeType: FlexSizeType = FlexRadioDefaults.DefaultSizeType,
	colorType: FlexColorType = FlexRadioDefaults.DefaultColorType,
	cornerType: FlexCornerType = FlexRadioDefaults.DefaultCornerType,
	radioType: FlexRadioType = FlexRadioDefaults.DefaultRadioType,
	switchType: FlexRadioSwitchType = FlexRadioDefaults.DefaultSwitchType,
	scaleEffect: Boolean = FlexRadioDefaults.DefaultScaleEffect,
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
		FlexRadioSwitchType.None -> FlexNoneRadio(
			options = options,
			selectedKey = targetSelectedKey,
			onSelectedKeyChange = onSelectedKeyChange,
			sizeType = sizeType,
			colorType = colorType,
			cornerType = cornerType,
			radioType = radioType,
			scaleEffect = scaleEffect
		)
		
		FlexRadioSwitchType.Swipe -> FlexSwipeRadio(
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
object FlexRadioDefaults : FlexDefaults() {
	
	override val FlexComposeDefaultConfig.defaultConfig
		get() = this.radio
	
	val DefaultRadioType = FlexRadioType.Default
	
	val DefaultSwitchType = FlexRadioSwitchType.None
	
	const val DefaultScaleEffect = false
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
 * 选项
 */
class RadioOption<Key>(
	val key: Key,
	val value: String = key.toString(),
	val enabled: Boolean = true,
)

fun <T> List<T>.toRadioOptions(
	valueTransform: (T) -> String = { it.toString() },
	enabledTransform: (T) -> Boolean = { true },
): List<RadioOption<T>> = this.map { RadioOption(it, valueTransform(it), enabledTransform(it)) }

fun <T> Array<T>.toRadioOptions(
	valueTransform: (T) -> String = { it.toString() },
	enabledTransform: (T) -> Boolean = { true },
): List<RadioOption<T>> = this.map { RadioOption(it, valueTransform(it), enabledTransform(it)) }

fun <T> Iterable<T>.toRadioOptions(
	valueTransform: (T) -> String = { it.toString() },
	enabledTransform: (T) -> Boolean = { true },
): List<RadioOption<T>> = this.map { RadioOption(it, valueTransform(it), enabledTransform(it)) }

internal val DisabledBackgroundColor: Color
	@Composable
	get() = (if (LocalDarkTheme.current) Color.DarkGray else Color.LightGray).disabledWithBackground

/**
 * 单选框文本
 */
@Composable
internal fun FlexRadioText(
	value: String,
	enabled: Boolean,
	colorType: FlexColorType,
	radioType: FlexRadioType,
	config: FlexRadioConfig,
	selected: Boolean,
	isPressed: Boolean,
	isHovered: Boolean,
	scaleEffect: Boolean,
) {
	val scale by animateFloatAsState(
		targetValue = when {
			!enabled || !scaleEffect -> 1f
			isPressed -> 0.98f
			isHovered -> 1.02f
			else -> 1f
		}
	)
	val fontSize by animateFloatAsState(config.fontSize.value)
	val contentColor by animateColorAsState(
		targetValue = when {
			!enabled -> MaterialTheme.colorScheme.outline
			!selected -> when {
				isPressed || isHovered -> colorType.color
				else -> MaterialTheme.colorScheme.outline
			}
			
			radioType == Button -> colorType.contentColor
			else -> {
				val color = colorType.color
				when {
					isPressed -> color.darkenWithContent
					isHovered -> color.lightenWithContent
					else -> color
				}
			}
		}
	)
	Text(
		text = value,
		modifier = Modifier.scale(scale),
		color = contentColor,
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
			options[index].key != selectedKey && options[index - 1].key != selectedKey -> MaterialTheme.colorScheme.outlineVariant
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

internal fun Modifier.bottomBorder(
	width: Dp,
	color: Color,
	corner: Dp,
): Modifier = this.drawBehind {
	val borderWidth = width.toPx()
	drawRoundRect(
		color = color,
		size = size.copy(
			width = size.width - borderWidth,
			height = size.height - borderWidth
		),
		topLeft = Offset(borderWidth / 2f, borderWidth / 2f),
		cornerRadius = CornerRadius(corner.toPx()),
		style = Stroke(width = borderWidth)
	)
}