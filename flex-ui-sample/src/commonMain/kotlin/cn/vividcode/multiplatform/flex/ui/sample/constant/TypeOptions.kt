package cn.vividcode.multiplatform.flex.ui.sample.constant

import androidx.compose.runtime.Composable
import cn.vividcode.multiplatform.flex.ui.config.type.FlexColorType
import cn.vividcode.multiplatform.flex.ui.config.type.FlexCornerType
import cn.vividcode.multiplatform.flex.ui.config.type.FlexSizeType
import cn.vividcode.multiplatform.flex.ui.foundation.radio.RadioOption

val sizeTypeOptions by lazy {
	FlexSizeType.entries.map { RadioOption(it, it.toString()) }
}

val cornerTypeOptions by lazy {
	FlexCornerType.entries.map { RadioOption(it, it.toString()) }
}

val colorTypeOptions: List<RadioOption<FlexColorType>>
	@Composable
	get() = FlexColorType.allColorTypes.map { RadioOption(it, it.toString()) }

val booleanOptions by lazy {
	listOf(true, false).map { RadioOption(it, it.toString()) }
}