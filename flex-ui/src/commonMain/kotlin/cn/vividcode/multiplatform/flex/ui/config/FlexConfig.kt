package cn.vividcode.multiplatform.flex.ui.config

import cn.vividcode.multiplatform.flex.ui.config.foundation.FlexButtonConfig
import cn.vividcode.multiplatform.flex.ui.config.foundation.FlexRadioConfig
import cn.vividcode.multiplatform.flex.ui.config.theme.FlexThemeConfig

/**
 * Flex 配置
 */
fun flexConfig(config: (FlexConfig.() -> Unit)? = null) = FlexConfig().also {
	config?.invoke(it)
}

class FlexConfig internal constructor() {
	
	internal val theme = FlexThemeConfig()
	
	internal val button = FlexSizeConfig(FlexButtonConfig)
	
	internal val radio = FlexSizeConfig(FlexRadioConfig)
	
	fun theme(config: FlexThemeConfig.() -> Unit) {
		this.theme.apply(config)
	}
	
	fun button(config: FlexSizeConfig<FlexButtonConfig>.() -> Unit) {
		this.button.apply(config)
	}
	
	fun radio(config: FlexSizeConfig<FlexRadioConfig>.() -> Unit) {
		this.radio.apply(config)
	}
}