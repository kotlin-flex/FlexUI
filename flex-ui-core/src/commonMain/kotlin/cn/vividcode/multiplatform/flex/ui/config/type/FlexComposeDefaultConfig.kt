package cn.vividcode.multiplatform.flex.ui.config.type

import androidx.compose.runtime.Composable

/**
 * 默认类型配置
 */
class FlexComposeDefaultConfig internal constructor() {
	
	internal val common: FlexDefaultConfig = FlexDefaultConfig.DefaultConfig
	
	internal var button: FlexDefaultConfig = FlexDefaultConfig()
	
	internal var radio: FlexDefaultConfig = FlexDefaultConfig()
	
	internal var input: FlexDefaultConfig = FlexDefaultConfig()
	
	internal var switch: FlexDefaultConfig = FlexDefaultConfig(
		colorType = FlexColorType.Primary,
		cornerType = FlexCornerType.Circle
	)
	
	fun common(config: FlexDefaultConfig.() -> Unit) {
		this.common.apply(config)
	}
	
	fun button(config: FlexDefaultConfig.() -> Unit) {
		this.button.apply(config)
	}
	
	fun radio(config: FlexDefaultConfig.() -> Unit) {
		this.radio.apply(config)
	}
	
	fun input(config: FlexDefaultConfig.() -> Unit) {
		this.input.apply(config)
	}
	
	fun switch(config: FlexDefaultConfig.() -> Unit) {
		this.switch.apply(config)
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
				colorType = FlexColorType.Default,
				cornerType = FlexCornerType.Default,
				sizeType = FlexSizeType.Medium
			)
	}
}

@Suppress("PropertyName")
abstract class FlexDefaults {
	
	internal abstract val FlexComposeDefaultConfig.defaultConfig: FlexDefaultConfig
	
	internal val DefaultSizeType: FlexSizeType
		@Composable
		get() = getDefaultSizeType { defaultConfig.sizeType }
	
	internal val DefaultColorType: FlexColorType
		@Composable
		get() = getDefaultColorType { defaultConfig.colorType }
	
	internal val DefaultCornerType: FlexCornerType
		@Composable
		get() = getDefaultCornerType { defaultConfig.cornerType }
}