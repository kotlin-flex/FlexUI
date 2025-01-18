package cn.vividcode.multiplatform.flex.ui.config

import cn.vividcode.multiplatform.flex.ui.config.foundation.FlexButtonConfig
import cn.vividcode.multiplatform.flex.ui.config.foundation.FlexRadioConfig
import cn.vividcode.multiplatform.flex.ui.config.theme.FlexThemeConfig
import cn.vividcode.multiplatform.flex.ui.config.type.FlexDefaultTypeConfig

/**
 * Flex 配置
 */
fun flexConfig(config: (FlexConfig.() -> Unit)? = null) = FlexConfig().also {
	config?.invoke(it)
}

class FlexConfig internal constructor() {
	
	internal val theme = FlexThemeConfig()
	
	internal val defaultType = FlexDefaultTypeConfig.DefaultTypeConfig
	
	internal val button = FlexSizeConfig(FlexButtonConfig)
	
	internal val radio = FlexSizeConfig(FlexRadioConfig)
	
	/**
	 * 主题配置
	 */
	fun theme(config: FlexThemeConfig.() -> Unit) {
		this.theme.apply(config)
	}
	
	/**
	 * 默认类型配置
	 */
	fun defaultType(config: FlexDefaultTypeConfig.() -> Unit) {
		this.defaultType.apply(config)
	}
	
	/**
	 * 按钮配置
	 */
	fun button(config: FlexSizeConfig<FlexButtonConfig>.() -> Unit) {
		this.button.apply(config)
	}
	
	/**
	 * 单选框配置
	 */
	fun radio(config: FlexSizeConfig<FlexRadioConfig>.() -> Unit) {
		this.radio.apply(config)
	}
}