package cn.vividcode.multiplatform.flex.ui.sample.constant

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

val colorTypeOptions by lazy {
	FlexColorType.entries.map { RadioOption(it, it.toString()) }
}

val booleanOptions by lazy {
	arrayOf(true, false).map { RadioOption(it, it.toString()) }
}