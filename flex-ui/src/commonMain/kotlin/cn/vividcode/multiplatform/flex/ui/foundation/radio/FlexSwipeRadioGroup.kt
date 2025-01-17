package cn.vividcode.multiplatform.flex.ui.foundation.radio

import androidx.compose.runtime.Composable
import cn.vividcode.multiplatform.flex.ui.config.type.FlexColorType
import cn.vividcode.multiplatform.flex.ui.config.type.FlexCornerType
import cn.vividcode.multiplatform.flex.ui.config.type.FlexSizeType

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
 */
@Composable
internal fun FlexSwipeRadioGroup(
	options: List<RadioOption>,
	selectedKey: String,
	onSelectedKeyChange: (String) -> Unit,
	sizeType: FlexSizeType = FlexRadioGroups.DefaultSizeType,
	colorType: FlexColorType = FlexRadioGroups.DefaultColorType,
	cornerType: FlexCornerType = FlexRadioGroups.DefaultCornerType,
	radioType: FlexRadioType = FlexRadioGroups.DefaultRadioType,
) {

}