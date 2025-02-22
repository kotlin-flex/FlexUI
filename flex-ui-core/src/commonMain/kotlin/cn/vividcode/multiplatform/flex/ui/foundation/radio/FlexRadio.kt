package cn.vividcode.multiplatform.flex.ui.foundation.radio

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.LocalTextStyle
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
import androidx.compose.ui.util.fastMap
import androidx.compose.ui.util.fastMapIndexed
import cn.vividcode.multiplatform.flex.ui.config.FlexComposeDefaultConfig
import cn.vividcode.multiplatform.flex.ui.config.FlexDefaults
import cn.vividcode.multiplatform.flex.ui.config.foundation.FlexRadioConfig
import cn.vividcode.multiplatform.flex.ui.foundation.radio.FlexRadioType.Button
import cn.vividcode.multiplatform.flex.ui.graphics.FlexBrush
import cn.vividcode.multiplatform.flex.ui.graphics.toSolidColor
import cn.vividcode.multiplatform.flex.ui.theme.LocalDarkTheme
import cn.vividcode.multiplatform.flex.ui.type.FlexBrushType
import cn.vividcode.multiplatform.flex.ui.type.FlexCornerType
import cn.vividcode.multiplatform.flex.ui.type.FlexSizeType
import cn.vividcode.multiplatform.flex.ui.type.darkenBrush
import cn.vividcode.multiplatform.flex.ui.type.lightenBrush
import cn.vividcode.multiplatform.flex.ui.utils.animateFlexBrushAsState
import cn.vividcode.multiplatform.flex.ui.utils.disabledWithColor
import kotlin.jvm.JvmName

/**
 * FlexRadio 单选框
 *
 * @param selectedKey 选中的 key
 * @param onSelectedKeyChange 当选中改变事件
 * @param options 选项 [RadioOption] 类型
 * @param sizeType 尺寸类型
 * @param brushType 颜色类型
 * @param cornerType 圆角类型
 * @param radioType 单选框组类型
 * @param switchType 切换类型
 * @param scaleEffect 缩放效果
 */
@JvmName("FlexRadio")
@Composable
fun <Key : Any> FlexRadio(
	selectedKey: Key,
	onSelectedKeyChange: (Key) -> Unit,
	options: FlexRadioOptions.() -> List<RadioOption<Key>>,
	sizeType: FlexSizeType = FlexRadioDefaults.DefaultSizeType,
	brushType: FlexBrushType = FlexRadioDefaults.DefaultBrushType,
	cornerType: FlexCornerType = FlexRadioDefaults.DefaultCornerType,
	radioType: FlexRadioType = FlexRadioDefaults.DefaultRadioType,
	switchType: FlexRadioSwitchType = FlexRadioDefaults.DefaultSwitchType,
	scaleEffect: Boolean = FlexRadioDefaults.DefaultScaleEffect,
) {
	val options by remember(options) {
		derivedStateOf {
			FlexRadioOptionsImpl.options()
		}
	}
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
			brushType = brushType,
			cornerType = cornerType,
			radioType = radioType,
			scaleEffect = scaleEffect
		)
		
		FlexRadioSwitchType.Swipe -> FlexSwipeRadio(
			options = options,
			selectedKey = targetSelectedKey,
			onSelectedKeyChange = onSelectedKeyChange,
			sizeType = sizeType,
			brushType = brushType,
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
class RadioOption<Key : Any>(
	val key: Key,
	val value: String = key.toString(),
	val enabled: Boolean = true,
)

sealed interface FlexRadioOptions {
	
	fun <T : Any> Array<T>.options(
		transform: (T) -> RadioOption<T> = { RadioOption(it, it.toString()) },
	): List<RadioOption<T>>
	
	fun <T : Any> Array<T>.optionsIndexed(
		transform: (index: Int, T) -> RadioOption<T> = { _, it -> RadioOption(it, it.toString()) },
	): List<RadioOption<T>>
	
	fun <T : Any> Iterable<T>.options(
		transform: (T) -> RadioOption<T> = { RadioOption(it, it.toString()) },
	): List<RadioOption<T>>
	
	fun <T : Any> Iterable<T>.optionsIndexed(
		transform: (index: Int, T) -> RadioOption<T> = { _, it -> RadioOption(it, it.toString()) },
	): List<RadioOption<T>>
	
	fun <T : Any> List<T>.options(
		transform: (T) -> RadioOption<T> = { RadioOption(it, it.toString()) },
	): List<RadioOption<T>>
	
	fun <T : Any> List<T>.optionsIndexed(
		transform: (index: Int, T) -> RadioOption<T> = { _, it -> RadioOption(it, it.toString()) },
	): List<RadioOption<T>>
}

private object FlexRadioOptionsImpl : FlexRadioOptions {
	
	override fun <T : Any> Array<T>.options(
		transform: (T) -> RadioOption<T>,
	): List<RadioOption<T>> = this.map(transform)
	
	override fun <T : Any> Array<T>.optionsIndexed(
		transform: (Int, T) -> RadioOption<T>,
	): List<RadioOption<T>> = this.mapIndexed(transform)
	
	override fun <T : Any> Iterable<T>.options(
		transform: (T) -> RadioOption<T>,
	): List<RadioOption<T>> = this.map(transform)
	
	override fun <T : Any> Iterable<T>.optionsIndexed(
		transform: (Int, T) -> RadioOption<T>,
	): List<RadioOption<T>> = this.mapIndexed(transform)
	
	override fun <T : Any> List<T>.options(
		transform: (T) -> RadioOption<T>,
	): List<RadioOption<T>> = this.fastMap(transform)
	
	override fun <T : Any> List<T>.optionsIndexed(
		transform: (Int, T) -> RadioOption<T>,
	): List<RadioOption<T>> = this.fastMapIndexed(transform)
}

internal val DisabledBackgroundBrush: FlexBrush
	@Composable
	get() = if (LocalDarkTheme.current) {
		Color.DarkGray.copy(alpha = 0.6f).toSolidColor()
	} else {
		Color.LightGray.copy(alpha = 0.6f).toSolidColor()
	}

internal val DisabledBackgroundColor: Color
	@Composable
	get() = (if (LocalDarkTheme.current) Color.DarkGray else Color.LightGray).disabledWithColor

/**
 * 单选框文本
 */
@Composable
internal fun FlexRadioText(
	value: String,
	enabled: Boolean,
	brushType: FlexBrushType,
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
	val onBrush by animateFlexBrushAsState(
		targetValue = when {
			!enabled -> MaterialTheme.colorScheme.outline.toSolidColor()
			!selected -> when {
				isPressed || isHovered -> brushType.brush
				else -> MaterialTheme.colorScheme.outline.toSolidColor()
			}
			
			radioType == Button -> brushType.onBrush
			
			else -> {
				when {
					isPressed -> brushType.darkenBrush()
					isHovered -> brushType.lightenBrush()
					else -> brushType.brush
				}
			}
		}
	)
	Text(
		text = value,
		modifier = Modifier.scale(scale),
		fontSize = fontSize.sp,
		fontWeight = config.fontWeight,
		lineHeight = fontSize.sp,
		letterSpacing = config.letterSpacing,
		style = LocalTextStyle.current.copy(
			brush = onBrush.original
		)
	)
}

/**
 * 单选框竖线
 */
@Composable
internal fun <Key : Any> FlexRadioLine(
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

internal fun Modifier.buttonBorder(
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