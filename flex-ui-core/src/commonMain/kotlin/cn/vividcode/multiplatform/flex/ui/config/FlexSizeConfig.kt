package cn.vividcode.multiplatform.flex.ui.config

import cn.vividcode.multiplatform.flex.ui.type.FlexSizeType
import cn.vividcode.multiplatform.flex.ui.type.FlexSizeType.ExtraLarge
import cn.vividcode.multiplatform.flex.ui.type.FlexSizeType.ExtraSmall
import cn.vividcode.multiplatform.flex.ui.type.FlexSizeType.Large
import cn.vividcode.multiplatform.flex.ui.type.FlexSizeType.Medium
import cn.vividcode.multiplatform.flex.ui.type.FlexSizeType.Small

/**
 * 尺寸配置
 */
class FlexSizeConfig<Config> internal constructor(
	sizeDefaults: FlexSizeDefaults<Config>,
) {
	
	private val extraSmall by lazy { sizeDefaults.DefaultExtraSmall }
	
	private val small by lazy { sizeDefaults.DefaultSmall }
	
	private val medium by lazy { sizeDefaults.DefaultMedium }
	
	private val large by lazy { sizeDefaults.DefaultLarge }
	
	private val extraLarge by lazy { sizeDefaults.DefaultExtraLarge }
	
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
internal abstract class FlexSizeDefaults<Config> {
	
	private companion object {
		private const val EXTRA_SMALL_SCALE = 0.7f
		private const val SMALL_SCALE = 0.85f
		private const val LARGE_SCALE = 1.15f
		private const val EXTRA_LARGE_SCALE = 1.3f
	}
	
	val DefaultExtraSmall: Config by lazy { DefaultMedium.scale(EXTRA_SMALL_SCALE) }
	
	val DefaultSmall: Config by lazy { DefaultMedium.scale(SMALL_SCALE) }
	
	abstract val DefaultMedium: Config
	
	val DefaultLarge: Config by lazy { DefaultMedium.scale(LARGE_SCALE) }
	
	val DefaultExtraLarge: Config by lazy { DefaultMedium.scale(EXTRA_LARGE_SCALE) }
	
	abstract fun Config.scale(scale: Float): Config
}