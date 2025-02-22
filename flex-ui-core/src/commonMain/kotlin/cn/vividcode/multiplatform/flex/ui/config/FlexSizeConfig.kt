package cn.vividcode.multiplatform.flex.ui.config

import cn.vividcode.multiplatform.flex.ui.type.FlexSizeType
import cn.vividcode.multiplatform.flex.ui.type.FlexSizeType.*

/**
 * 尺寸配置
 */
class FlexSizeConfig<Config> internal constructor(
	sizeDefaults: FlexSizeDefaults<Config>,
) {
	
	private val extraSmall = sizeDefaults.DefaultExtraSmall
	
	private val small = sizeDefaults.DefaultSmall
	
	private val medium = sizeDefaults.DefaultMedium
	
	private val large = sizeDefaults.DefaultLarge
	
	private val extraLarge = sizeDefaults.DefaultExtraLarge
	
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
internal interface FlexSizeDefaults<Config> {
	
	val DefaultExtraSmall: Config
	
	val DefaultSmall: Config
	
	val DefaultMedium: Config
	
	val DefaultLarge: Config
	
	val DefaultExtraLarge: Config
}