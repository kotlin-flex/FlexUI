package cn.vividcode.multiplatform.flex.ui.config.radio

import cn.vividcode.multiplatform.flex.ui.config.theme.FlexSizeType
import cn.vividcode.multiplatform.flex.ui.config.theme.FlexSizeType.*

/**
 * 按钮尺寸配置
 */
class FlexRadioSizeConfig {
	
	private val extraSmall = FlexRadioConfig.DefaultExtraSmallRadio
	
	private val small = FlexRadioConfig.DefaultSmallRadio
	
	private val medium = FlexRadioConfig.DefaultMediumRadio
	
	private val large = FlexRadioConfig.DefaultLargeRadio
	
	private val extraLarge = FlexRadioConfig.DefaultExtraLargeRadio
	
	fun extraSmall(config: FlexRadioConfig.() -> Unit) {
		this.extraSmall.apply(config)
	}
	
	fun small(config: FlexRadioConfig.() -> Unit) {
		this.small.apply(config)
	}
	
	fun medium(config: FlexRadioConfig.() -> Unit) {
		this.medium.apply(config)
	}
	
	fun large(config: FlexRadioConfig.() -> Unit) {
		this.large.apply(config)
	}
	
	fun extraLarge(config: FlexRadioConfig.() -> Unit) {
		this.extraLarge.apply(config)
	}
	
	internal fun getConfig(size: FlexSizeType) = when (size) {
		ExtraSmall -> this.extraSmall
		Small -> this.small
		Medium -> this.medium
		Large -> this.large
		ExtraLarge -> this.extraLarge
	}
}