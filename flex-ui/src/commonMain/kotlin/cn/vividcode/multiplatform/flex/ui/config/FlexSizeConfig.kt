package cn.vividcode.multiplatform.flex.ui.config

import cn.vividcode.multiplatform.flex.ui.config.type.FlexSizeType
import cn.vividcode.multiplatform.flex.ui.config.type.FlexSizeType.*

/**
 * 尺寸配置
 */
class FlexSizeConfig<Config> internal constructor(
	config: FlexDefaultSizeConfig<Config>,
) {
	
	private val extraSmall = config.DefaultExtraSmall
	
	private val small = config.DefaultSmall
	
	private val medium = config.DefaultMedium
	
	private val large = config.DefaultLarge
	
	private val extraLarge = config.DefaultExtraLarge
	
	fun extraSmall(config: Config.() -> Unit) {
		this.extraSmall.apply(config)
	}
	
	fun small(config: Config.() -> Unit) {
		this.small.apply(config)
	}
	
	fun medium(config: Config.() -> Unit) {
		this.medium.apply(config)
	}
	
	fun large(config: Config.() -> Unit) {
		this.large.apply(config)
	}
	
	fun extraLarge(config: Config.() -> Unit) {
		this.extraLarge.apply(config)
	}
	
	internal fun getConfig(sizeType: FlexSizeType): Config = when (sizeType) {
		ExtraSmall -> this.extraSmall
		Small -> this.small
		Medium -> this.medium
		Large -> this.large
		ExtraLarge -> this.extraLarge
	}
}

@Suppress("PropertyName")
internal interface FlexDefaultSizeConfig<Config> {
	
	val DefaultExtraSmall: Config
	
	val DefaultSmall: Config
	
	val DefaultMedium: Config
	
	val DefaultLarge: Config
	
	val DefaultExtraLarge: Config
}