package cn.vividcode.multiplatform.flex.ui.config.type

import androidx.compose.runtime.Composable

/**
 * 默认类型配置
 */
class FlexComposeDefaultConfig internal constructor() {
	
	internal var common: FlexDefaultConfig? = null
	
	internal var button: FlexDefaultConfig? = null
	
	internal var radio: FlexDefaultConfig? = null
	
	internal var input: FlexDefaultConfig? = null
	
	internal var switch: FlexDefaultConfig? = null
	
	internal var slider: FlexDefaultConfig? = null
	
	fun common(config: FlexDefaultConfig.() -> Unit) {
		this.common = FlexDefaultConfig().apply(config)
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
	
	fun slider(config: FlexDefaultConfig.() -> Unit) {
		this.slider = FlexDefaultConfig(
			cornerType = FlexCornerType.Circle
		).apply(config)
	}
}

class FlexDefaultConfig internal constructor(
	var brushType: FlexBrushType? = null,
	var cornerType: FlexCornerType? = null,
	var sizeType: FlexSizeType? = null,
)

@Suppress("PropertyName")
abstract class FlexDefaults(
	private val defaultSizeType: FlexSizeType = FlexSizeType.Medium,
	private val defaultbrushType: FlexBrushType = FlexBrushType.Primary,
	private val defaultCornerType: FlexCornerType = FlexCornerType.Medium,
) {
	
	abstract val FlexComposeDefaultConfig.defaultConfig: FlexDefaultConfig?
	
	val DefaultSizeType: FlexSizeType
		@Composable
		get() = getDefaultSizeType(
			defaultSizeType = defaultSizeType,
			composeDefaultSizeType = { defaultConfig?.sizeType }
		)
	
	val DefaultbrushType: FlexBrushType
		@Composable
		get() = getDefaultbrushType(
			defaultbrushType = defaultbrushType,
			composeDefaultbrushType = { defaultConfig?.brushType }
		)
	
	val DefaultCornerType: FlexCornerType
		@Composable
		get() = getDefaultCornerType(
			defaultCornerType = defaultCornerType,
			composeDefaultCornerType = { defaultConfig?.cornerType }
		)
}