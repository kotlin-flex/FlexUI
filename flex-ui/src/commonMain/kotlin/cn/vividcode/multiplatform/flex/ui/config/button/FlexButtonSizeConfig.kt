package cn.vividcode.multiplatform.flex.ui.config.button

import cn.vividcode.multiplatform.flex.ui.config.theme.FlexSizeType
import cn.vividcode.multiplatform.flex.ui.config.theme.FlexSizeType.*

class FlexButtonSizeConfig {
	
	internal val small = FlexButtonConfig.smallButton
	
	internal val medium = FlexButtonConfig.mediumButton
	
	internal val large = FlexButtonConfig.largeButton
	
	fun small(config: FlexButtonConfig.() -> Unit) {
		this.small.apply(config)
	}
	
	fun medium(config: FlexButtonConfig.() -> Unit) {
		this.medium.apply(config)
	}
	
	fun large(config: FlexButtonConfig.() -> Unit) {
		this.large.apply(config)
	}
	
	fun getButton(size: FlexSizeType) = when (size) {
		Small -> this.small
		Medium -> this.medium
		Large -> this.large
	}
}