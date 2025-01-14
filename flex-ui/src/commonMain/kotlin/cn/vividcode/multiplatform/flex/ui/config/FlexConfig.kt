package cn.vividcode.multiplatform.flex.ui.config

import cn.vividcode.multiplatform.flex.ui.config.button.FlexButtonSizeConfig
import cn.vividcode.multiplatform.flex.ui.config.theme.FlexThemeConfig

/**
 * Flex 配置
 */
fun flexConfig(config: (FlexConfig.() -> Unit)? = null) = FlexConfig().also {
	config?.invoke(it)
}

class FlexConfig internal constructor() {
	
	internal val theme = FlexThemeConfig()
	
	internal val button = FlexButtonSizeConfig()
	
	fun theme(config: FlexThemeConfig.() -> Unit) {
		this.theme.apply(config)
	}
	
	fun button(config: FlexButtonSizeConfig.() -> Unit) {
		this.button.apply(config)
	}
}