package cn.vividcode.multiplatform.flex.ui.config.button

import cn.vividcode.multiplatform.flex.ui.config.theme.FlexSizeType
import cn.vividcode.multiplatform.flex.ui.config.theme.FlexSizeType.*

class FlexButtonSizeConfig {
	
	private val mini = FlexButtonConfig.DefaultMiniButton
	
	private val small = FlexButtonConfig.DefaultSmallButton
	
	private val medium = FlexButtonConfig.DefaultMediumButton
	
	private val large = FlexButtonConfig.DefaultLargeButton
	
	fun mini(config: FlexButtonConfig.() -> Unit) {
		this.mini.apply(config)
	}
	
	fun small(config: FlexButtonConfig.() -> Unit) {
		this.small.apply(config)
	}
	
	fun medium(config: FlexButtonConfig.() -> Unit) {
		this.medium.apply(config)
	}
	
	fun large(config: FlexButtonConfig.() -> Unit) {
		this.large.apply(config)
	}
	
	fun getButtonConfig(size: FlexSizeType) = when (size) {
		Mini -> this.mini
		Small -> this.small
		Medium -> this.medium
		Large -> this.large
	}
}