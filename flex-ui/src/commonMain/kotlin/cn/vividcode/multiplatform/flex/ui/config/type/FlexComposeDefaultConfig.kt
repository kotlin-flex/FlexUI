package cn.vividcode.multiplatform.flex.ui.config.type

/**
 * 默认类型配置
 */
class FlexComposeDefaultConfig internal constructor() {
	
	internal val common: FlexDefaultConfig = FlexDefaultConfig.DefaultConfig
	
	internal var button: FlexDefaultConfig = FlexDefaultConfig()
	
	internal var radio: FlexDefaultConfig = FlexDefaultConfig()
	
	internal var input: FlexDefaultConfig = FlexDefaultConfig()
	
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