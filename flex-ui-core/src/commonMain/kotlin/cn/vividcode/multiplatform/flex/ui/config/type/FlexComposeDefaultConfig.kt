package cn.vividcode.multiplatform.flex.ui.config.type

import androidx.compose.runtime.Composable

/**
 * 默认类型配置
 */
class FlexComposeDefaultConfig internal constructor() {
	
	internal val common: FlexDefaultConfig = FlexDefaultConfig.DefaultConfig
	
	internal var button: FlexDefaultConfig? = null
	
	internal var radio: FlexDefaultConfig? = null
	
	internal var input: FlexDefaultConfig? = null
	
	internal var switch: FlexDefaultConfig? = null
	
	fun common(config: FlexDefaultConfig.() -> Unit) {
		this.common.apply(config)
	}
	
	fun button(config: FlexDefaultConfig.() -> Unit) {
		this.button = (this.button ?: FlexDefaultConfig()).apply(config)
	}
	
	fun radio(config: FlexDefaultConfig.() -> Unit) {
		this.radio = (this.radio ?: FlexDefaultConfig()).apply(config)
	}
	
	fun input(config: FlexDefaultConfig.() -> Unit) {
		this.input = (this.input ?: FlexDefaultConfig()).apply(config)
	}
	
	fun switch(config: FlexDefaultConfig.() -> Unit) {
		this.switch = FlexDefaultConfig(
			cornerType = FlexCornerType.Circle
		).apply(config)
	}
}

class FlexDefaultConfig internal constructor(
	var colorType: FlexColorType? = null,
	var cornerType: FlexCornerType? = null,
	var sizeType: FlexSizeType? = null,
) {
	
	internal companion object {
		
		val DefaultConfig: FlexDefaultConfig
			get() = FlexDefaultConfig(
				colorType = FlexColorType.Primary,
				cornerType = FlexCornerType.Medium,
				sizeType = FlexSizeType.Medium
			)
	}
}

@Suppress("PropertyName")
abstract class FlexDefaults {
	
	internal abstract val FlexComposeDefaultConfig.defaultConfig: FlexDefaultConfig?
	
	internal val DefaultSizeType: FlexSizeType
		@Composable
		get() = getDefaultSizeType { defaultConfig?.sizeType }
	
	internal val DefaultColorType: FlexColorType
		@Composable
		get() = getDefaultColorType { defaultConfig?.colorType }
	
	internal val DefaultCornerType: FlexCornerType
		@Composable
		get() = getDefaultCornerType { defaultConfig?.cornerType }
}