package cn.vividcode.multiplatform.flex.ui.config.button

import cn.vividcode.multiplatform.flex.ui.config.theme.FlexSizeType
import cn.vividcode.multiplatform.flex.ui.config.theme.FlexSizeType.*

/**
 * 按钮尺寸配置
 */
class FlexButtonSizeConfig {
	
	private val extraSmall = FlexButtonConfig.DefaultExtraSmallButton
	
	private val small = FlexButtonConfig.DefaultSmallButton
	
	private val medium = FlexButtonConfig.DefaultMediumButton
	
	private val large = FlexButtonConfig.DefaultLargeButton
	
	private val extraLarge = FlexButtonConfig.DefaultExtraLargeButton
	
	fun extraSmall(config: FlexButtonConfig.() -> Unit) {
		this.extraSmall.apply(config)
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
	
	fun extraLarge(config: FlexButtonConfig.() -> Unit) {
		this.extraLarge.apply(config)
	}
	
	internal fun getConfigBySize(size: FlexSizeType) = when (size) {
		ExtraSmall -> this.extraSmall
		Small -> this.small
		Medium -> this.medium
		Large -> this.large
		ExtraLarge -> this.extraLarge
	}
}