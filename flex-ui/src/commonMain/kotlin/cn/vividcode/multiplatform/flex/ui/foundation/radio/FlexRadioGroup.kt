package cn.vividcode.multiplatform.flex.ui.foundation.radio

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.Color
import cn.vividcode.multiplatform.flex.ui.config.type.FlexColorType
import cn.vividcode.multiplatform.flex.ui.config.type.FlexCornerType
import cn.vividcode.multiplatform.flex.ui.config.type.FlexSizeType
import cn.vividcode.multiplatform.flex.ui.foundation.radio.FlexSwitchType.Default
import cn.vividcode.multiplatform.flex.ui.foundation.radio.FlexSwitchType.Swipe
import cn.vividcode.multiplatform.flex.ui.theme.LocalDarkTheme
import kotlin.jvm.JvmName

/**
 * FlexRadioGroup 单选框组
 *
 * @param options 选项 [RadioOption] 类型
 * @param selectedKey 选中的 key
 * @param onSelectedKeyChange 当选中改变事件
 * @param sizeType 尺寸类型
 * @param colorType 颜色类型
 * @param cornerType 圆角类型
 * @param radioType 单选框组类型
 * @param switchType 切换类型
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
	switchType: FlexSwitchType = FlexRadioGroups.DefaultSwitchType,
) {
	LaunchedEffect(options) {
		val defaultOption = options.find { it.key == selectedKey }
		if (defaultOption == null || !defaultOption.enabled) {
			val resetSelectedKey = options.find { it.enabled }?.key ?: return@LaunchedEffect
			onSelectedKeyChange(resetSelectedKey)
		}
	}
	when (switchType) {
		Default -> FlexDefaultRadioGroup(
			options = options,
			selectedKey = selectedKey,
			onSelectedKeyChange = onSelectedKeyChange,
			sizeType = sizeType,
			colorType = colorType,
			cornerType = cornerType,
			radioType = radioType
		)
		
		Swipe -> FlexSwipeRadioGroup(
			options = options,
			selectedKey = selectedKey,
			onSelectedKeyChange = onSelectedKeyChange,
			sizeType = sizeType,
			colorType = colorType,
			cornerType = cornerType,
			radioType = radioType
		)
	}
}

object FlexRadioGroups {
	
	val DefaultSizeType = FlexSizeType.Medium
	
	val DefaultColorType = FlexColorType.Default
	
	val DefaultCornerType = FlexCornerType.Default
	
	val DefaultRadioType = FlexRadioType.Default
	
	val DefaultSwitchType = Default
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

enum class FlexSwitchType {
	
	/**
	 * 默认（直接切换）
	 */
	Default,
	
	/**
	 * 滑动切换
	 */
	Swipe
}

/**
 * FlexRadioGroup 单选框组
 *
 * @param options 选项 [Pair] 类型
 * @param selectedKey 选中的 key
 * @param onSelectedKeyChange 当选中改变事件
 * @param sizeType 尺寸类型
 * @param colorType 颜色类型
 * @param cornerType 圆角类型
 * @param radioType 单选框组类型
 */
@JvmName("FlexRadioGroupWithPairOptions")
@Composable
fun FlexRadioGroup(
	options: List<Pair<String, String>>,
	selectedKey: String,
	onSelectedKeyChange: (String) -> Unit,
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
 * FlexRadioGroup 单选框组
 *
 * @param options 选项 [String] 列表
 * @param selectedKey 选中的 key
 * @param onSelectedKeyChange 当选中改变事件
 * @param sizeType 尺寸类型
 * @param colorType 颜色类型
 * @param cornerType 圆角类型
 * @param radioType 单选框组类型
 */
@JvmName("FlexRadioGroupWithStringOptions")
@Composable
fun FlexRadioGroup(
	options: List<String>,
	selectedKey: String,
	onSelectedKeyChange: (String) -> Unit,
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

internal val BorderColor = Color.Gray.copy(alpha = 0.3f)

internal val UnselectedFontColor
	@Composable
	get() = if (LocalDarkTheme.current) Color.LightGray else Color.DarkGray

internal val DisabledBackgroundColor = Color.Gray.copy(alpha = 0.15f)